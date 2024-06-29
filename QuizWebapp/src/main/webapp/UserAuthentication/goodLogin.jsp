<%--
  Created by IntelliJ IDEA.
  User: sandr
  Date: 5/25/2024
  Time: 4:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%
        String name = request.getParameter("username");
    %>
    <head>
        <title>Welcome <%= name%></title>
    </head>

    <body>
        <h1>Welcome <%= name%></h1>
    </body>
</html>
