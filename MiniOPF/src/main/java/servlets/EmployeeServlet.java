package servlets;

import lombok.SneakyThrows;
import model.Model;
import model.ModelFactory;
import model.dto.EmployeeDTO;
import model.entities.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet (name = "EmployeeServlet", value = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ModelFactory modelFactory = new ModelFactory();
        Model model = modelFactory.getModel();
        HttpSession session = request.getSession();
        EmployeeDTO currentEmployee = (EmployeeDTO) session.getAttribute("currentEmployee");
        if("on".equals(request.getParameter("waitForWork"))){
            currentEmployee.setWaitingForOrders(true);
        }else {
            currentEmployee.setWaitingForOrders(false);
        }
        model.updateEmployee(currentEmployee);
        session.removeAttribute("currentEmployee");
        session.setAttribute("currentEmployee",currentEmployee);
        response.sendRedirect(request.getHeader("referer"));

    }
}
