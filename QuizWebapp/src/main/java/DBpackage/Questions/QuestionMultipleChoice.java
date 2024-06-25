package DBpackage.Questions;

import DBpackage.DatabaseInfo;

import java.sql.*;
import java.util.ArrayList;

public class QuestionMultipleChoice extends Question{
    private String question;
    private String correctAnswer;
    private ArrayList<String> answerList;
    public QuestionMultipleChoice(int type, int quizID, int questionNumber, String question, String correctAnswer) {
        super(type, quizID, questionNumber);
        this.question = question;
        this.correctAnswer = correctAnswer;
        initAnswerList();
    }
    private void initAnswerList() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");


        Connection con = DriverManager.getConnection("jdbc:mysql://" +
                        DatabaseInfo.server + "/" + DatabaseInfo.database,
                DatabaseInfo.username, DatabaseInfo.password);

        Statement stmt = con.createStatement();
        int quizID = this.getQuizID();
        int questionOrder = this.getQuestionNumber();
        String query = "select * from Multiple_choice_answers where quiz_id = '" + quizID +
                "' and sub_id = '" + questionOrder + "';";

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                answerList.add();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getAnswerList(){
        return answerList;
    }
    public String getQuestion(){
        return question;
    }
    public String getCorrectAnswer(){
        return correctAnswer;
    }
}
