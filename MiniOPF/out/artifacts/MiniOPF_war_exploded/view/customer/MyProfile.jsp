<jsp:useBean id="nextPayDay" scope="request" class="java.lang.String"/>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
<link rel="stylesheet" href="/src/main/resources/css/customer/myProfile.css">
<html>
<head>
    <title>Mine</title>
</head>
<body>
    <c:import url="Header.jsp"/>
    <p><a href="#popup"
          style="position: absolute;
           top: 9%;
           right: 10px;"
          data-toggle="popover"
          data-placement="auto"
          title="Click here to learn more."
          data-trigger="hover"
    ><img src="https://img.icons8.com/officexs/18/000000/info.png" alt=""
          style="position: relative;
      bottom: 2px;"
    /> About this page</a></p>
    <br>
    <main class="main">
        <p><img src="https://img.icons8.com/pastel-glyph/40/4a90e2/person-male--v1.png"/><span class="username"> ${currentUser.firstName} ${currentUser.lastName}</span></p>
        <br>
        <a href="/view/customer/EditProfile.jsp" class="edit_profile"><img src="https://img.icons8.com/color/40/000000/settings--v1.png"/></a>
        <p><strong>Address: </strong>${currentUser.address}</p>
        <p><strong>Balance: </strong>${currentUser.balance}</p>
        <p><strong>The next day of debiting money: </strong>${nextPayDay}</p>
        <br>
        <form action="/CustomerTopUpServlet" method="post">
            <p>Enter the top-up amount: <input type="number" min="0" name="topUp">
            <button type="submit" name="topUpButton" value="click">TOP UP</button></p>
        </form>
    </main>
    <c:import url="/view/employee/popUps/customerPopUps/MyProfilePopUp.jsp"/>
</body>
</html>
