package ServletPackage;

import DBpackage.DAOpackage.QuizDAO;
import DBpackage.DatabaseAccess;
import DBpackage.Questions.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CheckAnswerServlet")
public class CheckAnswerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        int questionIndex = Integer.parseInt(request.getParameter("questionIndex"));
        String answerJson = request.getParameter("answer");
        System.out.println(answerJson);
        String questionType = request.getParameter("questionType");

        ArrayList<Question> questions = QuizDAO.getQuizQuestions(quizId);
        Question question = questions.get(questionIndex);

        String[] userAnswers = parseAnswer(answerJson, questionType);

        int[] result = SubmitQuizServlet.checkAnswer(question, userAnswers);
        int markReceived = result[0];
        int maxMark = result[1];

        String feedback = String.format("You scored %d out of %d points.", markReceived, maxMark);
        response.getWriter().write(feedback);
    }

    private String[] parseAnswer(String answerJson, String questionType) {
        if (answerJson == null || answerJson.isEmpty()) {
            return new String[0];
        }

        int type = Integer.parseInt(questionType);

        switch (type) {
            case DatabaseAccess.QUESTION_TEXTBOX:
            case DatabaseAccess.QUESTION_FILL_BLANK:
            case DatabaseAccess.QUESTION_PICTURE:
            case DatabaseAccess.QUESTION_MULTIPLE_CHOICE:
                // For single answer questions, just return the JSON string as is
                return new String[]{answerJson.replaceAll("\"", "")};

            case DatabaseAccess.QUESTION_MULTIANSWER:
            case DatabaseAccess.QUESTION_CHECKBOX:
                // For multi-answer questions, parse the JSON array
                try {
                    // Remove brackets and split by comma
                    String[] answers = answerJson.replaceAll("[\\[\\]\"]", "").split(",");
                    // Trim each answer
                    for (int i = 0; i < answers.length; i++) {
                        answers[i] = answers[i].trim();
                    }
                    return answers;
                } catch (Exception e) {
                    System.err.println("Error parsing multi-answer JSON: " + e.getMessage());
                    return new String[0];
                }

            case DatabaseAccess.QUESTION_MATCHING:
                // For matching questions, return the JSON string as is
                // It will be parsed in the checkMatchingAnswer method
                return new String[]{answerJson};

            default:
                System.err.println("Unknown question type: " + type);
                return new String[0];
        }
    }
}