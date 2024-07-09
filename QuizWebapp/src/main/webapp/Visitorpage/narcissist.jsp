<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>It's You</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Visitorpage/narcissist.css">
</head>
<body>
<button type="button" class="home-button" onclick="window.location.href='<%= request.getContextPath() %>/UserHomePage.jsp'">Back to Homepage</button>
<form action="<%= request.getContextPath() %>/LookUpServlet" method="post">
    <h1> Why'd You Look For Yourself? </h1>
    <div>
        <label for="usernameToLookUp">Enter Username:</label>
        <input type="text" id="usernameToLookUp" name="usernameToLookUp">
    </div>
    <div>
        <button type="submit" class="button">Search</button>
    </div>
</form>
</body>
</html>
