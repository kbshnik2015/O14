<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Mine</title>
        <link rel="stylesheet" href="/src/main/resources/css/employee/table.css">
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="allOrders" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
        <form action="/employee/NavigationServlet" method="get" name="navigation">
            <button name="allOrders" value="click" type="submit" disabled="disabled">All orders</button>
            <button name="myOrders" value="click" type="submit">My orders</button>
            <button name="services" value="click" type="submit">Services</button>
            <button name="spec" value="click" type="submit">Specifications</button>
            <button name="customers" value="click" type="submit">Customers</button>
            <button name="districts" value="click" type="submit">Districts</button>
        </form>
        <table class="table table-bordered" border="1" cellspacing="0">
            <form action="/employee/MineTableServlet" method="post">
                <c:import url="tableButtons/ordersButtons.jsp"/>
                <thead>
                    <tr bgcolor="#a9a9a9">
                        <th ><input type="checkbox" id="all"></th>
                        <td>
                            id
                            <button class="arrow" type="button" name="idSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button class="arrow" type="button" name="idSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <input class="ShortInput" type="text" name="id" value=${filterParams.get("id")}>

                        </td>
                        <td>
                            Service id
                            <button class="arrow" type="button" name="serviceIdSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button class="arrow" type="button" name="serviceIdSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <input type="text" name="serviceId" value=${filterParams.get("serviceId")}>
                        </td>
                        <td>
                            Specification id
                            <button class="arrow" type="button" name="specIdSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button class="arrow" type="button" name="specIdSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <input type="text" name="specId" value=${filterParams.get("specId")}>
                        </td>
                        <td>
                            Customer id
                            <button class="arrow" type="button" name="customerIdSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button class="arrow" type="button" name="customerIdSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <input type="text" name="customerId" value=${filterParams.get("customerId")}>
                        </td>
                        <td>
                            Employee id
                            <button class="arrow" type="button" name="employeeIdSortDescending" value="click" ><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button class="arrow" type="button" name="employeeIdSortAscending" value="click"  ><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <input type="text" name="employeeId" value=${filterParams.get("employeeId")}>
                        </td>
                        <td>
                            Address
                            <button class="arrow" type="button" name="addressSortDescending" value="click" ><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button class="arrow" type="button" name="addressSortAscending" value="click" ><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <input type="text" name="address" value=${filterParams.get("address")}>
                        </td>
                        <td>
                            Order aim
                            <button class="arrow" type="button" name="aimSortDescending" value="click" ><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button class="arrow" type="button" name="aimSortAscending" value="click" ><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <select size="1"  name="orderAim">
                                <option disabled>Choose aim</option>
                                <option selected value="">-</option>
                                <option value="NEW">NEW</option>
                                <option value="SUSPENDED">SUSPENDED</option>
                                <option value="RESTORE">RESTORE</option>
                                <option value="DISCONNECT">DISCONNECT</option>
                            </select>
                        </td>
                        <td>
                            Order status
                            <button type="button" name="statusSortDescending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></button>
                            <button type="button" name="statusSortAscending" value="click"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></button>
                            <br>
                            <select size="1"  name="orderStatus">
                                <option disabled>Choose status</option>
                                <option selected value="">-</option>
                                <option value="ENTERING">ENTERING</option>
                                <option value="IN_PROGRESS">IN PROGRESS</option>
                                <option value="SUSPENDED">SUSPENDED</option>
                                <option value="COMPLETED">COMPLETED</option>
                                <option value="CANCELLED">CANCELLED</option>
                            </select>
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${allOrders}">
                        <tr>
                            <th><input type="checkbox" name="checks" value="${order.id}"></th>
                            <td><a href="/employee/MineTableServlet?id=${order.id }">${order.id }</a></td>
                            <td><a href="/employee/ServicesTableServlet?id=${order.serviceId}">${order.serviceId}</a></td>
                            <td><a href="/employee/SpecsTableServlet?id=${order.specId}">${order.specId}</a></td>
                            <td><a href="/employee/CustomersTableServlet?id=${order.customerId}">${order.customerId}</a></td>
                            <td>${order.employeeId}</td>
                            <td>${order.address}</td>
                            <td>${order.orderAim}</td>
                            <td>${order.orderStatus}</td>
                        </tr>
                    </c:forEach>
                <tbody>
            </form>
        </table>
        <script>
            $('.table').checkboxTable();
        </script>
    </body>
</html>

