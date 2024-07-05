<%@ page import="DBpackage.Quiz" %>
<%@ page import="DBpackage.DatabaseAccess" %>
<%@ page import="DBpackage.Score" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DBpackage.ScoreAndUser" %><%--
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

    DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    Quiz quiz = dbAccess.getQuizInfo(Integer.parseInt(request.getParameter("quizId")));
    String quizName = quiz.getName();
    String quizDescription = quiz.getDescription();
    String quizCreator = quiz.getCreatorUsername();
    int quizCreatorID = quiz.getCreatorID();

    ArrayList<Score> pastScores = dbAccess.getLastAttemptsOfUserOnQuiz(user, quiz.getQuiz_id(), 0);
    ArrayList<ScoreAndUser> highestPerformers = dbAccess.getTopScorers(quiz.getQuiz_id(), 10);
    ArrayList<ScoreAndUser> lastDayHighestPerformers = dbAccess.getTopPerformersForLastDay(quiz.getQuiz_id(), 10);
    ArrayList<ScoreAndUser> recentScores = dbAccess.();
%>
<html>
<head>
    <title>Quiz Summery</title>
</head>
<body>
    <div class = "container">
        <h1 align = "center"><%=quizName%></h1>

        <div class = "tab-container">
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
                        <h3><%=s.getScore()%></h3>
                        <p><%=s.getTime() + " " + s.getDate_scored()%></p>
                    </div>
                    <% } %>
                </div>
            </div>

            <div id = "tab2" class = "tab-content">
                <h2>Highest Performers</h2>
                <div>
                    <% for(ScoreAndUser sa : highestPerformers) {%>
                    <div class = "performer">
                        <h3><%=sa.getUser().getUsername() + " " + sa.getScore().getScore()%></h3>
                        <p><%=sa.getScore().getTime() + " " + sa.getScore().getDate_scored()%></p>
                    </div>
                    <% } %>
                </div>
            </div>

            <div id = "tab3" class = "tab-content">
                <h2>Yesterday's Highest Performers</h2>
                <div>
                    <% for(ScoreAndUser sa : lastDayHighestPerformers) { %>
                    <div class = "performer">
                        <h3><%=sa.getUser().getUsername() + " " + sa.getScore().getScore()%></h3>
                        <p><%=sa.getScore().getTime() + " " + sa.getScore().getDate_scored()%></p>
                    </div>
                </div>
            </div>

            <div id = "tab4" class = "tab-content">
                <h2>Recent Scores for</h2>
                <div>
                    <% for(ScoreAndUser sa : recentScores) %>
                    <div class = "performer">
                        <h3><%=sa.getUser().getUsername() + " " + sa.getScore().getScore()%></h3>
                        <p><%=sa.getScore().getTime() + " " + sa.getScore().getDate_scored()%></p>
                    </div>
                    <% } %>
                </div>
            </div>

            <div id = "tab5" class = "tab-content">
                <h2>Statistics</h2>
                <div>

                </div>
            </div>
        </div>
    </div>


</body>
</html>
