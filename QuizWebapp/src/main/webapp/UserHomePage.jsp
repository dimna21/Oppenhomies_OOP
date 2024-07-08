<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DBpackage.*" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="DBpackage.DAOpackage.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
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
    ChallengeDAO.getChallengesForUser(userID, challenges, quizzesForChallenges);

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
%>
<html>
<head>
    <title>User Home Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="UserHomePage.css?v=2.0">
</head>
<body>
<div class="container">
    <div class="user-info">
        <div class="user-name"><%=username%></div>
        <form action="CreateQuiz.jsp" method="post">
            <button type="submit">Create Quiz</button>
        </form>
        <form action="<%= request.getContextPath() %>/LogoutServlet" method="post">
            <button type="submit">Log Out</button>
        </form>
    </div>
    <br><br><br>
    <div class="tabs-container">
        <ul class="tabs">
            <li class="tab-link active" data-tab="tab1">Announcements</li>
            <li class="tab-link" data-tab="tab2">Popular Quizzes</li>
            <li class="tab-link" data-tab="tab3">Recently Created Quizzes</li>
            <li class="tab-link" data-tab="tab4">Your Quiz Taking Record</li>
            <li class="tab-link" data-tab="tab5">Quizzes Created by You</li>
            <li class="tab-link" data-tab="tab6">Your Achievements</li>
            <li class="tab-link" data-tab="tab7">Inbox</li>
            <li class="tab-link" data-tab="tab8">Activities of Your Friends</li>
        </ul>

        <div id="tab1" class="tab-content active">
            <h2>Announcements by Administration</h2>
            <% if (user.getAdmin_status() == 1) { %>
            <button id="createAnnouncementBtn">Create Announcement</button>
            <div id="announcementForm" style="display: none;">
                <input type="text" id="announcementTitle" placeholder="Title">
                <textarea id="announcementText" placeholder="Text"></textarea>
                <button id="saveAnnouncement">Save</button>
                <button id="discardAnnouncement">Discard</button>
            </div>
            <% } %>
            <div id="announcementsList">
                <% for(Announcement announcement : announcements) {%>
                <div class="announcement">
                    <h3><%=announcement.getTitle()%></h3>
                    <p><%=announcement.getText()%></p>
                    <p><%="Date: " + announcement.getCreationDate()%></p>
                    <p><%="By: " + announcement.getUsername()%></p>
                </div>
                <%}%>
            </div>
        </div>

        <div id="tab2" class="tab-content">
            <h2>Popular Quizzes</h2>
            <div>
                <% for(Quiz quiz: popularQuizzes) {%>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="popular-quizzes">
                        <h3><%=quiz.getName()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%="Creation Date: " + quiz.getCreationDate()%></p>
                        <p><%="Author: " + quiz.getCreatorUsername()%> </p>
                    </div>
                </a>
                <%}%>
            </div>
        </div>

        <div id="tab3" class="tab-content">
            <h2>Recently Created Quizzes</h2>
            <div>
                <% for(Quiz quiz: recentlyCreatedQuizzes) {%>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="recently-created-quizzes">
                        <h3><%=quiz.getName()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%="Creation Date: " + quiz.getCreationDate()%></p>
                        <p><%="Author: " + quiz.getCreatorUsername()%> </p>
                    </div>
                </a>
                <%}%>
            </div>
        </div>

        <div id="tab4" class="tab-content">
            <h2>Your Quiz Taking Record</h2>
            <div>
                <% for(int i=0; i<recentScores.size(); i++) {
                    Score s = recentScores.get(i);
                    Quiz q = corrQuizzes.get(i);
                %>
                <a href="QuizSummeryPage.jsp?quizId=<%=q.getQuiz_id()%>" class="quiz-link">
                    <div class="recently-taken-quizzes">
                        <h3><%=q.getName()%></h3>
                        <p><%=q.getDescription()%></p>
                        <p><%="Creation Date: " + q.getCreationDate()%></p>
                        <p><%="Author: " + q.getCreatorUsername()%> </p>
                        <div class="score-info">
                            <p>Score: <%= s.getScore() %></p>
                        </div>
                        <div class="time-info">
                            <p>Time: <%= s.getTime() %> seconds</p>
                        </div>
                        <div class="date-info">
                            <p>Date: <%= s.getDate_scored() %></p>
                        </div>
                    </div>
                </a>
                <%}%>
            </div>
        </div>


        <div id="tab5" class="tab-content">
            <h2>Quizzes Created By You</h2>
            <div>
                <% if(recentQuizzesByUser.size() > 0) {
                    for(Quiz quiz : recentQuizzesByUser) {%>
                <a href="QuizSummeryPage.jsp?quizId=<%=quiz.getQuiz_id()%>" class="quiz-link">
                    <div class="recently-created-quizzes-by-user">
                        <h3><%=quiz.getName()%></h3>
                        <p><%=quiz.getDescription()%></p>
                        <p><%="Creation Date: " + quiz.getCreationDate()%></p>
                        <p><%="Author: " + quiz.getCreatorUsername()%> </p>
                    </div>
                </a>
                <%}
                }%>
            </div>
        </div>

        <div id="tab6" class="tab-content">
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



        <div id="tab7" class="tab-content">
            <h2>Inbox</h2>
            <div class="inbox-columns">
                <div class="inbox-column">
                    <h3>Challenges</h3>
                    <% for (int i = 0; i < challenges.size(); i++) {
                        Challenge challenge = challenges.get(i);
                        Quiz quiz = quizzesForChallenges.get(i);
                    %>
                    <div class="challenge">
                        <div class="challenge-details">
                            <h4><%= quiz.getName() %></h4>
                            <p><%= quiz.getDescription() %></p>
                            <p>From: <%= challenge.getFrom_username() %></p>
                            <a href="QuizSummeryPage.jsp?quizId=<%= quiz.getQuiz_id() %>">Quiz Details</a>
                        </div>
                        <div class="challenge-actions">
                            <form action="AcceptChallengeServlet" method="post">
                                <input type="hidden" name="challengeId" value="<%= challenge.getRequestId() %>">
                                <button type="submit">Accept</button>
                            </form>
                            <form action="RejectChallengeServlet" method="post">
                                <input type="hidden" name="challengeId" value="<%= challenge.getRequestId() %>">
                                <button type="submit">Reject</button>
                            </form>
                        </div>
                    </div>
                    <% } %>
                </div>
                <div class="inbox-column">
                    <h3>Friend Requests</h3>
                    <%
                        for (FriendRequest friendRequest : friendRequests) {
                    %>
                    <div class="friend-request">
                        <div class="friend-request-details">
                            <p>From: <%= friendRequest.getFrom_username() %></p>
                        </div>
                        <div class="friend-request-actions">
                            <form action="AcceptFriendRequestServlet" method="post">
                                <input type="hidden" name="requestId" value="<%= friendRequest.getRequestId() %>">
                                <button type="submit">Accept</button>
                            </form>
                            <form action="RejectFriendRequestServlet" method="post">
                                <input type="hidden" name="requestId" value="<%= friendRequest.getRequestId() %>">
                                <button type="submit">Reject</button>
                            </form>
                        </div>
                    </div>
                    <% } %>
                </div>
                <div class="inbox-column">
                    <h3>Notes</h3>
                    <% for (Note note : notes) { %>
                    <div class="note">
                        <div class="note-details">
                            <p>From: <%= note.getFromUsername() %></p>
                            <p><%= note.getText() %></p>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>

        <div id="tab8" class="tab-content">
            <h2>Friends' Activities</h2>
                <% for(Activity activity : activities) { %>
                <div class="friend-activity">
                    <div class="friend-header">
                        <h3><%= activity.getUsername() %>'s Activities</h3>
                        <button class="toggle-button">Show All Activities</button>
                    </div>
                    <div class="activity-content">
                        <div class="activity-column scores">
                            <h4>Scores</h4>
                            <% if(!activity.getScoreList().isEmpty()) { %>
                            <div class="score-item">
                                <p>Quiz ID: <%= activity.getScoreList().get(0).getQuiz_id() %>, Score: <%= activity.getScoreList().get(0).getScore() %></p>
                            </div>
                            <% } %>
                            <div class="detailed-content" style="display: none;">
                                <% for(int i = 1; i < activity.getScoreList().size(); i++) { %>
                                <div class="score-item">
                                    <p>Quiz ID: <%= activity.getScoreList().get(i).getQuiz_id() %>, Score: <%= activity.getScoreList().get(i).getScore() %></p>
                                </div>
                                <% } %>
                            </div>
                        </div>
                        <div class="activity-column quizzes">
                            <h4>Quizzes</h4>
                            <% if(!activity.getQuizList().isEmpty()) { %>
                            <div class="quiz-item">
                                <p>Quiz: <%= activity.getQuizList().get(0).getName() %></p>
                            </div>
                            <% } %>
                            <div class="detailed-content" style="display: none;">
                                <% for(int i = 1; i < activity.getQuizList().size(); i++) { %>
                                <div class="quiz-item">
                                    <p>Quiz: <%= activity.getQuizList().get(i).getName() %></p>
                                </div>
                                <% } %>
                            </div>
                        </div>
                        <div class="activity-column achievements">
                            <h4>Achievements</h4>
                            <% if(!activity.getAchievementList().isEmpty()) { %>
                            <div class="achievement-item">
                                <p>Achievement: <%= activity.getAchievementList().get(0).getAchievementTitle() %></p>
                            </div>
                            <% } %>
                            <div class="detailed-content" style="display: none;">
                                <% for(int i = 1; i < activity.getAchievementList().size(); i++) { %>
                                <div class="achievement-item">
                                    <p>Achievement: <%= activity.getAchievementList().get(i).getAchievementTitle() %></p>
                                </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
    </div>

    <div class="LOOKUP-container">
        <div class="LOOKUP">
            <a href="<%= request.getContextPath() %>/Homepage/Looker.jsp" class="button">Look Someone Up</a>
        </div>
    </div>

</div>

<script>
    $(document).ready(function(){
        $(".tab-link").click(function(){
            var tabId = $(this).attr("data-tab");

            // Remove active class from all tabs
            $(".tab-link").removeClass("active");
            $(".tab-content").removeClass("active");

            // Add active class to the clicked tab
            $(this).addClass("active");
            $("#" + tabId).addClass("active");
        });

        $('.toggle-button').click(function(){
            var $friendActivity = $(this).closest('.friend-activity');
            var $detailedContent = $friendActivity.find('.detailed-content');

            if ($detailedContent.is(':visible')) {
                $detailedContent.hide();
                $(this).text('Show All Activities');
            } else {
                $detailedContent.show();
                $(this).text('Show Preview');
            }
        });

        $('#createAnnouncementBtn').click(function() {
            $('#announcementForm').slideToggle();
        });

        $('#discardAnnouncement').click(function() {
            $('#announcementForm').slideUp();
            $('#announcementTitle').val('');
            $('#announcementText').val('');
        });


        $('#saveAnnouncement').click(function() {
            var title = $('#announcementTitle').val();
            var text = $('#announcementText').val();

            var newAnnouncement = '<div class="announcement">' +
                '<h3>' + title + '</h3>' +
                '<p>' + text + '</p>' +
                '<p>Just now - ' + '<%=username%>' + '</p>' +
                '</div>';
            $('#announcementsList').prepend(newAnnouncement);

            $('#announcementTitle').val('');
            $('#announcementText').val('');
            $('#announcementForm').slideUp();
        });
    });
</script>
<div>
    <div class="wave"></div>
    <div class="wave"></div>
    <div class="wave"></div>
</div>

</body>
</html>
