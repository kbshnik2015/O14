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
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/employee/ServicesTableServlet" method="post">
            <c:import url="tableButtons/servicesButtons.jsp"/>
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
                    <input class="ShortInput" type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    Pay day
                    <input id="payDayAscending" type="radio" name="sort" value="payDayAscending">
                    <label for="payDayAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="payDayDescending" type="radio" name="sort" value="payDayDescending">
                    <label for="payDayDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="payDay" value="${filterParams.get("payDay")}">
                </td>
                <td>
                    Specification id
                    <input id="specIdAscending" type="radio" name="sort" value="specIdAscending">
                    <label for="specIdAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="specIdDescending" type="radio" name="sort" value="specIdDescending">
                    <label for="specIdDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                     <br>
                    <input type="text"name="specificationId" value="${filterParams.get("specificationId")}">
                </td>
                <td>
                    Service status
                    <input id="serviceStatusAscending" type="radio" name="sort" value="serviceStatusAscending">
                    <label for="serviceStatusAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="serviceStatusDescending" type="radio" name="sort" value="serviceStatusDescending">
                    <label for="serviceStatusDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
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
                    <input id="customerIdAscending" type="radio" name="sort" value="customerIdAscending">
                    <label for="customerIdAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="customerIdDescending" type="radio" name="sort" value="customerIdDescending">
                    <label for="customerIdDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="customerId" value="${filterParams.get("customerId")}">
                </td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="service" items="${services}">
                <tr>
                    <th><input type="checkbox" name="checks" value="${service.id}"
                               data-toggle="popover"
                               data-placement="auto"
                               title="Click here to highlight this line."
                               data-trigger="hover"
                    ></th>
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
