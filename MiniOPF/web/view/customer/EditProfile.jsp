<html>
<head>
    <title>My orders</title>
    <link rel="stylesheet" href="/src/main/resources/css/customer/options.css">
    <%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core"%>
    <jsp:useBean id="model" scope="page" class="model.ModelDB"/>
    <jsp:useBean id="currentUser" scope="session" class="model.dto.CustomerDTO"/>
    <c:set scope="page" value="${model.districts.values()}" var="districts"/>
</head>
<body>
<c:import url="Header.jsp"/>
<br>
<p class="about_this_page"><a href="#popup"
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
            <div class="grid_body">
                <div class="passwordInfo">
                    <form action="/CustomerOptionsServlet" method="post">
                        <p>Old password: <input required type="password" autocomplete="off" name="oldPassword" value=""></p>
                        <br>
                        <p>New password: <input required type="password" autocomplete="off" name="newPassword" value=""></p>
                        <br>
                        <p>Repeat new password: <input required type="password" autocomplete="off" name="newPassword2" value=""></p>
                        <br>
                        <br>
                        <button type="submit" name="changePassword" value="click">Change password</button>
                    </form>
                </div>
                <div class="info">
                    <form action="/CustomerOptionsServlet" method="post" id="userData">
                        <p>Name: <input type="text" name="name" value="${currentUser.firstName}"></p>
                        <br>
                        <p>Lsat name: <input type="text" name="lastName" value="${currentUser.lastName}"></p>
                        <br>
                        <p>Login: <input type="text" name="login" readonly value="${currentUser.login}"></p>
                        <br>
                        <p>District:
                            <c:choose>
                                <c:when test="${currentUser.districtId == null }">
                                    <select name="districtId">
                                        <option selected value="">-</option>
                                        <c:forEach var="district" items="${districts}" >
                                            <option value="${district.id}">${district.name}</option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    <select name="districtId">
                                        <option selected value="${currentUser.districtId}">${model.getDistrict(currentUser.districtId).name}</option>
                                        <option value="">-</option>
                                        <c:forEach var="district" items="${districts}" >
                                            <c:if test="${currentUser.districtId != district.id}">
                                                <option value="${district.id}">${district.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>

                        </p>
                        <br>
                        <p>Address: <input type="text" name="address"  value="${currentUser.address}"></p>
                        <br>
                        <button type="submit" name="save" value="click" >Ok (save)</button>
                    </form>
                    <br>
                    <br>
                    <a href="/view/customer/MyProfile.jsp" class="hrefButton">Cancel</a>
                </div>
            </div>
<c:import url="/view/employee/popUps/customerPopUps/EditProfilePopUp.jsp"/>
</body>
</html>
