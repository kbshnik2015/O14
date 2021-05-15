<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="districts" scope="request" type="java.util.List"/>
    <jsp:useBean id="customer" scope="request" type="model.dto.CustomerDTO"/>
</head>
<body>
<div align="middle">
    <form action="/employee/CustomersTableServlet" method="post">
        <input hidden name="id" value="${customer.id}">
        <table>
            <tbody>
            <tr>
                <td>FIRST NAME</td>
                <td><input tabindex="1" placeholder="${customer.firstName}" type="text" name="firstName"></td>
            </tr>
            <tr>
                <td>LAST NAME</td>
                <td><input tabindex="1" placeholder="${customer.lastName}" type="text" name="lastName"></td>
            </tr>
            <tr>
                <td>DISTRICT ID</td>
                <td>
                    <select name="districtId">
                        <option value="">-</option>
                        <c:forEach var="dist" items="${districts}">
                            <c:choose>
                                <c:when test="${dist.id == customer.districtId}">
                                    <option selected value="${dist.id}">${dist.id} : ${dist.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${dist.id}">${dist.id} : ${dist.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>ADDRESS</td>
                <td><input tabindex="1" placeholder="${customer.address}" type="text" name="address"></td>
            </tr>
            <tr>
                <td>BALANCE</td>
                <td><input tabindex="1" placeholder="${customer.balance}" type="text" name="balance"></td>
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
