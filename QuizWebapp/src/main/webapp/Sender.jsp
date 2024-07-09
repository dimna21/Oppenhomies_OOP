<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Send Challenge</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Sender.css">
</head>
<body>
<form action="<%= request.getContextPath() %>/SendChallengeServlet" method="post">
    <div>
        <label for="Quiz ID">Quiz ID:</label>
        <input type="text" id="Quiz ID" name="Quiz ID" >
    </div>
    <div>
        <button type="submit" class="challenge-button">Send Challenge</button>
    </div>
</form>
<div class="wave"></div>
<div class="wave"></div>
<div class="wave"></div>
</body>
</html>