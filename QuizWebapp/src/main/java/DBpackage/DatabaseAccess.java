package DBpackage;
import DBpackage.Questions.*;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseAccess {
    private static Statement stmt;
    private Connection con;

    public DatabaseAccess() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");


        con = DriverManager.getConnection("jdbc:mysql://" +
                        DatabaseInfo.server + "/" + DatabaseInfo.database,
                DatabaseInfo.username, DatabaseInfo.password);

        stmt = con.createStatement();
    }
    public static String getUsername(int userID) {
        String query = "SELECT username FROM users WHERE user_id = '" + userID + "';";
        String username = null;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return username;
    }
    public boolean login(String name, String pw)  {
        String hashcode = null;
        try {
            hashcode = hasher.getHash(pw);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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
    public boolean addUser(String username, String pw, int adminStatus){
        String Hashcode = null;
        try {
            Hashcode = hasher.getHash(pw);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if(getUserInfo(username)!=null)return false;
        String query = "INSERT INTO Users ( username, password, admin_status, quizzes_taken, quizzes_created, highest_scorer, practice_mode, profile_pic_url) VALUES" +
                "( '" + username+ "', '"+Hashcode +"', "+ adminStatus + " , 0, 0, 0, 0, 'http://example.com/images/john.jpg')";

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
        String query = "select * from friend_requests where to_id = '" + userID
                + "';";

        try {
            FriendRequest fr;
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                int req = resultSet.getInt("request_id");
                int fromid =  resultSet.getInt("from_id");
                int toid =  resultSet.getInt("to_id");
                int notif =  resultSet.getInt("notification");
                fr =  new FriendRequest(req, fromid, toid, notif, getUsername(fromid), getUsername(toid));
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
        String query = "select * from Friend_requests where to_id = '" + userID
                + "';";

        try {
            Challenge ch;
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                int req = resultSet.getInt("request_id");
                int fromid = resultSet.getInt("from_id");
                int toid = resultSet.getInt("to_id");
                int quizid = resultSet.getInt("quiz_id");
                int notif = resultSet.getInt("notification");
                ch =  new Challenge(req,fromid,toid,quizid,notif, getUsername(fromid),getUsername(toid));
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
                        resultSet.getTimestamp("creation_date"),
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
            query = "select * from quizdatabase.quizzes ORDER BY creation_date desc;";
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
                        resultSet.getTimestamp("creation_date"),
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
        ArrayList<Question> questions2 = new ArrayList<>();
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
            Question q2;
            switch(q.getType()){
                case 1:    //textbox
                    q2=getTextBox(q);
                    questions2.add(q2);
                    break;
                case 2:    //fillBlank
                    q2 = getFillBlank(q);
                    questions2.add(q2);
                    break;
                case 3:    //multipleChoice
                    q2=getMultipleChoice(q);
                    questions2.add(q2);
                case 4:    //picture
                case 5:     //multitextbox
                case 6:      //multi-multi-choice
                case 7:      //match
            }

        }
        return questions2;

    }
    public Question getTextBox(Question ques) {
        int quizId=ques.getQuizID(); int subId=ques.getSubID();
        String query = "select * from textbox_questions where quiz_id = " + quizId + " and sub_id = " + subId + " ;";
        QuestionTextbox q=null;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                q = new QuestionTextbox(
                        resultSet.getInt("question_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("sub_id"),
                        ques.getType(),
                        resultSet.getString("question"),
                        resultSet.getString("answer")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return q;
    }
    public Question getFillBlank(Question ques) {
        int quizId=ques.getQuizID(); int subId=ques.getSubID();

        String query = "select * from fill_blank_questions where quiz_id = " + quizId + " and sub_id = " + subId + " ;";
        QuestionFillBlank q=null;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                q = new QuestionFillBlank(
                        resultSet.getInt("question_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("sub_id"),
                        ques.getType(),
                        resultSet.getString("text_before"),
                        resultSet.getString("text_after"),
                        resultSet.getString("answer")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return q;
    }

    public Question getMultipleChoice(Question ques){
        int quizId=ques.getQuizID(); int subId=ques.getSubID();
        String query = "select * from multiple_choice_questions where quiz_id = " + quizId + " and sub_id = " + subId + " ;";
        QuestionMultipleChoice q=null;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
//( int questionID, int quizID,int subID, int type, String question, String correctAnswer)
                q = new QuestionMultipleChoice(
                        resultSet.getInt("question_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("sub_id"),
                        ques.getType(),
                        resultSet.getString("question"),
                        resultSet.getInt("ordered")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "select * from multiple_choice_answers where quiz_id = " + quizId+ " and sub_id = " + subId + " order by order_number ASC;";
        ArrayList<String> strings = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
//( int questionID, int quizID,int subID, int type, String question, String correctAnswer)
                if(resultSet.getInt("correct")==1){
                    q.setCorrectAnswer(resultSet.getString("answer"));
                }
                strings.add(resultSet.getString("answer"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        q.setAnswerList(strings);

        return q;
    }

    public Question getPictureQuestion(Question ques){
        int quizId=ques.getQuizID(); int subId=ques.getSubID();

        String query = "select * from picture_questions where quiz_id = " + quizId + " and sub_id = " + subId + " ;";
        QuestionPicture q=null;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                q = new QuestionPicture(
                        resultSet.getInt("answer_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("sub_id"),
                        ques.getType(),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        resultSet.getString("image_url")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return q;

    }

    public boolean accountExists(String username){
        String query = "select username from Users where username = '" + username + "' ;";
        int len = 0;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                len++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return len != 0;
    }


    public HashMap<String, Integer> getTopScorers(int quizID, int numScorers){
        String query =  "select user_id, score from Scores where quiz_id = " + quizID + " order by score DESC;";
        HashMap<String, Integer> ans = new HashMap<>();
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                if(numScorers == 0)break;
                String user = resultSet.getString("user_id");
                int score = resultSet.getInt("score");
                ans.put(user, score);
                numScorers--;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    public ArrayList<Quiz> getQuizzesByPopularity(int amountToGet) {
        ArrayList<Quiz> quizzes= new ArrayList<>();
        String query;
        if(amountToGet>0){
            query = "SELECT * FROM quizzes ORDER BY times_taken desc LIMIT " + amountToGet
                    + ";";
        }else{
            query = "select * from quizdatabase.quizzes ORDER BY times_taken desc;";
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
                quizzes.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }



    public boolean createNote(String fromUsername, String toUsername, String messageText) {
        String query = "INSERT INTO Messages (from_id, to_id, text, notification) " +
                "SELECT " +
                "(SELECT user_id FROM Users WHERE username = ? LIMIT 1), " +
                "(SELECT user_id FROM Users WHERE username = ? LIMIT 1), " +
                "?, " +
                "1 " +
                "WHERE EXISTS (SELECT 1 FROM Users WHERE username = ?) " +
                "AND EXISTS (SELECT 1 FROM Users WHERE username = ?)";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            // Set parameters for PreparedStatement
            pstmt.setString(1, fromUsername);
            pstmt.setString(2, toUsername);
            pstmt.setString(3, messageText);
            pstmt.setString(4, fromUsername);
            pstmt.setString(5, toUsername);

            int rowsAffected = pstmt.executeUpdate();

            // Check if the insert was successful (1 row affected)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false on exception
        }

    }

    public void anotherTest(){

    }

    public void helpMe(){

    }
    public ArrayList<Announcement> getLatestAnnouncements(int num) {
        String query = "SELECT * FROM Announcements ORDER BY announcement_date DESC LIMIT ?";
        ArrayList<Announcement> ans = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://" +
                        DatabaseInfo.server + "/" + DatabaseInfo.database,
                DatabaseInfo.username, DatabaseInfo.password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, num);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Announcement newAnnouncement = new Announcement(
                        resultSet.getString("announcement_title"),
                        resultSet.getString("announcement_text"),
                        resultSet.getInt("announcer_id"),
                        resultSet.getTimestamp("announcement_date")
                );
                ans.add(newAnnouncement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }


}
