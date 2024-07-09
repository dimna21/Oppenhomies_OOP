<%@ page import="java.util.ArrayList" %>
<%@ page import="DBpackage.*" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="DBpackage.DAOpackage.*" %><%--
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
    //DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    String profile = DatabaseAccess.getUsername(profileId);

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
%>
<html>
<head>
    <title>Profile Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="ProfilePage.css">
</head>
<body>
    <div class="fixed-button-container">
        <a href="UserHomePage.jsp" class="fixed-button">Home</a>
    </div>
    <div class = "container">
        <h1 align="center"><%=profile%></h1>

        <%if (drugi) {%>
        <div class="FRIEND-container">
            <div class="FRIEND">
                <p class="waitfa">Friends</p>
            </div>
        </div>
        <%}

        else if (waiting) {%>
        <div class="FRIEND-container">
            <div class="FRIEND">
            <p class="waitfa">Waiting For Answer</p>
            </div>
        </div>
        <%}

        else{ %>
        <div class="FRIEND-container">
            <form action="<%= request.getContextPath() %>/AddFriendServlet" method="post">
                <button type="submit" class="fixed-button">Add Friend</button>
            </form>
        </div>
        <%}%>


        <div class = "tabs-container">
            <ul class = "tabs">
                <li class = "tab-link active" data-tab = "tab1">Most Popular Quizzes</li>
                <li class = "tab-link" data-tab = "tab2">Recently Created Quizzes</li>
                <li class = "tab-link" data-tab = "tab3">Most Successful Quizzes</li>
                <li class = "tab-link" data-tab = "tab4">Recently Taken Quizzes</li>
                <li class = "tab-link" data-tab = "tab5">Achievements</li>
                <li class = "tab-link" data-tab = "tab6">Chat</li>
            </ul>


        <div id = "tab1" class="tab-content active">
            <h2><%=profile%>'s Most Popular Quizzes</h2>
            <div class = "popuquiz">
                <% for(Quiz quiz: mostPopularQuizzes) {%>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="popular-quizzes">
                        <h3><%=quiz.getName()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%="Creation Date: " + quiz.getCreationDate()%></p>
                    </div>
                </a>
                <%}%>
            </div>
        </div>

        <div id="tab2" class="tab-content">
            <h2>Recently Created Quizzes</h2>
            <div class = "rescre">
                <% for(Quiz quiz: recentQuizzes) {%>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="recently-created-quizzes">
                        <h3><%=quiz.getName()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%="Creation Date: " + quiz.getCreationDate()%></p>
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
                        <h3><%=quiz.getName()%></h3>
                        <h3> <%="Score: " + score.getScore()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%="Date Taken: " + score.getDate_scored()%></p>
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
                        <h3><%=quiz.getName()%></h3>
                        <h3> <%="Score: " + score.getScore()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%="Date Taken: " + score.getDate_scored()%></p>
                    </div>
                </a>
                <% } %>
            </div>
        </div>

            <div id="tab5" class="tab-content">
                <h2>Achievements</h2>
                <div class="achievements-container">
                    <% for(Achievement achievement: achievements) {%>
                    <div class="achievement-item">
                        <div class="achievement">
                            <div class="achievement-image">
                                <img src="<%=achievPictureMap.get(achievement.getAchievementTitle())%>" alt="Achievement Icon" width="100" height="100">
                            </div>
                            <div class="achievement-details">
                                <h3><%=achievement.getAchievementTitle()%></h3>
                                <p><%=achievement.getAchievementDate()%></p>
                            </div>
                        </div>
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
                        <% } else { %>
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
            <div id="chatInput">
                <input type="text" id="messageInput" placeholder="Type your message...">
                <button onclick="sendMessage()">Send</button>
            </div>
        </div>
        </div>
        <div>
            <a href="Sender.jsp" class="challenge-button">Send Challenge</a>
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

        function sendMessage() {
            var message = $("#messageInput").val();
            $.ajax({
                url: "ChatServlet",
                method: "POST",
                data: { message: message },
                success: function(response) {
                    location.reload();
                }
            });
        }

        // Allow sending message with Enter key
        $("#messageInput").keypress(function(e) {
            if(e.which == 13) {
                sendMessage();
                return false;
            }
        });
    </script>
    <div>
        <div class="wave"></div>
        <div class="wave"></div>
        <div class="wave"></div>
    </div>
</body>
</html>
<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>