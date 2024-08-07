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

import DBpackage.DAOpackage.FriendDAO;
import DBpackage.DAOpackage.UserDAO;
import DBpackage.DatabaseAccess;
import DBpackage.FriendRequest;

public class AcceptFriendRequestServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");
        HttpSession session = req.getSession();

        String LoggedInUser = session.getAttribute("LoggedInUser").toString();
        int userID = UserDAO.getUserInfo(LoggedInUser).getUser_id();
        String Sender = "";

        int requestId = Integer.parseInt(req.getParameter("requestId"));
        System.out.println("Request ID: " + requestId);
        ArrayList<FriendRequest> fr = FriendDAO.waitingFriendRequests(userID);
        for (FriendRequest frm : fr)
            if (frm.getRequestId() == requestId)
                Sender = frm.getFrom_username();

        int SenderID = UserDAO.getUserInfo(Sender).getUser_id();

        try {
            FriendDAO.updateFriendRequestStatus(userID, SenderID, 1);
            FriendDAO.sendFriendRequest(LoggedInUser, Sender);
            FriendDAO.updateFriendRequestStatus(SenderID, userID, 1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher;
        dispatcher = req.getRequestDispatcher("UserHomePage.jsp");
        dispatcher.forward(req, res);
    }
}
