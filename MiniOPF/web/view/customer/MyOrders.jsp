<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My orders</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/item.css">
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
    <jsp:useBean id="orders" scope="request" type="java.util.List"/>
    <jsp:useBean id="model" scope="page" class="model.ModelDB"/>
</head>
<body>
    <c:import url="Header.jsp"/>
    <div class="grid">
        <div class="grid_body">
            <c:forEach var="order" items="${orders}">
                <div class="item">
                    <p>${model.getSpecification(order.specId).name}</p>
                    <p>Aim:${order.orderAim}</p>
                    <p>Status:${order.orderStatus}</p>
                    <c:choose>
                        <c:when test="${'SUSPENDED' eq order.orderStatus.toString() || 'ENTERING' eq order.orderStatus.toString() ||'IN_PROGRESS' eq order.orderStatus.toString()}">
                            <form action="/CustomerMyOrdersServlet" method="post">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <button type="submit" class="button">Cancel the order</button>
                            </form>
                            <br>
                        </c:when>
                        <c:when test="${ 'COMPLETED' eq order.orderStatus.toString() }">
                            <img src="https://img.icons8.com/color/48/000000/checked--v1.png"/>
                        </c:when>
                        <c:when test="${'CANCELLED' eq order.orderStatus.toString()}">
                            <img src="https://img.icons8.com/color/48/000000/cancel--v1.png"/>
                        </c:when>
                    </c:choose>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
