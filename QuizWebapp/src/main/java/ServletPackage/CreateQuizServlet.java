package ServletPackage;

import DBpackage.DatabaseAccess;
import DBpackage.Questions.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");

        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line=reader.readLine()) != null) {
            sb.append(line);
        }

        String jsonString = sb.toString();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        String quizName = jsonObject.get("title").getAsString();
        String quizDescription = jsonObject.get("description").getAsString();
        int creatorId = (Integer) request.getSession().getAttribute("userID");
        int randomQuestion = 0;
        if(jsonObject.get("randomOrder").getAsBoolean()) randomQuestion = 1;
        int onePage = 0;
        if(jsonObject.get("onePage").getAsBoolean()) onePage = 1;
        int immediate = 0;
        if(jsonObject.get("immediateCorrection").getAsBoolean()) immediate=1;
        int practice = 0;
        if(jsonObject.get("practice").getAsBoolean()) practice=1;
        java.util.Date now = new java.util.Date();
        java.sql.Timestamp currDate = new java.sql.Timestamp(now.getTime());

        int quizId = dbAccess.createQuizAndGetID(quizName, quizDescription, creatorId, "", randomQuestion, immediate, practice, onePage, currDate);

        JsonArray questionsArray = jsonObject.getAsJsonArray("questions");
        ArrayList<Question> questions = new ArrayList<>();

        for(int i=0; i<questionsArray.size(); i++) {

            JsonObject questionJson = questionsArray.get(i).getAsJsonObject();

            System.out.println("Processing question " + i);
            System.out.println("Question JSON: " + questionJson);

            String type = questionJson.get("type").getAsString();
            Question question;

            switch (type) {
                case "questionResponse":
                    question = new QuestionTextbox();
                    ((QuestionTextbox) question).setQuestion(questionJson.get("question").getAsString());
                    ((QuestionTextbox) question).setAnswer(questionJson.get("answer").getAsString());
                    question.setType(DatabaseAccess.QUESTION_TEXTBOX);
                    break;

                case "fillBlank":
                    question = new QuestionFillBlank();
                    ((QuestionFillBlank) question).setTextBefore(questionJson.get("before").getAsString());
                    ((QuestionFillBlank) question).setTextAfter(questionJson.get("after").getAsString());
                    ((QuestionFillBlank) question).setAnswer(questionJson.get("answer").getAsString());
                    question.setType(DatabaseAccess.QUESTION_FILL_BLANK);
                    break;

                case "multipleChoice":
                    question = new QuestionMultipleChoice();
                    ((QuestionMultipleChoice)question).setQuestion(questionJson.get("question").getAsString());
                    JsonArray answersArray = questionJson.getAsJsonArray("answers");
                    ArrayList<String> answers = new ArrayList<>();
                    for (int j = 0; j < answersArray.size(); j++) {
                        JsonObject answerJson = answersArray.get(j).getAsJsonObject();
                        String answer = answerJson.get("text").getAsString();
                        if(answerJson.get("isCorrect").getAsBoolean()) ((QuestionMultipleChoice)question).setCorrectAnswer(answer);
                        answers.add(answer);
                    }
                    ((QuestionMultipleChoice) question).setAnswerList(answers);
                    question.setType(DatabaseAccess.QUESTION_MULTIPLE_CHOICE);
                    break;

                case "pictureResponse":
                    question = new QuestionPicture();
                    ((QuestionPicture)question).setQuestion(questionJson.get("question").getAsString());
                    ((QuestionPicture) question).setImageURL(questionJson.get("image").getAsString());
                    ((QuestionPicture) question).setAnswer(questionJson.get("answer").getAsString());
                    question.setType(DatabaseAccess.QUESTION_PICTURE);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown question type: " + type);
            }
            question.setQuizID(quizId);
            question.setSubID(i);
            questions.add(question);

            System.out.println(question);
        }



        dbAccess.populateQuiz(questions);
    }
}