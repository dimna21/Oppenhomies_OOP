<!DOCTYPE html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DBpackage.*" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="DBpackage.DAOpackage.AnnouncementDAO" %>
<%@ page import="DBpackage.DAOpackage.QuizDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");

    int userID = (Integer)session.getAttribute("userID");
    String username = (String) session.getAttribute("username");

    ArrayList<Announcement> announcements = AnnouncementDAO.getLatestAnnouncements(0);
    ArrayList<Quiz> popularQuizzes = QuizDAO.getQuizzesByPopularity(0);
    ArrayList<Quiz> recentlyCreatedQuizzes = QuizDAO.getNewestQuiz(0);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Wellcome to Quizzler </title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Homepage/WellWellWell.css">
</head>
<body>

<div class="container">
    <div class="center-text">QUIZZLER</div>
    <hr />
    <div class="tabs-container">
        <ul class="tabs">
            <li class="tab-link active" data-tab="tab1">Announcements</li>
            <li class="tab-link" data-tab="tab2">Popular Quizzes</li>
            <li class="tab-link" data-tab="tab3">Recently Created Quizzes</li>
        </ul>

        <div id="tab1" class="tab-content active">
            <h2>Announcements by Administration</h2>
            <div>
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
    </div>
</div>

<div class="opciones-container">
    <div class="opciones">
        <a href="<%= request.getContextPath() %>/UserAuthentication/loginPage.jsp" class="button">Sign In</a>
        <a href="<%= request.getContextPath() %>/UserAuthentication/newAccount.jsp" class="button">Sign Up</a>
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
    });
</script>


</body>
</html>
