<!DOCTYPE html>
<html>
<head>
    <title>exQUIZit</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Homepage/WellWellWell.css">
</head>
<body>

<div class="container">
    <div class="center-text">exQUIZit</div>
    <hr />
    <div class = "container2">
        <div class = "intro-text">WELLCOME</div>
        <div class = "opciones">
            <a href="<%= request.getContextPath() %>/UserAuthentication/loginPage.jsp" class="button">Sign In</a>
            <a href="<%= request.getContextPath() %>/UserAuthentication/newAccount.jsp" class="button">Sign Up</a>
            <a href="" class="button">Enter as Guest</a>
        </div>
    </div>
</div>

</body>
</html>
