<html>
<head>
    <link rel="stylesheet" href="/src/main/resources/css/employee/button.css">
    <button class="tableButton" name="create" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to create a new order for you."
            data-trigger="hover"
    ><img src="https://img.icons8.com/color/20/000000/plus--v1.png" >Create new order</button>
    <button class="tableButton" name="delete" value="click" onclick="confirmDelete()"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to delete an entity selected in the table."
            data-trigger="hover"
    ><img src="https://img.icons8.com/color/20/000000/filled-trash.png" >Delete</button>
    <button class="tableButton" name="filter" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to filter the page by the values specified in the fields. You can use regular expressions in these fields. You can also use one of the sort buttons."
            data-trigger="hover"
    ><img src="https://img.icons8.com/color/20/000000/sorting-answers.png" >Sort/Filter</button>
    <button class="tableButton" name="discardFilter" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to reset all filters."
            data-trigger="hover"
    ><img src="https://img.icons8.com/officel/16/000000/clear-filters.png" >Discard sort/filter</button>
    <button class="tableButton" name="unassignOrders" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to stop being the executor of this order."
            data-trigger="hover"
    ><img src="https://img.icons8.com/color/20/000000/add-list.png" >Unassign order(s)</button>
    <button class="tableButton" name="startOrder" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to start fulfilling your order."
            data-trigger="hover"
    ><img src="https://img.icons8.com/officexs/16/000000/start.png" >Start</button>
    <button class="tableButton" name="completeOrder" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here if the order is completed."
            data-trigger="hover"
    ><img src="https://img.icons8.com/officexs/16/000000/checkmark.png" >Complete</button>
    <button class="tableButton" name="cancelOrder" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to cancel."
            data-trigger="hover"
    ><img src="https://img.icons8.com/officexs/16/000000/cancel.png" >Cancel</button>
    <button class="tableButton" name="suspendOrder" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to pause this order."
            data-trigger="hover"
    ><img src="https://img.icons8.com/officexs/16/000000/circled-pause.png" >Suspend</button>
    <button class="tableButton" name="restoreOrder" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to restore a canceled order."
            data-trigger="hover"
    ><img src="https://img.icons8.com/small/16/000000/settings-backup-restore.png" >Restore</button>

    <script>
        function confirmDelete() {
            isDelete = confirm("Are you sure you want delete the order(s)?");
        }
    </script>
</head>
</html>
