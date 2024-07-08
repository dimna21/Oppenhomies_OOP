package ServletPackage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TakeQuizServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve the username from the session
        String username = (String) session.getAttribute("username");

        // Retrieve the quiz ID from the request
        String quizIdStr = request.getParameter("quizId");
        int quizId = Integer.parseInt(quizIdStr);

        // Here, you can add your business logic
        // For example, you might want to check if the user is eligible to take the quiz,
        // log the quiz attempt, or perform other operations

        // For now, we will just forward the user to the quiz page
        request.setAttribute("quizId", quizId);

        request.getRequestDispatcher("QuizPage.jsp").forward(request, response);
    }
}
