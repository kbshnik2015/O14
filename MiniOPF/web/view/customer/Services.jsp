<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/item.css">
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
    <jsp:useBean id="services" scope="request" type="java.util.List"/>
    <jsp:useBean id="model" scope="page" class="model.ModelDB"/>
    <jsp:useBean id="controller" scope="page" class="controller.Controller"/>
</head>
<body>
<c:import url="Header.jsp"/>
<div class="grid">
    <div class="grid_body">
        <c:forEach var="service" items="${services}">
            <div class="item">
                <p>Name:${model.getSpecification(service.specificationId).name}</p>
                <p>Status: ${service.serviceStatus}</p>
                <p>Next pay day:${service.payDay}</p>
                <c:choose>
                    <c:when test="${controller.isThereDisconnectionOrder(service.id)}">
                        <br>
                        This service will be disabled.
                    </c:when>
                    <c:when test="${controller.isThereSuspensionOrder(service.id)}">
                        <br>
                        This service will be suspended.
                    </c:when>
                    <c:when test="${controller.isThereRestorationOrder(service.id)}">
                        <br>
                        This service will be restored.
                    </c:when>
                    <c:when test="${service.serviceStatus.toString() eq 'ACTIVE'}">
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="disconnect" value="click" class="button" >Disconnect</button>
                            <br>
                            <button type="submit" name="suspend" value="click" class="button">Suspend</button>
                        </form>
                    </c:when>
                    <c:when test="${'SUSPENDED' eq service.serviceStatus.toString()}">
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button  type="submit" name="restore" value="click" class="button">Restore</button>
                        </form>
                    </c:when>
                    <c:when test="${'PAY_MONEY_SUSPENDED' eq service.serviceStatus.toString()}">
                        <p><span class="payMoney">Top up your balance on ${model.getSpecification(service.specificationId).price} rub and service will be restore</span> </p>
                    </c:when>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</div>
</html>
