package servlets;

import controller.Controller;
import model.Model;
import model.ModelFactory;
import model.dto.EmployeeDTO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet (name = "NavigationServlet", value = "/NavigationServlet")
public class NavigationServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ModelFactory modelFactory = new ModelFactory();
        Model model = modelFactory.getModel();
        Controller controller = new Controller();
        HttpSession session = request.getSession();
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("currentEmployee");
        if ("click".equals(request.getParameter("allOrders"))) {
            request.setAttribute("allOrders",model.getOrders());
            getServletContext().getRequestDispatcher("/view/employee/tableView/Mine.jsp").forward(request, response);
        }else if("click".equals(request.getParameter("myOrders"))){
            request.setAttribute("myOrders",controller.getEmployeeOrders(employee.getId()));
            getServletContext().getRequestDispatcher("/view/employee/tableView/MyOrders.jsp").forward(request, response);
        }else if("click".equals(request.getParameter("services"))){
            request.setAttribute("services",model.getServices());
            getServletContext().getRequestDispatcher("/view/employee/tableView/Services.jsp").forward(request, response);
        }else if("click".equals(request.getParameter("spec"))){
            request.setAttribute("specs",model.getSpecifications());
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
        }else if("click".equals(request.getParameter("customers"))){
            request.setAttribute("customers",model.getCustomers());
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp").forward(request, response);
        }else if("click".equals(request.getParameter("districts"))){
            request.setAttribute("districts",model.getDistricts());
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
