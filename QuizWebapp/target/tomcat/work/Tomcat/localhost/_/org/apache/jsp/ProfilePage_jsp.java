/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2024-07-09 19:50:30 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import DBpackage.*;
import java.util.HashMap;
import DBpackage.DAOpackage.*;

public final class ProfilePage_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    int userId = (Integer) session.getAttribute("userID");
    String username = (String) session.getAttribute("username");
    int profileId = Integer.parseInt(request.getParameter("profileId"));
    //DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    String profile = DatabaseAccess.getUsername(profileId);

    boolean isAdmin = UserDAO.getUserInfo(username).getAdmin_status() == 1;
    boolean isProfileAdmin = UserDAO.getUserInfo(UserDAO.getUsername(profileId)).getAdmin_status() == 1;
    boolean isProfileBanned = UserDAO.getUserInfo(UserDAO.getUsername(profileId)).getActiveAcc() == 0;
    //System.out.println(isProfileBanned);
    ArrayList<Quiz> mostPopularQuizzes = QuizDAO.getPopularQuizzesByUser(profileId, 0);
    ArrayList<Quiz> recentQuizzes = QuizDAO.getRecentQuizzesByUser(profileId, 0);

    ArrayList<Score> mostSuccessfulScores = new ArrayList<Score>();
    ArrayList<Quiz> mostSuccessfulQuizzes = new ArrayList<Quiz>();
    QuizDAO.getMostSuccessfulScoresAndQuizzesForUser(mostSuccessfulScores, mostSuccessfulQuizzes, profileId, 0);
    //System.out.println("MostSuccessfulScores: " + mostSuccessfulScores.size());
    //System.out.println("MostPopularQuizzes: " + mostSuccessfulQuizzes.size());

    ArrayList<Score> recentScoresTaken = new ArrayList<Score>();
    ArrayList<Quiz> recentQuizzesTaken = new ArrayList<Quiz>();
    QuizDAO.getRecentScoresAndQuizzesForUser(recentScoresTaken, recentQuizzesTaken, profileId, 0);
    //System.out.println("RecentScoresTaken: " + recentScoresTaken.size());
    //System.out.println("RecentQuizzesTaken: " + recentQuizzesTaken.size());

    ArrayList<Achievement> achievements = AchievementDAO.getRecentAchievements(profile, 0);
    //System.out.println("Achievements: " + achievements.size());

    ArrayList<Note> chat = NoteDAO.getNotesForChat(userId, profileId, 0);
    //System.out.println("Chat: " + chat.size());

    String LoggedInUser = (String) session.getAttribute("LoggedInUser");
    String VisitedUser = (String) session.getAttribute("VisitedUser");

    boolean drugi = false;
    ArrayList<User> friends = FriendDAO.getFriendlist(LoggedInUser);
    for(User user : friends)
        if(user.getUsername().equals(VisitedUser)) drugi = true;

    boolean waiting = false;
    ArrayList<FriendRequest> requests = FriendDAO.waitingFriendRequests(UserDAO.getUserID(VisitedUser));
    for(FriendRequest friendRequest : requests)
        if (friendRequest.getFrom_username().equals(LoggedInUser)) waiting = true;

    HashMap<String, String> achievPictureMap = new HashMap<String, String>();
    achievPictureMap.put("Amateur author", "Pictures/Achievements/Amateur author.png");
    achievPictureMap.put("I am the greatest", "Pictures/Achievements/I am the greatest.png");
    achievPictureMap.put("Prodigious author", "Pictures/Achievements/Prodigious author.png");
    achievPictureMap.put("Prolific author", "Pictures/Achievements/Prolific author.png");
    achievPictureMap.put("Quiz machine", "Pictures/Achievements/Quiz machine.png");
    achievPictureMap.put("Practice makes perfect", "Pictures/Achievements/Practice makes perfect.png");

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Profile Page</title>\r\n");
      out.write("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"ProfilePage.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    <div class = \"container\">\r\n");
      out.write("        <h1 align=\"center\">");
      out.print(profile);
      out.write("</h1>\r\n");
      out.write("\r\n");
      out.write("        ");
