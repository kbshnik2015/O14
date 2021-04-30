package servlets.employeeServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.SneakyThrows;
import model.Model;
import model.ModelFactory;
import model.dto.EmployeeDTO;

@WebServlet(name = "EmployeeServlet", value = "/employee/EmployeeServlet")
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
        if ("on".equals(request.getParameter("waitForWork")))
        {
            currentEmployee.setWaitingForOrders(true);
        }
        else
        {
            currentEmployee.setWaitingForOrders(false);
        }
        model.updateEmployee(currentEmployee);
        session.removeAttribute("currentEmployee");
        session.setAttribute("currentEmployee", currentEmployee);
        response.sendRedirect(request.getHeader("referer"));

    }
}
