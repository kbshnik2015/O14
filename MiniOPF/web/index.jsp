<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MiniOPF</title>
</head>
<body>
<div align="middle">
    <form action="/login" method="post" name="loginForm">
        <div>
            <input tabindex="1" placeholder="LOGIN" type="text" name="login" required>
        </div>
        <br>
        <div>
            <input tabindex="2" placeholder="PASSWORD" type="password" name="password" required>
        </div>
        <br>
        <div>
            <button type="submit">Sign in</button>
        </div>
    </form>
    <button type="button" onClick='location.href="/view/registration/CustomerRegistration.jsp"' >Register Customer</button>
    <button type="button" onClick='location.href="/view/registration/EmployeeRegistration.jsp"'>Register Employee</button>
</div>
</body>
</html>
