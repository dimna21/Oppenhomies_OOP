<%--
  Created by IntelliJ IDEA.
  User: sandr
  Date: 5/25/2024
  Time: 4:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/UserAuthentication/authen.css">
</head>
<body>
<div class="container">
    <h1>
        <%
            String name = request.getParameter("username");
        %>
        The Name <%= name %> is Already In Use
    </h1>
    <hr> <!-- Horizontal line beneath the heading -->
    <div class="mainFormDiv">
        <div class="main-text"><strong>Please enter another name and password.</strong></div>
        <form action="<%= request.getContextPath() %>/NewAccServlet" method="post">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username">
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password">
                <br>
                <button type="submit" class="button">Create Account</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>