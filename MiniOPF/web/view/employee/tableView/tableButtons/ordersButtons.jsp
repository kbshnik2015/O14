<%--
  Created by IntelliJ IDEA.
  User: dvorn
  Date: 12.04.2021
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/src/main/resources/css/employee/button.css">
    <button class="tableButton" name="create" value="click" ><img src="https://img.icons8.com/color/20/000000/plus--v1.png" style="vertical-align: middle"/>Create new order</button>
    <button class="tableButton" name="delete" value="click" onclick="confirmDelete()" ><img src="https://img.icons8.com/color/20/000000/filled-trash.png" style="vertical-align: middle"/>Delete</button>
    <button class="tableButton" name="filter" value="click" ><img src="https://img.icons8.com/color/20/000000/sorting-answers.png" style="vertical-align: middle"/>Sort/Filter</button>
    <button class="tableButton" name="discardFilter" value="click" ><img src="https://img.icons8.com/officel/16/000000/clear-filters.png" style="vertical-align: middle">Discard sort/filter</button>
    <button class="tableButton" name="assignOrders" value="click" ><img src="https://img.icons8.com/color/20/000000/add-list.png" style="vertical-align: middle"/>Assign order(s)</button>

    <script>
        function confirmDelete() {
            isDelete = confirm("Are you sure you want delete the order(s)?");
        }
    </script>
</head>
</html>
