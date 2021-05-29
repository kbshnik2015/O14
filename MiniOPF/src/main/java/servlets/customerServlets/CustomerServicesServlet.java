package servlets.customerServlets;

import controller.Controller;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigInteger;

@WebServlet (name = "CustomerServicesServlet", value = "/CustomerServicesServlet")
public class CustomerServicesServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        BigInteger serviceId = new BigInteger(request.getParameter("serviceId"));
        CustomerDTO customer = (CustomerDTO) request.getSession().getAttribute("currentUser");
        Controller controller = new Controller();
        if("click".equals(request.getParameter("disconnect"))){
            if(controller.isThereSuspensionOrder(serviceId) || controller.isThereRestorationOrder(serviceId)){
                try
                {
                    controller.cancelOrder(controller.getNotCompletedOrderOnService(serviceId).getId());
                } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
                {
                    dataNotUpdatedWarning.printStackTrace();
                }
            }
            try
            {
                controller.createDisconnectOrder(customer.getId(),serviceId);
            } catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }
        }else if("click".equals(request.getParameter("suspend"))){
            try
            {
                controller.createSuspendOrder(customer.getId(),serviceId);
            } catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }
        }else if("click".equals(request.getParameter("restore"))){
            try
            {
                controller.createRestoreOrder(customer.getId(),serviceId);
            } catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }
        }else if("click".equals(request.getParameter("cancel"))){
            BigInteger orderId = controller.getNotCompletedOrderOnService(serviceId).getId();
            try
            {
                controller.cancelOrder(orderId);
            } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
            {
                dataNotUpdatedWarning.printStackTrace();
            }
        }
        response.sendRedirect("/CustomerNavigationServlet?ref=myServices");
    }
}
