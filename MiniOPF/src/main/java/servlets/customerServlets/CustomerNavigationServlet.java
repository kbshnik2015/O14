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
import java.util.List;

@WebServlet (name = "CustomerNavigationServlet", value = "/CustomerNavigationServlet")
public class CustomerNavigationServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Controller controller = new Controller();
        HttpSession session = request.getSession();
        CustomerDTO currentUser = (CustomerDTO)session.getAttribute("currentUser");

        if ("myProfile".equals(request.getParameter("ref")))
        {
            String nextPayDay = controller.getNextPayDay(currentUser.getId());
            request.setAttribute("nextPayDay",nextPayDay);
            getServletContext().getRequestDispatcher("/view/customer/MyProfile.jsp").forward(request, response);
        }
        else if ("myOrders".equals(request.getParameter("ref")))
        {
            List<OrderDTO> orders = controller.getNotFinishedOrders(currentUser.getId());
            request.setAttribute("orders",orders);
            getServletContext().getRequestDispatcher("/view/customer/MyOrders.jsp").forward(request, response);

        }
        else if ("specs".equals(request.getParameter("ref")))
        {
            List<SpecificationDTO> accessibleSpecs = controller.getAccessibleSpecs(currentUser.getId());
            List<SpecificationDTO> notAvailableSpecs = controller.getNotAvailableSpecs(currentUser.getId());
            request.setAttribute("accessibleSpecs",accessibleSpecs);
            request.setAttribute("notAvailableSpecs",notAvailableSpecs);
            getServletContext().getRequestDispatcher("/view/customer/Specifications.jsp").forward(request, response);
        }
        else if ("myServices".equals(request.getParameter("ref")))
        {
            List<ServiceDTO> services = controller.getCustomersNotDisconnectedServices(currentUser.getId());
            request.setAttribute("services",services);
            getServletContext().getRequestDispatcher("/view/customer/Services.jsp").forward(request, response);
        }
        else if ("editProfile".equals(request.getParameter("ref")))
        {
            getServletContext().getRequestDispatcher("/view/customer/EditProfile.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
