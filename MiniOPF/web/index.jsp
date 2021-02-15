<%@ page import="controller.Controller" %>
<%@ page import="model.entities.Service" %>
<%@ page import="model.entities.Customer" %>
<%@ page import="jdk.nashorn.internal.ir.CatchNode" %>
<%@ page import="model.Model" %><%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 11.02.2021
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>MiniOPF</title>
  </head>
  <body>
  <form method="POST" action="/main">
      <div align="center">
          <p>LOGIN:
              <label>
                  <input type="text" name="login" required>
              </label>
          </p>
          <p>PASSWORD:
              <label>
                  <input type="text" name="password" required>
              </label>
          </p>
          <input type="submit" name="button" value="LOGIN">
      </div>
  </form>
  </body>
</html>
