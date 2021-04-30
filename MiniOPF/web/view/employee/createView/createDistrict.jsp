<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="districts" scope="request" type="java.util.List"/>
</head>
<body>
<div align="middle">
    <form action="/employee/DistrictsTableServlet" method="get">
        <table>
            <tbody>
            <tr>
                <td>NAME</td>
                <td><input tabindex="1" placeholder="Enter name" type="text" name="name" required></td>
            </tr>
            <tr>
                <td>PARENT ID</td>
                <td>
                    <select name="parentId">
                        <option selected value="">-</option>
                        <c:forEach var="district" items="${districts}" >
                            <option value="${district.id}">${district.id} : ${district.name}</option>
                        </c:forEach>
                    </select>
                </td>
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
