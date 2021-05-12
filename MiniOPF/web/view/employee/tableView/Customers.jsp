<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Customers</title>
        <link rel="stylesheet" href="/src/main/resources/css/employee/table.css">
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="customers" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <form action="/employee/NavigationServlet" method="get" name="navigation" style="margin: 0px">
        <button name="allOrders" value="click" type="submit">All orders</button>
        <button name="myOrders" value="click" type="submit">My orders</button>
        <button name="services" value="click" type="submit">Services</button>
        <button name="spec" value="click" type="submit">Specifications</button>
        <button name="customers" value="click" type="submit" disabled="disabled">Customers</button>
        <button name="districts" value="click" type="submit">Districts</button>
    </form>
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/employee/CustomersTableServlet" method="post">
            <c:import url="tableButtons/customersButtons.jsp"/>
        <thead>
            <tr bgcolor="#a9a9a9">
                <th ><input type="checkbox" id="all" ></th>
                <td class="idColumn">
                    id
                    <button class="arrow" type="button" name="idSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="idSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input class="ShortInput" type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    First name
                    <button class="arrow" type="button" name="firstNameSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="firstNameSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text" name="firstName" value="${filterParams.get("firstName")}">
                </td>
                <td>
                    Last name
                    <button class="arrow" type="button" name="lastNameSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="lastNameSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text" name="lastName" value="${filterParams.get("lastName")}">
                </td>
                <td>
                    Login
                    <button class="arrow" type="button" name="loginSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="loginSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text" name="login" value="${filterParams.get("login")}">
                </td>
                <td>
                    Address
                    <button class="arrow" type="button" name="addressSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="addressSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text" name="address" value="${filterParams.get("address")}">
                </td>
                <td>
                    Balance
                    <button class="arrow" type="button" name="balanceSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="balanceSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text" name="balance" value="${filterParams.get("balance")}">
                </td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="customer" items="${customers}">
                <tr>
                    <th><input type="checkbox" name="checks" value="${customer.id}"></th>
                    <td><a href="/employee/CustomersTableServlet?id=${customer.id }">${customer.id }</a></td>
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
