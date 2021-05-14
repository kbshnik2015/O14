package servlets.customerServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.InvalidInputDataException;
import controller.exceptions.PasswordMismatchException;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;

@WebServlet(name = "CustomerOptionsServlet", value = "/CustomerOptionsServlet")
public class CustomerOptionsServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        CustomerDTO customer = (CustomerDTO) request.getSession().getAttribute("currentUser");
        Model model = ModelFactory.getModel();

        if ("click".equals(request.getParameter("changePassword")))
        {
            String oldPass = request.getParameter("oldPassword");
            String newPassword1 = request.getParameter("newPassword");
            String newPassword2 = request.getParameter("newPassword2");
            if (customer.getPassword().equals(oldPass))
            {
                if (newPassword1.equals(newPassword2))
                {
                    customer.setPassword(newPassword1);
                }
                else
                {
                    throw new IllegalLoginOrPasswordException("Passwords are different");
                }
            }
            else
            {
                throw new PasswordMismatchException("Wrong old password");
            }
        }
        else if ("click".equals(request.getParameter("save")))
        {
            String name = request.getParameter("name");
            String lastName = request.getParameter("lastName");
            String address = request.getParameter("address");

            if (!"".equals(name))
            {
                customer.setFirstName(name);
            }
            else
            {
                throw new InvalidInputDataException();
            } if (!"".equals(lastName))
        {
            customer.setLastName(lastName);
        }
        else
        {
            throw new InvalidInputDataException();
        } if (!"".equals(address))
        {
            customer.setAddress(address);
        }
        else
        {
            throw new InvalidInputDataException();
        }
        }
        try
        {
            model.updateCustomer(customer);
        }
        catch (DataNotUpdatedWarning dataNotUpdatedWarning)
        {
            dataNotUpdatedWarning.printStackTrace();
        }
        request.getSession().setAttribute("currentUser", customer);
        response.sendRedirect("/CustomerNavigationServlet?ref=options");
    }
}
