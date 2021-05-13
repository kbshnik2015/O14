
<html>
    <head>
        <title>My orders</title>
        <link rel="stylesheet" href="/src/main/resources/css/employee/table.css">
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="myOrders" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
        <table class="table table-bordered" border="1" cellspacing="0">
            <form action="/employee/MyOrdersTableServlet" method="post">
                <c:import url="tableButtons/myOrdersButtons.jsp"/>
                <thead>
                <tr bgcolor="#a9a9a9">
                    <th><input type="checkbox" id="all"
                               data-toggle="popover"
                               data-placement="auto"
                               title="Click here to check/uncheck all rows of the table."
                               data-trigger="hover"
                    ></th>
                    <td>
                        id
                        <input id="idAscending" type="radio" name="sort" value="idAscending">
                        <label for="idAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="idDescending" type="radio" name="sort" value="idDescending">
                        <label for="idDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                        <br>
                        <input class="ShortInput" type="text" name="id" value=${filterParams.get("id")}>

                    </td>
                    <td>
                        Service id
                        <input id="serviceIdAscending" type="radio" name="sort" value="serviceIdAscending">
                        <label for="serviceIdAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="serviceIdDescending" type="radio" name="sort" value="serviceIdDescending">
                        <label for="serviceIdDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                        <br>
                        <input type="text" name="serviceId" value=${filterParams.get("serviceId")}>
                    </td>
                    <td>
                        Specification id
                        <input id="specIdAscending" type="radio" name="sort" value="specIdAscending">
                        <label for="specIdAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="specIdDescending" type="radio" name="sort" value="specIdDescending">
                        <label for="specIdDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                        <br>
                        <input type="text" name="specId" value=${filterParams.get("specId")}>
                    </td>
                    <td>
                        Customer id
                        <input id="customerIdAscending" type="radio" name="sort" value="customerIdAscending">
                        <label for="customerIdAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="customerIdDescending" type="radio" name="sort" value="customerIdDescending">
                        <label for="customerIdDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                        <br>
                        <input type="text" name="customerId" value=${filterParams.get("customerId")}>
                    </td>
                    <td>
                        Employee id
                        <input id="employeeIdAscending" type="radio" name="sort" value="employeeIdAscending">
                        <label for="employeeIdAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="employeeIdDescending" type="radio" name="sort" value="employeeIdDescending">
                        <label for="employeeIdDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                        <br>
                        <input type="text" name="employeeId" value=${filterParams.get("employeeId")}>
                    </td>
                    <td>
                        Address
                        <input id="addressAscending" type="radio" name="sort" value="addressAscending">
                        <label for="addressAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="addressDescending" type="radio" name="sort" value="addressDescending">
                        <label for="addressDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                        <br>
                        <input type="text" name="address" value=${filterParams.get("address")}>
                    </td>
                    <td>
                        Order aim
                        <input id="aimAscending" type="radio" name="sort" value="aimAscending">
                        <label for="aimAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="aimDescending" type="radio" name="sort" value="aimDescending">
                        <label for="aimDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
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
                        <input id="statusAscending" type="radio" name="sort" value="statusAscending">
                        <label for="statusAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                        <input id="statusDescending" type="radio" name="sort" value="statusDescending">
                        <label for="statusDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
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
                <c:forEach var="order" items="${myOrders}">
                <tr>
                    <th><input type="checkbox" name="checks" value="${order.id}"
                               data-toggle="popover"
                               data-placement="auto"
                               title="Click here to highlight this line."
                               data-trigger="hover"
                    ></th>
                    <td><a href="/employee/MyOrdersTableServlet?id=${order.id }">${order.id }</a></td>
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
