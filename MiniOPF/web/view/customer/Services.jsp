<html>
<head>
    <title>Services</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/item.css">
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
    <jsp:useBean id="services" scope="request" type="java.util.List"/>
    <jsp:useBean id="model" class="model.ModelDB"/>
    <jsp:useBean id="controller" class="controller.Controller"/>
</head>
<body>
<c:import url="Header.jsp"/>
<div class="grid">
    <div class="grid_body">
        <c:forEach var="service" items="${services}">
            <div class="item">
                <p><span class="name">${model.getSpecification(service.specificationId).name}</span> </p>
                <p><span class="field">Status:</span> ${service.serviceStatus}</p>
                <c:choose>
                    <c:when test="${controller.isThereDisconnectionOrder(service.id)}">
                        <br>
                        This service will be disabled.
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="cancel" value="click" class="cancel_button_in_centre" >Cancel</button>
                        </form>
                    </c:when>
                    <c:when test="${controller.isThereSuspensionOrder(service.id)}">
                        <br>
                        This service will be suspended.
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="cancel" value="click" class="cancel_button" >Cancel</button>
                            <button type="submit" name="disconnect" value="click" class="disconnect_button" >Disconnect</button>
                        </form>
                    </c:when>
                    <c:when test="${controller.isThereRestorationOrder(service.id)}">
                        <br>
                        This service will be restored.
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="cancel" value="click" class="cancel_button_in_centre" >Cancel</button>
                        </form>
                    </c:when>
                    <c:when test="${service.serviceStatus.toString() eq 'ACTIVE'}">
                        <p><span class="field">Next pay day:</span> ${service.payDay}</p>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="suspend" value="click" class="suspend_button">Suspend</button>
                            <button type="submit" name="disconnect" value="click" class="disconnect_button" >Disconnect</button>
                        </form>
                    </c:when>
                    <c:when test="${'SUSPENDED' eq service.serviceStatus.toString()}">
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button  type="submit" name="restore" value="click" class="restore_button">Restore</button>
                            <button  type="submit" name="disconnect" value="click" class="disconnect_button">Disconnect</button>
                        </form>
                    </c:when>
                    <c:when test="${'PAY_MONEY_SUSPENDED' eq service.serviceStatus.toString()}">
                        <p><span class="payMoney">Top up your balance on ${model.getSpecification(service.specificationId).price} rub and service will be restore</span> </p>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button  type="submit" name="disconnect" value="click" class="disconnect_button_in_center">Disconnect</button>
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</div>
</html>
