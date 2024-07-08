package DBpackage.DAOpackage;

import DBpackage.DatabaseAccess;
import DBpackage.Questions.Question;
import DBpackage.Quiz;
import DBpackage.Score;
import DBpackage.ScoreAndUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class QuizDAO {
    public static Quiz getQuizInfo(int quiz_id) {
        return DatabaseAccess.getQuizInfo(quiz_id);
    }

    public static ArrayList<Quiz> getNewestQuiz(int amountToGet) {
        return DatabaseAccess.getNewestQuiz(amountToGet);
    }

    public static ArrayList<Question> getQuizQuestions(int quizId) {
        return DatabaseAccess.getQuizQuestions(quizId);
    }

    public static HashMap<String, Integer> getTopScorers(int quizID, int numScorers) {
        return DatabaseAccess.getTopScorers(quizID, numScorers);

    }

    public static ArrayList<Quiz> getQuizzesByPopularity(int amountToGet) {
        return DatabaseAccess.getQuizzesByPopularity(amountToGet);

    }

    public static void deleteQuiz(int quizID) {
        DatabaseAccess.deleteQuiz(quizID);

    }

    public static ArrayList<Quiz> getPopularQuiz(int amountToGet) {
        return DatabaseAccess.getPopularQuiz(amountToGet);

    }

    public static ArrayList<Score> getLastAttemptsOfUserOnQuiz(String username, int quizId, int amount) {
        return DatabaseAccess.getLastAttemptsOfUserOnQuiz(username, quizId, amount);

    }

    public static ArrayList<Score> getLastAttemptsOfUser(String username, int amount) {
        return DatabaseAccess.getLastAttemptsOfUser(username, amount);

    }

    public static void recentQuizTakingActivitiesForUser(int userID, ArrayList<Score> scores, ArrayList<Quiz> quizzes) {
        DatabaseAccess.recentQuizTakingActivitiesForUser(userID, scores, quizzes);

    }

    public static double getAverageTime(int quizID) {
        return DatabaseAccess.getAverageTime(quizID);

    }

    public static double getAverageScore(int quizID) {
        return DatabaseAccess.getAverageScore(quizID);

    }

    public static double getQuizRating(int quizID) {
        return DatabaseAccess.getQuizRating(quizID);

    }

    public static void deleteQuizAndRelatedQuestions(int quizId) {
        DatabaseAccess.getQuizRating(quizId);
    }
    public static  int createQuizAndGetID(String quizName, String quizDescription,
                                          int creatorID, String creatorUsername, int randomQuestion,
                                          int immediate, int practice, int onePage, Timestamp creationDate) {

        return DatabaseAccess.createQuizAndGetID( quizName,  quizDescription,
         creatorID,  creatorUsername,  randomQuestion,
         immediate,  practice,  onePage,  creationDate);

    }
    public static  void createQuizWithID(int quizId,String quizName, String quizDescription,
                                         int creatorID, String creatorUsername, int randomQuestion,
                                         int immediate, int practice, int onePage, Timestamp creationDate) {
        DatabaseAccess.createQuizWithID(quizId, quizName,  quizDescription,
                creatorID,  creatorUsername,  randomQuestion,
                immediate,  practice,  onePage,  creationDate);
    }
    public  static void populateQuiz(ArrayList<Question> questions) {
        DatabaseAccess.populateQuiz(questions);

    }
    public  static ArrayList<ScoreAndUser> getTopPerformers(int quizID, int amount) {
        return DatabaseAccess.getTopPerformers( quizID,  amount);

    }
    public static  ArrayList<ScoreAndUser> getRecentPerformers(int quizID, int amount) {
        return DatabaseAccess.getRecentPerformers( quizID,  amount);

    }
    public  static ArrayList<Quiz> getPopularQuizzesByUser(int userId, int amount) {
        return DatabaseAccess.getPopularQuizzesByUser( userId,  amount);

    }

    public static  ArrayList<Quiz> getRecentQuizzesByUser(int userId, int amount) {
        return DatabaseAccess.getPopularQuizzesByUser( userId,  amount);

    }
    private  static ArrayList<Quiz> getQuizzesFromResultSet(ResultSet rs) throws SQLException {
        return DatabaseAccess.getQuizzesFromResultSet( rs);

    }
    public static  void getMostSuccessfulScoresAndQuizzesForUser(ArrayList<Score> scores, ArrayList<Quiz> quizzes,int userId, int amount) {
         DatabaseAccess.getMostSuccessfulScoresAndQuizzesForUser( scores,quizzes,userId,amount);

    }
    private  static ArrayList<Score> getScoresFromResultSet(ResultSet rs) throws SQLException {
        return DatabaseAccess.getScoresFromResultSet( rs);

    }
    public static  void getRecentScoresAndQuizzesForUser(ArrayList<Score> scores, ArrayList<Quiz> quizzes, int userId, int amount) {
         DatabaseAccess.getRecentScoresAndQuizzesForUser( scores,quizzes,userId,amount);

    }


    }
