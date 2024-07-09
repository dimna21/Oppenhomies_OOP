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

public class LookUpServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");
        HttpSession session = req.getSession();

        String usernameToLookUp = req.getParameter("usernameToLookUp");
        String LoggedInUser = session.getAttribute("LoggedInUser").toString();

//        System.out.println("LoggedInUser: " + LoggedInUser);
//        System.out.println("username: " + username);
//        System.out.println("-- END OF LOOKUP --");

        boolean exists = (UserDAO.getUserInfo(usernameToLookUp) != null);
        boolean same = usernameToLookUp.equals(LoggedInUser);
        RequestDispatcher dispatcher;
        if(exists){
            if(!same){
                int userID = UserDAO.getUserInfo(usernameToLookUp).getUser_id();

                session.setAttribute("LoggedInUser", LoggedInUser);
                session.setAttribute("VisitedUser", usernameToLookUp);
                dispatcher = req.getRequestDispatcher("ProfilePage.jsp?profileId=" + userID);
            }
            else {
                dispatcher = req.getRequestDispatcher("Visitorpage/narcissist.jsp");
            }
        }else{
            dispatcher = req.getRequestDispatcher("Visitorpage/badstalker.jsp");
        }
        dispatcher.forward(req, res);
    }
}
