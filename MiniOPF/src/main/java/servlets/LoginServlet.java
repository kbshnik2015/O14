package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import lombok.SneakyThrows;
import model.dto.AbstractUserDTO;
import model.dto.CustomerDTO;
import model.dto.EmployeeDTO;
import model.entities.AbstractUser;
import model.entities.Customer;
import model.entities.Employee;

@WebServlet(name = "LoginServlet", urlPatterns = "/main")
public class LoginServlet extends HttpServlet
{
    Controller controller;

    @SneakyThrows
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        controller = new Controller();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        AbstractUserDTO user = controller.login(login, password);

        if (user instanceof CustomerDTO)
        {
            request.getRequestDispatcher("view/customer.jsp").forward(request, response);
        }
        else if (user instanceof EmployeeDTO)
        {
            request.getRequestDispatcher("view/employee.jsp").forward(request, response);

        }
    }

    //    @SneakyThrows
    //    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    //            throws ServletException, IOException
    //    {
    //        PrintWriter writer = response.getWriter();
    //        writer.println("<html>");
    //        writer.println("<h1>Hello World!</h1>");
    //        writer.println("</html>");
    //    }

}
