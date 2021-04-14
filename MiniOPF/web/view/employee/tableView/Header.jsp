
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
        <jsp:useBean id="currentEmployee" scope="session" class="model.dto.EmployeeDTO"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
        <p>You are login as: ${currentEmployee.firstName}  ${currentEmployee.lastName}</p>
        <form action="/EmployeeServlet" method="POST" name="waitForWorkForm">
            <c:choose>
                <c:when test="${currentEmployee.waitingForOrders}" >
                    <input type = "checkbox"  name = "waitForWork" value="on" checked onClick="waitForWorkForm.submit()"> Wait for work
                </c:when>
                <c:otherwise>
                    <input type = "checkbox"  name = "waitForWork" value="on" onClick="waitForWorkForm.submit()"> Wait for work
                </c:otherwise>
            </c:choose>
        </form>
    </head>
</html>
