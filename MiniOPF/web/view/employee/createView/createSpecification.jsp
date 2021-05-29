<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="districts" scope="request" type="java.util.List"/>
</head>
<body>
<a href="#popup"
   style="position: absolute;
           top: 10px;
           right: 10px;
"
   class="info"
   data-toggle="popover"
   data-placement="auto"
   title="Click here to learn more."
   data-trigger="hover"
>About this page</a>
<div align="middle">
    <form action="/employee/SpecsTableServlet" method="post">
        <table>
            <tbody>
            <tr>
                <td>NAME</td>
                <td><input tabindex="1" placeholder="Enter name" type="text" name="name" required></td>
            </tr>
            <tr>
                <td>PRICE</td>
                <td><input tabindex="1" placeholder="Enter price" type="text" name="price" required></td>
            </tr>
            <tr>
                <td>DESCRIPTION</td>
                <td><input tabindex="1" placeholder="Enter description" type="text" name="description"></td>
            </tr>
            <tr>
                <td>ADDRESS DEPENDED</td>
                <td><input type = "checkbox"  name = "isAddressDepended" value="on"></td>
            </tr>
            <tr>
                <td>DISTRICTS</td>
                <td>
                    <select multiple name="districtId">
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
<c:import url="/view/employee/popUps/createSpecificationPopUp.jsp"/>
</body>
</html>
