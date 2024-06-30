<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="DBpackage.DatabaseAccess" %>
<%@ page import="java.util.ArrayList" %><%--
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

    ArrayList<Announcement> announcements = new ArrayList<>;
%>
<html>
<head>
    <title>User Home Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <h1 align="center"><%=username%></h1>

    <ul class = "tabs">
        <li class = "tab-link" data-tab="tab1">Announcements</li>
        <li class = "tab-link" data-tab="tab2">Popular Quizzes</li>
        <li class = "tab-link" data-tab="tab3">Recently Created Quizzes</li>
        <li class = "tab-link" data-tab="tab4">Your Quiz Record</li>
        <li class = "tab-link" data-tab="tab5">Quizzes Created by You</li>
        <li class = "tab-link" data-tab="tab6">Your Quiz Record</li>
        <li class = "tab-link" data-tab="tab7">Your Achievements</li>
        <li class = "tab-link" data-tab="tab8">Messages</li>
        <li class = "tab-link" data-tab="tab9">Activities of Your Friends</li>
    </ul>

    <div id = "tab1" class="tab-content active">
        <h2>Announcements by Administration</h2>
            <div>
                <% for(Announcement announcement : announcements) {%>
                <div>
                    <h3><%=announcement.getTitle()%></h3>
                    <p><%=announcement.getText()%></p>
                    <p><%=announcement.getDate() announcement.getAuthor()%></p>
                </div>
                <%}%>
            </div>
    </div>


    <script>
        $(document).ready(function(){
            $(".tab-link").click(function(){
                var tabId = $(this).attr("data-tab");

                // Hide all tab contents
                $(".tab-content").hide();

                // Show the clicked tab content
                $("#" + tabId).show();
            });
        });
    </script>

</body>
</html>
