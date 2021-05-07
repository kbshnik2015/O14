<%@ page import="model.Model" %>
<%@ page import="controller.Controller" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="model.ModelFactory" %>
<jsp:useBean id="nextPayDay" scope="request" class="java.util.Date"/>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
<link rel="stylesheet" href="/src/main/resources/css/customer/maine.css">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mine</title>
</head>
<body>
        <c:import url="Header.jsp"/>
        <main class="main">
            <p>${currentUser.firstName} ${currentUser.lastName}</p>
            <p>Address: ${currentUser.address}</p>
            <p>Balance: ${currentUser.balance}</p>
            <p>The next day of debiting money: ${nextPayDay}</p>
        </main>

</body>
</html>
