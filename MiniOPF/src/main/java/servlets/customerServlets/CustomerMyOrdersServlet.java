package servlets.customerServlets;

import controller.Controller;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigInteger;

@WebServlet (name = "CustomerMyOrdersServlet", value = "/CustomerMyOrdersServlet")
public class CustomerMyOrdersServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Controller controller = new Controller();
        BigInteger orderId = new BigInteger(request.getParameter("orderId"));
        try
        {
            controller.cancelOrder(orderId);
        } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
        {
            dataNotUpdatedWarning.printStackTrace();
        }
        response.sendRedirect("/CustomerNavigationServlet?ref=myOrders");

    }
}
