<%@ page import="DBpackage.Quiz" %>
<%@ page import="DBpackage.DatabaseAccess" %>
<%@ page import="DBpackage.Score" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DBpackage.ScoreAndUser" %>
<%@ page import="DBpackage.DAOpackage.QuizDAO" %>
<%@ page import="DBpackage.DAOpackage.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: Nicolas
  Date: 6/29/2024
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String user = (String) session.getAttribute("username");
    int userId = (Integer) session.getAttribute("userID");

    //DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    Quiz quiz = QuizDAO.getQuizInfo(Integer.parseInt(request.getParameter("quizId")));
    String quizName = quiz.getName();
    String quizDescription = quiz.getDescription();
    String quizCreator = quiz.getCreatorUsername();
    int quizCreatorID = quiz.getCreatorID();

    ArrayList<Score> pastScores = QuizDAO.getLastAttemptsOfUserOnQuiz(user, quiz.getQuiz_id(), 0);
    ArrayList<ScoreAndUser> highestPerformers = QuizDAO.getTopPerformers(quiz.getQuiz_id(), 10);
    ArrayList<ScoreAndUser> lastDayHighestPerformers = UserDAO.getTopPerformersForLastDay(quiz.getQuiz_id(), 10);
    ArrayList<ScoreAndUser> recentScores = QuizDAO.getRecentPerformers(quiz.getQuiz_id(), 0);
%>
<html>
<head>
    <title>Quiz Summary</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/QuizSummeryPage.css">
</head>
<body>
    <div class = "container">
        <h1 align = "center"><%=quizName%></h1>

        <div class = "tabs-container">
            <ul class = "tabs">
                <li class = "tab-link active" data-tab = "tab1">Your Past Scores</li>
                <li class = "tab-link" data-tab = "tab2">Highest Performers</li>
                <li class = "tab-link" data-tab = "tab3">Yesterday's Highest Performers</li>
                <li class = "tab-link" data-tab = "tab4">Recent Scores for</li>
                <li class = "tab-link" datatype = "tab5">Statistics</li>
            </ul>

            <div id = "tab1" class = "tab-content active">
                <h2>Your Past Scores</h2>
                <div>
                    <% for (Score s : pastScores) { %>
                    <div class = "user-score">
                        <h3><%="Score: " + s.getScore()%></h3>
                        <p><%="Time: " + s.getTime() + " Seconds"%></p>
                        <p><%="Date Taken: " + s.getDate_scored()%></p>
                    </div>
                    <% } %>
                </div>
            </div>

            <div id = "tab2" class = "tab-content">
                <h2>Highest Performers</h2>
                <div>
                    <% for(ScoreAndUser sa : highestPerformers) {%>
                    <div class = "performer">
                        <h3><a href="ProfilePage.jsp?profileId=<%=sa.getUser().getUser_id()%>"><%=sa.getUser().getUsername()%></a> </h3>
                        <h3><%="Score: " + sa.getScore().getScore()%></h3>
                        <p><%="Time: " + sa.getScore().getTime() + " Seconds"%></p>
                        <p><%="Date Taken: " + sa.getScore().getDate_scored()%></p>
                    </div>
                    <% } %>
                </div>
            </div>

            <div id = "tab3" class = "tab-content">
                <h2>Yesterday's Highest Performers</h2>
                <div>
                    <% for(ScoreAndUser sa : lastDayHighestPerformers) { %>
                    <div class = "performer">
                        <h3><a href="ProfilePage.jsp?profileId=<%=sa.getUser().getUser_id()%>"><%=sa.getUser().getUsername()%></a> </h3>
                        <h3><%="Score: " + sa.getScore().getScore()%></h3>
                        <p><%="Time: " + sa.getScore().getTime() + " Seconds"%></p>
                        <p><%="Date Taken: " + sa.getScore().getDate_scored()%></p>
                    </div>
                    <% } %>
                </div>
            </div>

            <div id = "tab4" class = "tab-content">
                <h2>Recent Scores for Quiz</h2>
                <div>
                    <% for(ScoreAndUser sa : recentScores) {%>
                    <div class = "performer">
                        <h3><a href="ProfilePage.jsp?profileId=<%=sa.getUser().getUser_id()%>"><%=sa.getUser().getUsername()%></a> </h3>
                        <h3><%="Score: " + sa.getScore().getScore()%></h3>
                        <p><%="Time: " + sa.getScore().getTime() + " Seconds"%></p>
                        <p><%="Date Taken: " + sa.getScore().getDate_scored()%></p>
                    </div>
                    <% } %>
                </div>
            </div>

            <div id = "tab5" class = "tab-content">
                <h2>Statistics</h2>

            </div>
        </div>

        <div class="button-container">
            <form action="RejectFriendRequestServlet" method="post">
                <button type="submit">Take</button>
            </form>
            <% if(quiz.isPractice() > 0) { %>
            <form action="RejectFriendRequestServlet" method="post">
                <button type="submit">Practice</button>
            </form>
            <% } %>
        </div>
    </div>
<script>
    $(document).ready(function(){
        // Tab functionality
        $('.tab-link').click(function(){
            var tab_id = $(this).attr('data-tab');

            $('.tab-link').removeClass('active');
            $('.tab-content').removeClass('active');

            $(this).addClass('active');
            $("#"+tab_id).addClass('active');
        });

        // Add hover effect to performers
        $('.performer, .user-score').hover(
            function() {
                $(this).addClass('hover');
            }, function() {
                $(this).removeClass('hover');
            }
        );
    });
</script>

</body>
</html>
