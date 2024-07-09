<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="DBpackage.DatabaseAccess" %>
<%@ page import="DBpackage.*" %>
<%
    int userId = (Integer) session.getAttribute("userID");

    // Fetch user information from database
    User user = DatabaseAccess.getUserInfo(DatabaseAccess.getUsername(userId));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="MyProfile.css">
</head>
<body>
<a href="UserHomePage.jsp" class="back-button">Back to Home</a>
<div class="profile-container">
    <h1>User Profile</h1>
    <div class="profile-info">
        <div class="profile-picture">
            <img src="<%= user.getProfile_pic_url() %>" alt="Profile Picture">
            <form action="UpdateProfilePictureServlet" method="post">
                <input type="text" name="profilePictureUrl" placeholder="Enter URL for Profile Picture">
                <button type="submit">Update</button>
            </form>
        </div>
        <div class="profile-details">
            <p><strong>Username:</strong> <%= user.getUsername() %></p>
            <p><strong>Admin Status:</strong> <%= (user.getAdmin_status()==1) ? "Admin" : "Regular User" %></p>
            <p><strong>Quizzes Taken:</strong> <%= user.getQuizzes_taken() %></p>
            <p><strong>Quizzes Created:</strong> <%= user.getQuizzes_created() %></p>
        </div>
    </div>
</div>
</body>
</html>
<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>
