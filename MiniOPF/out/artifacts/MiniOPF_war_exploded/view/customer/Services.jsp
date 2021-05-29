<link rel="stylesheet" href="/src/main/resources/css/customer/item.css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="services" scope="request" type="java.util.List"/>
<jsp:useBean id="model" class="model.ModelDB"/>
<jsp:useBean id="controller" class="controller.Controller"/>
<html>
<head>
    <title>Services</title>
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
<c:when test="${services.isEmpty()}">
<div class="message_div">
    <span class="message">You have no active services. </span>
</div>
</c:when>
<c:otherwise>
<div class="grid">
    <div class="grid_body">
        <c:forEach var="service" items="${services}">
            <div class="item">
                <p><h3>${model.getSpecification(service.specificationId).name}</h3></p>
                <br>
                <p> <c:choose>
                    <c:when test="${service.serviceStatus.toString() eq 'ACTIVE'}">
                        <span class="green bold">${service.serviceStatus}</span>
                    </c:when>
                    <c:when test="${service.serviceStatus.toString() eq 'SUSPENDED'}">
                        <span class="yellow bold">${service.serviceStatus}</span>
                    </c:when>
                    <c:when test="${service.serviceStatus.toString() eq 'PAY_MONEY_SYSPENDED'}">
                        <span class="red bold">${service.serviceStatus}</span>
                    </c:when>
                </c:choose>
                </p>
                <c:choose>
                    <c:when test="${controller.isThereDisconnectionOrder(service.id)}">
                        <br>
                        <p>This service will be disabled.</p>
                        <br>
                        <br>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="cancel" value="click" class="cancel_button_in_centre">Cancel
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${controller.isThereSuspensionOrder(service.id)}">
                        <br>
                       <p>This service will be suspended.</p>
                        <br>
                        <br>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="cancel" value="click" class="cancel_button">Cancel</button>
                            <button type="submit" name="disconnect" value="click" class="disconnect_button">Disconnect
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${controller.isThereRestorationOrder(service.id)}">
                        <br>
                        This service will be restored.
                        <br>
                        <br>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="cancel" value="click" class="cancel_button_in_centre">Cancel
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${service.serviceStatus.toString() eq 'ACTIVE'}">
                        <br>
                        <p><strong>Next pay day:</strong> ${controller.transformDateInCorrectForm(service.payDay)}</p>
                        <br>
                        <br>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="suspend" value="click" class="suspend_button">Suspend</button>
                            <button type="submit" name="disconnect" value="click" class="disconnect_button">Disconnect
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${'SUSPENDED' eq service.serviceStatus.toString()}">
                        <br>
                        <br>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="restore" value="click" class="restore_button">Restore</button>
                            <button type="submit" name="disconnect" value="click" class="disconnect_button">Disconnect
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${'PAY_MONEY_SUSPENDED' eq service.serviceStatus.toString()}">
                        <p><span class="payMoney">Top up your balance on ${model.getSpecification(service.specificationId).price} rub and service will be restore</span>
                        </p>
                        <br>
                        <br>
                        <form action="/CustomerServicesServlet" method="post">
                            <input type="hidden" name="serviceId" value="${service.id}">
                            <button type="submit" name="disconnect" value="click" class="disconnect_button_in_center">
                                Disconnect
                            </button>
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </c:forEach>
    </div>
    </c:otherwise>
    </c:choose>
    <c:import url="/view/employee/popUps/customerPopUps/ServcesPopUp.jsp"/>
</div>
</html>
