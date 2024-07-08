package DBpackage.DAOpackage;
import DBpackage.*;
import DBpackage.Questions.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChallengeDAO {
    public static void getChallengesForUser(int userID, ArrayList<Challenge> challenges, ArrayList<Quiz> quizzes) {
        DatabaseAccess.getChallengesForUser(userID, challenges, quizzes);
    }

    public static void sendChallenge(String from, String to, int quizID) {
        DatabaseAccess.sendChallenge(from, to, quizID);

    }
    public  static void answerChallenge(int userAnswering, int answeringTo, int status) throws SQLException {
        DatabaseAccess.answerChallenge(userAnswering, answeringTo, status);

    }


    }
