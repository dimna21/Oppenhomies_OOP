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
    double averageTime = QuizDAO.getAverageTime(quiz.getQuiz_id());
    double averageScore = QuizDAO.getAverageScore(quiz.getQuiz_id());
    double quizRating = QuizDAO.getQuizRating(quiz.getQuiz_id());
    int amountTaken = QuizDAO.amountOfTimesTaken(quiz.getQuiz_id());
    int differentTake = QuizDAO.amountOfDifferentUsersTaken(quiz.getQuiz_id());
    ArrayList<Integer> hist = QuizDAO.getScoreRangeCounts(quiz.getQuiz_id());

%>
<html>
<head>
    <title>Quiz Summary</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/QuizSummeryPage.css">
</head>
<body>
    <div class = "LinkBack">
        <a href="UserHomePage.jsp" class="back-to-home">Home</a>
    </div>
    <div class = quizMaker>
        <h3>Author: <%=quizCreator%></h3>
    </div>
    <div class = "container">
        <h1 align = "center"><%=quizName%></h1>
        <h2 align = "center"><%=quizDescription%> </h2>
        <div class = "tabs-container">
            <ul class = "tabs">
                <li class = "tab-link active" data-tab = "tab1">Your Past Scores</li>
                <li class = "tab-link" data-tab = "tab2">Highest Performers</li>
                <li class = "tab-link" data-tab = "tab3">Yesterday's Highest Performers</li>
                <li class = "tab-link" data-tab = "tab4">Recent Scores</li>
                <li class = "tab-link" data-tab = "tab5">Statistics</li>
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
                <div class = "performer">
                    <div>
                        <h3><%="Average time: " + averageTime + " Seconds"%></h3>
                        <h3><%="Average score: " + averageScore%></h3>
                        <h3><%="Average rating: " + quizRating%></h3>
                        <h3><%="Amount of times taken: " + amountTaken%></h3>
                        <h3><%="Amount of different users taken: " + differentTake%></h3>
                        <div class="histogram-container">
                            <h2>Score Distribution for Quiz ID: <%= quiz.getQuiz_id() %></h2>
                            <div class="histogram">
                                <% int maxAmount = 1;
                                for(int a : hist){if(a>maxAmount)maxAmount=a;}%>
                                <%-- Iterate through score ranges and counts --%>
                                <% int rangeIndex = 0;
                                    for (Integer count : hist) { %>
                                <div class="bar">
                                    <div class="bar-label">
                                        <%-- Determine label based on rangeIndex --%>
                                        <%
                                            int lowerBound = rangeIndex * 10+1;
                                            if(rangeIndex == 0)lowerBound=0;
                                            int upperBound = (rangeIndex + 1) * 10 ;
                                            String label =(lowerBound + "-" + upperBound);
                                            out.print(label);
                                        %>
                                    </div>                                    <div class="bar-fill" style="height: <%= 100.0 *count/maxAmount  %>px;"></div>
                                    <div class="bar-count"> <%= count %> </div>
                                </div>
                                <% rangeIndex++;
                                } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="button-container">
            <form action="Quiz.jsp?quizId=<%=quiz.getQuiz_id()%>" method="post">
                <button type="submit">Take</button>
            </form>
            <% if(quiz.isPractice() > 0) { %>
            <form action="Quiz.jsp?quizId=<%=quiz.getQuiz_id()%>&practice=1" method="post">
                <button type="submit">Practice</button>
            </form>
            <% } %>
            <% if(quizCreatorID == userId) {%>
            <form action="EditQuiz.jsp?quizId=<%=quiz.getQuiz_id()%>" method="post">
                <button type="submit">Edit</button>
            </form>
            <%} %>
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
    <div>
        <div class="wave"></div>
        <div class="wave"></div>
        <div class="wave"></div>
    </div>
</body>
</html>

