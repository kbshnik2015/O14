package servlets.employeeServlets;

import controller.RegexParser;
import model.Model;
import model.ModelFactory;
import model.dto.OrderDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet (name = "ServicesTableServlet", value = "/employee/ServicesTableServlet")
public class ServicesTableServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ("click".equals(request.getParameter("filter"))){
            HashMap<String,String> filterParams = new HashMap<>();
            Model model = ModelFactory.getModel();

            filterParams.put("id",request.getParameter("id"));
            filterParams.put("payDay",request.getParameter("payDay"));
            filterParams.put("specificationId",request.getParameter("specificationId"));
            filterParams.put("serviceStatus",request.getParameter("serviceStatus"));
            filterParams.put("customerId",request.getParameter("customerId"));

            request.setAttribute("filterParams",filterParams);
            request.setAttribute("services", RegexParser.filterServices(model.getServices(),filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Services.jsp").forward(request, response);
        }
    }
}
