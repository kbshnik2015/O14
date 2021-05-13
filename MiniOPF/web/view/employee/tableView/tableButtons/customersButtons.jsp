<html>
<head>
    <link rel="stylesheet" href="/src/main/resources/css/employee/button.css">
    <button class="tableButton" name="create" value="click"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to create a new customer."
            data-trigger="hover"
    ><img src="https://img.icons8.com/color/20/000000/plus--v1.png" >Create new customer</button>
    <button class="tableButton" name="delete" value="click" onclick="confirmDelete()"
            data-toggle="popover"
            data-placement="auto"
            title="Click here to delete an entity selected in the table."
            data-trigger="hover"
    ><img src="https://img.icons8.com/color/20/000000/filled-trash.png">Delete</button>
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
    ><img src="https://img.icons8.com/officel/16/000000/clear-filters.png">Discard sort/filter</button>
    <script>
        function confirmDelete() {
            isDelete = confirm("Are you sure you want delete the customer(s)? Orders and services of the customer(s) will be cascade deleted");
        }
    </script>
</head>
</html>
