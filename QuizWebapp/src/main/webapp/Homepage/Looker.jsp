<%--
  Created by IntelliJ IDEA.
  User: Khatuna.Tkebuchava
  Date: 7/7/2024
  Time: 12:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<%= request.getContextPath() %>/LookUpServlet" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username">
    </div>
    <div>
        <button type="submit" class="button">Search</button>
    </div>
</form>

</body>
</html>
