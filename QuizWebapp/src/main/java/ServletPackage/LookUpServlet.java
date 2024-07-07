package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import DBpackage.DatabaseAccess;

public class LookUpServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");

        String username = req.getParameter("username");

        boolean exists = (dbAccess.getUserInfo(username) != null);

        RequestDispatcher dispatcher;
        if(exists){
            int userID = dbAccess.getUserInfo(username).getUser_id();
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            dispatcher = req.getRequestDispatcher("ProfilePage.jsp?profileId=" + userID);
        }else{
            dispatcher = req.getRequestDispatcher("Visitorpage/badstalker.jsp");
        }
        dispatcher.forward(req, res);
    }
}
