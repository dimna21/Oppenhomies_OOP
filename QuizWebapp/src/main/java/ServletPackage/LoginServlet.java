package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import DBpackage.DatabaseAccess;

public class LoginServlet extends HttpServlet {
    private DatabaseAccess dbAcc;

    @Override
    public void init() throws ServletException {
        try {
            dbAcc = new DatabaseAccess();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String hashedPass = "";

        boolean isValid = dbAcc.login(username, hashedPass);

        if(isValid){
            RequestDispatcher dispatcher = req.getRequestDispatcher("goodLogin.jsp");
            dispatcher.forward(req, res);
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("badLogin.jsp");
            dispatcher.forward(req, res);
        }
    }
}
