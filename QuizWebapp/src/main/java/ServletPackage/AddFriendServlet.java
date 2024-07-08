package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import DBpackage.DAOpackage.FriendDAO;
import DBpackage.DAOpackage.UserDAO;
import DBpackage.DatabaseAccess;
public class AddFriendServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");
        HttpSession session = req.getSession();

        String VisitedUser = session.getAttribute("VisitedUser").toString();
        String LoggedInUser = session.getAttribute("LoggedInUser").toString();
        int userID = UserDAO.getUserInfo(VisitedUser).getUser_id();

        try {
            FriendDAO.sendFriendRequest(LoggedInUser, VisitedUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher;
        dispatcher = req.getRequestDispatcher("ProfilePage.jsp?profileId=" + userID);
        dispatcher.forward(req, res);
    }
}
