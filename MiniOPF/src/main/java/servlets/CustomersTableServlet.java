package servlets;

import controller.Controller;
import controller.RegexParser;
import model.Model;
import model.ModelFactory;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet (name = "CustomersTableServlet", value = "/CustomersTableServlet")
public class CustomersTableServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ("click".equals(request.getParameter("filter"))){
            HashMap<String,String> filterParams = new HashMap<>();
            Model model = ModelFactory.getModel();

            filterParams.put("id",request.getParameter("id"));
            filterParams.put("firstName",request.getParameter("firstName"));
            filterParams.put("lastName",request.getParameter("lastName"));
            filterParams.put("login",request.getParameter("login"));
            filterParams.put("address",request.getParameter("address"));
            filterParams.put("balance",request.getParameter("balance"));

            request.setAttribute("filterParams",filterParams);
            request.setAttribute("customers", RegexParser.filterCustomers(model.getCustomers(),filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp").forward(request, response);
        }
    }
}
