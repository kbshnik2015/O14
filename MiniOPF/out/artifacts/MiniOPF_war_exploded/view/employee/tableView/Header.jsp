<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="currentUser" scope="session" class="model.dto.EmployeeDTO"/>
<link rel="stylesheet" href="/src/main/resources/css/employee/header.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<html>
    <head>
        <script>
            (function ($) {
                $.fn.checkboxTable = function () {
                    target = this;
                    $(target).on('click', 'thead :checkbox', function () {
                        var check = this;
                        $(this).parents('table').find('tbody :checkbox').each(function () {
                            if ($(check).is(':checked')) {
                                $(this).prop('checked', true);
                                $(this).parents('tr').addClass('selected');
                            } else {
                                $(this).prop('checked', false);
                                $(this).parents('tr').removeClass('selected');
                            }
                        });
                    });
                    $(target).on('click', 'tbody :checkbox', function () {
                        var parents = $(this).parents('table');
                        if ($(this).is(':checked')) {
                            $(this).parents('tr').addClass('selected');
                            $(parents).find('thead :checkbox').prop('checked', true);
                        } else {
                            $(this).parents('tr').removeClass('selected');
                            if ($(parents).find('tbody :checkbox:checked').length == 0) {
                                $(parents).find('thead :checkbox').prop('checked', false);
                            }
                        }
                    });
                    $(target).on('click', 'tbody tr', function (event) {
                        if (event.target.tagName == 'TH' || event.target.tagName == 'TD') {
                            $(this).find(':checkbox').trigger('click');
                        }
                    });
                };
            })(jQuery);
        </script>
        <script>
            $(function(){
                $(".myPopover").popover();
            });
        </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <p>
            You are login as: <a href="${pageContext.request.contextPath}/view/employee/Options.jsp">${currentUser.firstName}  ${currentUser.lastName}</a>
            <a class="LogOut" href="/LogOutServlet"><img src="https://img.icons8.com/metro/26/000000/exit.png" alt="log out"/></a>
        </p>
        <form action="/employee/EmployeeServlet" method="post" name="waitForWorkForm">
            <c:choose>
                <c:when test="${currentUser.waitingForOrders}" >
                    <input type = "checkbox"  name = "waitForWork" value="off" checked onClick="waitForWorkForm.submit()"
                           data-toggle="popover"
                           data-placement="auto"
                           title="When this function is activated, a free order will appear - it will be transferred to you for processing"
                           data-trigger="hover"
                           > Wait for work
                </c:when>
                <c:otherwise>
                    <input type = "checkbox"  name = "waitForWork" value="on" onClick="waitForWorkForm.submit()"
                           data-toggle="popover"
                           data-placement="auto"
                           title="When this function is activated, a free order will appear - it will be transferred to you for processing"
                           data-trigger="hover"
                    > Wait for work
                </c:otherwise>
            </c:choose>
            <a href="#popup" class="info"
               data-toggle="popover"
               data-placement="auto"
               title="Click here to learn more."
               data-trigger="hover"
            >About this page</a>
            <br>
        </form>
        <br>
            <form action="/employee/NavigationServlet" method="get" name="navigation">
                <c:choose>
                    <c:when test="${'/view/employee/tableView/Mine.jsp' eq pageContext.request.requestURI}">
                        <button  name="allOrders" value="click" type="submit" disabled
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the table of managing all orders."
                                data-trigger="hover"
                        >All orders</button>
                        <button name="myOrders" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here to get your orders management table"
                                data-trigger="hover"
                        >My orders</button>
                        <button name="services" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the services management table."
                                data-trigger="hover"
                        >Services</button>
                        <button name="spec" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the specification management table"
                                data-trigger="hover"
                        >Specifications</button>
                        <button name="customers" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the customers management table"
                                data-trigger="hover">Customers</button>
                        <button name="districts" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the districts management table"
                                data-trigger="hover"
                        >Districts</button>
                    </c:when>
                    <c:when test="${'/view/employee/tableView/MyOrders.jsp' eq pageContext.request.requestURI}">
                        <button  name="allOrders" value="click" type="submit"
                                 data-toggle="popover"
                                 data-placement="auto"
                                 title="Click here for the table of managing all orders."
                                 data-trigger="hover"
                        >All orders</button>
                        <button name="myOrders" value="click" type="submit" disabled
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here to get your orders management table"
                                data-trigger="hover"
                        >My orders</button>
                        <button name="services" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the services management table."
                                data-trigger="hover"
                        >Services</button>
                        <button name="spec" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the specification management table"
                                data-trigger="hover"
                        >Specifications</button>
                        <button name="customers" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the customers management table"
                                data-trigger="hover">Customers</button>
                        <button name="districts" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the districts management table"
                                data-trigger="hover"
                        >Districts</button>
                    </c:when>
                    <c:when test="${'/view/employee/tableView/Customers.jsp' eq pageContext.request.requestURI}">
                        <button  name="allOrders" value="click" type="submit"
                                 data-toggle="popover"
                                 data-placement="auto"
                                 title="Click here for the table of managing all orders."
                                 data-trigger="hover"
                        >All orders</button>
                        <button name="myOrders" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here to get your orders management table"
                                data-trigger="hover"
                        >My orders</button>
                        <button name="services" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the services management table."
                                data-trigger="hover"
                        >Services</button>
                        <button name="spec" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the specification management table"
                                data-trigger="hover"
                        >Specifications</button>
                        <button name="customers" value="click" type="submit" disabled
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the customers management table"
                                data-trigger="hover">Customers</button>
                        <button name="districts" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the districts management table"
                                data-trigger="hover"
                        >Districts</button></c:when>
                    <c:when test="${'/view/employee/tableView/Districts.jsp' eq pageContext.request.requestURI}">
                        <button  name="allOrders" value="click" type="submit"
                                 data-toggle="popover"
                                 data-placement="auto"
                                 title="Click here for the table of managing all orders."
                                 data-trigger="hover"
                        >All orders</button>
                        <button name="myOrders" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here to get your orders management table"
                                data-trigger="hover"
                        >My orders</button>
                        <button name="services" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the services management table."
                                data-trigger="hover"
                        >Services</button>
                        <button name="spec" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the specification management table"
                                data-trigger="hover"
                        >Specifications</button>
                        <button name="customers" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the customers management table"
                                data-trigger="hover">Customers</button>
                        <button name="districts" value="click" type="submit" disabled
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the districts management table"
                                data-trigger="hover"
                        >Districts</button></c:when>
                    <c:when test="${'/view/employee/tableView/Spec.jsp' eq pageContext.request.requestURI}">
                        <button  name="allOrders" value="click" type="submit"
                                 data-toggle="popover"
                                 data-placement="auto"
                                 title="Click here for the table of managing all orders."
                                 data-trigger="hover"
                        >All orders</button>
                        <button name="myOrders" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here to get your orders management table"
                                data-trigger="hover"
                        >My orders</button>
                        <button name="services" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the services management table."
                                data-trigger="hover"
                        >Services</button>
                        <button name="spec" value="click" type="submit" disabled
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the specification management table"
                                data-trigger="hover"
                        >Specifications</button>
                        <button name="customers" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the customers management table"
                                data-trigger="hover">Customers</button>
                        <button name="districts" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the districts management table"
                                data-trigger="hover"
                        >Districts</button></c:when>
                    <c:when test="${'/view/employee/tableView/Services.jsp' eq pageContext.request.requestURI}">
                        <button  name="allOrders" value="click" type="submit"
                                 data-toggle="popover"
                                 data-placement="auto"
                                 title="Click here for the table of managing all orders."
                                 data-trigger="hover"
                        >All orders</button>
                        <button name="myOrders" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here to get your orders management table"
                                data-trigger="hover"
                        >My orders</button>
                        <button name="services" value="click" type="submit" disabled
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the services management table."
                                data-trigger="hover"
                        >Services</button>
                        <button name="spec" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the specification management table"
                                data-trigger="hover"
                        >Specifications</button>
                        <button name="customers" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the customers management table"
                                data-trigger="hover">Customers</button>
                        <button name="districts" value="click" type="submit"
                                data-toggle="popover"
                                data-placement="auto"
                                title="Click here for the districts management table"
                                data-trigger="hover"
                        >Districts</button></c:when>
                </c:choose>
            </form>
        <br>
    </head>
<body>
<c:import url="/view/employee/popUps/tablePopUp.jsp"/>
</body>
</html>
