/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2024-07-07 18:11:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.Homepage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import DBpackage.*;
import java.util.HashMap;

public final class Wellcome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

    DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");

    int userID = (Integer)session.getAttribute("userID");
    String username = (String) session.getAttribute("username");

    ArrayList<Announcement> announcements = dbAccess.getLatestAnnouncements(0);
    ArrayList<Quiz> popularQuizzes = dbAccess.getQuizzesByPopularity(0);
    ArrayList<Quiz> recentlyCreatedQuizzes = dbAccess.getNewestQuiz(0);

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <title>Wellcome to Quizzler </title>\r\n");
      out.write("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"");
      out.print( request.getContextPath() );
      out.write("/Homepage/WellWellWell.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("    <div class=\"center-text\">QUIZZLER</div>\r\n");
      out.write("    <hr />\r\n");
      out.write("    <div class=\"container2\">\r\n");
      out.write("        <div class=\"intro-text\">Stick Yo &#9757; Gyatt &#127825; Out For Quizzler &#129397; </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"tabs-container\">\r\n");
      out.write("        <ul class=\"tabs\">\r\n");
      out.write("            <li class=\"tab-link active\" data-tab=\"tab1\">Announcements</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab2\">Popular Quizzes</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab3\">Recently Created Quizzes</li>\r\n");
      out.write("        </ul>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab1\" class=\"tab-content active\">\r\n");
      out.write("            <h2>Announcements by Administration</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 for(Announcement announcement : announcements) {
      out.write("\r\n");
      out.write("                <div class=\"announcement\">\r\n");
      out.write("                    <h3>");
      out.print(announcement.getTitle());
      out.write("</h3>\r\n");
      out.write("                    <p>");
      out.print(announcement.getText());
      out.write("</p>\r\n");
      out.write("                    <p>");
      out.print("Date: " + announcement.getCreationDate());
      out.write("</p>\r\n");
      out.write("                    <p>");
      out.print("By: " + announcement.getUsername());
      out.write("</p>\r\n");
      out.write("                </div>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab2\" class=\"tab-content\">\r\n");
      out.write("            <h2>Popular Quizzes</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 for(Quiz quiz: popularQuizzes) {
      out.write("\r\n");
      out.write("\r\n");
      out.write("                    <div class=\"popular-quizzes\">\r\n");
      out.write("                        <h3>");
      out.print(quiz.getName());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print(quiz.getDescription());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Creation Date: " + quiz.getCreationDate());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Author: " + quiz.getCreatorUsername());
      out.write(" </p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </a>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab3\" class=\"tab-content\">\r\n");
      out.write("            <h2>Recently Created Quizzes</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 for(Quiz quiz: recentlyCreatedQuizzes) {
      out.write("\r\n");
      out.write("                    <div class=\"recently-created-quizzes\">\r\n");
      out.write("                        <h3>");
      out.print(quiz.getName());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print(quiz.getDescription());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Creation Date: " + quiz.getCreationDate());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Author: " + quiz.getCreatorUsername());
      out.write(" </p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </a>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<div class=\"opciones-container\">\r\n");
      out.write("    <div class=\"opciones\">\r\n");
      out.write("        <a href=\"");
      out.print( request.getContextPath() );
      out.write("/UserAuthentication/loginPage.jsp\" class=\"button\">Sign In</a>\r\n");
      out.write("        <a href=\"");
      out.print( request.getContextPath() );
      out.write("/UserAuthentication/newAccount.jsp\" class=\"button\">Sign Up</a>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("    $(document).ready(function(){\r\n");
      out.write("        $(\".tab-link\").click(function(){\r\n");
      out.write("            var tabId = $(this).attr(\"data-tab\");\r\n");
      out.write("\r\n");
      out.write("            // Remove active class from all tabs\r\n");
      out.write("            $(\".tab-link\").removeClass(\"active\");\r\n");
      out.write("            $(\".tab-content\").removeClass(\"active\");\r\n");
      out.write("\r\n");
      out.write("            // Add active class to the clicked tab\r\n");
      out.write("            $(this).addClass(\"active\");\r\n");
      out.write("            $(\"#\" + tabId).addClass(\"active\");\r\n");
      out.write("        });\r\n");
      out.write("    });\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
