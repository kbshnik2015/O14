<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:useBean id="customer" scope="request" type="model.dto.CustomerDTO"/>
</head>
<body>
<div align="middle">
    <form action="/employee/CustomersTableServlet" method="post">
        <input hidden name="id" value="${customer.id}">
        <table>
            <tbody>
            <tr>
                <td>FIRST NAME</td>
                <td><input tabindex="1" placeholder="${customer.firstName}" type="text" name="firstName"></td>
            </tr>
            <tr>
                <td>LAST NAME</td>
                <td><input tabindex="1" placeholder="${customer.lastName}" type="text" name="lastName"></td>
            </tr>
            <tr>
                <td>ADDRESS</td>
                <td><input tabindex="1" placeholder="${customer.address}" type="text" name="address"></td>
            </tr>
            <tr>
                <td>BALANCE</td>
                <td><input tabindex="1" placeholder="${customer.balance}" type="text" name="balance"></td>
            </tr>
            </tbody>
        </table>

        <div>
            <button type="submit" name="confirmEdit" value="click">OK</button>
        </div>
    </form>
</div>
</body>
</html>
