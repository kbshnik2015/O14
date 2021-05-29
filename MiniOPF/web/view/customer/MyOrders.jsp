<link rel="stylesheet" href="/src/main/resources/css/customer/item.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="orders" scope="request" type="java.util.List"/>
<jsp:useBean id="model" scope="page" class="model.ModelDB"/>
<html>
<head>
    <title>My orders</title>
</head>
<body>
<c:import url="Header.jsp"/>
<p><a href="#popup"
      style="position: absolute;
           top: 9%;
           right: 10px;"
      data-toggle="popover"
      data-placement="auto"
      title="Click here to learn more."
      data-trigger="hover"
><img src="https://img.icons8.com/officexs/18/000000/info.png" alt=""
      style="position: relative;
      bottom: 2px;"
/> About this page</a></p>
<br>
<c:choose>
    <c:when test="${orders.isEmpty()}">
        <div class="message_div">
            <span class="message">You have no active orders. </span>
        </div>
    </c:when>
    <c:otherwise>
        <div class="grid">
            <div class="grid_body">
                <c:forEach var="order" items="${orders}">
                    <div class="item">
                        <h3>${model.getSpecification(order.specId).name}</h3>
                        <br>
                        <c:choose>
                            <c:when test="${order.orderAim.toString() eq 'NEW'}">
                                <p><strong>Aim: </strong> <span class="green">${order.orderAim}</span> </p>
                            </c:when>
                            <c:when test="${order.orderAim.toString() eq 'RESTORE'}">
                                <p><strong>Aim: </strong> <span class="green">${order.orderAim}</span> </p>
                            </c:when>
                            <c:when test="${order.orderAim.toString() eq 'SUSPEND'}">
                                <p><strong>Aim: </strong> <span class="yellow">${order.orderAim}</span> </p>
                            </c:when>
                            <c:when test="${order.orderAim.toString() eq 'DISCONNECT'}">
                                <p><strong>Aim: </strong> <span class="red">${order.orderAim}</span> </p>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${order.orderStatus.toString() eq 'ENTERING'}">
                                <p><strong>Status: </strong> <span class="yellow">${order.orderStatus}</span></p>
                            </c:when>
                            <c:when test="${order.orderStatus.toString() eq 'IN_PROGRESS'}">
                                <p><strong>Status: </strong> <span class="green">${order.orderStatus}</span> </p>
                            </c:when>
                            <c:when test="${order.orderStatus.toString() eq 'SUSPENDED'}">
                                <p><strong>Status: </strong> <span class="yellow">${order.orderStatus}</span> </p>
                            </c:when>
                        </c:choose>
                        <br>
                        <br>
                        <form action="/CustomerMyOrdersServlet" method="post">
                            <input type="hidden" name="orderId" value="${order.id}">
                            <button type="submit" class="center_button">Cancel the order</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<c:import url="/view/employee/popUps/customerPopUps/MyOrderPopUp.jsp"/>
</body>
</html>
