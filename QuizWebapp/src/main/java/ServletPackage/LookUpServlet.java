package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import DBpackage.DAOpackage.FriendDAO;
import DBpackage.DAOpackage.UserDAO;
import DBpackage.DatabaseAccess;
import DBpackage.User;

public class LookUpServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String usernameToLookUp = req.getParameter("usernameToLookUp");
        Object loggedInUserObj = session.getAttribute("LoggedInUser");

        if (loggedInUserObj == null) {
            res.sendRedirect("login.jsp"); // Redirect to login page if user is not logged in
            return;
        }

        String LoggedInUser = loggedInUserObj.toString();

        User userInfo = UserDAO.getUserInfo(usernameToLookUp);
        boolean exists = (userInfo != null);
        boolean same = usernameToLookUp.equals(LoggedInUser);

        RequestDispatcher dispatcher;
        if (exists) {
            if (!same) {
                int userID = userInfo.getUser_id();
                session.setAttribute("VisitedUser", usernameToLookUp);
                dispatcher = req.getRequestDispatcher("ProfilePage.jsp?profileId=" + userID);
            } else {
                dispatcher = req.getRequestDispatcher("Visitorpage/narcissist.jsp");
            }
        } else {
            dispatcher = req.getRequestDispatcher("Visitorpage/badstalker.jsp");
        }
        dispatcher.forward(req, res);
    }
}
