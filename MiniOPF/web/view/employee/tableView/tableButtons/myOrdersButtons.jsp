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
    <link rel="stylesheet" href="../../css/button.css" type="text/css"/>
    <button name="create" value="click" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/plus--v1.png" style="vertical-align: middle"/>Create new order</button>
    <button name="delete" value="click" onclick="confirmDelete()" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/filled-trash.png" style="vertical-align: middle"/>Delete</button>
    <button name="edit" value="click" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/edit--v1.png" style="vertical-align: middle"/>Edit</button>
    <button name="filter" value="click" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/sorting-answers.png" style="vertical-align: middle"/>Filter</button>
    <button name="unassignOrders" value="click" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/add-list.png" style="vertical-align: middle"/>Unassign order(s)</button>

    <script>
        function confirmDelete() {
            isDelete = confirm("Are you sure you want delete the order(s)?");
        }
    </script>
</head>
</html>
