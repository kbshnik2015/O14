<jsp:useBean id="model" class="model.ModelDB"/>
<jsp:useBean id="controller" class="controller.Controller"/>
<jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
<jsp:useBean id="accessibleSpecs" scope="request" type="java.util.List"/>
<jsp:useBean id="notAvailableSpecs" scope="request" type="java.util.List"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/src/main/resources/css/customer/specification.css">
<html>
<head>
    <title>Specifications</title>
</head>
<body>
<c:import url="Header.jsp"/>
<br>
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
<div class="header">
    <h2>Available specifications:</h2>
</div>
<div class="grid_body">
    <c:if test="${accessibleSpecs.isEmpty()}">
        <div class="spec_message">
            <p>
                There isn't available specifications
            </p>
        </div>
    </c:if>
    <c:forEach var="spec" items="${accessibleSpecs}">
        <div class="item">
            <p>
            <h3>${spec.name}</h3></p>
            <br>
            <p><span class="price">$${controller.transformPriceInCorrectForm(spec.price)}</span> <span
                    class="small gray">per month</span></p>
            <c:if test="${!spec.districtsIds.isEmpty()}">
                <p>
                    <strong>Accessible district:</strong>
                    <c:forEach var="district" items="${spec.districtsIds}">
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${model.getDistrict(district).name}
                    </c:forEach>
                </p>
            </c:if>
            <br>
            <p class="descriptions">${spec.description}</p>
            <br>
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
<div class="header">
    <h2>Unavailable specifications:</h2>
</div>
<c:if test="${notAvailableSpecs.isEmpty()}">
    <div class="spec_message">
        <p>
            There isn't unavailable specifications
        </p>
    </div>
</c:if>
<div class="grid_body">
    <c:forEach var="spec" items="${notAvailableSpecs}">
        <div class="item">
            <p>
            <h3>${spec.name}</h3></p>
            <br>
            <p><span class="price">$${controller.transformPriceInCorrectForm(spec.price)}</span> <span
                    class="small gray">per month</span></p>
            <c:if test="${!spec.districtsIds.isEmpty()}">
                <p>
                    <strong>Accessible district:</strong>
                    <c:forEach var="district" items="${spec.districtsIds}">
                        <br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &#8226;${model.getDistrict(district).name}
                    </c:forEach>
                </p>
            </c:if>
            <br>
            <p class="descriptions">${spec.description}</p>
            <br>
            <br>
            <div class="message">
                <c:choose>
                    <c:when test="${controller.isAvailableSpecByDistrict(currentUser.id,spec.id)}">
                        <span class="green_message">This specification is connected or there is a connection order.</span>
                    </c:when>
                    <c:otherwise>
                        <span class="red_message">This specification is not available in your district.</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:forEach>
</div>
<c:import url="/view/employee/popUps/customerPopUps/SpecificationPopUp.jsp"/>
</body>
</html>
