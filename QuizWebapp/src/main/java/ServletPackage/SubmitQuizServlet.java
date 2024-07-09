package ServletPackage;

import DBpackage.DAOpackage.QuizDAO;
import DBpackage.DatabaseAccess;
import DBpackage.Questions.*;
import DBpackage.Quiz;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static DBpackage.Questions.QuestionTypeConstants.*;
import static DBpackage.Questions.QuestionTypeConstants.QUESTION_TEXTBOX;

@WebServlet("/SubmitQuizServlet")
public class SubmitQuizServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        Quiz quiz = QuizDAO.getQuizInfo(quizId);
        ArrayList<Question> questions = QuizDAO.getQuizQuestions(quizId);

        String questionOrderJson = request.getParameter("questionOrder");
        System.out.println(questionOrderJson);
        int[] questionOrder = parseQuestionOrder(questionOrderJson);

        int userScore = 0;
        int maxScore = 0;

        assert questionOrder != null;
        for (int originalIndex : questionOrder) {
            Question question = questions.get(originalIndex);
            String[] answers = request.getParameterValues("answer["+originalIndex+"]");

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

        int percentageScore = (int) Math.round((double) userScore / maxScore * 100);
        System.out.println(percentageScore);
        response.sendRedirect("QuizResultPage.jsp?score="+percentageScore);
    }

    private int[] checkAnswer(Question question, String[] userAnswers) {
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

    private int[] checkMatchingAnswer(Question question, String matchingJson) {

        // Parse the JSON string of matchings manually
        matchingJson = matchingJson.substring(1, matchingJson.length() - 1); // Remove outer brackets
        String[] pairs = matchingJson.split("\\},\\{");
        QuestionMatching matchingQuestion = (QuestionMatching) question;
        ArrayList<String> words = matchingQuestion.getWords();
        ArrayList<String> matchingWords = matchingQuestion.getMatchingWords();
        int maxScore = words.size();

        int correctMatches = 0;
        for (String pair : pairs) {
            pair = pair.replaceAll("[{}\"]", ""); // Remove brackets and quotes
            String[] keyValue = pair.split(",");
            String left = keyValue[0].split(":")[1].trim();
            String right = keyValue[1].split(":")[1].trim();
            int leftIndex = words.indexOf(left);
            int rightIndex = matchingWords.indexOf(right);
            if (leftIndex == rightIndex) {
                correctMatches++;
            }
        }

        return new int[]{correctMatches, maxScore};
    }

    private int[] checkCheckboxAnswer(Question question, String[] userAnswers) {
        QuestionCheckbox q = (QuestionCheckbox) question;
        ArrayList<Integer> correctList = q.getCorrectList();
        int maxScore = 0;
        int userScore = 0;
        for(Integer i : correctList) {
            if(i == 1) maxScore++;
        }

        ArrayList<Integer> userSelectedAnswers = new ArrayList<>();
        for (String answer : userAnswers) {
            userSelectedAnswers.add(Integer.parseInt(answer));
        }

        Set<Integer> set = new HashSet<>(correctList);
        for(Integer i : userSelectedAnswers) {
            if(set.contains(i)) {
                set.remove(i);
                userScore++;
            }
        }

        return new int[]{userScore, maxScore};
    }

    private int[] checkMultiAnswerQuestion(Question question, String[] userAnswers) {
        QuestionMultiAnswer q = (QuestionMultiAnswer) question;
        int maxScore = q.getAnswerList().size();
        ArrayList<String> correctAnswers = q.getAnswerList();
        int correctCount = 0;
        System.out.println(Arrays.toString(userAnswers));
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


    private int[] checkMultipleChoiceAnswer(Question question, String userAnswer) {
        int maxScore = 1;
        int selectedIndex = Integer.parseInt(userAnswer);
        userAnswer = ((QuestionMultipleChoice)question).getAnswerList().get(selectedIndex);
        if (userAnswer.trim().equalsIgnoreCase(((QuestionMultipleChoice)question).getCorrectAnswer())) {
            return new int[]{maxScore, maxScore};
        }
        return new int[]{0, maxScore};
    }

    private int[] checkTextAnswer(Question question, String userAnswer) {
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
