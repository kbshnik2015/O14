<jsp:useBean id="currentUser" scope="session" class="model.dto.EmployeeDTO"/>
<html>
<head>
    <title>Title</title>
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"  %>
    <h1>OPTIONS</h1>
</head>
<body>
<br>
<div class="grid_body">
    <div class="passwordInfo">
        <form action="/employee/EmployeeOptionsServlet" method="post">
            <p>Old password: <label>
                <input required type="password" autocomplete="off" name="oldPassword" value="">
            </label></p>
            <p>New password: <label>
                <input required type="password" autocomplete="off" name="newPassword" value="">
            </label></p>
            <p>Repeat new password: <label>
                <input required type="password" autocomplete="off" name="newPassword2" value="">
            </label></p>
            <button type="submit" name="changePassword" value="click">Change password</button>
        </form>
    </div>
    <div class="info">
        <form action="/employee/EmployeeOptionsServlet" method="post" id="userData">
            <p>
                Name:
                <label>
                    <input type="text" name="name" value="${currentUser.firstName}">
                </label>
            </p>
            <p>
                Lsat name:
                <label>
                    <input type="text" name="lastName" value="${currentUser.lastName}">
                </label>
            </p>
            <p>
                Login:
                <label>
                    <input type="text" name="login" readonly value="${currentUser.login}">
                </label>
            </p>
            <button type="submit" name="save" value="click" >Ok (save)</button>
        </form>
        <a href="/employee/NavigationServlet?allOrders=click">Cancel</a>
    </div>
</div>
</body>
</html>
