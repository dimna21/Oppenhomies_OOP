package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import DBpackage.Challenge;
import DBpackage.DatabaseAccess;
import DBpackage.Quiz;


public class RejectChallengeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");
        HttpSession session = req.getSession();

        String LoggedInUser = session.getAttribute("LoggedInUser").toString();
        int userID = dbAccess.getUserInfo(LoggedInUser).getUser_id();
        String Sender = "";

        int challengeId = Integer.parseInt(req.getParameter("challengeId"));
        int quizId = 0;
        ArrayList<Challenge> challenges = new ArrayList<Challenge>();
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        dbAccess.getWaitingChallengesForUser(userID, challenges, quizzes);
        for (Challenge challenge : challenges) {
            if (challenge.getRequestId() == challengeId) {
                Sender = challenge.getFrom_username();
                quizId = challenge.getQuiz_id();
            }
        }


        int SenderID = dbAccess.getUserInfo(Sender).getUser_id();

        try {
            dbAccess.answerChallenge(userID, SenderID, 2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher;
        dispatcher = req.getRequestDispatcher("UserHomePage.jsp");
        dispatcher.forward(req, resp);
    }
}
