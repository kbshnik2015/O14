package servlets.employeeServlets;

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
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet (name = "MyOrdersTableServlet", value = "/employee/MyOrdersTableServlet")
public class MyOrdersTableServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        if ("click".equals(request.getParameter("filter"))){
            HashMap<String,String> filterParams = new HashMap<>();
            Controller controller = new Controller();
            HttpSession session = request.getSession();
            EmployeeDTO employee = (EmployeeDTO) session.getAttribute("currentEmployee");
            List<OrderDTO> myOrders = (List<OrderDTO>) controller.getEmployeeOrders(employee.getId());

            filterParams.put("id",request.getParameter("id"));
            filterParams.put("serviceId",request.getParameter("serviceId"));
            filterParams.put("customerId",request.getParameter("customerId"));
            filterParams.put("specId",request.getParameter("specId"));
            filterParams.put("employeeId",request.getParameter("employeeId"));
            filterParams.put("orderAim",request.getParameter("orderAim"));
            filterParams.put("orderStatus",request.getParameter("orderStatus"));
            filterParams.put("address",request.getParameter("address"));
            request.setAttribute("filterParams",filterParams);
            request.setAttribute("myOrders", RegexParser.filterOrders(myOrders,filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }
    }
}
