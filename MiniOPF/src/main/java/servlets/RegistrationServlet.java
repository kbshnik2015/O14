package servlets;

import controller.exceptions.PasswordMismatchException;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.dto.CustomerDTO;
import model.dto.EmployeeDTO;
import model.enums.EmployeeStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet (name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
            if("customerRegistration".equals(request.getParameter("button"))){
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String repeatPassword = request.getParameter("repeatPassword");
                String address = request.getParameter("address");

                if(password.equals(repeatPassword)){
                    CustomerDTO customer = new CustomerDTO(firstName,lastName,login,password,address,0);
                    try
                    {
                        model.createCustomer(customer);
                    } catch (DataNotCreatedWarning dataNotCreatedWarning)
                    {
                        dataNotCreatedWarning.printStackTrace();
                    }
                    response.sendRedirect("/login?login="+login+"&password="+password);
                }else {
                    throw new PasswordMismatchException("Password mismatch");
                }

            }else if("employeeRegistration".equals(request.getParameter("button"))){
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String repeatPassword = request.getParameter("repeatPassword");

                if (password.equals(repeatPassword)){
                    EmployeeDTO employee = new EmployeeDTO(firstName,lastName,login,password, EmployeeStatus.WORKING);
                    try
                    {
                        model.createEmployee(employee);
                    } catch (DataNotCreatedWarning dataNotCreatedWarning)
                    {
                        dataNotCreatedWarning.printStackTrace();
                    }
                    response.sendRedirect("/login?login="+login+"&password="+password);
                }else{
                    throw new PasswordMismatchException("Password mismatch");
                }
            }
    }
}
