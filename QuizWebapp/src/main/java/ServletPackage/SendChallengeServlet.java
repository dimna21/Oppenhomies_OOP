package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import DBpackage.DAOpackage.ChallengeDAO;
import DBpackage.DAOpackage.UserDAO;

public class SendChallengeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String quizId = req.getParameter("Quiz ID");
        int QUIZID = Integer.parseInt(quizId);


        String VisitedUser = session.getAttribute("VisitedUser").toString();
        String LoggedInUser = session.getAttribute("LoggedInUser").toString();
        int userID = UserDAO.getUserInfo(VisitedUser).getUser_id();

        ChallengeDAO.sendChallenge(LoggedInUser, VisitedUser, QUIZID);

        RequestDispatcher dispatcher;
        dispatcher = req.getRequestDispatcher("ProfilePage.jsp?profileId=" + userID);
        dispatcher.forward(req, resp);
    }
}
