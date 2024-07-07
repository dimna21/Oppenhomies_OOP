<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Lookup</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Homepage/LookerDesign.css">
</head>
<body>
<form action="<%= request.getContextPath() %>/LookUpServlet" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username">
    </div>
    <div>
        <button type="submit" class="button">Search</button>
    </div>
</form>
</body>
</html>