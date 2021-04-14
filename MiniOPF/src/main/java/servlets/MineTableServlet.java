package servlets;

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
import java.util.HashMap;
import java.util.Map;

@WebServlet (name = "MineTableServlet", value = "/MineTableServlet")
public class MineTableServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ("click".equals(request.getParameter("filter"))){
            RegexParser regexParser = new RegexParser();
            HashMap<String,String> regexps = new HashMap<>();
            ModelFactory modelFactory = new ModelFactory();
            Model model = modelFactory.getModel();
            Map<BigInteger, OrderDTO> orders = model.getOrders();

            regexps.put("id",request.getParameter("id"));
            regexps.put("serviceId",request.getParameter("serviceId"));
            regexps.put("customerId",request.getParameter("customerId"));
            regexps.put("specId",request.getParameter("specId"));
            regexps.put("employeeId",request.getParameter("employeeId"));
            regexps.put("orderAim",request.getParameter("orderAim"));
            regexps.put("orderStatus",request.getParameter("orderStatus"));
            regexps.put("address",request.getParameter("address"));
            //request.setAttribute("allOrders",regexParser.filterOrders(orders,regexps));
            HashMap<BigInteger,OrderDTO> result =
                    (HashMap<BigInteger, OrderDTO>) regexParser.filterOrders(orders,regexps);
            PrintWriter printWriter = response.getWriter();
            for(Map.Entry<BigInteger, OrderDTO> order : result.entrySet()) {
                printWriter.println(order.getValue().toString());
            }
            printWriter.println("end");
            //getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }
    }
}
