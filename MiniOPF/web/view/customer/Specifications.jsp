<jsp:useBean id="model" class="model.ModelDB"/>
<jsp:useBean id="controller" class="controller.Controller"/>
<jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
<html>
<head>
    <title>Specifications</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/specification.css">
    <jsp:useBean id="accessibleSpecs" scope="request" type="java.util.List"/>
    <jsp:useBean id="notAvailableSpecs" scope="request" type="java.util.List"/>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>
<body>
<c:import url="Header.jsp"/>
<p>
<h2>Available specifications:</h2></p>
<div class="grid_body">
    <c:forEach var="spec" items="${accessibleSpecs}">
        <div class="item">
            <p class="name">${spec.name}</p>
            <p><span class="field">Price:</span> ${spec.price}</p>
            <c:if test="${!spec.districtsIds.isEmpty()}">
               <p>
                   <span class="field">Accessible district:</span>
                   <c:forEach var="district" items="${spec.districtsIds}">
                       <br>
                       ${model.getDistrict(district).name}
                   </c:forEach>
               </p>
            </c:if>
            <br>
            <p class="descriptions">${spec.description}</p>
            <br>
            <c:choose>
                <c:when test="${currentUser.balance < spec.price}">
                    <span class="payMoney">You don't have enough money for connecting this specification.</span>
                </c:when>
                <c:when test="${controller.isAvailableSpecByDistrict(currentUser.id,spec.id)}">
                    <form action="/CustomerSpecServlet" method="post">
                        <input type="hidden" name="specId" value="${spec.id}">
                        <button class="connect_button" type="submit" name="connect" value="click">Connect</button>
                    </form>
                </c:when>
            </c:choose>
        </div>
    </c:forEach>
</div>
<br>
<h2>Not available specifications:</h2>
<div class="grid_body">
    <c:forEach var="spec" items="${notAvailableSpecs}">
        <div class="item">
            <p class="name">${spec.name}</p>
            <p>
                <spam class="field">Price:</spam>
                    ${spec.price}</p>
            <p>
                <span class="field">Accessible district:</span>
                <c:forEach var="district" items="${spec.districtsIds}">
                    <br>
                    ${model.getDistrict(district).name}
                </c:forEach>
            </p>
            <br>
            <p class="descriptions">${spec.description}</p>
            <br>
            <span class="payMoney">This specification is not available in your district.</span>
        </div>
    </c:forEach>
</div>
</body>
</html>
