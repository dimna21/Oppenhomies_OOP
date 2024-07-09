<%--
  Created by IntelliJ IDEA.
  User: sandr
  Date: 5/25/2024
  Time: 4:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Welcome</title>
  <link rel="stylesheet" type="text/css" href="authen.css?v=2.0">
</head>
<body>
  <div class="mainFormDiv">
    <div class="main-text"><strong>Hello, Please Log In</strong></div>

    <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
      <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username">
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password">
        <br>
        <button type="submit" class="button">Log In</button>
        <a href="<%= request.getContextPath() %>/UserAuthentication/newAccount.jsp" class="button">Sign Up</a>
      </div>
    </form>
  </div>


</body>
</html>

<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>
