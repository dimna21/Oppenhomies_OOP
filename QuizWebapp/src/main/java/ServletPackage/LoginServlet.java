package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import DBpackage.DAOpackage.UserDAO;
import DBpackage.DatabaseAccess;

public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isValid;
        isValid = UserDAO.login(username, password);

        RequestDispatcher dispatcher;
        if(isValid){
            int userID = UserDAO.getUserInfo(username).getUser_id();
            HttpSession session = req.getSession();
            session.setAttribute("loginStatus", 1);
            session.setAttribute("userID", userID);
            session.setAttribute("username", username);
            dispatcher = req.getRequestDispatcher("UserHomePage.jsp");
        }else{
            dispatcher = req.getRequestDispatcher("UserAuthentication/badLogin.jsp");
        }
        dispatcher.forward(req, res);
    }
}
