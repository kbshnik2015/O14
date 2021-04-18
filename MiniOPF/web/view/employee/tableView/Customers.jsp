<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Customers</title>
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="customers" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <form action="/NavigationServlet" method="get" name="navigation" style="margin: 0px">
        <button name="allOrders" value="click" type="submit">All orders</button>
        <button name="myOrders" value="click" type="submit">My orders</button>
        <button name="services" value="click" type="submit">Services</button>
        <button name="spec" value="click" type="submit">Specifications</button>
        <button name="customers" value="click" type="submit" disabled="disabled">Customers</button>
        <button name="districts" value="click" type="submit">Districts</button>
    </form>
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/CustomersTableServlet" method="get">
            <c:import url="../tableView/tableButtons/customersButtons.jsp"/>
        <thead>
            <tr bgcolor="#a9a9a9">
                <th style="text-align:center;"><input type="checkbox" id="all" ></th>
                <td>
                    id
                    <br>
                    <input type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    First name
                    <br>
                    <input type="text" name="firstName" value="${filterParams.get("firstName")}">
                </td>
                <td>
                    Last name
                    <br>
                    <input type="text" name="lastName" value="${filterParams.get("lastName")}">
                </td>
                <td>
                    Login
                    <br>
                    <input type="text" name="login" value="${filterParams.get("login")}">
                </td>
                <td>
                    Address
                    <br>
                    <input type="text" name="address" value="${filterParams.get("address")}">
                </td>
                <td>
                    Balance
                    <br>
                    <input type="text" name="balance" value="${filterParams.get("balance")}">
                </td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="customer" items="${customers}">
                <tr>
                    <th style="text-align:center;"><input type="checkbox"></th>
                    <td>${customer.id }</td>
                    <td>${customer.firstName}</td>
                    <td>${customer.lastName}</td>
                    <td>${customer.login}</td>
                    <td>${customer.address}</td>
                    <td>${customer.balance}</td>
                </tr>
            </c:forEach>
        </tbody>
        </form>
    </table>
    <script>
        $('.table').checkboxTable();
    </script>
    </body>
</html>
