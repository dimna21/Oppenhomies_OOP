package DBpackage;
import DBpackage.Questions.*;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseAccess {
    private static Statement stmt;
    private static Connection con;

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
        String query = "SELECT quizzes.*, users.user_id, users.username FROM quizzes" +
                " LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id" +
                " WHERE quizzes.quiz_id = " + quiz_id +" ;";
        Quiz q = null;
        try {

            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                q =  new Quiz(
                        resultSet.getInt("quiz_id"),
                        resultSet.getString("quiz_name"),
                        resultSet.getString("quiz_description"),
                        resultSet.getInt("quiz_creator_id"),
                        resultSet.getString("username"),
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
            query = "SELECT quizzes.*, users.user_id, users.username FROM quizzes " +
                    "LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "ORDER BY quizzes.creation_date DESC LIMIT" + amountToGet
                    + ";";
        }else{
            query = "SELECT quizzes.*, users.user_id, users.username FROM quizzes" +
                    " LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "ORDER BY quizzes.creation_date DESC;";
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
                        resultSet.getString("username"),
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
                    q2=getPictureQuestion(q);
                    questions2.add(q2);
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
    public QuestionCheckbox getCheckbox(Question ques) {
        int quizId = ques.getQuizID();
        int subId = ques.getSubID();

        String query = "SELECT * FROM checkbox_questions WHERE quiz_id = " + quizId + " AND sub_id = " + subId + ";";
        QuestionCheckbox q = null;

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                q = new QuestionCheckbox(
                        resultSet.getInt("question_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("sub_id"),
                        ques.getType(),  // Assuming getType() exists in Question class
                        resultSet.getString("question"),
                        resultSet.getInt("ordered"),
                        new ArrayList<String>(),
                        new ArrayList<Integer>()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "SELECT * FROM checkbox_answers WHERE quiz_id = " + q.getQuizID() +
                " and sub_id = "+q.getSubID()+" ORDER BY order_num ASC;";
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<Integer> correct = new ArrayList<>();

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                answers.add(resultSet.getString("answer"));
                correct.add(resultSet.getInt("correct"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        q.setAnswerList(answers);
        q.setCorrectList(correct);

        return q;
    }

    public QuestionMultiAnswer getMultiAnswer(Question ques) {
        int quizId = ques.getQuizID();
        int subId = ques.getSubID();

        String query = "SELECT * FROM multi_answer_questions WHERE quiz_id = " + quizId + " AND sub_id = " + subId + ";";
        QuestionMultiAnswer q = null;

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                q = new QuestionMultiAnswer(
                        resultSet.getInt("question_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("sub_id"),
                        ques.getType(),  // Assuming getType() exists in Question class
                        resultSet.getString("question"),
                        resultSet.getInt("ordered"),
                        new ArrayList<String>()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "SELECT * FROM multi_answer_answers WHERE quiz_id = " + q.getQuizID() +
                " and sub_id = "+q.getSubID()+" ORDER BY order_num ASC;";
        ArrayList<String> answers = new ArrayList<>();

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                answers.add(resultSet.getString("answer"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        q.setAnswerList(answers);

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
            query = "SELECT quizzes.*, users.user_id, users.username FROM quizzes L" +
                    "EFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "ORDER BY quizzes.times_taken DESC LIMIT" + amountToGet
                    + ";";
        }else{
            query = "SELECT quizzes.*, users.user_id, users.username FROM quizzes " +
                    "LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "ORDER BY quizzes.times_taken DESC;";
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
                        resultSet.getString("username"),
                        resultSet.getInt("random_question"),
                        resultSet.getInt("one_page"),
                        resultSet.getInt("immediate"),
                        resultSet.getInt("practice"),
                        resultSet.getTimestamp("creation_date"),
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

    /**Creates an announcement in the database*/
    public boolean makeAnnouncement(String username, String title, String text, Timestamp date) throws SQLException {
        if (!isAdmin(username)) {
            return false;
        }

        String query = "INSERT INTO Announcements (announcement_title, announcement_text, announcer_id, announcement_date) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = con.prepareStatement(query);{
            stmt.setString(1, title);
            stmt.setString(2, text);
            stmt.setInt(3, getUserID(username)); // Assuming getUserId(username) retrieves the user ID
            stmt.setTimestamp(4, date);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;  // Returns true if at least one row was inserted
        }
    }


    /** Returns a user's ID based in their username*/
    public static int getUserID(String username) {
        String query = "SELECT user_id FROM users WHERE username = '" + username + "';";
        int ans = 0;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                ans = resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }


    /**
     * Checks whether a user is admin or not
     */
    public boolean isAdmin(String username){
        String query = "select * from Users where username = '" + username + "' ;";
        int x = 0;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                x = resultSet.getInt("admin_status");
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return x == 1;
    }

    /** Promotes a user to admin and returns true if successful.
     *  If the user is already an admin or the promoting user is not an admin,
     *  operation returns false
     */
    public boolean promoteToAdmin(String admin, String user) {
        if (!isAdmin(admin) || isAdmin(user)) {
            return false;
        }

        String query = "UPDATE Users " +
                "SET admin_status = 1 " +  // Assuming admin_status 1 indicates admin status
                "WHERE username = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://" +
                        DatabaseInfo.server + "/" + DatabaseInfo.database,
                DatabaseInfo.username, DatabaseInfo.password);
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, user);
            int rowsUpdated = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to promote user to admin: " + e.getMessage(), e);
        }
        return true;
    }

    /**
     * Sends a friend request from one account to another
     *
     */
    public void sendFriendRequest(String from, String to) throws SQLException {
        String query = "INSERT INTO Friend_requests (from_id, to_id, notification) VALUES (?, ?, 0)";
        PreparedStatement preparedStmt = con.prepareStatement(query);
        int fromId = getUserID(from);
        int toId = getUserID(to);
        preparedStmt.setInt(1, fromId);
        preparedStmt.setInt(2, toId);
        int rowsUpdated = preparedStmt.executeUpdate();
    }

    /**
     * Takes user IDs as parameters and answers a friend request.
     *  Status = 1 accepted
     *  Status = 2 rejected
     */
    public void updateFriendRequestStatus(int userAnswering, int answeringTo, int status) throws SQLException {
        String query = "UPDATE Friend_requests " +
                "SET notification = " + status +
                " WHERE from_id = " + answeringTo + " AND to_id = " + userAnswering;
        try (Statement stmt = con.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /** Returns a friendlist of specified username */
    public ArrayList<User> getFriendlist(String username){
        ArrayList<User> friendlist = new ArrayList<>();
        String query = "SELECT * FROM Friend_requests WHERE (to_id = " + getUserID(username) + " OR from_id = " + getUserID(username) + ") AND notification = 1";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                int ID = resultSet.getInt("to_id");
                friendlist.add(getUserInfo(getUsername(ID)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendlist;
    }

    /** Sends a pending challenge from one user to another */
    public void sendChallenge(String from, String to, int quizID) {
        String query = "INSERT INTO Challenge (from_id, to_id, quiz_id, notification) VALUES (?, ?, ?, 0)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, getUserID(from));
            stmt.setInt(2, getUserID(to));
            stmt.setInt(3, quizID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to send challenge", e);
        }
    }

    /**
     * Takes user IDs as parameters and answers a challenge.
     *  Status = 1 accepted
     *  Status = 2 rejected
     */
    public void answerChallenge(int userAnswering, int answeringTo, int status) throws SQLException {
        String query = "UPDATE Challenge " +
                "SET notification = " + status  +
                " WHERE from_id = " + answeringTo + " AND to_id = " + userAnswering;
        try (Statement stmt = con.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /** Deletes a user's account if the action is performed by an admin
     * and returns a corresponding boolean
     */
    public boolean deleteAccount(int adminID, int userID){
        if (!isAdmin(getUsername(adminID))) {
            return false;
        }
        String query = "UPDATE Users " +
                " SET activeAccount = " + 0 +
                " WHERE user_id = " + userID;
        try (Statement stmt = con.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return true;
    }

    /** Deletes a quiz from the database */
    public void deleteQuiz(int quizID){
        String query = "DELETE from Quizzes where quiz_id = " + quizID;
        try (Statement stmt = con.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     *  Returns the site statistics.
     * The first entry of the ArrayList is total number of users.
     * The second entry of the ArrayList is total number of quizzes taken
     */
    public ArrayList<Integer> getSiteStatistics(){
        ArrayList<Integer> stats = new ArrayList<>(2);
        String queryUsers = "SELECT COUNT(*) AS totalUsers FROM Users";
        String queryQuizzesTaken = "SELECT COUNT(*) AS totalQuizzesTaken FROM Scores";
        try (
                PreparedStatement stmtUsers = con.prepareStatement(queryUsers);
                PreparedStatement stmtQuizzesTaken = con.prepareStatement(queryQuizzesTaken);
        ) {
            ResultSet rsUsers = stmtUsers.executeQuery();
            if (rsUsers.next()) {
                int totalUsers = rsUsers.getInt("totalUsers");
                stats.add(totalUsers);
            }
            ResultSet rsQuizzesTaken = stmtQuizzesTaken.executeQuery();
            if (rsQuizzesTaken.next()) {
                int totalQuizzesTaken = rsQuizzesTaken.getInt("totalQuizzesTaken");
                stats.add(totalQuizzesTaken);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stats;
    }

    public void anotherTest(){

    }

    public void helpMe(){

    }
    public ArrayList<Announcement> getLatestAnnouncements(int num)  {
        String query = "SELECT Announcements.*, users.user_id, users.username FROM Announcements " +
                "LEFT JOIN users ON Announcements.announcer_id = users.user_id " +
                "ORDER BY Announcements.announcement_date DESC;\n";
        if (num > 0) {
            query += " LIMIT ?";
        }

        ArrayList<Announcement> ans = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(query);
            if (num > 0) {
                stmt.setInt(1, num);
            }   

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Announcement newAnnouncement = new Announcement(
                        resultSet.getString("announcement_title"),
                        resultSet.getString("announcement_text"),
                        resultSet.getInt("announcer_id"),
                        resultSet.getString("username"),
                        resultSet.getTimestamp("announcement_date")
                );
                ans.add(newAnnouncement);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ans;
    }
    public static ArrayList<Note> getNotes(String toUsername, int maxAmount) {
        String query = "SELECT message_id, from_id, to_id, text, notification " +
                "FROM Messages " +
                "WHERE to_id = (SELECT user_id FROM Users WHERE username = '" + toUsername + "') " +
                (maxAmount > 0 ? "ORDER BY message_id desc LIMIT " + maxAmount : "ORDER BY message_id DESC");

        ArrayList<Note> notes = new ArrayList<>();

        try {
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                int noteId = resultSet.getInt("message_id");
                int fromId = resultSet.getInt("from_id");
                int toId = resultSet.getInt("to_id");
                String text = resultSet.getString("text");
                int notification = resultSet.getInt("notification");

                Note note = new Note(noteId, fromId, toId, text, notification);
                notes.add(note);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving notes: " + e.getMessage());
        }

        return notes;
    }

    public static ArrayList<Score> getRecentPerformance(String username, int quizId, int amount) {
        ArrayList<Score> scores = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Retrieve user_id from Users table using getUserID method
            int userId = getUserID(username);

            // Step 2: Construct the query to retrieve scores
            String getScoresQuery;
            if (quizId > 0 && amount > 0) {
                getScoresQuery = "SELECT * FROM Scores WHERE user_id = ? AND quiz_id = ? ORDER BY date_scored DESC LIMIT ?";
                pstmt = con.prepareStatement(getScoresQuery);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, quizId);
                pstmt.setInt(3, amount);
            } else {
                getScoresQuery = "SELECT * FROM Scores WHERE user_id = ? AND quiz_id = ? ORDER BY date_scored DESC";
                pstmt = con.prepareStatement(getScoresQuery);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, quizId);
            }

            // Step 3: Execute the query and process the ResultSet
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int scoreId = resultSet.getInt("score_id");
                int retrievedQuizId = resultSet.getInt("quiz_id");
                int retrievedUserId = resultSet.getInt("user_id");
                int score = resultSet.getInt("score");
                int time = resultSet.getInt("time");
                Timestamp dateScored = resultSet.getTimestamp("date_scored");

                Score scoreObject = new Score(scoreId, retrievedQuizId, retrievedUserId, score, time, dateScored);
                scores.add(scoreObject);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving recent scores: " + e.getMessage());
        } finally {
            // Close ResultSet and PreparedStatement in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            }
        }

        return scores;
    }

    public ArrayList<Quiz> getPopularQuiz(int amountToGet){
        ArrayList<Quiz> ls= new ArrayList<>();
        String query;
        if(amountToGet>0){
            query = "select quizzes.*, users.user_id, users.username " +
                    "from quizzes " +
                    "LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "order by times_taken desc LIMIT " + amountToGet
                    + ";";
        }else{
            query = "select quizzes.*, users.user_id, users.username " +
                    "from quizzes " +
                    "LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "order by times_taken desc;";
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
                        resultSet.getString("username"), // quiz Creator username
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
    public ArrayList<Quiz> recentCreationsByUser(String username, int maxAmount){
        int userId = getUserID(username);
        String query;
        ArrayList<Quiz> A = new ArrayList<>();
        if(maxAmount == 0){
            query = "select quizzes.*, users.user_id, users.username " +
                    "from quizzes " +
                    "LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "WHERE quizzes.quiz_creator_id = " + userId + " " +
                    "order by creation_date desc;";
        }else{
            query = "select quizzes.*, users.user_id, users.username " +
                    "from quizzes " +
                    "LEFT JOIN users ON quizzes.quiz_creator_id = users.user_id " +
                    "WHERE quizzes.quiz_creator_id = " + userId + " " +
                    "order by creation_date desc LIMIT " + maxAmount + ";";
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
                        resultSet.getString("username"), // Quiz creator username
                        resultSet.getInt("random_question"),
                        resultSet.getInt("one_page"),
                        resultSet.getInt("immediate"),
                        resultSet.getInt("practice"),
                        resultSet.getTimestamp("creation_date"),
                        resultSet.getInt("times_taken")
                );
                A.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return A;
    }
    public ArrayList<Achievement> getRecentAchievements(String username, int maxAmount) {
        String query = "SELECT a.achievement_id, a.achievement_title, a.user_id, a.achievement_date " +
                "FROM Achievements a " +
                "JOIN Users u ON a.user_id = u.user_id " +
                "WHERE u.username = '" + username + "' ";
        if(maxAmount>0){
            query+="ORDER BY a.achievement_date DESC LIMIT " + maxAmount + " ;";
        }else{
            query+="ORDER BY a.achievement_date DESC ;";
        }
        ArrayList<Achievement> ac = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            Achievement achievement;
            while (resultSet.next()) {
                int achievementId = resultSet.getInt("achievement_id");
                String achievementTitle = resultSet.getString("achievement_title");
                int userId = resultSet.getInt("user_id");
                Timestamp achievementDate = resultSet.getTimestamp("achievement_date");

                achievement = new Achievement(achievementId, achievementTitle, userId, achievementDate);
                ac.add(achievement);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving achievements: " + e.getMessage());
        }

        return ac;
    }

    public ArrayList<Activity> getFriendsActivity(String user, int maxActivities){
        ArrayList<Activity> actArr = new ArrayList<>();
        ArrayList<User> allFriends = getFriendlist(user);
        ArrayList<Score> taken;
        ArrayList<Quiz> made;
        ArrayList<Achievement> ach;
        String name;
        for(User f : allFriends ){
            taken = getLastAttemptsOfUser(f.getUsername(),maxActivities);
            made = recentCreationsByUser(f.getUsername(),maxActivities);
            ach=getRecentAchievements(f.getUsername(),maxActivities);
            name = f.getUsername();
            actArr.add(new Activity(
                    name,
                    taken,
                    made,
                    ach
            ));

        }
        return actArr;
    }
    public ArrayList<Score> getLastAttemptsOfUser(String username, int amount){
        String query = "select * from Scores where  user_id = " +
                getUserID(username) + " order by date_scored desc limit "+amount+" ;" ;
        ArrayList<Score>s=new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                s.add(new Score(
                        resultSet.getInt("score_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("score"),
                        resultSet.getInt("time"),
                        resultSet.getTimestamp("date_scored")

                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
    public ArrayList<Score> getLastAttemptsOfUserOnQuiz(String username, int quizId, int amount){
        String query = "select * from Scores where quiz_id = "+ quizId+" and user_id = " +
                getUserID(username) + " order by date_scored desc limit "+amount+" ;" ;
        ArrayList<Score>s=new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                s.add(new Score(
                        resultSet.getInt("score_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("score"),
                        resultSet.getInt("time"),
                        resultSet.getTimestamp("date_scored")

                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
    public ArrayList<ScoreAndUser> getTopPerformersForLastDay(int quizID, int amount) {
        String query;
        if (amount == 0) {
            query = "SELECT u.user_id, " +
                    "u.username, " +
                    "u.password, " +
                    "u.admin_status, " +
                    "u.quizzes_taken, " +
                    "u.quizzes_created, " +
                    "u.highest_scorer, " +
                    "u.practice_mode, " +
                    "u.profile_pic_url, " +
                    "u.activeAccount, " +
                    "s.score, " +
                    "s.date_scored, " +
                    "s.score_id, " +
                    "s.quiz_id, " +
                    "s.user_id, " +
                    "s.time " +
                    "FROM Users u " +
                    "JOIN Scores s ON u.user_id = s.user_id " +
                    "WHERE s.quiz_id = " + quizID + " " +
                    "AND s.date_scored >= NOW() - INTERVAL 1 DAY " +
                    "ORDER BY s.score DESC ;" ;
        } else {
            query = "SELECT u.user_id, " +
                    "u.username, " +
                    "u.password, " +
                    "u.admin_status, " +
                    "u.quizzes_taken, " +
                    "u.quizzes_created, " +
                    "u.highest_scorer, " +
                    "u.practice_mode, " +
                    "u.profile_pic_url, " +
                    "u.activeAccount, " +
                    "s.score, " +
                    "s.date_scored, " +
                    "s.score_id, " +
                    "s.quiz_id, " +
                    "s.user_id, " +
                    "s.time " +
                    "FROM Users u " +
                    "JOIN Scores s ON u.user_id = s.user_id " +
                    "WHERE s.quiz_id = " + quizID + " " +
                    "AND s.date_scored >= NOW() - INTERVAL 1 DAY " +
                    "ORDER BY s.score DESC " +
                    "LIMIT " + amount;
        }

        ArrayList<ScoreAndUser> users = new ArrayList<>();

        try {
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                int scoreId = resultSet.getInt("score_id");
                int quizId = resultSet.getInt("quiz_id");
                int userId = resultSet.getInt("user_id");
                int score = resultSet.getInt("score");
                int time = resultSet.getInt("time");
                Timestamp dateScored = resultSet.getTimestamp("date_scored");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                int adminStatus = resultSet.getInt("admin_status");
                int quizzesTaken = resultSet.getInt("quizzes_taken");
                int quizzesCreated = resultSet.getInt("quizzes_created");
                int highestScorer = resultSet.getInt("highest_scorer");
                int practiceMode = resultSet.getInt("practice_mode");
                String profilePicUrl = resultSet.getString("profile_pic_url");
                int activeAccount = resultSet.getInt("activeAccount");
                Score myScore = new Score(scoreId,quizId,userId,score,time,dateScored);
                User user = new User(userId, username, password, adminStatus, quizzesTaken, quizzesCreated, highestScorer, practiceMode, profilePicUrl, activeAccount);
                users.add(new ScoreAndUser(myScore,user));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving top performers: " + e.getMessage());
        }

        return users;
    }


    public void recentQuizTakingActivitiesForUser(int userID, ArrayList<Score> scores,  ArrayList<Quiz> quizzes) {
        String query = "SELECT s.*, q.*, u1.username " +
                "FROM scores s " +
                "LEFT JOIN quizzes q ON s.quiz_id = q.quiz_id " +
                "LEFT JOIN users u1 ON q.quiz_creator_id = u1.user_id " +
                "WHERE s.user_id =" + userID + ";";

        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Score score = new Score(
                        rs.getInt("score_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("time"),
                        rs.getTimestamp("date_scored")
                );
                Quiz quiz = new Quiz(
                        rs.getInt("quiz_id"),
                        rs.getString("quiz_name"),
                        rs.getString("quiz_description"),
                        rs.getInt("quiz_creator_id"), // Creator UserId
                        rs.getString("username"), // Creator Username
                        rs.getInt("random_question"),
                        rs.getInt("one_page"),
                        rs.getInt("immediate"),
                        rs.getInt("practice"),
                        rs.getTimestamp("creation_date"),
                        rs.getInt("times_taken")
                );
                scores.add(score);
                quizzes.add(quiz);
            }

        }
        catch (SQLException e) { throw new RuntimeException(e); }

    }
    public double getAverageTime(int quizID) {
        String query = "SELECT AVG(time) AS average_time FROM Scores WHERE quiz_id = " + quizID;
        double averageTime = 0.0;

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                averageTime = resultSet.getDouble("average_time");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving average time: " + e.getMessage());
        }

        return averageTime;
    }
    public double getAverageScore(int quizID) {
        String query = "SELECT AVG(score) AS average_score FROM Scores WHERE quiz_id = " + quizID;
        double averageScore = 0.0;

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                averageScore = resultSet.getDouble("average_score");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving average score: " + e.getMessage());
        }

        return averageScore;
    }







    /** Returns the achievements of a user in an arrayList format of strings */
    public ArrayList<String> getUserAchievements(String username){
        ArrayList<String> list = new ArrayList<>();
        User user = getUserInfo(username);
        int quizzesTaken = user.getQuizzes_taken();
        int quizzesCreated = user.getQuizzes_created();
        int highestScorer = user.getHighest_scorer();
        int practiceMode = user.getPractice_mode();

        String amateurAuthor = "Amateur author";
        String prolificAuthor = "Prolific author";
        String prodigiousAuthor = "Prodigious author";
        String quizMachine = "Quiz machine";
        String greatest = "I am the greatest";
        String practice = "Practice makes perfect";

        if(quizzesCreated > 0){
            list.add(amateurAuthor);
        }
        if(quizzesCreated > 4){
            list.add(prolificAuthor);
        }
        if(quizzesCreated > 9){
            list.add(prodigiousAuthor);
        }
        if(quizzesTaken > 9){
            list.add(quizMachine);
        }
        if(highestScorer == 1){
            list.add(greatest);
        }
        if(practiceMode == 1){
            list.add(practice);
        }
        return list;
    }

    /** This method must be called after a user takes a quiz. It takes in specified parameters
     *  and updates the database accordingly */
    public void quizFinished(int userID, int quizID, int score,
                             Timestamp date, int secondsTaken, boolean practiceMode, int quizRating){

        // Update practice mode achievement
        User user = getUserInfo(getUsername(userID));
        if(practiceMode && user.getPractice_mode() == 1) return;
        if(practiceMode && user.getPractice_mode() == 0){
            String query = "UPDATE Users " +
                    " SET practice_mode = " + 1 +
                    " WHERE user_id = " + userID;
            try (Statement stmt = con.createStatement()) {
                int rowsUpdated = stmt.executeUpdate(query);
            } catch (SQLException e) {
                throw new RuntimeException();
            }

            String achievementTitle = "Practice makes perfect";
            String qq = "INSERT INTO achievements (achievement_title, user_id, achievement_date) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(qq)) {
                pstmt.setString(1, achievementTitle);
                pstmt.setInt(2, userID);
                pstmt.setTimestamp(3, date);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error executing SQL query", e);
            }
            return;
        }

        //  Update top scorer achievement
        HashMap<String, Integer> scores = getTopScorers(quizID, 1);
        int greatest = 0;
        if(scores.size() != 0){
            int oldScore = scores.values().iterator().next();
            if(score>oldScore){
                String query = "UPDATE Users " +
                        " SET highest_scorer = " + 1 +
                        " WHERE user_id = " + userID;
                try (Statement stmt = con.createStatement()) {
                    int rowsUpdated = stmt.executeUpdate(query);
                } catch (SQLException e) {
                    throw new RuntimeException();
                }

                String achievementTitle = "I am the greatest";
                String q2 = "INSERT INTO achievements (achievement_title, user_id, achievement_date) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(q2)) {
                    pstmt.setString(1, achievementTitle);
                    pstmt.setInt(2, userID);
                    pstmt.setTimestamp(3, date);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Error executing SQL query", e);
                }
            }
        }

        // Update quizzes taken
        int taken = user.getQuizzes_taken();
        taken++;
        String query = "UPDATE Users " +
                " SET quizzes_taken = " + taken +
                " WHERE user_id = " + userID;
        try (Statement stmt = con.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        if(taken == 10){
            String achievementTitle = "Quiz machine";
            String qqq = "INSERT INTO achievements (achievement_title, user_id, achievement_date) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(qqq)) {
                pstmt.setString(1, achievementTitle);
                pstmt.setInt(2, userID);
                pstmt.setTimestamp(3, date);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error executing SQL query", e);
            }
        }

        //update scores table
        String query2 = "INSERT INTO Scores (quiz_id, user_id, score, time, date_scored) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query2)) {
            pstmt.setInt(1, quizID);
            pstmt.setInt(2, userID);
            pstmt.setInt(3, score);
            pstmt.setInt(4, secondsTaken);
            pstmt.setTimestamp(5, date);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }


        //update quiz rating
        String query3 = "Select * from Quiz_ratings where quiz_id = " + quizID;
        int totalRating = 0;
        try {
            ResultSet resultSet = stmt.executeQuery(query3);
            while (resultSet.next()) {
                totalRating = resultSet.getInt("rating");
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        totalRating += quizRating;
        String query4 = "UPDATE quiz_ratings " +
                " SET rating = " + totalRating +
                " WHERE quiz_id = " + quizID;
        try (Statement stmt = con.createStatement()) {
            int rowsUpdated = stmt.executeUpdate(query4);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /** Returns quiz rating on a scale from 0 to 10 as a double*/
    public double getQuizRating(int quizID){
        String query = "Select * from Quiz_ratings where quiz_id = " + quizID;
        int totalRating = 0;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                totalRating = resultSet.getInt("rating");
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String query2 = "Select * from Quizzes where quiz_id = " + quizID;
        int taken = 0;
        try {
            ResultSet resultSet = stmt.executeQuery(query2);
            while (resultSet.next()) {
                taken = resultSet.getInt("times_taken");
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(taken == 0)taken = 1;

        double rating = (double)totalRating;
        double t = (double)taken;
        return rating/t;
    }




    public static final int QUESTION_TEXTBOX = 1;
    public static final int QUESTION_FILL_BLANK = 2;
    public static final int QUESTION_MULTIPLE_CHOICE = 3;
    public static final int QUESTION_PICTURE = 4;
    public static final int QUESTION_MULTIANSWER = 5;
    public static final int QUESTION_CHECKBOX = 6;
    public static final int QUESTION_MATCHING = 7;

    public void createQuiz(ArrayList<Question> questions, String quizName, String quizDescription,
                           int creatorID, String creatorUsername, int randomQuestion,
                           int immediate, int practice, int onePage, Timestamp creationDate){
        String query = "INSERT INTO Quizzes (quiz_name, quiz_description, quiz_creator_id, " +
                "random_question, one_page, immediate, practice, creation_date, times_taken) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, quizName); // quiz_name
            pstmt.setString(2, quizDescription); // quiz_description
            pstmt.setInt(3, creatorID); // quiz_creator_id
            pstmt.setInt(4, randomQuestion); // random_question
            pstmt.setInt(5, onePage); // one_page
            pstmt.setInt(6, immediate); // immediate
            pstmt.setInt(7, practice); // practice
            pstmt.setTimestamp(8, creationDate); // creation_date (assuming it's a Timestamp object)
            pstmt.setInt(9, 0); // times_taken
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing SQL query", e);
        }

        for (Question q : questions) {
            int type = q.getType();
            switch (type) {
                case QUESTION_TEXTBOX:
                    q = (QuestionTextbox) q;
                    String executable1 = "INSERT INTO Textbox_questions (quiz_id, sub_id, question, answer)" +
                            " VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(executable1)) {
                        pstmt.setInt(1,q.getQuizID());
                        pstmt.setInt(2,q.getSubID());
                        pstmt.setString(3, ((QuestionTextbox) q).getQuestion());
                        pstmt.setString(4, ((QuestionTextbox) q).getAnswer());
                        int rowsUpdated = pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error executing SQL query", e);
                    }
                    break;

                case QUESTION_FILL_BLANK:
                    q = (QuestionFillBlank) q;
                    String executable2 = "INSERT INTO Fill_blank_questions (quiz_id, sub_id, text_before, text_after, answer)" +
                            " VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(executable2)) {
                        pstmt.setInt(1,q.getQuizID());
                        pstmt.setInt(2,q.getSubID());
                        pstmt.setString(3, ((QuestionFillBlank) q).getTextBefore());
                        pstmt.setString(4, ((QuestionFillBlank) q).getTextAfter());
                        pstmt.setString(5,((QuestionFillBlank) q).getAnswer());
                        int rowsUpdated = pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error executing SQL query", e);
                    }
                    break;

                case QUESTION_MULTIPLE_CHOICE:
                    q = (QuestionMultipleChoice) q;
                    String correctAnswer = "";
                    ArrayList<String> allAnswers;
                    // insert question
                    String executable3 = "INSERT INTO Multiple_choice_questions (quiz_id, sub_id, question, ordered)" +
                            " VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(executable3)) {
                        pstmt.setInt(1,q.getQuizID());
                        pstmt.setInt(2,q.getSubID());
                        pstmt.setString(3, ((QuestionMultipleChoice) q).getQuestion());
                        correctAnswer = ((QuestionMultipleChoice) q).getCorrectAnswer();
                        pstmt.setInt(4, ((QuestionMultipleChoice) q).getOrdered());
                        allAnswers = ((QuestionMultipleChoice) q).getAnswerList();
                        int rowsUpdated = pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error executing SQL query", e);
                    }

                    // insert all answers
                    for(int i = 0; i < allAnswers.size(); i++){
                        String answer = allAnswers.get(i);
                        String executable3_1 = "INSERT INTO Multiple_choice_answers (quiz_id, sub_id, order_number, answer, correct)" +
                                " VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmt = con.prepareStatement(executable3_1)) {
                            pstmt.setInt(1,q.getQuizID());
                            pstmt.setInt(2,q.getSubID());
                            pstmt.setInt(3, i+1);
                            pstmt.setString(4, answer);
                            int correcto = 0;
                            if(answer.equals(correctAnswer)){
                                correcto = 1;
                            }
                            pstmt.setInt(5, correcto);
                            int rowsUpdated = pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException("Error executing SQL query", e);
                        }
                    }
                    break;

                case QUESTION_PICTURE:
                    q = (QuestionPicture) q;
                    String executable4 = "INSERT INTO Picture_questions (quiz_id, sub_id, question, answer, image_url)" +
                            " VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(executable4)) {
                        pstmt.setInt(1,q.getQuizID());
                        pstmt.setInt(2,q.getSubID());
                        pstmt.setString(3, ((QuestionPicture) q).getQuestion());
                        pstmt.setString(4, ((QuestionPicture) q).getAnswer());
                        pstmt.setString(5, ((QuestionPicture) q).getImageURL());
                        int rowsUpdated = pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error executing SQL query", e);
                    }
                    break;

                case QUESTION_MULTIANSWER:
                    q = (QuestionMultiAnswer) q;
                    ArrayList<String> allTextboxAnswers;
                    String executable5 = "INSERT INTO Multi_answer_questions (quiz_id, sub_id, question, ordered)" +
                            " VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(executable5)) {
                        pstmt.setInt(1,q.getQuizID());
                        pstmt.setInt(2,q.getSubID());
                        pstmt.setString(3, ((QuestionMultiAnswer) q).getQuestion());
                        pstmt.setInt(4, ((QuestionMultiAnswer) q).getOrdered());
                        allTextboxAnswers = ((QuestionMultiAnswer) q).getAnswerList();
                        int rowsUpdated = pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error executing SQL query", e);
                    }
                    for(int i = 0; i < allTextboxAnswers.size(); i++){
                        String executable5_1 = "INSERT INTO Multi_answer_answers (quiz_id, sub_id, answer, order_num)" +
                                " VALUES (?, ?, ?, ?)";
                        try (PreparedStatement pstmt = con.prepareStatement(executable5_1)) {
                            pstmt.setInt(1,q.getQuizID());
                            pstmt.setInt(2,q.getSubID());
                            pstmt.setString(3, allTextboxAnswers.get(i));
                            pstmt.setInt(4,i+1);
                            int rowsUpdated = pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException("Error executing SQL query", e);
                        }
                    }

                    break;

                case QUESTION_CHECKBOX:
                    q = (QuestionCheckbox) q;
                    ArrayList<String> allCheckboxAnswers;
                    ArrayList<Integer> correctCheckboxAnswers;
                    // insert question
                    String executable6 = "INSERT INTO checkbox_questions (quiz_id, sub_id, question, ordered)" +
                            " VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(executable6)) {
                        pstmt.setInt(1,q.getQuizID());
                        pstmt.setInt(2,q.getSubID());
                        pstmt.setString(3, ((QuestionCheckbox) q).getQuestion());
                        pstmt.setInt(4, ((QuestionCheckbox) q).getOrdered());
                        allCheckboxAnswers = ((QuestionCheckbox) q).getAnswerList();
                        correctCheckboxAnswers = ((QuestionCheckbox) q).getCorrectList();
                        int rowsUpdated = pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error executing SQL query", e);
                    }

                    // insert all answers
                    for(int i = 0; i < allCheckboxAnswers.size(); i++){
                        String answer = allCheckboxAnswers.get(i);
                        String executable6_1 = "INSERT INTO checkbox_answers (quiz_id, sub_id, answer, correct, order_num)" +
                                " VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmt = con.prepareStatement(executable6_1)) {
                            pstmt.setInt(1,q.getQuizID());
                            pstmt.setInt(2,q.getSubID());
                            pstmt.setString(3, answer);
                            pstmt.setInt(4, correctCheckboxAnswers.get(i));
                            pstmt.setInt(5,i+1);
                            int rowsUpdated = pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException("Error executing SQL query", e);
                        }
                    }
                    break;


                case QUESTION_MATCHING:
                    q = (QuestionMatching)q;
                    String executable7 = "INSERT INTO Matching_questions (quiz_id, sub_id, question)" +
                            " VALUES (?, ?, ?)";
                    ArrayList<String> words;
                    ArrayList<String> matchingWords;
                    try (PreparedStatement pstmt = con.prepareStatement(executable7)) {
                        pstmt.setInt(1,q.getQuizID());
                        pstmt.setInt(2,q.getSubID());
                        pstmt.setString(3, ((QuestionMatching) q).getQuestion());
                        words = ((QuestionMatching) q).getWords();
                        matchingWords = ((QuestionMatching) q).getMatchingWords();
                        int rowsUpdated = pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Error executing SQL query", e);
                    }

                    for(int i = 0; i < words.size(); i++){
                        String executable7_1 = "INSERT INTO Matching_answers (match_id, word, matching_word)" +
                                " VALUES (?, ?, ?)";
                        try (PreparedStatement pstmt = con.prepareStatement(executable7_1)) {
                            pstmt.setInt(1, q.getQuestionID());
                            pstmt.setString(2, words.get(i));
                            pstmt.setString(3, matchingWords.get(i));
                            int rowsUpdated = pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            throw new RuntimeException("Error executing SQL query", e);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }





}