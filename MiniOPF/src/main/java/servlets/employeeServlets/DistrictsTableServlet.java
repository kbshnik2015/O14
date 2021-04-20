package servlets.employeeServlets;

import controller.RegexParser;
import model.Model;
import model.ModelFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet (name = "DistrictsTableServlet", value = "/employee/DistrictsTableServlet")
public class DistrictsTableServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ("click".equals(request.getParameter("filter"))){
            HashMap<String,String> filterParams = new HashMap<>();
            Model model = ModelFactory.getModel();

            filterParams.put("id",request.getParameter("id"));
            filterParams.put("name",request.getParameter("name"));

            request.setAttribute("filterParams",filterParams);
            request.setAttribute("districts", RegexParser.filterDistricts(model.getDistricts(),filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp").forward(request, response);
        }
    }

}
