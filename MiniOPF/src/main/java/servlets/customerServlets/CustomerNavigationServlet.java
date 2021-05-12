package servlets.customerServlets;

import controller.Controller;
import model.Model;
import model.ModelFactory;
import model.dto.CustomerDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "CustomerNavigationServlet", value = "/CustomerNavigationServlet")
public class CustomerNavigationServlet extends HttpServlet
{
    Model model = ModelFactory.getModel();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Controller controller = new Controller();
        HttpSession session = request.getSession();
        CustomerDTO currentUser = (CustomerDTO)session.getAttribute("currentUser");

        if ("main".equals(request.getParameter("ref")))
        {
            String nextPayDay = controller.getNextPayDay(currentUser.getId());
            request.setAttribute("nextPayDay",nextPayDay);
            getServletContext().getRequestDispatcher("/view/customer/Mine.jsp").forward(request, response);
        }
        else if ("myOrders".equals(request.getParameter("ref")))
        {
            List<OrderDTO> orders = (List<OrderDTO>) controller.getCustomerOrders(currentUser.getId());
            request.setAttribute("orders",orders);
            getServletContext().getRequestDispatcher("/view/customer/MyOrders.jsp").forward(request, response);

        }
        else if ("specs".equals(request.getParameter("ref")))
        {
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            request.setAttribute("specs",specs);
            getServletContext().getRequestDispatcher("/view/customer/Specifications.jsp").forward(request, response);
        }
        else if ("myServices".equals(request.getParameter("ref")))
        {
            List<ServiceDTO> services = (List<ServiceDTO>) controller.getCustomerServices(currentUser.getId());
            request.setAttribute("services",services);
            getServletContext().getRequestDispatcher("/view/customer/Services.jsp").forward(request, response);
        }
        else if ("options".equals(request.getParameter("ref")))
        {
            getServletContext().getRequestDispatcher("/view/customer/Options.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
