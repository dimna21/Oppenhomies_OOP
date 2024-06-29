package DBpackage.Questions;

import DBpackage.DatabaseInfo;

import java.sql.*;
import java.util.ArrayList;

public class QuestionMultiFillBlank extends Question{
    private String textBefore;
    private String textAfter;
    private ArrayList<String> answerList;
    public QuestionMultiFillBlank(int questionID, int quizID,int subID, int type, String textBefore, String textAfter) {
        super(questionID, quizID, subID, type);
        this.textBefore = textBefore;
        this.textAfter = textAfter;
    }
    public String getTextBefore() {
        return textBefore;
    }

    public String getTextAfter() {
        return textAfter;
    }

    public ArrayList<String> getAnswerList() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://" +
                        DatabaseInfo.server + "/" + DatabaseInfo.database,
                DatabaseInfo.username, DatabaseInfo.password);

        Statement stmt = con.createStatement();
        int quizID = this.getQuizID();
        int questionOrder = this.getQuestionNumber();
        String query = "SELECT answer FROM Multi_fill_Blank_answers WHERE multifill_id = Multi_fill_Blank_answers.question_id;";

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String answer = resultSet.getString("answer");
                answerList.add(answer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) stmt.close();
        }
        return answerList;
    }

}
