<%@ page import="DBpackage.DAOpackage.QuizDAO" %>
<%@ page import="DBpackage.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int quizId = (int) session.getAttribute("quizId");
    int percentScore = (int) session.getAttribute("score");
    long timespan = (long) session.getAttribute("timespan");
    Quiz quiz = QuizDAO.getQuizInfo(quizId);
%>
<html>
<head>
    <title>Quiz Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f0f0f0;
        }
        .container {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            max-width: 600px;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        .result-item {
            margin-bottom: 10px;
        }
        .retake-btn {
            background-color: #4CAF50;
            border: none;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin-top: 20px;
            cursor: pointer;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Quiz Result</h1>
    <div class="result-item">
        <strong>Quiz Name:</strong> <%= quiz.getName() %>
    </div>
    <div class="result-item">
        <p>Quiz Description:</p> <%= quiz.getDescription() %>
    </div>
    <div class="result-item">
        <strong>Score:</strong> <%= percentScore %>%
    </div>
    <div class="result-item">
        <strong>Time Taken:</strong> <%= timespan / 1000 %> seconds
    </div>

    <form action="QuizSummeryPage.jsp?quizId=<%=quizId%>" method="get">
        <input type="hidden" name="quizId" value="<%= quizId %>">
        <button type="submit" class="retake-btn">Retake Quiz</button>
    </form>
</div>
</body>
</html>