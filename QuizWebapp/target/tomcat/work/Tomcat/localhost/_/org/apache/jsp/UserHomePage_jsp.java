/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2024-07-09 12:33:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import DBpackage.*;
import java.util.HashMap;
import DBpackage.DAOpackage.*;

public final class UserHomePage_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    //DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");

    int userID = (Integer)session.getAttribute("userID");
    String username = (String) session.getAttribute("username");
    User user = UserDAO.getUserInfo(username);

    ArrayList<Announcement> announcements = AnnouncementDAO.getLatestAnnouncements(0);
    ArrayList<Quiz> popularQuizzes = QuizDAO.getQuizzesByPopularity(0);
    ArrayList<Quiz> recentlyCreatedQuizzes = QuizDAO.getNewestQuiz(0);

    ArrayList<Score> recentScores = new ArrayList<Score>();
    ArrayList<Quiz> corrQuizzes = new ArrayList<Quiz>(); // Corresponding quizzes to recent Scores

    QuizDAO.recentQuizTakingActivitiesForUser(userID, recentScores, corrQuizzes);

    ArrayList<Quiz> recentQuizzesByUser = UserDAO.recentCreationsByUser(username,0);

    ArrayList<Achievement> achievements = AchievementDAO.getRecentAchievements(username,0);

    ArrayList<Challenge> challenges = new ArrayList<Challenge>();
    ArrayList<Quiz> quizzesForChallenges = new ArrayList<Quiz>();
    ChallengeDAO.getWaitingChallengesForUser(userID, challenges, quizzesForChallenges);

    ArrayList<FriendRequest> friendRequests = FriendDAO.waitingFriendRequests(userID);
    ArrayList<Note> notes = DatabaseAccess.getNotes(userID, 0);

    ArrayList<Activity> activities = FriendDAO.getFriendsActivity(username, 0);

    HashMap<String, String> achievPictureMap = new HashMap<String, String>();
    achievPictureMap.put("Amateur author", "Pictures/Achievements/Amateur author.png");
    achievPictureMap.put("I am the greatest", "Pictures/Achievements/I am the greatest.png");
    achievPictureMap.put("Prodigious author", "Pictures/Achievements/Prodigious author.png");
    achievPictureMap.put("Prolific author", "Pictures/Achievements/Prolific author.png");
    achievPictureMap.put("Quiz machine", "Pictures/Achievements/Quiz machine.png");
    achievPictureMap.put("Practice makes perfect", "Pictures/Achievements/Practice makes perfect.png");

    String searchQuery = request.getParameter("searchQuery");
    ArrayList<Quiz> searchResults = new ArrayList<Quiz>();
    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        searchResults = QuizDAO.getQuizBySimilarName(searchQuery);
    }

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>User Home Page</title>\r\n");
      out.write("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>\r\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"UserHomePage.css?v=2.0\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("    <div class=\"user-info\">\r\n");
      out.write("        <div class=\"user-name\">");
      out.print(username);
      out.write("</div>\r\n");
      out.write("        <form action=\"CreateQuiz.jsp\" method=\"post\">\r\n");
      out.write("            <button type=\"submit\">Create Quiz</button>\r\n");
      out.write("        </form>\r\n");
      out.write("        <form action=\"");
      out.print( request.getContextPath() );
      out.write("/LogoutServlet\" method=\"post\">\r\n");
      out.write("            <button type=\"submit\">Log Out</button>\r\n");
      out.write("        </form>\r\n");
      out.write("    </div>\r\n");
      out.write("    <br><br><br>\r\n");
      out.write("    <div class=\"tabs-container\">\r\n");
      out.write("        <ul class=\"tabs\">\r\n");
      out.write("            <li class=\"tab-link active\" data-tab=\"tab1\">Announcements</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab2\">Popular Quizzes</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab3\">Recently Created Quizzes</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab4\">Your Quiz Taking Record</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab5\">Quizzes Created by You</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab6\">Your Achievements</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab7\">Inbox</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab8\">Activities of Your Friends</li>\r\n");
      out.write("            <li class=\"tab-link\" data-tab=\"tab9\">Search for quizzes</li>\r\n");
      out.write("        </ul>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab1\" class=\"tab-content active\">\r\n");
      out.write("            <h2>Announcements by Administration</h2>\r\n");
      out.write("            ");
 if (user.getAdmin_status() == 1) { 
      out.write("\r\n");
      out.write("            <button id=\"createAnnouncementBtn\">Create Announcement</button>\r\n");
      out.write("            <div id=\"announcementForm\" style=\"display: none;\">\r\n");
      out.write("                <input type=\"text\" id=\"announcementTitle\" placeholder=\"Title\">\r\n");
      out.write("                <textarea id=\"announcementText\" placeholder=\"Text\"></textarea>\r\n");
      out.write("                <button id=\"saveAnnouncement\">Save</button>\r\n");
      out.write("                <button id=\"discardAnnouncement\">Discard</button>\r\n");
      out.write("            </div>\r\n");
      out.write("            ");
 } 
      out.write("\r\n");
      out.write("            <div id=\"announcementsList\">\r\n");
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
      out.write("        <div id=\"tab4\" class=\"tab-content\">\r\n");
      out.write("            <h2>Your Quiz Taking Record</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 for(int i=0; i<recentScores.size(); i++) {
                    Score s = recentScores.get(i);
                    Quiz q = corrQuizzes.get(i);
                
      out.write("\r\n");
      out.write("                <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print(q.getQuiz_id());
      out.write("\" class=\"quiz-link\">\r\n");
      out.write("                    <div class=\"recently-taken-quizzes\">\r\n");
      out.write("                        <h3>");
      out.print(q.getName());
      out.write("</h3>\r\n");
      out.write("                        <p>");
      out.print(q.getDescription());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Creation Date: " + q.getCreationDate());
      out.write("</p>\r\n");
      out.write("                        <p>");
      out.print("Author: " + q.getCreatorUsername());
      out.write(" </p>\r\n");
      out.write("                        <div class=\"score-info\">\r\n");
      out.write("                            <p>Score: ");
      out.print( s.getScore() );
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"time-info\">\r\n");
      out.write("                            <p>Time: ");
      out.print( s.getTime() );
      out.write(" seconds</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"date-info\">\r\n");
      out.write("                            <p>Date: ");
      out.print( s.getDate_scored() );
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </a>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab5\" class=\"tab-content\">\r\n");
      out.write("            <h2>Quizzes Created By You</h2>\r\n");
      out.write("            <div>\r\n");
      out.write("                ");
 if(recentQuizzesByUser.size() > 0) {
                    for(Quiz quiz : recentQuizzesByUser) {
      out.write("\r\n");
      out.write("                <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print(quiz.getQuiz_id());
      out.write("\" class=\"quiz-link\">\r\n");
      out.write("                    <div class=\"recently-created-quizzes-by-user\">\r\n");
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
                }
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab6\" class=\"tab-content\">\r\n");
      out.write("            <h2>Achievements</h2>\r\n");
      out.write("            <div class=\"achievements-container\">\r\n");
      out.write("                ");
 for(Achievement achievement: achievements) {
      out.write("\r\n");
      out.write("                <div class=\"achievement-item\">\r\n");
      out.write("                    <div class=\"achievement\">\r\n");
      out.write("                        <div class=\"achievement-image\">\r\n");
      out.write("                            <img src=\"");
      out.print(achievPictureMap.get(achievement.getAchievementTitle()));
      out.write("\" alt=\"Achievement Icon\" width=\"100\" height=\"100\">\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"achievement-details\">\r\n");
      out.write("                            <h3>");
      out.print(achievement.getAchievementTitle());
      out.write("</h3>\r\n");
      out.write("                            <p>");
      out.print(achievement.getAchievementDate());
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("                ");
}
      out.write("\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab7\" class=\"tab-content\">\r\n");
      out.write("            <h2>Inbox</h2>\r\n");
      out.write("            <div class=\"inbox-columns\">\r\n");
      out.write("                <div class=\"inbox-column\">\r\n");
      out.write("                    <h3>Challenges</h3>\r\n");
      out.write("                    ");
 for (int i = 0; i < challenges.size(); i++) {
                        Challenge challenge = challenges.get(i);
                        Quiz quiz = quizzesForChallenges.get(i);
                    
      out.write("\r\n");
      out.write("                    <div class=\"challenge\">\r\n");
      out.write("                        <div class=\"challenge-details\">\r\n");
      out.write("                            <h4>");
      out.print( quiz.getName() );
      out.write("</h4>\r\n");
      out.write("                            <p>");
      out.print( quiz.getDescription() );
      out.write("</p>\r\n");
      out.write("                            <p>From: ");
      out.print( challenge.getFrom_username() );
      out.write("</p>\r\n");
      out.write("                            <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print( quiz.getQuiz_id() );
      out.write("\">Quiz Details</a>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"challenge-actions\">\r\n");
      out.write("                            <form action=\"AcceptChallengeServlet\" method=\"post\">\r\n");
      out.write("                                <input type=\"hidden\" name=\"challengeId\" value=\"");
      out.print( challenge.getRequestId() );
      out.write("\">\r\n");
      out.write("                                <button type=\"submit\">Accept</button>\r\n");
      out.write("                            </form>\r\n");
      out.write("                            <form action=\"RejectChallengeServlet\" method=\"post\">\r\n");
      out.write("                                <input type=\"hidden\" name=\"challengeId\" value=\"");
      out.print( challenge.getRequestId() );
      out.write("\">\r\n");
      out.write("                                <button type=\"submit\">Reject</button>\r\n");
      out.write("                            </form>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"inbox-column\">\r\n");
      out.write("                    <h3>Friend Requests</h3>\r\n");
      out.write("                    ");

                        for (FriendRequest friendRequest : friendRequests) {
                    
      out.write("\r\n");
      out.write("                    <div class=\"friend-request\">\r\n");
      out.write("                        <div class=\"friend-request-details\">\r\n");
      out.write("                            <p>From: ");
      out.print( friendRequest.getFrom_username() );
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"friend-request-actions\">\r\n");
      out.write("                            <form action=\"AcceptFriendRequestServlet\" method=\"post\">\r\n");
      out.write("                                <input type=\"hidden\" name=\"requestId\" value=\"");
      out.print( friendRequest.getRequestId() );
      out.write("\">\r\n");
      out.write("                                <button type=\"submit\">Accept</button>\r\n");
      out.write("                            </form>\r\n");
      out.write("                            <form action=\"RejectFriendRequestServlet\" method=\"post\">\r\n");
      out.write("                                <input type=\"hidden\" name=\"requestId\" value=\"");
      out.print( friendRequest.getRequestId() );
      out.write("\">\r\n");
      out.write("                                <button type=\"submit\">Reject</button>\r\n");
      out.write("                            </form>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"inbox-column\">\r\n");
      out.write("                    <h3>Notes</h3>\r\n");
      out.write("                    ");
 for (Note note : notes) { 
      out.write("\r\n");
      out.write("                    <div class=\"note\">\r\n");
      out.write("                        <div class=\"note-details\">\r\n");
      out.write("                            <p>From: ");
      out.print( note.getFromUsername() );
      out.write("</p>\r\n");
      out.write("                            <p>");
      out.print( note.getText() );
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div id=\"tab8\" class=\"tab-content\">\r\n");
      out.write("            <h2>Friends' Activities</h2>\r\n");
      out.write("            ");
 for(Activity activity : activities) { 
      out.write("\r\n");
      out.write("            <div class=\"friend-activity\">\r\n");
      out.write("                <div class=\"friend-header\">\r\n");
      out.write("                    <h3>");
      out.print( activity.getUsername() );
      out.write("'s Activities</h3>\r\n");
      out.write("                    <button class=\"toggle-button\">Show All Activities</button>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"activity-content\">\r\n");
      out.write("                    <div class=\"activity-column scores\">\r\n");
      out.write("                        <h4>Scores</h4>\r\n");
      out.write("                        ");
 if(!activity.getScoreList().isEmpty()) { 
      out.write("\r\n");
      out.write("                        <div class=\"score-item\">\r\n");
      out.write("                            <p>Quiz ID: ");
      out.print( activity.getScoreList().get(0).getQuiz_id() );
      out.write(", Score: ");
      out.print( activity.getScoreList().get(0).getScore() );
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        ");
 } 
      out.write("\r\n");
      out.write("                        <div class=\"detailed-content\" style=\"display: none;\">\r\n");
      out.write("                            ");
 for(int i = 1; i < activity.getScoreList().size(); i++) { 
      out.write("\r\n");
      out.write("                            <div class=\"score-item\">\r\n");
      out.write("                                <p>Quiz ID: ");
      out.print( activity.getScoreList().get(i).getQuiz_id() );
      out.write(", Score: ");
      out.print( activity.getScoreList().get(i).getScore() );
      out.write("</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            ");
 } 
      out.write("\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div class=\"activity-column quizzes\">\r\n");
      out.write("                        <h4>Quizzes</h4>\r\n");
      out.write("                        ");
 if(!activity.getQuizList().isEmpty()) { 
      out.write("\r\n");
      out.write("                        <div class=\"quiz-item\">\r\n");
      out.write("                            <p>Quiz: ");
      out.print( activity.getQuizList().get(0).getName() );
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        ");
 } 
      out.write("\r\n");
      out.write("                        <div class=\"detailed-content\" style=\"display: none;\">\r\n");
      out.write("                            ");
 for(int i = 1; i < activity.getQuizList().size(); i++) { 
      out.write("\r\n");
      out.write("                            <div class=\"quiz-item\">\r\n");
      out.write("                                <p>Quiz: ");
      out.print( activity.getQuizList().get(i).getName() );
      out.write("</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            ");
 } 
      out.write("\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                    <div class=\"activity-column achievements\">\r\n");
      out.write("                        <h4>Achievements</h4>\r\n");
      out.write("                        ");
 if(!activity.getAchievementList().isEmpty()) { 
      out.write("\r\n");
      out.write("                        <div class=\"achievement-item\">\r\n");
      out.write("                            <p>Achievement: ");
      out.print( activity.getAchievementList().get(0).getAchievementTitle() );
      out.write("</p>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        ");
 } 
      out.write("\r\n");
      out.write("                        <div class=\"detailed-content\" style=\"display: none;\">\r\n");
      out.write("                            ");
 for(int i = 1; i < activity.getAchievementList().size(); i++) { 
      out.write("\r\n");
      out.write("                            <div class=\"achievement-item\">\r\n");
      out.write("                                <p>Achievement: ");
      out.print( activity.getAchievementList().get(i).getAchievementTitle() );
      out.write("</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            ");
 } 
      out.write("\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </div>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            ");
 } 
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"tab9\">\r\n");
      out.write("        <h2>Search Quizzes</h2>\r\n");
      out.write("        <form action=\"UserHomePage.jsp\" method=\"get\">\r\n");
      out.write("            <input type=\"text\" name=\"searchQuery\" placeholder=\"Enter quiz name\" value=\"");
      out.print( searchQuery != null ? searchQuery : "" );
      out.write("\">\r\n");
      out.write("            <button type=\"submit\">Search</button>\r\n");
      out.write("        </form>\r\n");
      out.write("        <div class=\"search-results\">\r\n");
      out.write("            ");
 if (searchResults.size() > 0) { 
      out.write("\r\n");
      out.write("            <h3>Search Results:</h3>\r\n");
      out.write("            ");
 for (Quiz q : searchResults) { 
      out.write("\r\n");
      out.write("            <a href=\"QuizSummeryPage.jsp?quizId=");
      out.print( q.getQuiz_id() );
      out.write("\" class=\"quiz-link\">\r\n");
      out.write("                <div class=\"quiz-result\">\r\n");
      out.write("                    <h3>");
      out.print( q.getName() );
      out.write("</h3>\r\n");
      out.write("                    <p>");
      out.print( q.getDescription() );
      out.write("</p>\r\n");
      out.write("                    <p>");
      out.print("Creation Date: " + q.getCreationDate() );
      out.write("</p>\r\n");
      out.write("                    <p>");
      out.print("Author: " + q.getCreatorUsername() );
      out.write("</p>\r\n");
      out.write("                </div>\r\n");
      out.write("            </a>\r\n");
      out.write("            ");
 } 
      out.write("\r\n");
      out.write("            ");
 } else if (searchQuery != null) { 
      out.write("\r\n");
      out.write("            <p>No results found for \"");
      out.print( searchQuery );
      out.write("\"</p>\r\n");
      out.write("            ");
 } 
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"LOOKUP-container\">\r\n");
      out.write("        <div class=\"LOOKUP\">\r\n");
      out.write("            <a href=\"");
      out.print( request.getContextPath() );
      out.write("/Homepage/Looker.jsp\" class=\"button\">Look Someone Up</a>\r\n");
      out.write("            <a href=\"");
      out.print( request.getContextPath() );
      out.write("/MyProfile.jsp\" class=\"button\">My profile</a>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("\r\n");
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
      out.write("\r\n");
      out.write("        $('.toggle-button').click(function(){\r\n");
      out.write("            var $friendActivity = $(this).closest('.friend-activity');\r\n");
      out.write("            var $detailedContent = $friendActivity.find('.detailed-content');\r\n");
      out.write("\r\n");
      out.write("            if ($detailedContent.is(':visible')) {\r\n");
      out.write("                $detailedContent.hide();\r\n");
      out.write("                $(this).text('Show All Activities');\r\n");
      out.write("            } else {\r\n");
      out.write("                $detailedContent.show();\r\n");
      out.write("                $(this).text('Show Preview');\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("        $('#createAnnouncementBtn').click(function() {\r\n");
      out.write("            $('#announcementForm').slideToggle();\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("        $('#discardAnnouncement').click(function() {\r\n");
      out.write("            $('#announcementForm').slideUp();\r\n");
      out.write("            $('#announcementTitle').val('');\r\n");
      out.write("            $('#announcementText').val('');\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("        $(\"#saveAnnouncement\").click(function() {\r\n");
      out.write("            var title = $(\"#announcementTitle\").val();\r\n");
      out.write("            var text = $(\"#announcementText\").val();\r\n");
      out.write("\r\n");
      out.write("            $.post(\"MakeAnnouncementServlet\", {\r\n");
      out.write("                title: title,\r\n");
      out.write("                text: text\r\n");
      out.write("            }, function(response) {\r\n");
      out.write("                alert(response);\r\n");
      out.write("                if (response === \"Announcement created successfully\") {\r\n");
      out.write("                    location.reload(); // Refresh the page to show the new announcement\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("    });\r\n");
      out.write("</script>\r\n");
      out.write("<div>\r\n");
      out.write("    <div class=\"wave\"></div>\r\n");
      out.write("    <div class=\"wave\"></div>\r\n");
      out.write("    <div class=\"wave\"></div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
