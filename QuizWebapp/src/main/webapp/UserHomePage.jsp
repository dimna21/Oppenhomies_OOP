<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="DBpackage.DatabaseAccess" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="DBpackage.Announcement" %><%--
  Created by IntelliJ IDEA.
  User: Nicolas
  Date: 6/30/2024
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");

    int userID = (int) session.getAttribute("userID");
    String username = (String) session.getAttribute("username");

    ArrayList<Announcement> announcements = dbAccess.getLatestAnnouncements(0);
%>
<html>
<head>
    <title>User Home Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="UserHomePage.css">
</head>
<body>
<div class="container">
    <h1 align="center"><%=username%></h1>

    <div class="tabs-container">
        <ul class="tabs">
            <li class="tab-link active" data-tab="tab1">Announcements</li>
            <li class="tab-link" data-tab="tab2">Popular Quizzes</li>
            <li class="tab-link" data-tab="tab3">Recently Created Quizzes</li>
            <li class="tab-link" data-tab="tab4">Your Quiz Record</li>
            <li class="tab-link" data-tab="tab5">Quizzes Created by You</li>
            <li class="tab-link" data-tab="tab6">Your Quiz Record</li>
            <li class="tab-link" data-tab="tab7">Your Achievements</li>
            <li class="tab-link" data-tab="tab8">Messages</li>
            <li class="tab-link" data-tab="tab9">Activities of Your Friends</li>
        </ul>

        <div id="tab1" class="tab-content active">
            <h2>Announcements by Administration</h2>
            <div>
                <% for(Announcement announcement : announcements) {%>
                <div class="announcement">
                    <h3><%=announcement.getTitle()%></h3>
                    <p><%=announcement.getText()%></p>
                    <p><%=announcement.getCreationDate() + " " + announcement.getUsername() %></p>
                </div>
                <%}%>
            </div>
        </div>

        <!-- Add other tab content here -->
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