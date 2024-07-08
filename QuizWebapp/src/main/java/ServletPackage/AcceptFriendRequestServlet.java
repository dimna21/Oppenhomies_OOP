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

import DBpackage.DatabaseAccess;

public class AcceptFriendRequestServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");
        HttpSession session = req.getSession();

        String LoggedInUser = session.getAttribute("LoggedInUser").toString();
        ArrayList<String> Senders = (ArrayList<String>)session.getAttribute("SenderID");
        String Sender = Senders.get(0);

        int userID = dbAccess.getUserInfo(LoggedInUser).getUser_id();
        int SenderID = dbAccess.getUserInfo(Sender).getUser_id();
        try {
            dbAccess.updateFriendRequestStatus(userID, SenderID, 1);
            dbAccess.sendFriendRequest(LoggedInUser, Sender);
            dbAccess.updateFriendRequestStatus(SenderID, userID, 1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Senders.remove(0);
        session.setAttribute("SenderID", Senders);
        RequestDispatcher dispatcher;
        dispatcher = req.getRequestDispatcher("UserHomePage.jsp");
        dispatcher.forward(req, res);
    }
}
