package servlets.employeeServlets;

import java.io.IOException;
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
import controller.comparators.customerComparators.CustomerIdComparator;
import controller.comparators.districtComparators.DistrictIdComparator;
import controller.comparators.orderComparators.OrderIdComparator;
import controller.comparators.serviceComparators.ServiceIdComparator;
import controller.comparators.specificationComparators.SpecificationIdComparator;
import model.Model;
import model.ModelFactory;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;

@WebServlet(name = "NavigationServlet", value = "/employee/NavigationServlet")
public class NavigationServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ModelFactory modelFactory = new ModelFactory();
        Model model = modelFactory.getModel();
        Controller controller = new Controller();
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("currentUser");
        HashMap<String, String> filterParams = new HashMap<>();

        request.setAttribute("filterParams", filterParams);
        if ("click".equals(request.getParameter("allOrders")))
        {
            List<OrderDTO> allOrders = new ArrayList<>(model.getOrders().values());
            allOrders.sort(new OrderIdComparator());
            request.setAttribute("allOrders", allOrders);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }
        else if ("click".equals(request.getParameter("myOrders")))
        {
            List<OrderDTO> myOrders = (List<OrderDTO>) controller.getEmployeeOrders(employee.getId());
            myOrders.sort(new OrderIdComparator());
            request.setAttribute("myOrders", myOrders);
            getServletContext().getRequestDispatcher("/view/employee/tableView/MyOrders.jsp")
                    .forward(request, response);
        }
        else if ("click".equals(request.getParameter("services")))
        {
            List<ServiceDTO> services = new ArrayList<>(model.getServices().values());
            services.sort(new ServiceIdComparator());
            request.setAttribute("services", services);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Services.jsp")
                    .forward(request, response);
        }
        else if ("click".equals(request.getParameter("spec")))
        {
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            specs.sort(new SpecificationIdComparator());
            request.setAttribute("specs", specs);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
        }
        else if ("click".equals(request.getParameter("customers")))
        {
            List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
            customers.sort(new CustomerIdComparator());
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
                    .forward(request, response);
        }
        else if ("click".equals(request.getParameter("districts")))
        {
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            districts.sort(new DistrictIdComparator());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
