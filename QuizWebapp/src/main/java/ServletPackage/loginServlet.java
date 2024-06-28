package ServletPackage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class loginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //To do
        boolean isValid = false;

        if(isValid){
            RequestDispatcher dispatcher = req.getRequestDispatcher("goodLogin.jsp");
            dispatcher.forward(req, res);
        }else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("badLogin.jsp");
            dispatcher.forward(req, res);
        }
    }
}
