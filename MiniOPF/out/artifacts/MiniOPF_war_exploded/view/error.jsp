<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 14.05.2021
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().getName();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exception</title>
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
    <%--<c:import url="Header.jsp"/>--%>
    <style type="text/css">
        DIV {
            width: 600px; /* Ширина слоя */
            margin: 7px; /* Значение отступов */
            padding: 10px; /* Поля вокруг текста */
            border: 1px solid #ff000c; /* Параметры границы */
            background: #fffdfe;
        }
        p {
            color: #ff000c;
        }
        h2 {
            color: #ff000c;
        }
    </style>
</head>
<body>
<div align="center">
    <h2 align="center">ERROR</h2>
    <p align="center"><%= message%>
    </p>
</div>
</body>
</html>
