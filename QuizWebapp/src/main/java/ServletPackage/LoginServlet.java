package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import DBpackage.DatabaseAccess;

public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String hashedPass = "";

        boolean isValid = false;
        isValid = dbAccess.login(username, hashedPass);

        if(isValid){
            RequestDispatcher dispatcher = req.getRequestDispatcher("goodLogin.jsp");
            dispatcher.forward(req, res);
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("badLogin.jsp");
            dispatcher.forward(req, res);
        }
    }
}
