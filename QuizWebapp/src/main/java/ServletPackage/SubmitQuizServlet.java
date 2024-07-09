package ServletPackage;

import DBpackage.DAOpackage.QuizDAO;
import DBpackage.DAOpackage.UserDAO;
import DBpackage.DatabaseAccess;
import DBpackage.Questions.*;
import DBpackage.Quiz;
import DBpackage.Score;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        Quiz quiz = QuizDAO.getQuizInfo(quizId);
        ArrayList<Question> questions = QuizDAO.getQuizQuestions(quizId);

        String questionOrderJson = request.getParameter("questionOrder");

        int[] questionOrder = parseQuestionOrder(questionOrderJson);

        int userScore = 0;
        int maxScore = 0;

        assert questionOrder != null;
        for (int originalIndex : questionOrder) {
            Question question = questions.get(originalIndex);
            String[] answers;

            if (question instanceof QuestionMultiAnswer || question instanceof QuestionCheckbox) {
                List<String> multiAnswers = new ArrayList<>();
                int j = 0;
                String answer;
                while ((answer = request.getParameter("answer[" + originalIndex + "][" + j + "]")) != null) {
                    multiAnswers.add(answer);
                    j++;
                }
                answers = multiAnswers.toArray(new String[0]);
            } else {
                answers = request.getParameterValues("answer[" + originalIndex + "]");
            }

            int[] result = checkAnswer(question, answers);
            int markReceived = result[0];
            int maxMark = result[1];

            userScore += markReceived;
            maxScore += maxMark;
        }

        HttpSession session = request.getSession();
        long startTimeMillis = (long) session.getAttribute("quizStartTime");
        long endTimeMillis = System.currentTimeMillis();
        System.out.println(endTimeMillis);
        long timeTakenSeconds = (endTimeMillis - startTimeMillis) / 1000;
        int userId = (Integer) session.getAttribute("userID");
        java.util.Date now = new java.util.Date();
        java.sql.Timestamp currDate = new java.sql.Timestamp(now.getTime());
        int practice = (int) session.getAttribute("practice");
        boolean practiceMode = (practice == 1);

        int percentageScore = (int) Math.round((double) userScore / maxScore * 100);
        UserDAO.quizFinished(userId, quizId, percentageScore,currDate, (int) timeTakenSeconds, practiceMode, 10);
        session.setAttribute("timespan", timeTakenSeconds);
        session.setAttribute("quizId", quizId);
        session.setAttribute("score", percentageScore);
        response.sendRedirect("QuizResultPage.jsp?score="+percentageScore+"&quizId="+quizId);
    }

    public static int[] checkAnswer(Question question, String[] userAnswers) {
        int[] result;
        switch (question.getType()) {
            case DatabaseAccess.QUESTION_TEXTBOX:
            case DatabaseAccess.QUESTION_FILL_BLANK:
            case DatabaseAccess.QUESTION_PICTURE:
                result = checkTextAnswer(question, userAnswers[0]);
                break;
            case DatabaseAccess.QUESTION_MULTIPLE_CHOICE:
                result = checkMultipleChoiceAnswer(question, userAnswers[0]);
                break;
            case DatabaseAccess.QUESTION_MULTIANSWER:
                result = checkMultiAnswerQuestion(question, userAnswers);
                break;
            case DatabaseAccess.QUESTION_CHECKBOX:
                result = checkCheckboxAnswer(question, userAnswers);
                break;
            case DatabaseAccess.QUESTION_MATCHING:
                result = checkMatchingAnswer(question, userAnswers[0]);
                break;
            default:
                result = new int[]{0, 0};
        }
        return result;
    }

    private static int[] checkMatchingAnswer(Question question, String matchingJson) {
        System.out.println("Received matchingJson: " + matchingJson);

        QuestionMatching matchingQuestion = (QuestionMatching) question;
        ArrayList<String> words = matchingQuestion.getWords();
        ArrayList<String> matchingWords = matchingQuestion.getMatchingWords();
        int maxScore = words.size();
        int correctMatches = 0;

        if (matchingJson == null || matchingJson.isEmpty()) {
            System.out.println("Received empty or null matchingJson");
            return new int[]{0, maxScore};
        }

        // Remove outer brackets, quotes, and backslashes
        matchingJson = matchingJson.replaceAll("^\\[|\\]$", "").replaceAll("\\\\", "");

        StringTokenizer tokenizer = new StringTokenizer(matchingJson, "{}:,\"");

        String left = null;
        String right = null;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.equals("left")) {
                if (tokenizer.hasMoreTokens()) {
                    left = tokenizer.nextToken().trim();
                }
            } else if (token.equals("right")) {
                if (tokenizer.hasMoreTokens()) {
                    right = tokenizer.nextToken().trim();
                }

                // We have both left and right, so we can check the match
                if (left != null && right != null) {
                    int leftIndex = words.indexOf(left);
                    int rightIndex = matchingWords.indexOf(right);

                    if (leftIndex != -1 && rightIndex != -1 && leftIndex == rightIndex) {
                        correctMatches++;
                    }

                    // Reset left and right for the next pair
                    left = null;
                    right = null;
                }
            }
        }

        System.out.println("Correct matches: " + correctMatches + " out of " + maxScore);
        return new int[]{correctMatches, maxScore};
    }

    private static int[] checkCheckboxAnswer(Question question, String[] userAnswers) {
        QuestionCheckbox q = (QuestionCheckbox) question;
        ArrayList<Integer> correctList = q.getCorrectList();
        Set<Integer> set = new HashSet<>(correctList);
        int maxScore = 0;
        int userScore = 0;
        for (int i=0; i<correctList.size(); i++) {
            if(correctList.get(i) == 1) {
                maxScore++;
                set.add(i);
            }
        }

        ArrayList<Integer> userSelectedAnswers = new ArrayList<>();
        for (String answer : userAnswers) {
            userSelectedAnswers.add(Integer.parseInt(answer));
        }

        for(Integer i : userSelectedAnswers) {
            if(set.contains(i)) {
                set.remove(i);
                userScore++;
            }
        }

        return new int[]{userScore, maxScore};
    }

    private static int[] checkMultiAnswerQuestion(Question question, String[] userAnswers) {
        QuestionMultiAnswer q = (QuestionMultiAnswer) question;
        int maxScore = q.getAnswerList().size();
        ArrayList<String> correctAnswers = q.getAnswerList();
        int correctCount = 0;

        // Create a set of user answers to remove duplicates
        Set<String> uniqueUserAnswers = new HashSet<>();
        for (String answer : userAnswers) {
            uniqueUserAnswers.add(answer.toLowerCase().trim());
        }

        // Check each unique user answer against the correct answers
        for (String userAnswer : uniqueUserAnswers) {
            for (String correctAnswer : correctAnswers) {
                if (userAnswer.equalsIgnoreCase(correctAnswer.trim())) {
                    correctCount++;
                    break;  // Move to the next user answer
                }
            }
        }

        return new int[]{correctCount, maxScore};
    }


    private static int[] checkMultipleChoiceAnswer(Question question, String userAnswer) {
        int maxScore = 1;
        int selectedIndex = Integer.parseInt(userAnswer);
        userAnswer = ((QuestionMultipleChoice)question).getAnswerList().get(selectedIndex);
        if (userAnswer.trim().equalsIgnoreCase(((QuestionMultipleChoice)question).getCorrectAnswer())) {
            return new int[]{maxScore, maxScore};
        }
        return new int[]{0, maxScore};
    }

    private static int[] checkTextAnswer(Question question, String userAnswer) {
        int maxScore = 1;
        String answer = "";
        switch(question.getType()) {
            case DatabaseAccess.QUESTION_TEXTBOX:
                answer = ((QuestionTextbox)question).getAnswer();
                break;
            case DatabaseAccess.QUESTION_FILL_BLANK:
                answer = ((QuestionFillBlank)question).getAnswer();
                break;
            case DatabaseAccess.QUESTION_PICTURE:
                answer = ((QuestionPicture)question).getAnswer();
                break;
        }
        if ((userAnswer.trim()).equalsIgnoreCase(answer)) {
            return new int[]{maxScore, maxScore};
        }
        return new int[]{0, maxScore};
    }

    private int[] parseQuestionOrder(String questionOrderJson) {
        if (questionOrderJson == null || questionOrderJson.isEmpty()) {
            System.out.println("QuestionOrderJson is either null or empty!!!");
            return null;
        }
        // Remove brackets from the JSON array string
                questionOrderJson = questionOrderJson.substring(1, questionOrderJson.length() - 1);
        // Split the string by commas
        String[] orderStrings = questionOrderJson.split(",");
        int[] questionOrder = new int[orderStrings.length];
        for (int i = 0; i < orderStrings.length; i++) {
            questionOrder[i] = Integer.parseInt(orderStrings[i].trim());
        }
        return questionOrder;
    }
}
