<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="customers" scope="request" type="java.util.List"/>
    <jsp:useBean id="employees" scope="request" type="java.util.List"/>
    <jsp:useBean id="specs" scope="request" type="java.util.List"/>
    <jsp:useBean id="services" scope="request" type="java.util.List"/>
    <jsp:useBean id="employee" scope="request" type="model.dto.EmployeeDTO"/>
</head>
<body>
<a href="#popup"
   style="position: absolute;
           top: 10px;
           right: 10px;
"
   class="info"
   data-toggle="popover"
   data-placement="auto"
   title="Click here to learn more."
   data-trigger="hover"
>About this page</a>
<div align="middle">
    <form action="/employee/MyOrdersTableServlet" method="post">
        <table>
            <tbody>
            <tr>
                <td>AIM</td>
                <td>
                    <select name="aim">
                        <option value="NEW">NEW
                        <option value="SUSPEND">SUSPEND
                        <option value="RESTORE">RESTORE
                        <option value="DISCONNECT">DISCONNECT
                    </select>
                </td>
            </tr>
            <tr>
                <td>CUSTOMER</td>
                <td>
                    <select name="customerId">
                        <c:forEach var="customer" items="${customers}">
                            <option value="${customer.id}">
                                    ${customer.id} : ${customer.firstName} ${customer.lastName}</option>
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
                            <option value="${spec.id}">${spec.id} : ${spec.name}</option>
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
                                <option value="${service.id}">${service.id}</option>
                            </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>STATUS</td>
                <td>
                    <select name="status">
                        <option value="ENTERING">ENTERING
                        <option value="IN_PROGRESS">IN PROGRESS
                        <option value="COMPLETED">COMPLETED
                        <option value="CANCELLED">CANCELLED
                        <option value="SUSPENDED">SUSPENDED
                    </select>
                </td>
            </tr>
            <tr>
                <td>ADDRESS</td>
                <td><input tabindex="1" placeholder="Enter address" type="text" name="address"></td>
            </tr>
            </tbody>
        </table>

        <div>
            <button type="submit" name="confirmCreate" value="click">OK</button>
        </div>
    </form>
</div>
<c:import url="/view/employee/popUps/createOrderPagePopUp.jsp"/>
</body>
</html>
