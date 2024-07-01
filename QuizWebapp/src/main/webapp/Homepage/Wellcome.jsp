<!DOCTYPE html>
<html>
<head>
    <title>exQUIZit</title>
    <style>
        body, html {
            background-color: #643B9F;
            height: 70%;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
            font-weight: bold;
        }
        .container {
            text-align: center;
        }
        .center-text {
            font-size: 150px;
            margin: 0;
            color: #FFDD00FF;
            line-height: 1;
        }
        hr {
            width: 120%;
            color: #FFDD00FF;
            border: 2px solid #FFDD00FF;
            margin-left: -10%
        }
        .intro-text{
            font-weight: normal;
            font-size: 60px;
            margin-top: 50px;
            color: #00ffff;
        }
        .opciones {
            margin-top: 20px;
        }
        .opciones a {
            display: inline-block;
            text-decoration: none;
            color: white;
            background-color: #00b318;
            padding: 10px 20px;
            border-radius: 5px;
            margin: 0 10px;
            margin-top: 50px;
            font-size: 18px;
        }
        .opciones a:hover {
            background-color: #FFDD00FF;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="center-text">exQUIZit</div>
    <hr />
    <div class = "intro-text">WELLCOME</div>

    <div class = "opciones">
        <a href="<%= request.getContextPath() %>/UserAuthentication/loginPage.jsp" class="button">Sign In</a>
        <a href="<%= request.getContextPath() %>/UserAuthentication/newAccount.jsp" class="button">Sign Up</a>
        <a href="" class="button">Enter as Guest</a>
    </div>
</div>

</body>
</html>
