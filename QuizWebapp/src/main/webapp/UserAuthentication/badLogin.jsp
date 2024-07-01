<%--
  Created by IntelliJ IDEA.
  User: sandr
  Date: 5/25/2024
  Time: 4:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information Incorrect</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/UserAuthentication/authen.css">
</head>
<body>
<div class="container">
    <h1>Oops! Please Try Again</h1>
    <hr>

    <form action="<%= request.getContextPath() %>/LoginServlet" method="post" class="mainFormDiv">
        <div>
            <div class="main-text condensed-text"><strong>Incorrect username or password.<br>Please try again.</strong></div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username">
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password">
            <br>
            <button type="submit" class="button">Login</button>
        </div>
        <div>
            <p class="condensed-text">Don't have an account? <a href="<%= request.getContextPath() %>/UserAuthentication/newAccount.jsp">Create New Account</a></p>
        </div>
    </form>
</div>
</body>
</html>
