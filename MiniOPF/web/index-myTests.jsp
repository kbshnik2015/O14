<<<<<<< Updated upstream
=======
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="model.database.ConnectionPool" %>
<%@ page import="model.database.dao.DistrictDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.entities.District" %>
<%@ page import="java.util.List" %>
<%@ page import="model.database.exceptions.DataNotCreatedWarning" %>
<%@ page import="java.math.BigInteger" %>
<%@ page import="model.database.dao.OrderDAO" %>
<%@ page import="model.entities.Order" %>
<%@ page import="controller.Controller" %>
<%@ page import="model.database.dao.CustomerDAO" %>
<%@ page import="model.enums.OrderAim" %>
<%@ page import="model.enums.OrderStatus" %>
<%@ page import="model.database.dao.SpecificationDAO" %>
<%@ page import="model.entities.Specification" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.database.exceptions.DataNotFoundWarning" %>
<%@ page import="model.entities.Customer" %>
<%@ page import="model.ModelDB" %>
<%@ page import="model.entities.Service" %>
<%@ page import="java.util.Date" %>
<%@ page import="model.enums.ServiceStatus" %>
<%@ page import="model.dto.OrderDTO" %>
<%@ page import="model.dto.SpecificationDTO" %>
<%@ page import="model.dto.ServiceDTO" %>
<%@ page import="java.util.Map" %>
<%@ page import="controller.RegexParser" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.sun.org.apache.bcel.internal.generic.BIPUSH" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.dto.CustomerDTO" %>
<%@ page import="model.dto.DistrictDTO" %>
>>>>>>> Stashed changes
<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 11.02.2021
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="ru">
    <title>MiniOPF</title>
<<<<<<< Updated upstream
  </head>
  <body>
  <div align="middle">
      <form action="/login" method="get" name="loginForm">
          <div>
              <input tabindex="1" placeholder="LOGIN" type="text" name="login" required>
          </div>
          <br>
          <div>
              <input tabindex="2" placeholder="PASSWORD" type="password" name="password" required>
          </div>
          <br>
          <div>
              <button type="submit">Sing in</button>
          </div>
      </form>
      <button type="submit" formaction="">Register Customer</button>
      <button type="submit" formaction="">Register Employee</button>
  </div>
  </body>
=======
    <!-- Кодировка веб-страницы -->
    <meta charset="utf-8">
    <!-- Настройка viewport -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha512-Dop/vW3iOtayerlYAqCgkVr2aTr2ErwwTYOvRFUpzl2VhCMJyjQF0Q9TjUXIo6JhuM/3i0vVEt2e/7QQmnHQqw==" crossorigin="anonymous">
    <!-- Дополнительные стили (не обязательно) -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha512-iy8EXLW01a00b26BaqJWaCmk9fJ4PsMdgNRqV96KwMPSH+blO82OHzisF/zQbRIIi8m0PiO10dpS0QxrcXsisw==" crossorigin="anonymous">

    <!-- jQuery (необходим для Bootstrap JS) -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" integrity="sha512-bLT0Qm9VnAYZDflyKcBaQ2gg0hSYNQrJ8RilYldYQ1FxQYoCLtUjuuRuZo+fjqhx/qtq/1itJ0C2ejDxltZVFg==" crossorigin="anonymous"></script>
    <!-- Bootstrap JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha512-oBTprMeNEKCnqfuqKd6sbvFzmFQtlXS3e0C/RGFV0hD6QzhHV+ODfaQbAlmY6/q0ubbwlAM/nCJjkrgA3waLzg==" crossorigin="anonymous"></script>
</head>
<body>
<%
Connection connection = null;
try
{
connection = ConnectionPool.getConnection();
}
catch (SQLException e)
{
e.printStackTrace();
}
SpecificationDAO specificationDAO = new SpecificationDAO(connection);
CustomerDAO customerDAO = new CustomerDAO(connection);
    ModelDB modelDB = new ModelDB();
%>

<%=modelDB.getServices()%>

<button type="button" class="btn btn-primary" data-toggle="popover" title="Сообщение" data-content="Ура, Bootstrap 4 работает">Поднеси ко мне курсор</button>

<!-- После подключения jQuery, Popper и Bootstrap JS -->
<script>
    $(function () {
        $('[data-toggle="popover"]').popover({trigger:'hover'});
    });
</script>


<div class="container">
    <p>1. Активирование модального окна с помощью атрибутов data</p>
    <p><a href="#myModal1" class="btn btn-primary" data-toggle="modal">Открыть модальное окно 1</a></p>
    <div id="myModal1" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">Заголовок модального окна 1</h4>
                </div>
                <div class="modal-body">
                    Содержимое модального окна 1...
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
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary">Сохранить изменения</button>
                    <p><a href="#myModal2" class="btn btn-primary" data-toggle="modal">Открыть модальное окно 2</a></p>
                </div>
            </div>
        </div>
    </div>

    <div id="myModal2" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">Заголовок модального окна 1</h4>
                </div>
                <div class="modal-body">
                    Содержимое модального окна 2...
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
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    <button type="button" class="btn btn-primary">Сохранить изменения</button>
                </div>
            </div>
        </div>
    </div>
</div>

<table border="1" cellpadding="20">
<caption><h2>SERVICES</h2></caption>
<tr>
    <th>ID</th>
    <th>isAddress</th>
    <th>Districts</th>
