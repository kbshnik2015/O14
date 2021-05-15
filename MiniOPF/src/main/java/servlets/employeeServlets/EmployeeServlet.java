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
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();
        HttpSession session = request.getSession();
        EmployeeDTO currentEmployee = (EmployeeDTO) session.getAttribute("currentUser");

        currentEmployee.setWaitingForOrders("on".equals(request.getParameter("waitForWork")));
        model.updateEmployee(currentEmployee);
        session.setAttribute("currentUser", currentEmployee);
        response.sendRedirect(request.getHeader("referer"));
    }
}
