package ServletPackage;

import DBpackage.DatabaseAccess;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewAccServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean accountExists = dbAccess.accountExists(username);

        RequestDispatcher dispatcher;
        if (accountExists) {
            // Redirect to accountTaken.jsp
            dispatcher = request.getRequestDispatcher("UserAuthentication/accountTaken.jsp");
        } else {
            // Account creation successful, create new Account
            // Redirects to userPage when created
            dbAccess.addUser(username,password, 0);
            dispatcher = request.getRequestDispatcher("homePage.jsp");
        }
        dispatcher.forward(request, response);
    }
}
