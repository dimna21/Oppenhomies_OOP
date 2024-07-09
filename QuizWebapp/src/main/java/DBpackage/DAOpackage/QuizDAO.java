package DBpackage.DAOpackage;

import DBpackage.DatabaseAccess;
import DBpackage.Questions.Question;
import DBpackage.Quiz;
import DBpackage.Score;
import DBpackage.ScoreAndUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class QuizDAO {

    /** Returns a Quiz object using the quiz ID */
    public static Quiz getQuizInfo(int quiz_id) {
        return DatabaseAccess.getQuizInfo(quiz_id);
    }

    /**Returns the last N quizzes created*/
    public static ArrayList<Quiz> getNewestQuiz(int amountToGet) {
        return DatabaseAccess.getNewestQuiz(amountToGet);
    }

    /**Returns an arraylist of every question on a quiz*/
    public static ArrayList<Question> getQuizQuestions(int quizId) {
        return DatabaseAccess.getQuizQuestions(quizId);
    }

    /** Returns top scorers on a quiz */
    public static HashMap<String, Integer> getTopScorers(int quizID, int numScorers) {
        return DatabaseAccess.getTopScorers(quizID, numScorers);

    }

    /** Returns top N quizzes by popularity  */
    public static ArrayList<Quiz> getQuizzesByPopularity(int amountToGet) {
        return DatabaseAccess.getQuizzesByPopularity(amountToGet);

    }

    /** Deletes a quiz from the database */
    public static void deleteQuiz(int quizID) {
        DatabaseAccess.deleteQuiz(quizID);

    }

    /** Returns top N popular quizzes */
    public static ArrayList<Quiz> getPopularQuiz(int amountToGet) {
        return DatabaseAccess.getPopularQuiz(amountToGet);

    }

    /** Returns last attempts of a user on one quiz*/
    public static ArrayList<Score> getLastAttemptsOfUserOnQuiz(String username, int quizId, int amount) {
        return DatabaseAccess.getLastAttemptsOfUserOnQuiz(username, quizId, amount);

    }

    /** Returns last attempts of a user on all quizzes*/
    public static ArrayList<Score> getLastAttemptsOfUser(String username, int amount) {
        return DatabaseAccess.getLastAttemptsOfUser(username, amount);

    }

    /**Returns latest quiz activities of a user*/
    public static void recentQuizTakingActivitiesForUser(int userID, ArrayList<Score> scores, ArrayList<Quiz> quizzes) {
        DatabaseAccess.recentQuizTakingActivitiesForUser(userID, scores, quizzes);

    }

    /**Returns average time taken on a quiz in seconds*/
    public static double getAverageTime(int quizID) {
        return DatabaseAccess.getAverageTime(quizID);

    }

    /**Returns average score on a quiz*/
    public static double getAverageScore(int quizID) {
        return DatabaseAccess.getAverageScore(quizID);

    }

    /** Returns quiz rating on a scale from 0 to 10 as a double*/
    public static double getQuizRating(int quizID) {
        return DatabaseAccess.getQuizRating(quizID);

    }

    /** deletes a quiz from the database */
    public static void deleteQuizAndRelatedQuestions(int quizId) {
        DatabaseAccess.getQuizRating(quizId);
    }

    /** Creates a quiz object in database and returns its quizID */
    public static  int createQuizAndGetID(String quizName, String quizDescription,
                                          int creatorID, String creatorUsername, int randomQuestion,
                                          int immediate, int practice, int onePage, Timestamp creationDate) {

        return DatabaseAccess.createQuizAndGetID( quizName,  quizDescription,
         creatorID,  creatorUsername,  randomQuestion,
         immediate,  practice,  onePage,  creationDate);

    }

    /**Creates a quiz without populating it with questions*/
    public static  void createQuizWithID(int quizId,String quizName, String quizDescription,
                                         int creatorID, String creatorUsername, int randomQuestion,
                                         int immediate, int practice, int onePage, Timestamp creationDate) {
        DatabaseAccess.createQuizWithID(quizId, quizName,  quizDescription,
                creatorID,  creatorUsername,  randomQuestion,
                immediate,  practice,  onePage,  creationDate);
    }

    /** Populates database with quiz questions */
    public  static void populateQuiz(ArrayList<Question> questions) {
        DatabaseAccess.populateQuiz(questions);

    }

    /**Returns top N performers on a quiz*/
    public  static ArrayList<ScoreAndUser> getTopPerformers(int quizID, int amount) {
        return DatabaseAccess.getTopPerformers( quizID,  amount);

    }

    /**Returns recent quiztakers of a quiz*/
    public static  ArrayList<ScoreAndUser> getRecentPerformers(int quizID, int amount) {
        return DatabaseAccess.getRecentPerformers( quizID,  amount);

    }

    /**Returns top N quizzes created by a user*/
    public  static ArrayList<Quiz> getPopularQuizzesByUser(int userId, int amount) {
        return DatabaseAccess.getPopularQuizzesByUser( userId,  amount);

    }

    /**Returns a user's latest quiz creations*/
    public static  ArrayList<Quiz> getRecentQuizzesByUser(int userId, int amount) {
        return DatabaseAccess.getPopularQuizzesByUser( userId,  amount);

    }


    private  static ArrayList<Quiz> getQuizzesFromResultSet(ResultSet rs) throws SQLException {
        return DatabaseAccess.getQuizzesFromResultSet( rs);

    }

    /**Returns top scoring stats for a user*/
    public static  void getMostSuccessfulScoresAndQuizzesForUser(ArrayList<Score> scores, ArrayList<Quiz> quizzes,int userId, int amount) {
         DatabaseAccess.getMostSuccessfulScoresAndQuizzesForUser( scores,quizzes,userId,amount);

    }


    private  static ArrayList<Score> getScoresFromResultSet(ResultSet rs) throws SQLException {
        return DatabaseAccess.getScoresFromResultSet( rs);

    }

    /** Returns a user's recent scored quizzes */
    public static  void getRecentScoresAndQuizzesForUser(ArrayList<Score> scores, ArrayList<Quiz> quizzes, int userId, int amount) {
         DatabaseAccess.getRecentScoresAndQuizzesForUser( scores,quizzes,userId,amount);

    }

    /**Returns quizzes with a similar name to an input for search purposes*/
    public static ArrayList<Quiz> getQuizBySimilarName(String name) {
        return DatabaseAccess.getQuizBySimilarName(name);
    }

    /**Returns stats for a percentage histogram*/
    public static ArrayList<Integer> getScoreRangeCounts(int quizID) {
        return DatabaseAccess.getScoreRangeCounts(quizID);
    }

    /**Returns how many times a quiz has been taken*/
    public static int amountOfTimesTaken(int quizID) {
        return DatabaseAccess.amountOfTimesTaken(quizID);

    }

    /**Returns how many different users have taken a quiz*/
    public static int amountOfDifferentUsersTaken(int quizID) {
        return DatabaseAccess.amountOfDifferentUsersTaken(quizID);
    }

}
