<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Districts</title>
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="districts" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <form action="/NavigationServlet" method="get" name="navigation" style="margin: 0px">
        <button name="allOrders" value="click" type="submit" >All orders</button>
        <button name="myOrders" value="click" type="submit">My orders</button>
        <button name="services" value="click" type="submit">Services</button>
        <button name="spec" value="click" type="submit">Specifications</button>
        <button name="customers" value="click" type="submit">Customers</button>
        <button name="districts" value="click" type="submit" disabled="disabled">Districts</button>
    </form>
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/DistrictsTableServlet" method="get">
            <c:import url="../tableView/tableButtons/districtsButtons.jsp"/>
        <thead>
            <tr bgcolor="#a9a9a9">
                <th style="text-align:center;"><input type="checkbox" id="all" ></th>
                <td>
                    id<br>
                    <input type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    Name
                    <br>
                    <input type="text" name="name" value="${filterParams.get("name")}">
                </td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="district" items="${districts}" >
                <tr>
                    <th style="text-align:center;"><input type="checkbox"></th>
                    <td>${district.id }</td>
                    <td>${district.name}</td>
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
