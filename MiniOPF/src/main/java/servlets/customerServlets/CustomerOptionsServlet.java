package servlets.customerServlets;

import controller.Controller;
import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.InvalidInputDataException;
import controller.exceptions.PasswordMismatchException;
import model.Model;
import model.ModelDB;
import model.ModelFactory;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet (name = "CustomerOptionsServlet", value = "/CustomerOptionsServlet")
public class CustomerOptionsServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        CustomerDTO customer = (CustomerDTO) request.getSession().getAttribute("currentUser");
        Model model = ModelFactory.getModel();

        if("click".equals(request.getParameter("changePassword"))){
            String oldPass = request.getParameter("oldPassword");
            String newPassword1= request.getParameter("newPassword");
            String newPassword2= request.getParameter("newPassword2");
            if (customer.getPassword().equals(oldPass) ){
                if(newPassword1.equals(newPassword2)){
                    customer.setPassword(newPassword1);
                }else {
                    throw new IllegalLoginOrPasswordException();
                }
            }else {
                throw  new PasswordMismatchException();
            }
        }else if("click".equals(request.getParameter("save"))){
            String name = request.getParameter("name");
            String lastName = request.getParameter("lastName");
            String address = request.getParameter("address");

            if(!"".equals(name)){
                customer.setFirstName(name);
            }else {
                throw new InvalidInputDataException();
            }if(!"".equals(lastName)){
                customer.setLastName(lastName);
            }else {
                throw new InvalidInputDataException();
            }if(!"".equals(address)){
                customer.setAddress(address);
            }else {
                throw new InvalidInputDataException();
            }
        }
        try
        {
            model.updateCustomer(customer);
        } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
        {
            dataNotUpdatedWarning.printStackTrace();
        }
        request.getSession().setAttribute("currentUser",customer);
        response.sendRedirect("/CustomerNavigationServlet?ref=options");
    }
}
