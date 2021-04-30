<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="middle">
    <form action="/employee/CustomersTableServlet" method="get">
        <table>
            <tbody>
            <tr>
                <td>FIRST NAME</td>
                <td><input tabindex="1" placeholder="Enter firstName" type="text" name="firstName"></td>
            </tr>
            <tr>
                <td>LAST NAME</td>
                <td><input tabindex="1" placeholder="Enter lastName" type="text" name="lastName"></td>
            </tr>
            <tr>
                <td>LOGIN</td>
                <td><input tabindex="1" placeholder="Enter login" type="text" name="login" required></td>
            </tr>
            <tr>
                <td>PASSWORD</td>
                <td><input tabindex="1" placeholder="Enter password" type="text" name="password" required></td>
            </tr>
            <tr>
                <td>ADDRESS</td>
                <td><input tabindex="1" placeholder="Enter address" type="text" name="address"></td>
            </tr>
            <tr>
                <td>BALANCE</td>
                <td><input tabindex="1" placeholder="Enter balance" type="text" name="balance"></td>
            </tr>
            </tbody>
        </table>

        <div>
            <button type="submit" name="confirmCreate" value="click">OK</button>
        </div>
    </form>
</div>
</body>
</html>
