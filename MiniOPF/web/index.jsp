<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 11.02.2021
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MiniOPF</title>
</head>
<body>
<div align="middle">
    <form action="/login" method="get" name="loginForm">
        <div>
            <input tabindex="1" placeholder="LOGIN" type="text" name="login" required>
        </div>
        <br>
        <div>
            <input tabindex="2" placeholder="PASSWORD" type="password" name="password" required>
        </div>
        <br>
        <div>
            <button type="submit">Sing in</button>
        </div>
    </form>
    <button type="submit" formaction="">Register Customer</button>
    <button type="submit" formaction="">Register Employee</button>
</div>
</body>
</html>