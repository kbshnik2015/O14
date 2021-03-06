<html>
<head>
    <title>Title</title>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <jsp:useBean id="districts" scope="request" type="java.util.List"/>
    <jsp:useBean id="district" scope="request" type="model.dto.DistrictDTO"/>
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
><img src="https://img.icons8.com/officexs/18/000000/info.png" alt=""
      style="position: relative;
      bottom: -3px;"
/>About this page</a>
<div align="middle">
    <form action="/employee/DistrictsTableServlet" method="post">
        <input hidden name="id" value="${district.id}">
        <table>
            <tbody>
            <tr>
                <td>NAME</td>
                <td><input tabindex="1" placeholder="${district.name}" type="text" name="name"></td>
            </tr>
            <tr>
                <td>PARENT ID</td>
                <td>
                    <select name="parentId">
                        <option value="">-</option>
                        <c:forEach var="dist" items="${districts}">
                            <c:if test="${dist.id != district.id}">
                                <c:choose>
                                    <c:when test="${dist.id == district.parentId}">
                                        <option selected value="${dist.id}">${dist.id} : ${dist.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${dist.id}">${dist.id} : ${dist.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
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
<c:import url="/view/employee/popUps/editSpecificationPopUp.jsp"/>
</body>
</html>
