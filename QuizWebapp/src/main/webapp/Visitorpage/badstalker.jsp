<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Not Found</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Visitorpage/badstalker.css">
</head>
<body>
<button type="button" class="home-button" onclick="window.location.href='<%= request.getContextPath() %>/UserHomePage.jsp'">Back to Homepage</button>
<form action="<%= request.getContextPath() %>/LookUpServlet" method="post">
    <h1> No Such Account</h1>
    <div>
        <label for="username">Enter Username:</label>
        <input type="text" id="username" name="username">
    </div>
    <div>
        <button type="submit" class="button">Search</button>
    </div>
</form>

</body>
</html>

<div class="bg"></div>
<div class="bg bg2"></div>
<div class="bg bg3"></div>