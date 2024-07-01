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
    </head>

    <body>

        <h1>
            <%
                String name = request.getParameter("username");
            %>
            The Name <%= name%> is Already In Use
        </h1>

        <p>Please enter another name and password.</p>

        <form action="<%= request.getContextPath() %>/NewAccServlet" method="post">
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username">
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password">
                <button type="submit">Login</button>
            </div>
        </form>

    </body>
</html>
