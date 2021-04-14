<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>My orders</title>
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="myOrders" scope="request" type="java.util.List"/>
    </head>
    <body>
        <form action="/NavigationServlet" method="get" name="navigation" style="margin: 0px">
            <button name="allOrders" value="click" type="submit">All orders</button>
            <button name="myOrders" value="click" type="submit" disabled="disabled">My orders</button>
            <button name="services" value="click" type="submit">Services</button>
            <button name="spec" value="click" type="submit">Specifications</button>
            <button name="customers" value="click" type="submit">Customers</button>
            <button name="districts" value="click" type="submit">Districts</button>
        </form>
        <table class="table table-bordered" border="1" cellspacing="0">
            <form action="">
                <c:import url="../tableView/tableButtons/ordersButtons.jsp"/>
            <thead>
                <tr bgcolor="#a9a9a9">
                    <th style="text-align:center;"><input type="checkbox" id="all" ></th>
                    <td>
                        id
                        <br>
                        <input type="text">
                    </td>
                    <td>
                        Service id
                        <br>
                        <input type="text">
                    </td>
                    <td>
                        Specification id
                        <br>
                        <input type="text">
                    </td>
                    <td>
                        Customer id
                        <br>
                        <input type="text">
                    </td>
                    <td>
                        Employee id
                        <br>
                        <input type="text">
                    </td>
                    <td>
                        Address
                        <br>
                        <input type="text" name="address">
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
                <c:forEach var="order" items="${myOrders}">
                    <tr>
                        <th style="text-align:center;"><input type="checkbox"></th>
                        <td>${order.value.id }</td>
                        <td><a href="/view/employee/editView/editService.jsp?serviceId=$${order.value.serviceId}">${order.value.serviceId}</a></td>
                        <td><a href="/view/employee/editView/editSpecification.jsp?specId=${order.value.specId}">${order.value.specId}</a></td>
                        <td><a href="/view/employee/editView/editCustomer.jsp?specId=${order.value.customerId}">${order.value.customerId}</a></td>
                        <td>${order.value.employeeId}</td>
                        <td>${order.value.address}</td>
                        <td>${order.value.orderAim}</td>
                        <td>${order.value.orderStatus}</td>
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
