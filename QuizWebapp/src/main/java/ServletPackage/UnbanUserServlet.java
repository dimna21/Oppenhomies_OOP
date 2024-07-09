package ServletPackage;


import DBpackage.DAOpackage.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UnbanUserServlet")
public class UnbanUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int profileId = Integer.parseInt(request.getParameter("profileId"));
        UserDAO.unbanAccount(profileId);
        response.sendRedirect("UserHomePage.jsp");
    }
}
