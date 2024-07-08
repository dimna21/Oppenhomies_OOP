/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2024-07-08 08:32:04 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import DBpackage.Quiz;
import DBpackage.DatabaseAccess;
import DBpackage.Score;
import java.util.ArrayList;
import DBpackage.ScoreAndUser;

public final class QuizSummeryPage_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

    String user = (String) session.getAttribute("username");
    int userId = (Integer) session.getAttribute("userID");

    DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    Quiz quiz = dbAccess.getQuizInfo(Integer.parseInt(request.getParameter("quizId")));
    String quizName = quiz.getName();
    String quizDescription = quiz.getDescription();
    String quizCreator = quiz.getCreatorUsername();
    int quizCreatorID = quiz.getCreatorID();

    ArrayList<Score> pastScores = dbAccess.getLastAttemptsOfUserOnQuiz(user, quiz.getQuiz_id(), 0);
    ArrayList<ScoreAndUser> highestPerformers = dbAccess.getTopPerformers(quiz.getQuiz_id(), 10);
    ArrayList<ScoreAndUser> lastDayHighestPerformers = dbAccess.getTopPerformersForLastDay(quiz.getQuiz_id(), 10);
    ArrayList<ScoreAndUser> recentScores = dbAccess.getRecentPerformers(quiz.getQuiz_id(), 0);

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Quiz Summary</title>\r\n");
      out.write("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print( request.getContextPath() );
      out.write("/QuizSummeryPage.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div class = \"container\">\r\n");
      out.write("        <h1 align = \"center\">");
      out.print(quizName);
      out.write("</h1>\r\n");
      out.write("\r\n");
      out.write("        <div class = \"tabs-container\">\r\n");
      out.write("            <ul class = \"tabs\">\r\n");
      out.write("                <li class = \"tab-link active\" data-tab = \"tab1\">Your Past Scores</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab2\">Highest Performers</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab3\">Yesterday's Highest Performers</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab4\">Recent Scores for</li>\r\n");
      out.write("                <li class = \"tab-link\" datatype = \"tab5\">Statistics</li>\r\n");
      out.write("            </ul>\r\n");
      out.write("\r\n");
      out.write("            <div id = \"tab1\" class = \"tab-content active\">\r\n");
      out.write("                <h2>Your Past Scores</h2>\r\n");
      out.write("                <div>\r\n");
      out.write("                    ");
 for (Score s : pastScores) { 
      out.write("\r\n");
      out.write("                    <div class = \"user-score\">\r\n");
      out.write("                        <h3>");
      out.print("Score: " + s.getScore());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print("Time: " + s.getTime() + " Seconds");
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Date Taken: " + s.getDate_scored());
      out.write("</p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div id = \"tab2\" class = \"tab-content\">\r\n");
      out.write("                <h2>Highest Performers</h2>\r\n");
      out.write("                <div>\r\n");
      out.write("                    ");
 for(ScoreAndUser sa : highestPerformers) {
      out.write("\r\n");
      out.write("                    <div class = \"performer\">\r\n");
      out.write("                        <h3><a href=\"ProfilePage.jsp?profileId=");
      out.print(sa.getUser().getUser_id());
      out.write('"');
      out.write('>');
      out.print(sa.getUser().getUsername());
      out.write("</a> </h3>\r\n");
      out.write("                        <h3>");
      out.print("Score: " + sa.getScore().getScore());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print("Time: " + sa.getScore().getTime() + " Seconds");
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Date Taken: " + sa.getScore().getDate_scored());
      out.write("</p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div id = \"tab3\" class = \"tab-content\">\r\n");
      out.write("                <h2>Yesterday's Highest Performers</h2>\r\n");
      out.write("                <div>\r\n");
      out.write("                    ");
 for(ScoreAndUser sa : lastDayHighestPerformers) { 
      out.write("\r\n");
      out.write("                    <div class = \"performer\">\r\n");
      out.write("                        <h3><a href=\"ProfilePage.jsp?profileId=");
      out.print(sa.getUser().getUser_id());
      out.write('"');
      out.write('>');
      out.print(sa.getUser().getUsername());
      out.write("</a> </h3>\r\n");
      out.write("                        <h3>");
      out.print("Score: " + sa.getScore().getScore());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print("Time: " + sa.getScore().getTime() + " Seconds");
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Date Taken: " + sa.getScore().getDate_scored());
      out.write("</p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div id = \"tab4\" class = \"tab-content\">\r\n");
      out.write("                <h2>Recent Scores for Quiz</h2>\r\n");
      out.write("                <div>\r\n");
      out.write("                    ");
 for(ScoreAndUser sa : recentScores) {
      out.write("\r\n");
      out.write("                    <div class = \"performer\">\r\n");
      out.write("                        <h3><a href=\"ProfilePage.jsp?profileId=");
      out.print(sa.getUser().getUser_id());
      out.write('"');
      out.write('>');
      out.print(sa.getUser().getUsername());
      out.write("</a> </h3>\r\n");
      out.write("                        <h3>");
      out.print("Score: " + sa.getScore().getScore());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print("Time: " + sa.getScore().getTime() + " Seconds");
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Date Taken: " + sa.getScore().getDate_scored());
      out.write("</p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("            <div id = \"tab5\" class = \"tab-content\">\r\n");
      out.write("                <h2>Statistics</h2>\r\n");
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"button-container\">\r\n");
      out.write("            <form action=\"RejectFriendRequestServlet\" method=\"post\">\r\n");
      out.write("                <button type=\"submit\">Take</button>\r\n");
      out.write("            </form>\r\n");
      out.write("            ");
 if(quiz.isPractice() > 0) { 
      out.write("\r\n");
      out.write("            <form action=\"RejectFriendRequestServlet\" method=\"post\">\r\n");
      out.write("                <button type=\"submit\">Practice</button>\r\n");
      out.write("            </form>\r\n");
      out.write("            ");
 } 
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("<script>\r\n");
      out.write("    $(document).ready(function(){\r\n");
      out.write("        // Tab functionality\r\n");
      out.write("        $('.tab-link').click(function(){\r\n");
      out.write("            var tab_id = $(this).attr('data-tab');\r\n");
      out.write("\r\n");
      out.write("            $('.tab-link').removeClass('active');\r\n");
      out.write("            $('.tab-content').removeClass('active');\r\n");
      out.write("\r\n");
      out.write("            $(this).addClass('active');\r\n");
      out.write("            $(\"#\"+tab_id).addClass('active');\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("        // Add hover effect to performers\r\n");
      out.write("        $('.performer, .user-score').hover(\r\n");
      out.write("            function() {\r\n");
      out.write("                $(this).addClass('hover');\r\n");
      out.write("            }, function() {\r\n");
      out.write("                $(this).removeClass('hover');\r\n");
      out.write("            }\r\n");
      out.write("        );\r\n");
      out.write("    });\r\n");
      out.write("</script>\r\n");
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
