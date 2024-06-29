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
    </head>
    <body>
        <h1>Please Try Again</h1>
        <p>Either your name or password is incorrect, Please try again.</p>

        <form action="LoginServlet" method="post">
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

        <p><a href="newAccount.jsp">Create New Account</a></p>
    </body>
</html>
