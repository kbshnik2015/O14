<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="specs" scope="request" type="java.util.List"/>
    <jsp:useBean id="customers" scope="request" type="java.util.List"/>
    <jsp:useBean id="service" scope="request" type="model.dto.ServiceDTO"/>
</head>
<body>
<div align="middle">
    <form action="/employee/ServicesTableServlet" method="post">
        <input hidden name="id" value="${service.id}">
        <table>
            <tbody>
            <tr>
                <td>PAYDAY</td>
                <td><input tabindex="1" placeholder="${service.payDay.day}" type="text" name="day">-<input tabindex="1" placeholder="${service.payDay.month}" type="text" name="month">-<input tabindex="1" placeholder="${service.payDay.year}" type="text" name="year"></td>
            </tr>
            <tr>
                <td>SPECIFICATION</td>
                <td>
                    <select name="specId" required>
                        <c:forEach var="spec" items="${specs}">
                            <c:choose>
                                <c:when test="${spec.id == service.specificationId}">
                                    <option selected value="${spec.id}">${spec.id} : ${spec.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${spec.id}">${spec.id} : ${spec.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>STATUS</td>
                <td>
                    <select name="status" required>
                        <c:choose>
                            <c:when test="${service.serviceStatus.toString() == 'ACTIVE'}">
                                <option selected value="ACTIVE">ACTIVE</option>
                                <option value="DISCONNECTED">DISCONNECTED</option>
                                <option value="PAY_MONEY_SUSPENDED">PAY MONEY SUSPENDED</option>
                                <option value="SUSPENDED">SUSPENDED</option>
                            </c:when>
                            <c:when test="${service.serviceStatus.toString() == 'DISCONNECTED'}">
                                <option value="ACTIVE">ACTIVE</option>
                                <option selected value="DISCONNECTED">DISCONNECTED</option>
                                <option value="PAY_MONEY_SUSPENDED">PAY MONEY SUSPENDED</option>
                                <option value="SUSPENDED">SUSPENDED</option>
                            </c:when>
                            <c:when test="${service.serviceStatus.toString() == 'PAY_MONEY_SUSPENDED'}">
                                <option value="ACTIVE">ACTIVE</option>
                                <option value="DISCONNECTED">DISCONNECTED</option>
                                <option selected value="PAY_MONEY_SUSPENDED">PAY MONEY SUSPENDED</option>
                                <option value="SUSPENDED">SUSPENDED</option>
                            </c:when>
                            <c:when test="${service.serviceStatus.toString() == 'SUSPENDED'}">
                                <option value="ACTIVE">ACTIVE</option>
                                <option value="DISCONNECTED">DISCONNECTED</option>
                                <option value="PAY_MONEY_SUSPENDED">PAY MONEY SUSPENDED</option>
                                <option selected value="SUSPENDED">SUSPENDED</option>
                            </c:when>
                        </c:choose>
                    </select>
                </td>
            </tr>
            <tr>
                <td>CUSTOMER</td>
                <td>
                    <select name="customerId" required>
                        <c:forEach var="customer" items="${customers}">
                            <c:choose>
                                <c:when test="${customer.id == service.customerId}">
                                    <option selected value="${customer.id}">${customer.id} : ${customer.firstName} ${customer.lastName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${customer.id}">${customer.id} : ${customer.firstName} ${customer.lastName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>

        <div>
            <button type="submit" name="confirmEdit" value="click">OK</button>
        </div>
    </form>
</div>
</body>
</html>
