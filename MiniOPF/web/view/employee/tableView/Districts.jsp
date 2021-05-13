
<html>
    <head>
        <title>Districts</title>
        <link rel="stylesheet" href="/src/main/resources/css/employee/table.css">
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
        <c:import url="Header.jsp"/>
        <jsp:useBean id="districts" scope="request" type="java.util.List"/>
        <jsp:useBean id="filterParams" scope="request" type="java.util.Map"/>
    </head>
    <body>
    <table class="table table-bordered" border="1" cellspacing="0">
        <form action="/employee/DistrictsTableServlet" method="post">
            <c:import url="tableButtons/districtsButtons.jsp"/>
        <thead>
            <tr bgcolor="#a9a9a9">
                <th ><input type="checkbox" id="all"
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
                    Name
                    <input id="nameAscending" type="radio" name="sort" value="nameAscending">
                    <label for="nameAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="nameDescending" type="radio" name="sort" value="nameDescending">
                    <label for="nameDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="name" value="${filterParams.get("name")}">
                </td>
                <td>
                    parent Id
                    <input id="parentIdAscending" type="radio" name="sort" value="parentIdAscending">
                    <label for="parentIdAscending"><img src="https://img.icons8.com/officexs/16/000000/sort-down.png"/></label>
                    <input id="parentIdDescending" type="radio" name="sort" value="parentIdDescending">
                    <label for="parentIdDescending"><img src="https://img.icons8.com/officexs/16/000000/sort-up.png"/></label>
                    <br>
                    <input type="text" name="parentId" value="${filterParams.get("parentId")}">
                </td>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="district" items="${districts}" >
                <tr>
                    <th><input type="checkbox" name="checks" value="${district.id}"
                               data-toggle="popover"
                               data-placement="auto"
                               title="Click here to highlight this line."
                               data-trigger="hover"
                    ></th>
                    <td><a href="/employee/DistrictsTableServlet?id=${district.id }">${district.id }</a></td>
                    <td>${district.name}</td>
                    <td><a href="/employee/DistrictsTableServlet?id=${district.parentId}">${district.parentId}</a></td>
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
