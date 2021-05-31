<jsp:useBean id="controller"  class="controller.Controller"/>
<jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="/src/main/resources/css/customer/header.css">
</head>
<body>
    <header class>
        <nav class="header_menu">
                <ul class="header_list">
                    <li class="logo">
                        <form action=""></form>
                        <a href="/CustomerNavigationServlet?ref=myServices"><img class="logo_picture" src="https://img.icons8.com/ios/30/ffffff/internet--v1.png"/><span class="logo_text">MiniOPF</span></a>
                    </li>
                    <li class="my_services">
                        <c:choose>
                            <c:when test="${'/view/customer/Services.jsp' eq pageContext.request.requestURI}">
                                <a href="/CustomerNavigationServlet?ref=myServices" class="header_link"><u>My services</u></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=myServices" class="header_link">My services</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="my_orders">
                        <c:choose>
                            <c:when test="${'/view/customer/MyOrders.jsp' eq pageContext.request.requestURI}">
                                <a href="/CustomerNavigationServlet?ref=myOrders" class="header_link"><u>My orders</u></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=myOrders" class="header_link">My orders</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="specifications">
                        <c:choose>
                            <c:when test="${'/view/customer/Specifications.jsp' eq pageContext.request.requestURI}">
                                <a href="/CustomerNavigationServlet?ref=specs" class="header_link"><u>Specifications</u></a>

                            </c:when>
                            <c:otherwise>
                                <a href="/CustomerNavigationServlet?ref=specs" class="header_link">Specifications</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li class="balance">
                        <span class="header_link"><strong>Balance:</strong> ${controller.transformPriceInCorrectForm(currentUser.balance)} $</span>
                    </li>
                    <li class="login" >
                        <a href="/CustomerNavigationServlet?ref=myProfile" class="header_link"><img class="picture_to_text" alt=""  src="https://img.icons8.com/pastel-glyph/26/ffffff/person-male--v1.png"><span class="text_to_picture">${currentUser.login}</span></a>
                    </li>
                    <li class="log_out">
                        <a href="/LogOutServlet" class="header_link"><img src="https://img.icons8.com/material-outlined/24/ffffff/export.png"/></a>
                    </li>
                </ul>
        </nav>
    </header>
</body>
</html>
