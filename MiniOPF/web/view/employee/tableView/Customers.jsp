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
                    <input id="idAscending" type="radio" name="sort" value="idAscending">
                    <label for="idAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="idDescending" type="radio" name="sort" value="idDescending">
                    <label for="idDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                   <br>
                    <input class="ShortInput" type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    First name
                    <input id="firstNameAscending" type="radio" name="sort" value="firstNameAscending">
                    <label for="firstNameAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="firstNameDescending" type="radio" name="sort" value="firstNameDescending">
                    <label for="firstNameDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="firstName" value="${filterParams.get("firstName")}">
                </td>
                <td>
                    Last name
                    <input id="lastNameAscending" type="radio" name="sort" value="lastNameAscending">
                    <label for="lastNameAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="lastNameDescending" type="radio" name="sort" value="lastNameDescending">
                    <label for="lastNameDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="lastName" value="${filterParams.get("lastName")}">
                </td>
                <td>
                    Login
                    <input id="loginAscending" type="radio" name="sort" value="loginAscending">
                    <label for="loginAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="loginDescending" type="radio" name="sort" value="loginDescending">
                    <label for="loginDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                     <br>
                    <input type="text" name="login" value="${filterParams.get("login")}">
                </td>
                <td>
                    Address
                    <input id="addressAscending" type="radio" name="sort" value="addressAscending">
                    <label for="addressAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="addressDescending" type="radio" name="sort" value="addressDescending">
                    <label for="addressDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                   <br>
                    <input type="text" name="address" value="${filterParams.get("address")}">
                </td>
                <td>
                    Balance
                    <input id="balanceAscending" type="radio" name="sort" value="balanceAscending">
                    <label for="balanceAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="balanceDescending" type="radio" name="sort" value="balanceDescending">
                    <label for="balanceDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
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
