<jsp:useBean id="nextPayDay" scope="request" class="java.lang.String"/>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
<link rel="stylesheet" href="/src/main/resources/css/customer/myProfile.css">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mine</title>
</head>
<body>
    <c:import url="Header.jsp"/>
    <main class="main">
        <p>${currentUser.firstName} ${currentUser.lastName}</p>
        <a href="/view/customer/EditProfile.jsp" class="edit_profile"><img src="https://img.icons8.com/nolan/40/gear.png"/></a>
        <p>Address: ${currentUser.address}</p>
        <p>Balance: ${currentUser.balance}</p>
        <p>The next day of debiting money: ${nextPayDay}</p>
        <form action="/CustomerTopUpServlet" method="post">
            <p>Enter the top-up amount:<input type="number" min="0" name="topUp"></p>
            <p><button type="submit" name="topUpButton" value="click">TOP UP</button></p>
        </form>
    </main>
</body>
</html>
