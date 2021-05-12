package servlets.employeeServlets;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RegexParser;
import controller.comparators.districtComparators.DistrictIdComparator;
import controller.comparators.districtComparators.DistrictNameComparator;
import controller.comparators.districtComparators.DistrictParentIdComparator;
import controller.validators.DistrictValidator;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.DistrictDTO;

@WebServlet(name = "DistrictsTableServlet", value = "/employee/DistrictsTableServlet")
public class DistrictsTableServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Model model = ModelFactory.getModel();
        DistrictDTO districtDTO = model.getDistrict(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
        request.setAttribute("district", districtDTO);
        List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
        request.setAttribute("districts", districts);
        getServletContext().getRequestDispatcher("/view/employee/editView/editDistrict.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ("click".equals(request.getParameter("filter")))
        {
            HashMap<String, String> filterParams = new HashMap<>();
            Model model = ModelFactory.getModel();

            filterParams.put("id", request.getParameter("id"));
            filterParams.put("name", request.getParameter("name"));
            filterParams.put("parentId", request.getParameter("parentId"));

            List<DistrictDTO> districts = RegexParser.filterDistricts(model.getDistricts(), filterParams);

            if (request.getParameter("sort") != null)
            {
                switch (request.getParameter("sort"))
                {
                    case "idDescending":
                        districts.sort(new DistrictIdComparator().reversed());
                        break;
                    case "nameAscending":
                        districts.sort(new DistrictNameComparator());
                        break;
                    case "nameDescending":
                        districts.sort(new DistrictNameComparator().reversed());
                        break;
                    case "parentIdAscending":
                        districts.sort(new DistrictParentIdComparator());
                        break;
                    case "parentIdDescending":
                        districts.sort(new DistrictParentIdComparator().reversed());
                        break;
                    default:
                        districts.sort(new DistrictIdComparator());
                        break;
                }
            }
            else
            {
                districts.sort(new DistrictIdComparator());
            }

            request.setAttribute("filterParams", filterParams);
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("discardFilter")))
        {
            Model model = ModelFactory.getModel();
            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            districts.sort(new DistrictIdComparator());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("delete")))
        {
            Model model = ModelFactory.getModel();
            String[] checks = request.getParameterValues("checks");
            if (checks != null)
            {
                for (final String check : checks)
                {
                    try
                    {
                        model.deleteDistrict(BigInteger.valueOf(Long.valueOf(check)));
                    }
                    catch (DataNotFoundWarning dataNotFoundWarning)
                    {
                        dataNotFoundWarning.printStackTrace();
                    }
                }
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            districts.sort(new DistrictIdComparator());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmEdit")))
        {
            Model model = ModelFactory.getModel();
            DistrictDTO districtDTO = model.getDistrict(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
            if (!request.getParameter("name").equals(""))
            {
                districtDTO.setName(request.getParameter("name"));
            }
            if (!request.getParameter("parentId").equals(""))
            {
                districtDTO.setParentId(BigInteger.valueOf(Long.valueOf(request.getParameter("parentId"))));
            }
            else
            {
                districtDTO.setParentId(null);
            }

            try
            {
                model.updateDistrict(districtDTO);
            }
            catch (DataNotUpdatedWarning dataNotUpdatedWarning)
            {
                dataNotUpdatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            districts.sort(new DistrictIdComparator());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("create")))
        {
            Model model = ModelFactory.getModel();
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/createView/createDistrict.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmCreate")))
        {
            Model model = ModelFactory.getModel();
            DistrictDTO districtDTO = new DistrictDTO();
            districtDTO.setName(request.getParameter("name"));
            if (!request.getParameter("parentId").equals(""))
            {
                districtDTO.setParentId(BigInteger.valueOf(Long.valueOf(request.getParameter("parentId"))));
            }

            try
            {
                DistrictValidator districtValidator = new DistrictValidator();
                if (districtValidator.validate(districtDTO))
                {
                    model.createDistrict(districtDTO);
                }
            }
            catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            districts.sort(new DistrictIdComparator());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Districts.jsp")
                    .forward(request, response);
        }
    }

}
