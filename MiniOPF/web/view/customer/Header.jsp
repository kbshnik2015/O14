<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/src/main/resources/css/customer/header.css">
</head>
<body>
    <header class>
        <nav class="header_menu">
                <ul class="header_list">
                    <li>
                        <c:choose>
                            <c:when test="${'http://localhost:8080/view/customer/Mine.jsp' eq pageContext.request.requestURL}">
                                <a href="/CustomerNavigationServlet?ref=main" class="header_link" ><u>My page</u></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=main" class="header_link" >My page</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${'http://localhost:8080/view/customer/Services.jsp' eq pageContext.request.requestURL}">
                                <a href="/CustomerNavigationServlet?ref=myServices" class="header_link"><u>My services</u></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=myServices" class="header_link">My services</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${'http://localhost:8080/view/customer/MyOrders.jsp' eq pageContext.request.requestURL}">
                                <a href="/CustomerNavigationServlet?ref=myOrders" class="header_link"><u>My orders</u></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=myOrders" class="header_link">My orders</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${'http://localhost:8080/view/customer/Specifications.jsp' eq pageContext.request.requestURL}">
                                <a href="/CustomerNavigationServlet?ref=specs" class="header_link"><u>Specifications</u></a>

                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=specs" class="header_link">Specifications</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${'http://localhost:8080/view/customer/Options.jsp' eq pageContext.request.requestURL}">
                                <a href="/CustomerNavigationServlet?ref=options" class="header_link"><u>Options</u></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=options" class="header_link">Options</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <a href="/LogOutServlet"><img src="https://img.icons8.com/metro/26/000000/exit.png" alt="Выход"/></a>

                    </li>
                </ul>
        </nav>
    </header>
</body>
</html>
