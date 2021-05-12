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
    <button class="tableButton" name="delete" value="click" onclick="confirmDelete()" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/filled-trash.png" style="vertical-align: middle"/>Delete</button>
    <button class="tableButton" name="filter" value="click" ><img src="https://img.icons8.com/color/20/000000/sorting-answers.png" style="vertical-align: middle"/>Sort/Filter</button>
    <button class="tableButton" name="discardFilter" value="click" ><img src="https://img.icons8.com/officel/16/000000/clear-filters.png" style="vertical-align: middle">Discard sort/filter</button>
    <button class="tableButton" name="unassignOrders" value="click" ><img src="https://img.icons8.com/color/20/000000/add-list.png" style="vertical-align: middle"/>Unassign order(s)</button>
    <button class="tableButton" name="startOrder" value="click" ><img src="https://img.icons8.com/officexs/16/000000/start.png" style="vertical-align: middle"/>Start</button>
    <button class="tableButton" name="completeOrder" value="click" ><img src="https://img.icons8.com/officexs/16/000000/checkmark.png" style="vertical-align: middle"/>Complete</button>
    <button class="tableButton" name="cancelOrder" value="click" ><img src="https://img.icons8.com/officexs/16/000000/cancel.png" style="vertical-align: middle"/>Cancel</button>
    <button class="tableButton" name="suspendOrder" value="click" ><img src="https://img.icons8.com/officexs/16/000000/circled-pause.png" style="vertical-align: middle"/>Suspend</button>
    <button class="tableButton" name="restoreOrder" value="click" ><img src="https://img.icons8.com/small/16/000000/settings-backup-restore.png" style="vertical-align: middle"/>Restore</button>

    <script>
        function confirmDelete() {
            isDelete = confirm("Are you sure you want delete the order(s)?");
        }
    </script>
</head>
</html>
