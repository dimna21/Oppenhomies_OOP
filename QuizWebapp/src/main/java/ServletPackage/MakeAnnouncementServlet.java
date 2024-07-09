package ServletPackage;

import DBpackage.DAOpackage.AnnouncementDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/MakeAnnouncementServlet")
public class MakeAnnouncementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            boolean success = AnnouncementDAO.makeAnnouncement(username, title, text, timestamp);
            if (success) {
                response.getWriter().write("Announcement created successfully");
            } else {
                response.getWriter().write("Failed to create announcement");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
