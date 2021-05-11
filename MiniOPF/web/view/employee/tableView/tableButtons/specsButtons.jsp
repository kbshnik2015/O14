<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>

<link rel="stylesheet" href="../css/button.css" type="text/css"/>



<button name="create" value="click" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/plus--v1.png" style="vertical-align: middle"/>Create new specification</button>
<button name="delete" value="click" onclick="confirmDelete()" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/filled-trash.png" style="vertical-align: middle">Delete</button>
<button name="filter" value="click" style="background-color: Transparent; background-repeat:no-repeat; border: none; cursor:pointer; overflow: hidden; outline:none;"><img src="https://img.icons8.com/color/20/000000/sorting-answers.png" style="vertical-align: middle">Filter</button>


<script>
    function confirmDelete() {
        isDelete = confirm("Are you sure you want delete the specification(s)? Services and orders with the specification(s) will be cascade deleted");
    }
</script>
</body>
</html>

