<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Services</title>
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="services" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <form action="/NavigationServlet" method="get" name="navigation" style="margin: 0px">
        <button name="allOrders" value="click" type="submit" >All orders</button>
        <button name="myOrders" value="click" type="submit">My orders</button>
        <button name="services" value="click" type="submit" disabled="disabled">Services</button>
        <button name="spec" value="click" type="submit">Specifications</button>
        <button name="customers" value="click" type="submit">Customers</button>
        <button name="districts" value="click" type="submit">Districts</button>
    </form>
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/ServicesTableServlet" method="get">
            <c:import url="../tableView/tableButtons/servicesButtons.jsp"/>
        <thead>
            <tr bgcolor="#a9a9a9">
                <th style="text-align:center;"><input type="checkbox" id="all" ></th>
                <td>
                    id
                    <br>
                    <input type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    Pay day
                    <br>
                    <input type="text" name="payDay" value="${filterParams.get("payDay")}">
                </td>
                <td>
                    Specification id
                    <br>
                    <input type="text"name="specificationId" value="${filterParams.get("specificationId")}">
                </td>
                <td>
                    Service status
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
                    <br>
                    <input type="text" name="customerId" value="${filterParams.get("customerId")}">
                </td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="service" items="${services}">
                <tr>
                    <th style="text-align:center;"><input type="checkbox"></th>
                    <td>${service.id }</td>
                    <td>${service.payDay}</td>
                    <td><a href="/view/employee/editSpecification.jsp&specIf=${service.specificationId}">${service.specificationId}</a></td>
                    <td>${service.serviceStatus}</td>
                    <td>${service.customerId}</td>
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
