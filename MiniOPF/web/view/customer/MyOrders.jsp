<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My orders</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/item.css">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="orders" scope="request" type="java.util.List"/>
    <jsp:useBean id="model" scope="page" class="model.ModelDB"/>
</head>
<body>
<c:import url="Header.jsp"/>
<div class="grid">
    <div class="grid_body">
        <c:forEach var="order" items="${orders}">
            <div class="item">
                <p class="name">${model.getSpecification(order.specId).name}</p>
                <p><span class="field">Aim:</span>${order.orderAim}</p>
                <p><span class="field">Status:</span>${order.orderStatus}</p>
                <form action="/CustomerMyOrdersServlet" method="post">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <button  type="submit" class="center_button">Cancel the order</button>
                </form>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