if (drugi) {
      out.write("\r\n");
      out.write("        <div class=\"FRIEND-container\">\r\n");
      out.write("            <div class=\"FRIEND\">\r\n");
      out.write("                <p class=\"waitfa\">Friends</p>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        ");
}

        else if (waiting) {
      out.write("\r\n");
      out.write("        <div class=\"FRIEND-container\">\r\n");
      out.write("            <div class=\"FRIEND\">\r\n");
      out.write("            <p class=\"waitfa\">Waiting For Answer</p>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        ");
}

        else{ 
      out.write("\r\n");
      out.write("        <div class=\"FRIEND-container\">\r\n");
      out.write("            <form action=\"");
      out.print( request.getContextPath() );
      out.write("/AddFriendServlet\" method=\"post\">\r\n");
      out.write("                <button type=\"submit\" class=\"fixed-button\">Add Friend</button>\r\n");
      out.write("            </form>\r\n");
      out.write("        </div>\r\n");
      out.write("        ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("        <div class = \"tabs-container\">\r\n");
      out.write("            <ul class = \"tabs\">\r\n");
      out.write("                <li class = \"tab-link active\" data-tab = \"tab1\">Most Popular Quizzes</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab2\">Recently Created Quizzes</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab3\">Most Successful Quizzes</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab4\">Recently Taken Quizzes</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab5\">Achievements</li>\r\n");
      out.write("                <li class = \"tab-link\" data-tab = \"tab6\">Chat</li>\r\n");
      out.write("            </ul>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("        <div id = \"tab1\" class=\"tab-content active\">\r\n");
      out.write("            <h2>");
      out.print(profile);
      out.write("'s Most Popular Quizzes</h2>\r\n");
      out.write("            <div class = \"popuquiz\">\r\n");
      out.write("                ");
 for(Quiz quiz: mostPopularQuizzes) {
      out.write("\r\n");
      out.write("                <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print(quiz.getQuiz_id());
      out.write("\" class=\"quiz-link\">\r\n");
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
      out.write("                    </div>\r\n");
      out.write("                </a>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab2\" class=\"tab-content\">\r\n");
      out.write("            <h2>Recently Created Quizzes</h2>\r\n");
      out.write("            <div class = \"rescre\">\r\n");
      out.write("                ");
 for(Quiz quiz: recentQuizzes) {
      out.write("\r\n");
      out.write("                <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print(quiz.getQuiz_id());
      out.write("\" class=\"quiz-link\">\r\n");
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
      out.write("                    </div>\r\n");
      out.write("                </a>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab3\" class=\"tab-content\">\r\n");
      out.write("            <h2>Most Successful Quizzes</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 for(int i=0; i<mostSuccessfulQuizzes.size(); i++) {
                    Quiz quiz = mostSuccessfulQuizzes.get(i);
                    Score score = mostSuccessfulScores.get(i);
                
      out.write("\r\n");
      out.write("                <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print(quiz.getQuiz_id());
      out.write("\" class=\"quiz-link\">\r\n");
      out.write("                    <div class=\"recently-created-quizzes\">\r\n");
      out.write("                        <h3>");
      out.print(quiz.getName());
      out.write("</h3>\r\n");
      out.write("                        <h3> ");
      out.print("Score: " + score.getScore());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print(quiz.getDescription());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Date Taken: " + score.getDate_scored());
      out.write("</p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </a>\r\n");
      out.write("                ");
 } 
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab4\" class=\"tab-content\">\r\n");
      out.write("            <h2>Recently Taken Quizzes</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 for(int i=0; i<recentQuizzesTaken.size(); i++) {
                    Quiz quiz = recentQuizzesTaken.get(i);
                    Score score = recentScoresTaken.get(i);
                
      out.write("\r\n");
      out.write("                <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print(quiz.getQuiz_id());
      out.write("\" class=\"quiz-link\">\r\n");
      out.write("                    <div class=\"recently-created-quizzes\">\r\n");
      out.write("                        <h3>");
      out.print(quiz.getName());
      out.write("</h3>\r\n");
      out.write("                        <h3> ");
      out.print("Score: " + score.getScore());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print(quiz.getDescription());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Date Taken: " + score.getDate_scored());
      out.write("</p>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </a>\r\n");
      out.write("                ");
 } 
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("            <div id=\"tab5\" class=\"tab-content\">\r\n");
      out.write("                <h2>Achievements</h2>\r\n");
      out.write("                <div class=\"achievements-container\">\r\n");
      out.write("                    ");
 for(Achievement achievement: achievements) {
      out.write("\r\n");
      out.write("                    <div class=\"achievement-item\">\r\n");
      out.write("                        <div class=\"achievement\">\r\n");
      out.write("                            <div class=\"achievement-image\">\r\n");
      out.write("                                <img src=\"");
      out.print(achievPictureMap.get(achievement.getAchievementTitle()));
      out.write("\" alt=\"Achievement Icon\" width=\"100\" height=\"100\">\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"achievement-details\">\r\n");
      out.write("                                <h3>");
      out.print(achievement.getAchievementTitle());
      out.write("</h3>\r\n");
      out.write("                                <p>");
      out.print(achievement.getAchievementDate());
      out.write("</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
}
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab6\" class=\"tab-content\">\r\n");
      out.write("            <h2>Chat</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 for(Note note : chat) { 
      out.write("\r\n");
      out.write("                    <div class=\"note\">\r\n");
      out.write("                        ");
 if(note.getFromUsername().equals(username)) { 
      out.write("\r\n");
      out.write("                            <div class=\"user-note\">\r\n");
      out.write("                                <div class=\"note-details\">\r\n");
      out.write("                                    <p>From: ");
      out.print( note.getFromUsername() );
      out.write("</p>\r\n");
      out.write("                                    <p>");
      out.print( note.getText() );
      out.write("</p>\r\n");
      out.write("                                </div>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        ");
 } else { 
      out.write("\r\n");
      out.write("                        <div class=\"profile-note\">\r\n");
      out.write("                            <div class=\"note-details\">\r\n");
      out.write("                                <p>From: ");
      out.print( note.getFromUsername() );
      out.write("</p>\r\n");
      out.write("                                <p>");
      out.print( note.getText() );
      out.write("</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        ");
 } 
      out.write("\r\n");
      out.write("                    </div>\r\n");
      out.write("                ");
 } 
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("            <div id=\"chatInput\">\r\n");
      out.write("                <input type=\"text\" id=\"messageInput\" placeholder=\"Type your message...\">\r\n");
      out.write("                <button onclick=\"sendMessage()\">Send</button>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"fixed-button-container\">\r\n");
      out.write("            <div>\r\n");
      out.write("                <a href=\"Sender.jsp\" class=\"challenge-button\">Send Challenge</a>\r\n");
      out.write("                ");
 if (isAdmin && !isProfileAdmin && !isProfileBanned) { 
      out.write("\r\n");
      out.write("                <form action=\"PromoteUserServlet\" method=\"post\" style=\"display: inline;\">\r\n");
      out.write("                    <input type=\"hidden\" name=\"profileId\" value=\"");
      out.print( profileId );
      out.write("\">\r\n");
      out.write("                    <button type=\"submit\" class=\"promote-button\">Promote to Admin</button>\r\n");
      out.write("                </form>\r\n");
      out.write("                ");
 } 
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("            <a href=\"UserHomePage.jsp\" class=\"fixed-button\">Home</a>\r\n");
      out.write("            ");
 if (isAdmin && !isProfileAdmin && !isProfileBanned) { 
      out.write("\r\n");
      out.write("            <form action=\"BanUserServlet\" method=\"post\">\r\n");
      out.write("                <input type=\"hidden\" name=\"profileId\" value=\"");
      out.print( profileId );
      out.write("\">\r\n");
      out.write("                <button type=\"submit\" class=\"ban-button\">Ban User</button>\r\n");
      out.write("            </form>\r\n");
      out.write("            ");
 } 
      out.write("\r\n");
      out.write("\r\n");
      out.write("            ");
 if (isAdmin && isProfileBanned) { 
      out.write("\r\n");
      out.write("            <form action=\"UnbanUserServlet\" method=\"post\">\r\n");
      out.write("                <input type=\"hidden\" name=\"profileId\" value=\"");
      out.print( profileId );
      out.write("\">\r\n");
      out.write("                <button type=\"submit\" class=\"unban-button\">Unban User</button>\r\n");
      out.write("            </form>\r\n");
      out.write("            ");
 } 
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
      out.write("    <script>\r\n");
      out.write("        $(document).ready(function(){\r\n");
      out.write("            // Tab functionality\r\n");
      out.write("            $('.tab-link').click(function(){\r\n");
      out.write("                var tab_id = $(this).attr('data-tab');\r\n");
      out.write("\r\n");
      out.write("                $('.tab-link').removeClass('active');\r\n");
      out.write("                $('.tab-content').removeClass('active');\r\n");
      out.write("\r\n");
      out.write("                $(this).addClass('active');\r\n");
      out.write("                $(\"#\"+tab_id).addClass('active');\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("        function sendMessage() {\r\n");
      out.write("            var message = $(\"#messageInput\").val();\r\n");
      out.write("            $.ajax({\r\n");
      out.write("                url: \"ChatServlet\",\r\n");
      out.write("                method: \"POST\",\r\n");
      out.write("                data: { message: message },\r\n");
      out.write("                success: function(response) {\r\n");
      out.write("                    location.reload();\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        // Allow sending message with Enter key\r\n");
      out.write("        $(\"#messageInput\").keypress(function(e) {\r\n");
      out.write("            if(e.which == 13) {\r\n");
      out.write("                sendMessage();\r\n");
      out.write("                return false;\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    </script>\r\n");
      out.write("    <div>\r\n");
      out.write("        <div class=\"wave\"></div>\r\n");
      out.write("        <div class=\"wave\"></div>\r\n");
      out.write("        <div class=\"wave\"></div>\r\n");
      out.write("    </div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<div class=\"bg\"></div>\r\n");
      out.write("<div class=\"bg bg2\"></div>\r\n");
      out.write("<div class=\"bg bg3\"></div>");
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
