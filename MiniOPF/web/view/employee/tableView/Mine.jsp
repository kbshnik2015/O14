<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Mine</title>
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="allOrders" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
        <form action="/employee/NavigationServlet" method="get" name="navigation" style="margin: 0px">
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
                        <th style="text-align:center;"><input type="checkbox" id="all"></th>
                        <td>
                            id
                            <br>
                            <input type="text" name="id" value=${filterParams.get("id")}>
                        </td>
                        <td>
                            Service id
                            <br>
                            <input type="text" name="serviceId" value=${filterParams.get("serviceId")}>
                        </td>
                        <td>
                            Specification id
                            <br>
                            <input type="text" name="specId" value=${filterParams.get("specId")}>
                        </td>
                        <td>
                            Customer id
                            <br>
                            <input type="text" name="customerId" value=${filterParams.get("customerId")}>
                        </td>
                        <td>
                            Employee id
                            <br>
                            <input type="text" name="employeeId" value=${filterParams.get("employeeId")}>
                        </td>
                        <td>
                            Address
                            <br>
                            <input type="text" name="address" value=${filterParams.get("address")}>
                        </td>
                        <td>
                            Order aim
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
                            <br>
                            <select size="1"  name="orderStatus">
                                <option disabled>Choose status</option>
                                <option selected value="">-</option>
                                <option value="ENTERING">ENTERING</option>
                                <option value="IN_PROGRESS">IN_PROGRESS</option>
                                <option value="SUSPENDED">SUSPENDED</option>
                                <option value="COMPLETED">COMPLETED</option>
                                <option value="CANCELED">CANCELED</option>
                            </select>
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${allOrders}">
                        <tr>
                            <th style="text-align:center;"><input type="checkbox"></th>
                            <td>${order.id }</td>
                            <td><a href="/view/employee/editView/editService.jsp?serviceId=${order.serviceId}">${order.serviceId}</a></td>
                            <td><a href="/view/employee/editView/editSpecification.jsp?specId=${order.specId}">${order.specId}</a></td>
                            <td><a href="/view/employee/editView/editCustomer.jsp?specId=${order.customerId}">${order.customerId}</a></td>
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

