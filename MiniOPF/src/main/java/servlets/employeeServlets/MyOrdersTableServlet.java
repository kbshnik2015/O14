package servlets.employeeServlets;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.RegexParser;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;

@WebServlet(name = "MyOrdersTableServlet", value = "/employee/MyOrdersTableServlet")
public class MyOrdersTableServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        if ("click".equals(request.getParameter("filter")))
        {
            HashMap<String, String> filterParams = new HashMap<>();
            Controller controller = new Controller();
            HttpSession session = request.getSession();
            EmployeeDTO employee = (EmployeeDTO) session.getAttribute("currentUser");
            List<OrderDTO> myOrders = (List<OrderDTO>) controller.getEmployeeOrders(employee.getId());

            filterParams.put("id", request.getParameter("id"));
            filterParams.put("serviceId", request.getParameter("serviceId"));
            filterParams.put("customerId", request.getParameter("customerId"));
            filterParams.put("specId", request.getParameter("specId"));
            filterParams.put("employeeId", request.getParameter("employeeId"));
            filterParams.put("orderAim", request.getParameter("orderAim"));
            filterParams.put("orderStatus", request.getParameter("orderStatus"));
            filterParams.put("address", request.getParameter("address"));
            request.setAttribute("filterParams", filterParams);
            request.setAttribute("myOrders", RegexParser.filterOrders(myOrders, filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/MyOrders.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("delete")))
        {
            Model model = ModelFactory.getModel();
            String[] checks = request.getParameterValues("checks");
            for (int i = 0; i < checks.length; i++)
            {
                try
                {
                    model.deleteOrder(BigInteger.valueOf(Long.valueOf(checks[i])));
                }
                catch (DataNotFoundWarning dataNotFoundWarning)
                {
                    dataNotFoundWarning.printStackTrace();
                }
            }

            HashMap<String, String> filterParams = new HashMap<>();

            request.setAttribute("filterParams", filterParams);
            Controller controller = new Controller();
            HttpSession session = request.getSession();
            EmployeeDTO employee = (EmployeeDTO) session.getAttribute("currentUser");
            request.setAttribute("myOrders", controller.getEmployeeOrders(employee.getId()));
            getServletContext().getRequestDispatcher("/view/employee/tableView/MyOrders.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("unassignOrders")))
        {
            Model model = ModelFactory.getModel();
            Controller controller = new Controller();
            HttpSession session = request.getSession();
            EmployeeDTO employee = (EmployeeDTO) session.getAttribute("currentUser");
            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);

            String[] checks = request.getParameterValues("checks");
            for (int i = 0; i < checks.length; i++)
            {
                try
                {
                    controller.unassignOrder(BigInteger.valueOf(Long.valueOf(checks[i])));
                }
                catch (DataNotUpdatedWarning dataNotUpdatedWarning)
                {
                    dataNotUpdatedWarning.printStackTrace();
                }
            }

            request.setAttribute("myOrders", controller.getEmployeeOrders(employee.getId()));
            getServletContext().getRequestDispatcher("/view/employee/tableView/MyOrders.jsp")
                    .forward(request, response);
        }
    }
}
