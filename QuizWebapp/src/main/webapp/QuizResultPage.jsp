<%@ page import="DBpackage.DAOpackage.QuizDAO" %>
<%@ page import="DBpackage.Quiz" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int quizId = (Integer) session.getAttribute("quizId");
    int percentScore = (Integer) session.getAttribute("score");
    long timespan = (Long) session.getAttribute("timespan");
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
            border: 2px solid #371F76;
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
        .bg {
            animation:slide 3s ease-in-out infinite alternate;
            background-image: linear-gradient(-60deg, #6c3 50%, #09f 50%);
            bottom:0;
            left:-50%;
            opacity:.5;
            position:fixed;
            right:-50%;
            top:0;
            z-index:-1;
        }

        .bg2 {
            animation-direction:alternate-reverse;
            animation-duration:4s;
        }

        .bg3 {
            animation-duration:5s;
        }

        .content {
            background-color:rgba(255,255,255,.8);
            border-radius:.25em;
            box-shadow:0 0 .25em rgba(0,0,0,.25);
            box-sizing:border-box;
            left:50%;
            padding:10vmin;
            position:fixed;
            text-align:center;
            top:50%;
            transform:translate(-50%, -50%);
        }

        h1 {
            font-family:monospace;
        }

        @keyframes slide {
            0% {
                transform:translateX(-25%);
            }
            100% {
                transform:translateX(25%);
            }
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
<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>