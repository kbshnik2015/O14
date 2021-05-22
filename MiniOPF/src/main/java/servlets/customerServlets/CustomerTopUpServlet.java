package servlets.customerServlets;

import controller.Controller;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import static java.lang.Float.parseFloat;

@WebServlet (name = "CustomerTopUpServlet", value = "/CustomerTopUpServlet")
public class CustomerTopUpServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Controller controller = new Controller();
        Model model = ModelFactory.getModel();
        CustomerDTO customer = (CustomerDTO) request.getSession().getAttribute("currentUser");

        if("click".equals(request.getParameter("topUpButton"))){
            float amountOfMoney = parseFloat(request.getParameter("topUp"));
            try
            {
                controller.changeBalanceOn(customer.getId(),amountOfMoney);
            } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
            {
                dataNotUpdatedWarning.printStackTrace();
            }
        }
        request.getSession().setAttribute("currentUser",model.getCustomer(customer.getId()));
        response.sendRedirect("/CustomerNavigationServlet?ref=myProfile");
    }
}
