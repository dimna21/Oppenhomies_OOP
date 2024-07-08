package ServletPackage;

import DBpackage.DatabaseAccess;
import DBpackage.Note;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DatabaseAccess dbAccess = (DatabaseAccess) getServletContext().getAttribute("DatabaseAccess");
        HttpSession session = request.getSession();

        String LoggedInUser = (String) session.getAttribute("LoggedInUser");
        String VisitedUSer = (String) session.getAttribute("VisitedUser");
        int userID = dbAccess.getUserID(VisitedUSer);

        String message = request.getParameter("message");
        dbAccess.createNote(LoggedInUser, VisitedUSer, message);
    }
}