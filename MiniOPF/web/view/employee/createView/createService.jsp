<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="specs" scope="request" type="java.util.List"/>
    <jsp:useBean id="customers" scope="request" type="java.util.List"/>
</head>
<body>
<div align="middle">
    <form action="/employee/ServicesTableServlet" method="post">
        <table>
            <tbody>
            <tr>
                <td>PAYDAY</td>
                <td><input tabindex="1" placeholder="DD" type="text" name="day">-<input tabindex="1" placeholder="MM" type="text" name="month">-<input tabindex="1" placeholder="YEAR" type="text" name="year"></td>
            </tr>
            <tr>
                <td>SPECIFICATION</td>
                <td>
                    <select name="specId" required>
                        <c:forEach var="spec" items="${specs}">
                            <option value="${spec.id}">${spec.id} : ${spec.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>STATUS</td>
                <td>
                    <select name="status" required>
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="DISCONNECTED">DISCONNECTED</option>
                        <option value="PAY_MONEY_SUSPENDED">PAY MONEY SUSPENDED</option>
                        <option value="SUSPENDED">SUSPENDED</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>CUSTOMER</td>
                <td>
                    <select name="customerId" required>
                        <c:forEach var="customer" items="${customers}">
                            <option value="${customer.id}">
                                    ${customer.id} : ${customer.firstName} ${customer.lastName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>

        <div>
            <button type="submit" name="confirmCreate" value="click">OK</button>
        </div>
    </form>
</div>
</body>
</html>
