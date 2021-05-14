<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="model" scope="page" class="model.ModelDB"/>
<jsp:useBean id="controller" scope="page" class="controller.Controller"/>
<jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
<html>
<head>
    <title>Specifications</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/item.css">
    <jsp:useBean id="specs" scope="request" type="java.util.List"/>
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>

</head>
<body>
<c:import url="Header.jsp"/>
<div class="grid">
    <div class="grid_body">
        <c:forEach var="spec" items="${specs}">
            <div class="item">
                <p>${spec.name}</p>
                <p>Price :${spec.price}</p>
                <p>
                    Accessible district:
                    <c:choose>
                        <c:when test="${spec.districtsIds.isEmpty()}">
                            all
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="district" items="${spec.districtsIds}">
                                <br>
                                ${model.getDistrict(district).name}
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </p>
                <p>Description: ${spec.description}</p>
                <br>
                <c:choose>
                    <c:when test="${currentUser.balance < spec.price}">
                        <span class="payMoney">You don't have enough money for connecting this specification.</span>
                    </c:when>
                    <c:when test="${controller.isAvailableService(currentUser.id,spec.id)}">
                        <form action="/CustomerSpecServlet" method="post">
                            <input type="hidden" name="specId" value="${spec.id}">
                            <button type="submit" name="connect" value="click">Connect</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <span class="payMoney">This specification is not available in your district.</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
