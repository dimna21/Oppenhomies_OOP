<%@ page import="DBpackage.Quiz" %>
<%@ page import="DBpackage.DatabaseAccess" %><%--
  Created by IntelliJ IDEA.
  User: Nicolas
  Date: 6/29/2024
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DatabaseAccess dbAccess = (DatabaseAccess) application.getAttribute("DatabaseAccess");
    Quiz quiz = dbAccess.getQuizInfo(Integer.parseInt(request.getParameter("QuizId")));
    String quizName = quiz.getName();
    String quizDescription = quiz.getDescription();
    int quizCreatorID = quiz.getCreatorID();
%>
<html>
<head>
    <title>Quiz Summery</title>
</head>
<body>
    <h1>Quiz: <%= quizName %></h1>
</body>
</html>
