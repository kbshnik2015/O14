<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="customers" scope="request" type="java.util.List"/>
    <jsp:useBean id="employees" scope="request" type="java.util.List"/>
    <jsp:useBean id="specs" scope="request" type="java.util.List"/>
    <jsp:useBean id="services" scope="request" type="java.util.List"/>
    <jsp:useBean id="order" scope="request" type="model.dto.OrderDTO"/>
    <jsp:useBean id="employee" scope="request" type="model.dto.EmployeeDTO"/>
</head>
<body>
<div align="middle">
    <form action="/employee/MyOrdersTableServlet" method="post">
        <input hidden name="id" value="${order.id}">
        <table>
            <tbody>
            <tr>
                <td>AIM</td>
                <td>
                    <select name="aim">
                        <c:choose>
                            <c:when test="${order.orderAim.toString() == 'NEW'}">
                                <option selected value="NEW">NEW</option>
                                <option value="SUSPEND">SUSPEND</option>
                                <option value="RESTORE">RESTORE</option>
                                <option value="DISCONNECT">DISCONNECT</option>
                            </c:when>
                            <c:when test="${order.orderAim.toString() == 'SUSPEND'}">
                                <option value="NEW">NEW</option>
                                <option selected value="SUSPEND">SUSPEND</option>
                                <option value="RESTORE">RESTORE</option>
                                <option value="DISCONNECT">DISCONNECT</option>
                            </c:when>
                            <c:when test="${order.orderAim.toString() == 'RESTORE'}">
                                <option value="NEW">NEW</option>
                                <option value="SUSPEND">SUSPEND</option>
                                <option selected value="RESTORE">RESTORE</option>
                                <option value="DISCONNECT">DISCONNECT</option>
                            </c:when>
                            <c:when test="${order.orderAim.toString() == 'DISCONNECT'}">
                                <option value="NEW">NEW</option>
                                <option value="SUSPEND">SUSPEND</option>
                                <option value="RESTORE">RESTORE</option>
                                <option selected value="DISCONNECT">DISCONNECT</option>
                            </c:when>
                        </c:choose>
                    </select>
                </td>
            </tr>
            <tr>
                <td>CUSTOMER</td>
                <td>
                    <select name="customerId">
                        <c:forEach var="customer" items="${customers}">
                            <c:choose>
                                <c:when test="${customer.id == order.customerId}">
                                    <option selected value="${customer.id}">${customer.id}
                                        : ${customer.firstName} ${customer.lastName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${customer.id}">${customer.id}
                                        : ${customer.firstName} ${customer.lastName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>EMPLOYEE</td>
                <td>
                    <input tabindex="1" placeholder="${employee.id} : ${employee.firstName} ${employee.lastName}" type="text" name="employeeId" value="${employee.id} : ${employee.firstName} ${employee.lastName}" disabled>
                </td>
            </tr>
            <tr>
                <td>SPECIFICATION</td>
                <td>
                    <select name="specId">
                        <c:forEach var="spec" items="${specs}">
                            <c:choose>
                                <c:when test="${spec.id == order.specId}">
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
                <td>SERVICE</td>
                <td>
                    <select name="serviceId">
                        <option value="">-</option>
                        <c:forEach var="service" items="${services}">
                            <c:choose>
                                <c:when test="${service.id == order.serviceId}">
                                    <option selected value="${service.id}">${service.id}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${service.id}">${service.id}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>STATUS</td>
                <td>
                    <select name="status">
                        <c:choose>
                        <c:when test="${order.orderStatus.toString() == 'ENTERING'}">
                        <option selected value="ENTERING">ENTERING
                        <option value="IN_PROGRESS">IN PROGRESS
                        <option value="COMPLETED">COMPLETED
                        <option value="CANCELLED">CANCELLED
                        <option value="SUSPENDED">SUSPENDED
                            </c:when>
                            <c:when test="${order.orderStatus.toString() == 'IN_PROGRESS'}">
                        <option value="ENTERING">ENTERING
                        <option selected value="IN_PROGRESS">IN PROGRESS
                        <option value="COMPLETED">COMPLETED
                        <option value="CANCELLED">CANCELLED
                        <option value="SUSPENDED">SUSPENDED
                            </c:when>
                            <c:when test="${order.orderStatus.toString() == 'COMPLETED'}">
                        <option value="ENTERING">ENTERING
                        <option value="IN_PROGRESS">IN PROGRESS
                        <option selected value="COMPLETED">COMPLETED
                        <option value="CANCELLED">CANCELLED
                        <option value="SUSPENDED">SUSPENDED
                            </c:when>
                            <c:when test="${order.orderStatus.toString() == 'CANCELLED'}">
                        <option value="ENTERING">ENTERING
                        <option value="IN_PROGRESS">IN PROGRESS
                        <option value="COMPLETED">COMPLETED
                        <option selected value="CANCELLED">CANCELLED
                        <option value="SUSPENDED">SUSPENDED
                            </c:when>
                            <c:when test="${order.orderStatus.toString() == 'SUSPENDED'}">
                        <option value="ENTERING">ENTERING
                        <option value="IN_PROGRESS">IN PROGRESS
                        <option value="COMPLETED">COMPLETED
                        <option value="CANCELLED">CANCELLED
                        <option selected value="SUSPENDED">SUSPENDED
                            </c:when>
                            </c:choose>
                    </select>
                </td>
            </tr>
            <tr>
                <td>ADDRESS</td>
                <td><input tabindex="1" placeholder="${order.address}" type="text" name="address"></td>
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