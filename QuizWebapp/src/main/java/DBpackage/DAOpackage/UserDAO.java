package DBpackage.DAOpackage;
import DBpackage.*;
import DBpackage.Questions.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDAO {

    /**  Returns a username using a user ID*/
    public static String getUsername(int userID) {
        return DatabaseAccess.getUsername(userID);
    }

    /** Returns true for a successful login, False for an unsuccesful one*/
    public static boolean login(String name, String pw) {
        return DatabaseAccess.login(name, pw);
    }

    /** Returns a User object using only the username */
    public static User getUserInfo(String name) {
        return DatabaseAccess.getUserInfo(name);
    }

    /** Adds a new user to the database */
    public static boolean addUser(String username, String pw, int adminStatus) {
        return DatabaseAccess.addUser(username, pw, adminStatus);
    }

    /** Updates the profile picture */
    public static boolean updatePicture(String username, String pic) {
        return DatabaseAccess.updatePicture(username, pic);
    }

    /**Checks if an account already exists with a certain username*/
    public static boolean accountExists(String username) {
        return DatabaseAccess.accountExists(username);
    }

    /** Returns a user's ID based in their username*/
    public static int getUserID(String username) {
        return DatabaseAccess.getUserID(username);

    }

    /** Checks whether a user is admin or not*/
    public static boolean isAdmin(String username) {
        return DatabaseAccess.isAdmin(username);
    }

    /** Promotes a user to admin and returns true if successful.
     *  If the user is already an admin or the promoting user is not an admin,
     *  operation returns false
     */
    public static boolean promoteToAdmin(String admin, String user) {
        return DatabaseAccess.promoteToAdmin(admin, user);
    }

    /** Deletes a user's account if the action is performed by an admin
     * and returns a corresponding boolean
     */
    public static boolean deleteAccount(int adminID, int userID) {
        return DatabaseAccess.deleteAccount(adminID, userID);
    }

    /** Returns  a user's performance on recent N quizzes*/
    public static ArrayList<Score> getRecentPerformance(String username, int quizId, int amount) {
        return DatabaseAccess.getRecentPerformance(username, quizId, amount);
    }

    /**Returns N recent quizzes created by a user */
    public static ArrayList<Quiz> recentCreationsByUser(String username, int maxAmount) {
        return DatabaseAccess.recentCreationsByUser(username, maxAmount);
    }

    /**Returns top performers on a certain quiz for the last day*/
    public static  ArrayList<ScoreAndUser> getTopPerformersForLastDay(int quizID, int amount) {
        return DatabaseAccess.getTopPerformersForLastDay(quizID, amount);

    }

    /** This method must be called after a user takes a quiz. It takes in specified parameters
     *  and updates the database accordingly */
    public  static void quizFinished(int userID, int quizID, int score,
                                     Timestamp date, int secondsTaken, boolean practiceMode, int quizRating) {
         DatabaseAccess.quizFinished( userID,  quizID,  score,
         date,  secondsTaken,  practiceMode,  quizRating);
    }


}