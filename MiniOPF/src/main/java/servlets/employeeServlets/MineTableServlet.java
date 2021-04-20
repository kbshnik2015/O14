package servlets.employeeServlets;

import controller.RegexParser;
import model.Model;
import model.ModelFactory;
import model.dto.OrderDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet (name = "MineTableServlet", value = "/employee/MineTableServlet")
public class MineTableServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        if ("click".equals(request.getParameter("filter"))){
            HashMap<String,String> filterParams = new HashMap<>();
            Model model = ModelFactory.getModel();
            List<OrderDTO> allOrders = new ArrayList<>( model.getOrders().values());;

            filterParams.put("id",request.getParameter("id"));
            filterParams.put("serviceId",request.getParameter("serviceId"));
            filterParams.put("customerId",request.getParameter("customerId"));
            filterParams.put("specId",request.getParameter("specId"));
            filterParams.put("employeeId",request.getParameter("employeeId"));
            filterParams.put("orderAim",request.getParameter("orderAim"));
            filterParams.put("orderStatus",request.getParameter("orderStatus"));
            filterParams.put("address",request.getParameter("address"));

            request.setAttribute("filterParams",filterParams);
            request.setAttribute("allOrders",RegexParser.filterOrders(allOrders,filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }
    }
}
