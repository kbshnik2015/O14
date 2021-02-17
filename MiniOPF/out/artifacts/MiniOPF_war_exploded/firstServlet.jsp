<%@ page import="java.util.ArrayList" %>
<%@ page import="model.entities.Specification" %>
<%@ page import="model.Model" %><%--
  Created by IntelliJ IDEA.
  User: dvorn
  Date: 17.02.2021
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FirstJSP</title>
    
    <%
        Model model = Model.getInstance();
        ArrayList<Specification> specifications = new ArrayList<>(model.getSpecifications().values());
        for(Specification specification : specifications){
            out.println("<p>"+specification+"</p>");

        }

    %>
</head>
<body>

</body>
</html>
