<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Services</title>
        <link rel="stylesheet" href="/src/main/resources/css/employee/table.css">
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="services" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <form action="/employee/NavigationServlet" method="get" name="navigation" style="margin: 0px">
        <button name="allOrders" value="click" type="submit" >All orders</button>
        <button name="myOrders" value="click" type="submit">My orders</button>
        <button name="services" value="click" type="submit" disabled="disabled">Services</button>
        <button name="spec" value="click" type="submit">Specifications</button>
        <button name="customers" value="click" type="submit">Customers</button>
        <button name="districts" value="click" type="submit">Districts</button>
    </form>
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/employee/ServicesTableServlet" method="post">
            <c:import url="tableButtons/servicesButtons.jsp"/>
        <thead>
            <tr bgcolor="#a9a9a9">
                <th><input type="checkbox" id="all" ></th>
                <td>
                    id
                    <button class="arrow" type="button" name="idSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="idSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input class="ShortInput" type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    Pay day
                    <button class="arrow" type="button" name="payDaySortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="payDaySortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text" name="payDay" value="${filterParams.get("payDay")}">
                </td>
                <td>
                    Specification id
                    <button class="arrow" type="button" name="specIdSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="specIdSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text"name="specificationId" value="${filterParams.get("specificationId")}">
                </td>
                <td>
                    Service status
                    <button class="arrow" type="button" name="serviceStatusSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="serviceStatusSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <select size="1"  name="serviceStatus">
                        <option disabled>Choose service status</option>
                        <option selected value="">-</option>
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="DISCONNECTED">DISCONNECTED</option>
                        <option value="PAY_MONEY_SUSPENDED">PAY_MONEY_SUSPENDED</option>
                        <option value="SUSPENDED">SUSPENDED</option>
                    </select>
                </td>
                <td>
                    Customer id
                    <button class="arrow" type="button" name="customerIdSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                    <button class="arrow" type="button" name="customerIdSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                    <br>
                    <input type="text" name="customerId" value="${filterParams.get("customerId")}">
                </td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="service" items="${services}">
                <tr>
                    <th><input type="checkbox" name="checks" value="${service.id}"></th>
                    <td><a href="/employee/ServicesTableServlet?id=${service.id }">${service.id }</a></td>
                    <td>${service.payDay}</td>
                    <td><a href="/employee/SpecsTableServlet?id=${service.specificationId}">${service.specificationId}</a></td>
                    <td>${service.serviceStatus}</td>
                    <td><a href="/employee/CustomersTableServlet?id=${service.customerId}">${service.customerId}</a></td>
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
