package ServletPackage;

import DBpackage.DatabaseAccess;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class ListenerClass implements ServletContextListener {
    //EmptyConstructor
    public ListenerClass(){

    }
    //AccountListener Initialization
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        try {
            DatabaseAccess dbAccess = new DatabaseAccess();
            servletContext.setAttribute("DatabaseAccess", dbAccess);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Empty contextDestroyed
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
