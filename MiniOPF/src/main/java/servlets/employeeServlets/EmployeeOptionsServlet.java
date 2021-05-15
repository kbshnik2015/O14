package servlets.employeeServlets;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.InvalidInputDataException;
import controller.exceptions.PasswordMismatchException;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.EmployeeDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet (name = "EmployeeOptionsServlet", value = "/employee/EmployeeOptionsServlet")
public class EmployeeOptionsServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EmployeeDTO employee = (EmployeeDTO) request.getSession().getAttribute("currentUser");
        Model model = ModelFactory.getModel();

        if("click".equals(request.getParameter("changePassword"))){
            String oldPass = request.getParameter("oldPassword");
            String newPassword1= request.getParameter("newPassword");
            String newPassword2= request.getParameter("newPassword2");
            if (employee.getPassword().equals(oldPass) ){
                if(newPassword1.equals(newPassword2)){
                    employee.setPassword(newPassword1);
                }else {
                    throw new IllegalLoginOrPasswordException();
                }
            }else {
                throw  new PasswordMismatchException();
            }
        }else if("click".equals(request.getParameter("save"))){
            String name = request.getParameter("name");
            String lastName = request.getParameter("lastName");
            if(!"".equals(name)){
                employee.setFirstName(name);
            }else {
                throw new InvalidInputDataException();
            }if(!"".equals(lastName)){
                employee.setLastName(lastName);
            }else {
                throw new InvalidInputDataException();
            }
        }
        try
        {
            model.updateEmployee(employee);
        } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
        {
            dataNotUpdatedWarning.printStackTrace();
        }
        request.getSession().setAttribute("currentUser",employee);
        response.sendRedirect("/view/employee/Options.jsp");
    }
}
