package ServletPackage;

import DBpackage.DAOpackage.UserDAO;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateProfilePictureServlet")
public class UpdateProfilePictureServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUrl = request.getParameter("profilePictureUrl");
        String username = (String) request.getSession().getAttribute("username");

        // Update the profile picture URL in the database
        if (newUrl != null && !newUrl.isEmpty()) {
            UserDAO.updatePicture(username, newUrl);
        }

        // Redirect back to the profile page
        response.sendRedirect("MyProfile.jsp");
    }
}
