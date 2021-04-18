package servlets;

import controller.RegexParser;
import model.Model;
import model.ModelFactory;
import model.dto.OrderDTO;
import model.dto.SpecificationDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet (name = "SpecsTableServlet", value = "/SpecsTableServlet")
public class SpecsTableServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ("click".equals(request.getParameter("filter"))){
            HashMap<String,String> filterParams = new HashMap<>();
            Model model = ModelFactory.getModel();

            filterParams.put("id",request.getParameter("id"));
            filterParams.put("name",request.getParameter("name"));
            filterParams.put("price",request.getParameter("price"));
            filterParams.put("description",request.getParameter("description"));
            filterParams.put("isAddressDependence",request.getParameter("isAddressDependence"));
            filterParams.put("districtsIds",request.getParameter("districtsIds"));

            request.setAttribute("filterParams",filterParams);
            request.setAttribute("specs",RegexParser.filterSpecifications(model.getSpecifications(),filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
        }
    }
}
