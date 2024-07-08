<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>It's You</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Homepage/LookerDesign.css">
</head>
<body>
<form action="<%= request.getContextPath() %>/LookUpServlet" method="post">
    <h1> Why'd You Look For Yourself? </h1>
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