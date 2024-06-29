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

        boolean isValid;

        try {
            isValid = dbAccess.login(username, password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher;
        if(isValid){
            //goodLogin.jsp will be replaced by the UserPage :3
            dispatcher = req.getRequestDispatcher("goodLogin.jsp");
        }else{
            dispatcher = req.getRequestDispatcher("UserAuthentication/badLogin.jsp");
        }
        dispatcher.forward(req, res);
    }
}
