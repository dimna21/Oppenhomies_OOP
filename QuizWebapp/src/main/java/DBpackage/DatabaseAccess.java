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
    public ArrayList<FriendRequest> friendRequests(int userID){
        ArrayList<FriendRequest> friendRequests= new ArrayList<>();

        String query = "SELECT fr.*, fu.username as \"from_username\", tu.username as \"to_username\" " +
                "FROM friend_requests fr " +
                "LEFT JOIN users fu ON fr.from_id = fu.user_id " +
                "LEFT JOIN users tu ON fr.to_id = tu.user_id " +
                "WHERE fr.to_id = " + userID + " AND fr.notification = 1";

        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                FriendRequest friendRequest = new FriendRequest(
                        resultSet.getInt("request_id"),
                        resultSet.getInt("from_id"),
                        resultSet.getInt("to_id"),
                        resultSet.getInt("notification"),
                        resultSet.getString("from_username"),
                        resultSet.getString("to_username")
                );
                friendRequests.add(friendRequest);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendRequests;
    }


    public void getChallengesForUser(int userID, ArrayList<Challenge> challenges, ArrayList<Quiz> quizzes){
        String query = "SELECT c.*, q.*, u.username as \"quiz_creator_username\", tu.username as \"to_username\", fu.username as \"from_username\" " +
                "FROM challenge c " +
                "LEFT JOIN quizzes q ON c.quiz_id = q.quiz_id " +
                "LEFT JOIN users u ON q.quiz_creator_id = u.user_id " +
                "LEFT JOIN users tu ON c.to_id = tu.user_id " +
                "LEFT JOIN users fu ON c.from_id = fu.user_id " +
                "WHERE c.to_id = " + userID + " AND c.notification = 1";

        try {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Challenge challenge = new Challenge(
                        rs.getInt("challenge_id"),
                        rs.getInt("from_id"),
                        rs.getInt("to_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("notification"),
                        rs.getString("from_username"),
                        rs.getString("to_username")
                );

                Quiz quiz = new Quiz(
                        rs.getInt("quiz_id"),
                        rs.getString("quiz_name"),
                        rs.getString("quiz_description"),
                        rs.getInt("quiz_creator_id"),
                        rs.getString("quiz_creator_username"),
                        rs.getInt("random_question"),
                        rs.getInt("one_page"),
                        rs.getInt("immediate"),
                        rs.getInt("practice"),
                        rs.getTimestamp("creation_date"),
                        rs.getInt("times_taken")
                );
                challenges.add(challenge);
                quizzes.add(quiz);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}
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
    public static ArrayList<Note> getNotes(int userID, int maxAmount) {
        String query;
        if(maxAmount > 0) {
            query = "SELECT m.*, u.user_id, u.username " +
                    "FROM messages m " +
                    "LEFT JOIN users u ON m.from_id = u.user_id " +
                    "WHERE m.to_id = " + userID + " " +
                    "ORDER BY m.message_id DESC " +
                    "LIMIT " + maxAmount;
        } else {
            query = "SELECT m.*, u.user_id, u.username " +
                    "FROM messages m " +
                    "LEFT JOIN users u ON m.from_id = u.user_id " +
                    "WHERE m.to_id = " + userID + " " +
                    "ORDER BY m.message_id DESC ";
        }

        ArrayList<Note> notes = new ArrayList<>();

        try {
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                int noteId = resultSet.getInt("message_id");
                int fromId = resultSet.getInt("from_id");
                String fromUsername = resultSet.getString("username");
                int toId = resultSet.getInt("to_id");
                String text = resultSet.getString("text");
                int notification = resultSet.getInt("notification");

                Note note = new Note(noteId, fromId, fromUsername, toId, text, notification);
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
    public ArrayList<Activity> getFriendsActivity(String user, int maxActivities){
        ArrayList<Activity> actArr;
        // ArrayList<User> allFriends =
        return null;
    }
    public Score getLastAttemptOfUserOnQuiz(String username, int quizId){
        String query = "select * from Scores where quiz_id = "+ quizId+" and user_id = " +
                getUserID(username) + " order by date_scored desc limit 1;" ;
        try {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                return  new Score(
                        resultSet.getInt("score_id"),
                        resultSet.getInt("quiz_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("score"),
                        resultSet.getInt("time"),
                        resultSet.getTimestamp("date_scored")

                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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


}