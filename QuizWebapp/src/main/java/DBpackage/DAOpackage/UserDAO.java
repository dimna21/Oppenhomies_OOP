package DBpackage.DAOpackage;
import DBpackage.*;
import DBpackage.Questions.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDAO {
    public static String getUsername(int userID) {
        return DatabaseAccess.getUsername(userID);
    }

    ;

    public static boolean login(String name, String pw) {
        return DatabaseAccess.login(name, pw);
    }

    ;

    public static User getUserInfo(String name) {
        return DatabaseAccess.getUserInfo(name);
    }

    public static boolean addUser(String username, String pw, int adminStatus) {
        return DatabaseAccess.addUser(username, pw, adminStatus);
    }

    public static boolean updatePicture(String username, String pic) {
        return DatabaseAccess.updatePicture(username, pic);
    }

    public static boolean accountExists(String username) {
        return DatabaseAccess.accountExists(username);

    }

    public static int getUserID(String username) {
        return DatabaseAccess.getUserID(username);

    }

    public static boolean isAdmin(String username) {
        return DatabaseAccess.isAdmin(username);

    }

    public static boolean promoteToAdmin(String user) {
        return DatabaseAccess.promoteToAdmin(user);

    }

    public static boolean deleteAccount( int userID) {
        return DatabaseAccess.deleteAccount(userID);
    }
    public static boolean unbanAccount(int userID){
        return DatabaseAccess.unbanAccount(userID);

    }
    public static ArrayList<Score> getRecentPerformance(String username, int quizId, int amount) {
        return DatabaseAccess.getRecentPerformance(username, quizId, amount);

    }

    public static ArrayList<Quiz> recentCreationsByUser(String username, int maxAmount) {
        return DatabaseAccess.recentCreationsByUser(username, maxAmount);


    }

    public static  ArrayList<ScoreAndUser> getTopPerformersForLastDay(int quizID, int amount) {
        return DatabaseAccess.getTopPerformersForLastDay(quizID, amount);

    }
    public  static void quizFinished(int userID, int quizID, int score,
                                     Timestamp date, int secondsTaken, boolean practiceMode, int quizRating) {
         DatabaseAccess.quizFinished( userID,  quizID,  score,
         date,  secondsTaken,  practiceMode,  quizRating);

    }


    }