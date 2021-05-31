package servlets;

import controller.Controller;
import lombok.SneakyThrows;
import model.Model;
import model.ModelFactory;
import model.dto.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet
{
    @SneakyThrows
    @Override
    public void init() throws ServletException
    {
        super.init();

    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        ModelFactory modelFactory = new ModelFactory();
        Model model = modelFactory.getModel();
        Controller controller = new Controller();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(-1);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        AbstractUserDTO user = controller.login(login, password);

        if (user instanceof CustomerDTO)
        {
            CustomerDTO customer = (CustomerDTO) user;
            session.setAttribute("currentUser", customer);
            response.sendRedirect("/CustomerNavigationServlet?ref=myServices");
        }
        else if(user instanceof EmployeeDTO)
        {
            EmployeeDTO employee = (EmployeeDTO) user;
            session.setAttribute("currentUser",employee);
            response.sendRedirect("/employee/NavigationServlet?allOrders=click");
        }
    }
}
