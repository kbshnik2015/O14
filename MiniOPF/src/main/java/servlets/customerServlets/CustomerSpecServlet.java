package servlets.customerServlets;

import controller.Controller;
import model.database.dao.CustomerDAO;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigInteger;

@WebServlet (name = "CustomerSpecServlet", value = "/CustomerSpecServlet")
public class CustomerSpecServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Controller controller = new Controller();
        CustomerDTO customer = (CustomerDTO) request.getSession().getAttribute("currentUser");
        BigInteger specId = new BigInteger(request.getParameter("specId"));
        if("click".equals(request.getParameter("connect"))){
            try
            {
                controller.createNewOrder(customer.getId(),specId);
            } catch (DataNotCreatedWarning | DataNotUpdatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }
        }
        response.sendRedirect("/CustomerNavigationServlet?ref=specs");
    }
}
