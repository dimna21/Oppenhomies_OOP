package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NewAccServlet extends HttpServlet {
    //private AccountManager am;

    @Override
    public void init() throws ServletException {
        // Retrieve the AccountManager from the servlet context
        //am = (AccountManager) getServletContext().getAttribute("AccountManager");
        //if (am == null) {
       //     throw new ServletException("AccountManager not initialized in Listener");
       // }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //// Check if username already exists
        //boolean usernameExists = am.existsAccount(username);

//        if (usernameExists) {
//            // Redirect to accountTaken.jsp
//            RequestDispatcher dispatcher = request.getRequestDispatcher("accountTaken.jsp");
//            dispatcher.forward(request, response);
//        } else {
//            // Account creation successful, create new Account + redirect to homepage.jsp
//            am.addAccount(username, password);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("homePage.jsp");
//            dispatcher.forward(request, response);
//        }
    }
}
