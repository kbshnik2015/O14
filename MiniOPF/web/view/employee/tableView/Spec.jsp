<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Specifications</title>
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="specs" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <form action="/NavigationServlet" method="get" name="navigation" style="margin: 0px">
        <button name="allOrders" value="click" type="submit" >All orders</button>
        <button name="myOrders" value="click" type="submit">My orders</button>
        <button name="services" value="click" type="submit">Services</button>
        <button name="spec" value="click" type="submit" disabled="disabled">Specifications</button>
        <button name="customers" value="click" type="submit">Customers</button>
        <button name="districts" value="click" type="submit">Districts</button>
    </form>
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="">
            <c:import url="../tableView/tableButtons/specsButtons.jsp"/>
        <thead>
            <tr bgcolor="#a9a9a9">
                <th style="text-align:center;"><input type="checkbox" id="all" ></th>
                <td>
                    id
                    <br>
                    <input type="text" >
                </td>
                <td>
                    Name
                    <br>
                    <input type="text">
                </td>
                <td>
                    Price
                    <br>
                    <input type="text">
                </td>
                <td>
                    Description
                    <br>
                    <input type="text">
                </td>
                <td>
                    Is address depended
                    <br>
                    <input type="text">
                </td>
                <td>
                    Districts id's
                    <br>
                    <input type="text">
                </td>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="spec" items="${specs}">
                <tr>
                    <th style="text-align:center;"><input type="checkbox"></th>
                    <td>${spec.value.id }</td>
                    <td>${spec.value.name}</td>
                    <td>${spec.value.price}</td>
                    <td>${spec.value.description}</td>
                    <td>${spec.value.addressDependence}</td>
                    <td>
                        <c:forEach var="district" items="${spec.value.districtsIds}">
                            <a href="/view/employee/editView/editDistrict.jsp?districtId=${district}">${district}</a>
                            <br>
                        </c:forEach>
                    </td>
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
