package DBpackage;
import DBpackage.Questions.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class DatabaseAccess {
    private Statement stmt;
    private Connection con;

    public DatabaseAccess() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");


        con = DriverManager.getConnection("jdbc:mysql://" +
                        DatabaseInfo.server + "/" + DatabaseInfo.database,
                DatabaseInfo.username, DatabaseInfo.password);

        stmt = con.createStatement();
    }
    public boolean login(String name, String hashcode) {
        //ArrayList<ProductInfo> students = new ArrayList<>();
        String query = "select * from users where username = '" + name +
                "' and password = '" + hashcode + "';";

        int total=0;
        try {

            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                total++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total == 1;
    }
    public User getUserInfo(String name){
        String query = "select * from users where username = '" + name +"' ;";
        User user = null;
        try {

            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                user =  new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("admin_status"),
                        resultSet.getInt("quizzes_taken"),
                        resultSet.getInt("quizzes_created"),
                        resultSet.getInt("highest_scorer"),
                        resultSet.getInt("practice_mode"),
                        resultSet.getString("profile_pic_url")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public boolean addUser(String username, String Hashcode){
        if(getUserInfo(username)!=null)return false;
        String query = "INSERT INTO Users ( username, password, admin_status, quizzes_taken, quizzes_created, highest_scorer, practice_mode, profile_pic_url) VALUES" +
                "( '" + username+ "', ' "+Hashcode +"', 1, 10, 5, 1, 0, 'http://example.com/images/john.jpg')";

        try {

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public boolean updatePicture(String username, String pic){
        if(getUserInfo(username)==null)return false;
        String query = "UPDATE Users SET profile_pic_url = '" + pic+
                "' WHERE username = '"+username +"';";

        try {

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public ArrayList<FriendRequest> friendRequests(String currentUser){
        ArrayList<FriendRequest> ls= new ArrayList<>();
        if(getUserInfo(currentUser)==null)return null;
        int userID = getUserInfo(currentUser).getUser_id();
        String query = "select * from friend_requests where from_id = '" + userID
        + "';";

        try {
            FriendRequest fr;
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                fr =  new FriendRequest(
                        resultSet.getInt("request_id"),
                        resultSet.getInt("from_id"),
                        resultSet.getInt("to_id"),
                        resultSet.getInt("notification")

                );
                ls.add(fr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ls;
    }
    public ArrayList<Challenge> challenges(String currentUser){
        ArrayList<Challenge> ls= new ArrayList<>();
        if(getUserInfo(currentUser)==null)return null;
        int userID = getUserInfo(currentUser).getUser_id();
        String query = "select * from friend_request where from_id = '" + userID
                + "';";

        try {
            Challenge ch;
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                ch =  new Challenge(
                        resultSet.getInt("request_id"),
                        resultSet.getInt("from_id"),
                        resultSet.getInt("to_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("notification")


                );
                ls.add(ch);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ls;
    }




    public Quiz getQuizInfo(int quiz_id){
        String query = "select * from quizzes where quiz_id = '" + quiz_id +"' ;";
        Quiz q = null;
        try {

            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                q =  new Quiz(
                        resultSet.getInt("quiz_id"),
                        resultSet.getString("quiz_name"),
                        resultSet.getString("quiz_description"),
                        resultSet.getInt("quiz_creator_id"),
                        resultSet.getInt("random_question"),
                        resultSet.getInt("one_page"),
                        resultSet.getInt("immediate"),
                        resultSet.getInt("practice"),
                        resultSet.getDate("creation_date"),
                        resultSet.getInt("times_taken")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return q;
    }




    public ArrayList<Quiz> getNewestQuiz(int amountToGet){
        ArrayList<Quiz> ls= new ArrayList<>();
        String query;
        if(amountToGet>0){
            query = "SELECT * FROM quizzes ORDER BY creation_date desc LIMIT " + amountToGet
                    + ";";
        }else{
            query = "SELECT * FROM quizzes ORDER BY creation_date desc;";
        }


        try {
            Quiz q;
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                q =  new Quiz(
                        resultSet.getInt("quiz_id"),
                        resultSet.getString("quiz_name"),
                        resultSet.getString("quiz_description"),
                        resultSet.getInt("quiz_creator_id"),
                        resultSet.getInt("random_question"),
                        resultSet.getInt("one_page"),
                        resultSet.getInt("immediate"),
                        resultSet.getInt("practice"),
                        resultSet.getDate("creation_date"),
                        resultSet.getInt("times_taken")
                );
                ls.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ls;
    }
    public ArrayList<Question> getQuizQuestions(int quizId){
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Question> qList1 = new ArrayList<>();
        String query;
        query = "select * from quiz_questions where quiz_id = " + quizId + " order by sub_id ASC;";

        try {
            Question q;
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                q =  new Question(
                        resultSet.getInt("question_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("sub_id"),
                        resultSet.getInt("type")

                );
                qList1.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(Question q : qList1){

        }
        return questions;

    }

    public ArrayList<Score> getScoresForUserAndQuiz(int userId, int quizId) throws SQLException {
        ArrayList<Score> scores = new ArrayList<>();

        String query = "SELECT * FROM scores " +
                "WHERE user_id = " + userId + " quiz_id = " + quizId +
                "ORDER BY date_scored DESC";

        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()) {
            Score score = new Score(
                    rs.getInt("score_id"),
                    rs.getInt("quiz_id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("time"),
                    rs.getDate("date_scored")
            );
            scores.add(score);
        }

        return scores;
    }
}