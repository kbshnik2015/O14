<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer registration</title>
</head>
<body>
<h1>Customer registration</h1>
<br>
<br>
<form action="/RegistrationServlet" method="post">
    First name: <input type="text" name="firstName">
    <br>
    <br>
    Last name: <input type="text" name="lastName">
    <br>
    <br>
    Login: <input type="text" name="login">
    <br>
    <br>
    Password: <input type="password" name="password">
    <br>
    <br>
    Repeat password: <input type="password" name="repeatPassword">
    <br>
    <br>
    Address: <input type="text" name="address">
    <br>
    <br>
    <button type="submit"  name="button" value="customerRegistration">Registration</button>
</form>
</body>
</html>
