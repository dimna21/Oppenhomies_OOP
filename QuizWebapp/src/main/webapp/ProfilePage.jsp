<%@ page import="java.util.ArrayList" %>
<%@ page import="DBpackage.*" %><%--
  Created by IntelliJ IDEA.
  User: Nicolas
  Date: 7/6/2024
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int userId = (Integer) session.getAttribute("userID");
    String username = (String) session.getAttribute("username");

    int profileId = Integer.parseInt(request.getParameter("profileId"));
    DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    String profile = DatabaseAccess.getUsername(profileId);

    ArrayList<Quiz> mostPopularQuizzes = dbAccess.getPopularQuizzesByUser(profileId, 0);
    ArrayList<Quiz> recentQuizzes = dbAccess.getRecentQuizzesByUser(profileId, 0);

    ArrayList<Score> mostSuccessfulScores = new ArrayList<Score>();
    ArrayList<Quiz> mostSuccessfulQuizzes = new ArrayList<Quiz>();
    dbAccess.getMostSuccessfulScoresAndQuizzesForUser(mostSuccessfulScores, mostSuccessfulQuizzes, profileId, 0);
    System.out.println("MostSuccessfulScores: " + mostSuccessfulScores.size());
    System.out.println("MostPopularQuizzes: " + mostSuccessfulQuizzes.size());

    ArrayList<Score> recentScoresTaken = new ArrayList<Score>();
    ArrayList<Quiz> recentQuizzesTaken = new ArrayList<Quiz>();
    dbAccess.getRecentScoresAndQuizzesForUser(recentScoresTaken, recentQuizzesTaken, profileId, 0);
    System.out.println("RecentScoresTaken: " + recentScoresTaken.size());
    System.out.println("RecentQuizzesTaken: " + recentQuizzesTaken.size());

    ArrayList<Achievement> achievements = dbAccess.getRecentAchievements(profile, 0);
    System.out.println("Achievements: " + achievements.size());

    ArrayList<Note> chat = dbAccess.getNotesForChat(userId, profileId, 0);
    System.out.println("Chat: " + chat.size());
%>
<html>
<head>
    <title>Profile Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="ProfilePage.css?v=3.0">
</head>
<body>
    <div class = "container">
        <h1 align="center"><%=profile%></h1>

        <div class = "tab-container">
            <ul class = "tabs">
                <li class = "tab-link active" data-tab = "tab1">Most Popular Quizzes</li>
                <li class = "tab-link" data-tab = "tab2">Recently Created Quizzes</li>
                <li class = "tab-link" data-tab = "tab3">Most Successful Quizzes</li>
                <li class = "tab-link" data-tab = "tab4">Recently Taken Quizzes</li>
                <li class = "tab-link" data-tab = "tab5">Achievements</li>
                <li class = "tab-link" data-tab = "tab6">Chat</li>
            </ul>
        </div>

        <div id = "tab1" class="tab-content active">
            <h2><%=profile%>'s Most Popular Quizzes</h2>
            <div>
                <% for(Quiz quiz: mostPopularQuizzes) {%>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="popular-quizzes">
                        <h3><%=quiz.getName()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%=quiz.getCreationDate()%></p>
                    </div>
                </a>
                <%}%>
            </div>
        </div>

        <div id="tab2" class="tab-content">
            <h2>Recently Created Quizzes</h2>
            <div>
                <% for(Quiz quiz: recentQuizzes) {%>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="recently-created-quizzes">
                        <h3><%=quiz.getName()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%=quiz.getCreationDate()%></p>
                    </div>
                </a>
                <%}%>
            </div>
        </div>

        <div id="tab3" class="tab-content">
            <h2>Most Successful Quizzes</h2>
            <div>
                <% for(int i=0; i<mostSuccessfulQuizzes.size(); i++) {
                    Quiz quiz = mostSuccessfulQuizzes.get(i);
                    Score score = mostSuccessfulScores.get(i);
                %>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="recently-created-quizzes">
                        <h3><%=quiz.getName()%> <%=score.getScore()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%=score.getDate_scored()%></p>
                    </div>
                </a>
                <% } %>
            </div>
        </div>

        <div id="tab4" class="tab-content">
            <h2>Recently Taken Quizzes</h2>
            <div>
                <% for(int i=0; i<recentQuizzesTaken.size(); i++) {
                    Quiz quiz = recentQuizzesTaken.get(i);
                    Score score = recentScoresTaken.get(i);
                %>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="recently-created-quizzes">
                        <h3><%=quiz.getName()%> <%=score.getScore()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%=score.getDate_scored()%></p>
                    </div>
                </a>
                <% } %>
            </div>
        </div>

        <div id="tab5" class="tab-content">
            <h2>Achievements</h2>
            <div>
                <% for(Achievement achievement: achievements) {%>
                <div class="achievements">
                    <h3><%=achievement.getAchievementTitle()%></h3>
                    <p><%=achievement.getAchievementDate()%></p>
                </div>
                <%}%>
            </div>
        </div>

        <div id="tab6" class="tab-content">
            <h2>Chat</h2>
            <div>
                <% for(Note note : chat) { %>
                    <div class="note">
                        <% if(note.getFromUsername().equals(username)) { %>
                            <div class="user-note">
                                <div class="note-details">
                                    <p>From: <%= note.getFromUsername() %></p>
                                    <p><%= note.getText() %></p>
                                </div>
                            </div>
                        <% }
                        else { %>
                        <div class="profile-note">
                            <div class="note-details">
                                <p>From: <%= note.getFromUsername() %></p>
                                <p><%= note.getText() %></p>
                            </div>
                        </div>
                        <% } %>
                    </div>
                <% } %>
            </div>
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
        });
    </script>
</body>
</html>