<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>exQUIZit</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Homepage/WellWellWell.css">
</head>
<body>

<div class="container">
    <div class="center-text">QUIZZLER &#129300; </div>
    <hr />
    <div class="container2">
        <div class="intro-text">stick yo &#9757; gyatt &#127825; out for Quizzler &#129397; </div>
        <div class="opciones">
            <a href="<%= request.getContextPath() %>/UserAuthentication/loginPage.jsp" class="button">Sign In</a>
            <a href="<%= request.getContextPath() %>/UserAuthentication/newAccount.jsp" class="button">Sign Up</a>
            <a href="" class="button">Enter as Guest</a>
        </div>
    </div>
</div>

</body>
</html>