</tr>
<%
    Map<BigInteger, ServiceDTO> services = modelDB.getServices();
    Map<String, String> regexes = new HashMap<>();
    regexes.put("payDay", "*-*-*");
    Collection<ServiceDTO> filtered = RegexParser.filterServices(services, regexes).values();
    for (ServiceDTO serviceDTO : filtered) {
%>
<tr>
    <td><%=serviceDTO.getId()%></td>
    <td><%=serviceDTO.getPayDay()%></td>
</tr>
<%
}%>
</table>

<table border="1" cellpadding="20">
    <caption><h2>DISTRICTS</h2></caption>
    <tr>
        <th>ID</th>
        <th>CustomerID</th>
        <th>EmployeeID</th>
        <th>SpecID</th>
        <th>ServiceID</th>
        <th>AIM</th>
        <th>STATUS</th>
        <th>ADDRESS</th>
    </tr>
    <%
        Map<String, String> custRegexes = new HashMap<>();
        custRegexes.put("id", null);
        custRegexes.put("name", null);
        custRegexes.put("parentId", "-");
        Map<BigInteger, DistrictDTO> districts = modelDB.getDistricts();
        for (DistrictDTO districtDTO : RegexParser.filterDistricts(districts, custRegexes).values()) {
    %>
    <tr>
        <td><%=districtDTO.getId()%></td>
        <td><%=districtDTO.getName()%></td>
        <td><%=districtDTO.getParentId()%></td>
    </tr>
    <%}%>
</table>

<table border="1" cellpadding="20">
    <caption><h2>SPECIFICATION</h2></caption>
    <tr>
        <th>ID</th>
        <th>CustomerID</th>
        <th>EmployeeID</th>
        <th>SpecID</th>
        <th>ServiceID</th>
        <th>AIM</th>
        <th>STATUS</th>
        <th>ADDRESS</th>
    </tr>
    <%
        Map<String, String> specRegexes = new HashMap<>();
        specRegexes.put("id", null);
        specRegexes.put("name", "Inte*");
        specRegexes.put("description", null);
        Map<BigInteger, SpecificationDTO> specifications = modelDB.getSpecifications();
        for (SpecificationDTO specificationDTO : RegexParser.filterSpecifications(specifications, specRegexes).values()) {
    %>
    <tr>
        <td><%=specificationDTO.getId()%></td>
        <td><%=specificationDTO.getName()%></td>
        <td><%=specificationDTO.getDescription()%></td>
    </tr>
    <%}%>
</table>

<table border="1" cellpadding="20">
    <caption><h2>ORDERS AFTER</h2></caption>
    <tr>
        <th>ID</th>
        <th>CustomerID</th>
        <th>EmployeeID</th>
        <th>SpecID</th>
        <th>ServiceID</th>
        <th>AIM</th>
        <th>STATUS</th>
        <th>ADDRESS</th>
    </tr>
    <%
//        OrderDTO order = modelDB.getOrder(BigInteger.valueOf(22));
//        order.setEmployeeId(BigInteger.valueOf(4));
//        order.setOrderAim(OrderAim.SUSPEND);
//        order.setOrderStatus(OrderStatus.SUSPENDED);
//        modelDB.updateOrder(order);
//        modelDB.deleteOrder(BigInteger.valueOf(23));
//        modelDB.createOrder(new OrderDTO(BigInteger.valueOf(1), BigInteger.valueOf(4), BigInteger.valueOf(14),
//                BigInteger.valueOf(17), OrderAim.NEW, OrderStatus.ENTERING));
        Map<BigInteger, OrderDTO> orders = modelDB.getOrders();
        Map<String, String> regexesOrder = new HashMap<>();
        regexesOrder.put("orderAim", "NEW");
        regexesOrder.put("orderStatus", "ENTERING");
        regexesOrder.put("address", "-");
        Collection<OrderDTO> filteredOrders = RegexParser.filterOrders(orders, regexesOrder).values();
        for (OrderDTO orderDTO : filteredOrders) {
    %>
    <tr>
        <td><%=orderDTO.getId()%></td>
        <td><%=orderDTO.getCustomerId()%></td>
        <td><%=orderDTO.getEmployeeId()%></td>
        <td><%=orderDTO.getSpecId()%></td>
        <td><%=orderDTO.getServiceId()%></td>
        <td><%=orderDTO.getOrderAim()%></td>
        <td><%=orderDTO.getOrderStatus()%></td>
        <td><%=orderDTO.getAddress()%></td>
    </tr>
    <%
    }%>
</table>
<p><%=modelDB.getSpecifications()%></p>


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

<script>
    $(function () {

        // функция с параметрами idModal1 (1 модальное окно) и idModal2 (2 модальное окно)
        var twoModal = function (idModal1, idModal2) {
            var showModal2 = false;
            // при нажатии на ссылку в idModal2 устанавливаем переменной showModal2 значение равное true и закрываем idModal1
            $('[href="' + idModal2 + '"]').click(function (e) {
                e.preventDefault();
                showModal2 = true;
                $(idModal1).modal('hide');
            });
            // после закрытия idModal1, если значение showModal2 равно true, то открываем idModal2
            $(idModal1).on('hidden.bs.modal', function (e) {
                if (showModal2) {
                    showModal2 = false;
                    $(idModal2).modal('show');
                }
            });
            // при закрытии idModal2 открываем idModal1
            $(idModal2).on('hidden.bs.modal', function (e) {
                $(idModal1).modal('show');
            });
        };

        twoModal('#myModal1', '#myModal2');

    });
</script>

</body>

</html>


</body>
>>>>>>> Stashed changes
</html>
