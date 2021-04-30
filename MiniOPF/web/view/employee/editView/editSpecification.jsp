<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="districts" scope="request" type="java.util.List"/>
    <jsp:useBean id="spec" scope="request" type="model.dto.SpecificationDTO"/>
</head>
<body>
<div align="middle">
    <form action="/employee/SpecsTableServlet" method="post">
        <input hidden name="id" value="${spec.id}">
        <table>
            <tbody>
            <tr>
                <td>NAME</td>
                <td><input tabindex="1" placeholder="${spec.name}" type="text" name="name"></td>
            </tr>
            <tr>
                <td>PRICE</td>
                <td><input tabindex="1" placeholder="${spec.price}" type="text" name="price"></td>
            </tr>
            <tr>
                <td>DESCRIPTION</td>
                <td><input tabindex="1" placeholder="${spec.description}" type="text" name="description"></td>
            </tr>
            <tr>
                <td>ADDRESS DEPENDED</td>
                <td>
                    <c:choose>
                        <c:when test="${spec.addressDependence}" >
                            <input type = "checkbox"  name = "isAddressDepended" value="on" checked>
                        </c:when>
                        <c:otherwise>
                            <input type = "checkbox"  name = "isAddressDepended" value="on">
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <td>DISTRICTS</td>
                <td>
                    <select multiple name="districtId">
                        <c:forEach var="dist" items="${districts}" >
                            <c:choose>
                                <c:when test="${spec.districtsIds.contains(dist.id)}">
                                    <option selected value="${dist.id}">${dist.id} : ${dist.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${dist.id}">${dist.id} : ${dist.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
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
