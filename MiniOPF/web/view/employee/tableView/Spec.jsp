
<html>
    <head>
        <title>Specifications</title>
        <link rel="stylesheet" href="/src/main/resources/css/employee/table.css">
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="specs" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <form action="/employee/NavigationServlet" method="get" name="navigation" style="margin: 0px">
        <button name="allOrders" value="click" type="submit" >All orders</button>
        <button name="myOrders" value="click" type="submit">My orders</button>
        <button name="services" value="click" type="submit">Services</button>
        <button name="spec" value="click" type="submit" disabled="disabled">Specifications</button>
        <button name="customers" value="click" type="submit">Customers</button>
        <button name="districts" value="click" type="submit">Districts</button>
    </form>

    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/employee/SpecsTableServlet" method="post">
            <c:import url="/view/employee/tableView/tableButtons/specsButtons.jsp"/>
        <thead>

            <tr bgcolor="#a9a9a9">
                <th><input type="checkbox" id="all" ></th>
                <td>
                    id
                    <input id="idDescending" type="radio" name="sort" value="idDescending">
                    <label for="idDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="idAscending" type="radio" name="sort" value="idAscending">
                    <label for="idAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input class="ShortInput" type="text" name="id" value="${filterParams.get("id")}">
                </td>
                <td>
                    Name
                    <input id="nameDescending" type="radio" name="sort" value="nameDescending">
                    <label for="nameDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="nameAscending" type="radio" name="sort" value="nameAscending">
                    <label for="nameAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="name"  value="${filterParams.get("name")}">
                </td>
                <td>
                    Price
                    <input id="priceDescending" type="radio" name="sort" value="priceDescending">
                    <label for="priceDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="priceAscending" type="radio" name="sort" value="priceAscending">
                    <label for="priceAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="price"  value="${filterParams.get("price")}">
                </td>
                <td>
                    Description
                    <input id="descriptionDescending" type="radio" name="sort" value="descriptionDescending">
                    <label for="descriptionDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="descriptionAscending" type="radio" name="sort" value="descriptionAscending">
                    <label for="descriptionAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="description"  value="${filterParams.get("description")}">
                </td>
                <td>
                    Is address depended
                    <input id="isAddressDependedDescending" type="radio" name="sort" value="isAddressDependedDescending">
                    <label for="isAddressDependedDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="isAddressDependedAscending" type="radio" name="sort" value="isAddressDependedAscending">
                    <label for="isAddressDependedAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <select size="1"  name="isAddressDependence">
                        <option disabled>Choose address dependency</option>
                        <option selected value="">-</option>
                        <option value="true">true</option>
                        <option value="false">false</option>
                    </select>
                </td>
                <td>
                    Districts id's
                    <br>
                    <input type="text" name="districtsIds"  value="${filterParams.get("districtsIds")}">
                </td>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="spec" items="${specs}">
                <tr>
                    <th><input type="checkbox" name="checks" value="${spec.id}"></th>
                    <td><a href="/employee/SpecsTableServlet?id=${spec.id }">${spec.id }</a></td>
                    <td>${spec.name}</td>
                    <td>${spec.price}</td>
                    <td>${spec.description}</td>
                    <td>${spec.addressDependence}</td>
                    <td>
                        <c:forEach var="district" items="${spec.districtsIds}">
                            <a href="/employee/DistrictsTableServlet?id=${district}">${district}</a>
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
