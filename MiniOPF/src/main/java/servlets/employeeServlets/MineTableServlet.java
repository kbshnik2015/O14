package servlets.employeeServlets;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
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
import controller.validators.OrderValidator;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;
import model.enums.OrderAim;
import model.enums.OrderStatus;

@WebServlet(name = "MineTableServlet", value = "/employee/MineTableServlet")
public class MineTableServlet extends HttpServlet
{
    private static OrderStatus parseToOrderStatus(final String arg)
    {
        OrderStatus orderStatus = null;
        switch (arg)
        {
            case "IN_PROGRESS":
                orderStatus = OrderStatus.IN_PROGRESS;
                break;
            case "SUSPENDED":
                orderStatus = OrderStatus.SUSPENDED;
                break;
            case "COMPLETED":
                orderStatus = OrderStatus.COMPLETED;
                break;
            case "ENTERING":
                orderStatus = OrderStatus.ENTERING;
                break;
            case "CANCELLED":
                orderStatus = OrderStatus.CANCELLED;
                break;
        }
        return orderStatus;
    }

    private static OrderAim parseToOrderAim(final String arg)
    {
        OrderAim orderAim = null;
        switch (arg)
        {
            case "NEW":
                orderAim = OrderAim.NEW;
                break;
            case "SUSPEND":
                orderAim = OrderAim.SUSPEND;
                break;
            case "RESTORE":
                orderAim = OrderAim.RESTORE;
                break;
            case "DISCONNECT":
                orderAim = OrderAim.DISCONNECT;
                break;
        }
        return orderAim;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        if ("click".equals(request.getParameter("filter")))
        {
            HashMap<String, String> filterParams = new HashMap<>();
            Model model = ModelFactory.getModel();
            List<OrderDTO> allOrders = new ArrayList<>(model.getOrders().values());

            filterParams.put("id", request.getParameter("id"));
            filterParams.put("serviceId", request.getParameter("serviceId"));
            filterParams.put("customerId", request.getParameter("customerId"));
            filterParams.put("specId", request.getParameter("specId"));
            filterParams.put("employeeId", request.getParameter("employeeId"));
            filterParams.put("orderAim", request.getParameter("orderAim"));
            filterParams.put("orderStatus", request.getParameter("orderStatus"));
            filterParams.put("address", request.getParameter("address"));

            request.setAttribute("filterParams", filterParams);
            request.setAttribute("allOrders", RegexParser.filterOrders(allOrders, filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("delete")))
        {
            Model model = ModelFactory.getModel();
            String[] checks = request.getParameterValues("checks");
            if (checks != null)
            {
                for (final String check : checks)
                {
                    try
                    {
                        model.deleteOrder(BigInteger.valueOf(Long.valueOf(check)));
                    }
                    catch (DataNotFoundWarning dataNotFoundWarning)
                    {
                        dataNotFoundWarning.printStackTrace();
                    }
                }
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<OrderDTO> allOrders = new ArrayList<>(model.getOrders().values());
            request.setAttribute("allOrders", allOrders);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("assignOrders")))
        {
            Model model = ModelFactory.getModel();
            Controller controller = new Controller();
            HttpSession session = request.getSession();
            EmployeeDTO employee = (EmployeeDTO) session.getAttribute("currentUser");
            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);

            String[] checks = request.getParameterValues("checks");
            if (checks != null)
            {
                for (final String check : checks)
                {
                    try
                    {
                        controller.assignOrder(employee.getId(), BigInteger.valueOf(Long.valueOf(check)));
                    }
                    catch (DataNotUpdatedWarning dataNotUpdatedWarning)
                    {
                        dataNotUpdatedWarning.printStackTrace();
                    }
                }
            }

            List<OrderDTO> allOrders = new ArrayList<>(model.getOrders().values());
            request.setAttribute("allOrders", allOrders);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("create")))
        {
            Model model = ModelFactory.getModel();
            List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
            request.setAttribute("customers", customers);
            List<EmployeeDTO> employees = new ArrayList<>(model.getEmployees().values());
            request.setAttribute("employees", employees);
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            request.setAttribute("specs", specs);
            List<ServiceDTO> services = new ArrayList<>(model.getServices().values());
            request.setAttribute("services", services);
            getServletContext().getRequestDispatcher("/view/employee/createView/createOrder.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmCreate")))
        {
            Model model = ModelFactory.getModel();
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderAim(parseToOrderAim(request.getParameter("aim")));
            orderDTO.setOrderStatus(parseToOrderStatus(request.getParameter("status")));
            orderDTO.setCustomerId(BigInteger.valueOf(Long.valueOf(request.getParameter("customerId"))));
            if (!request.getParameter("employeeId").equals(""))
            {
                orderDTO.setEmployeeId(BigInteger.valueOf(Long.valueOf(request.getParameter("employeeId"))));
            }
            orderDTO.setSpecId(BigInteger.valueOf(Long.valueOf(request.getParameter("specId"))));
            if (!request.getParameter("serviceId").equals(""))
            {
                orderDTO.setServiceId(BigInteger.valueOf(Long.valueOf(request.getParameter("serviceId"))));
            }
            if (!request.getParameter("address").equals(""))
            {
                orderDTO.setAddress(request.getParameter("address"));
            }

            try
            {
                OrderValidator orderValidator = new OrderValidator();
                if (orderValidator.validate(orderDTO))
                {
                    model.createOrder(orderDTO);
                }
            }
            catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<OrderDTO> allOrders = new ArrayList<>(model.getOrders().values());
            request.setAttribute("allOrders", allOrders);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("edit")))
        {
            Model model = ModelFactory.getModel();
            String[] checks = request.getParameterValues("checks");
            if (checks != null)
            {
                OrderDTO orderDTO = model.getOrder(BigInteger.valueOf(Long.valueOf(checks[0])));
                request.setAttribute("order", orderDTO);
                List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
                request.setAttribute("customers", customers);
                List<EmployeeDTO> employees = new ArrayList<>(model.getEmployees().values());
                request.setAttribute("employees", employees);
                List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
                request.setAttribute("specs", specs);
                List<ServiceDTO> services = new ArrayList<>(model.getServices().values());
                request.setAttribute("services", services);
                getServletContext().getRequestDispatcher("/view/employee/editView/editOrder.jsp")
                        .forward(request, response);
            }
            else
            {
                HashMap<String, String> filterParams = new HashMap<>();
                request.setAttribute("filterParams", filterParams);
                List<OrderDTO> allOrders = new ArrayList<>(model.getOrders().values());
                request.setAttribute("allOrders", allOrders);
                getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp")
                        .forward(request, response);
            }
        }

        if ("click".equals(request.getParameter("confirmEdit")))
        {
            Model model = ModelFactory.getModel();
            OrderDTO orderDTO = model.getOrder(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
            orderDTO.setOrderAim(parseToOrderAim(request.getParameter("aim")));
            orderDTO.setOrderStatus(parseToOrderStatus(request.getParameter("status")));
            orderDTO.setCustomerId(BigInteger.valueOf(Long.valueOf(request.getParameter("customerId"))));
            if (!request.getParameter("employeeId").equals(""))
            {
                orderDTO.setEmployeeId(BigInteger.valueOf(Long.valueOf(request.getParameter("employeeId"))));
            }
            orderDTO.setSpecId(BigInteger.valueOf(Long.valueOf(request.getParameter("specId"))));
            if (!request.getParameter("serviceId").equals(""))
            {
                orderDTO.setServiceId(BigInteger.valueOf(Long.valueOf(request.getParameter("serviceId"))));
            }
            if (!request.getParameter("address").equals(""))
            {
                orderDTO.setAddress(request.getParameter("address"));
            }

            try
            {
                model.updateOrder(orderDTO);
            }
            catch (DataNotUpdatedWarning dataNotUpdatedWarning)
            {
                dataNotUpdatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<OrderDTO> allOrders = new ArrayList<>(model.getOrders().values());
            request.setAttribute("allOrders", allOrders);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }
    }
}
