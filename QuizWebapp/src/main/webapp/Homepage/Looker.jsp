<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Lookup</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Homepage/LookerDesign.css">
</head>
<body>
<form action="<%= request.getContextPath() %>/LookUpServlet" method="post">
    <div>
        <label for="usernameToLookUp">Username:</label>
        <input type="text" id="usernameToLookUp" name="usernameToLookUp">
    </div>
    <div>
        <button type="submit" class="button">Search</button>
    </div>
</form>
<div>
    <div class="wave"></div>
    <div class="wave"></div>
    <div class="wave"></div>
</div>
</body>
</html>