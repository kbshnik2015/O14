<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My orders</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/options.css">
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
    <jsp:useBean id="model" scope="page" class="model.ModelDB"/>
    <jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
</head>
<body>
<c:import url="Header.jsp"/>
<br>
            <div class="grid_body">
                <div class="passwordInfo">
                    <form action="/CustomerOptionsServlet" method="post">
                        <p>Old password: <input required type="password" autocomplete="off" name="oldPassword" value=""></p>
                        <br>
                        <p>New password: <input required type="password" autocomplete="off" name="newPassword" value=""></p>
                        <br>
                        <p>Repeat new password: <input required type="password" autocomplete="off" name="newPassword2" value=""></p>
                        <br>
                        <br>
                        <button type="submit" name="changePassword" value="click">Change password</button>
                    </form>
                </div>
                <div class="info">
                    <form action="/CustomerOptionsServlet" method="post" id="userData">
                        <p>Name: <input type="text" name="name" value="${currentUser.firstName}"></p>
                        <br>
                        <p>Lsat name: <input type="text" name="lastName" value="${currentUser.lastName}"></p>
                        <br>
                        <p>Login: <input type="text" name="login" readonly value="${currentUser.login}"></p></p>
                        <br>
                        <p>Address: <input type="text" name="address"  value="${currentUser.address}"></p>
                        <br>
                        <button type="submit" name="save" value="click" >Ok (save)</button>
                    </form>
                    <br>
                    <br>
                    <a href="/CustomerNavigationServlet?ref=options" class="hrefButton">Cancel</a>
                </div>
            </div>
</body>
</html>
