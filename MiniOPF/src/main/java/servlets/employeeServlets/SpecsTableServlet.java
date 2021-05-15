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
import controller.comparators.SpecificationComparator;
import controller.validators.CreateSpecificationValidator;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.DistrictDTO;
import model.dto.SpecificationDTO;

@WebServlet(name = "SpecsTableServlet", value = "/employee/SpecsTableServlet")
public class SpecsTableServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Model model = ModelFactory.getModel();
        SpecificationDTO specificationDTO = model
                .getSpecification(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
        request.setAttribute("spec", specificationDTO);
        List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
        request.setAttribute("districts", districts);
        getServletContext().getRequestDispatcher("/view/employee/editView/editSpecification.jsp")
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
            filterParams.put("price", request.getParameter("price"));
            filterParams.put("description", request.getParameter("description"));
            filterParams.put("isAddressDependence", request.getParameter("isAddressDependence"));
            filterParams.put("districtsIds", request.getParameter("districtsIds"));

            List<SpecificationDTO> specs = RegexParser.filterSpecifications(model.getSpecifications(), filterParams);

            if (request.getParameter("sort") != null)
            {
                switch (request.getParameter("sort"))
                {
                    case "idDescending":
                        specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.ID)
                                .reversed());
                        break;
                    case "nameAscending":
                        specs.sort(
                                new SpecificationComparator(SpecificationComparator.SpecificationSortableField.NAME));
                        break;
                    case "nameDescending":
                        specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.NAME)
                                .reversed());
                        break;
                    case "priceAscending":
                        specs.sort(
                                new SpecificationComparator(SpecificationComparator.SpecificationSortableField.PRICE));
                        break;
                    case "priceDescending":
                        specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.PRICE)
                                .reversed());
                        break;

                    case "descriptionAscending":
                        specs.sort(new SpecificationComparator(
                                SpecificationComparator.SpecificationSortableField.DESCRIPTION));
                        break;
                    case "descriptionDescending":
                        specs.sort(new SpecificationComparator(
                                SpecificationComparator.SpecificationSortableField.DESCRIPTION).reversed());
                        break;
                    case "isAddressDependedAscending":
                        specs.sort(new SpecificationComparator(
                                SpecificationComparator.SpecificationSortableField.ADDRESS_DEPENDENCE));
                        break;
                    case "isAddressDependedDescending":
                        specs.sort(new SpecificationComparator(
                                SpecificationComparator.SpecificationSortableField.ADDRESS_DEPENDENCE).reversed());
                        break;
                    default:
                        specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.ID));
                        break;
                }
            }
            else
            {
                specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.ID));
            }

            request.setAttribute("filterParams", filterParams);
            request.setAttribute("specs", specs);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("discardFilter")))
        {
            Model model = ModelFactory.getModel();
            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.ID));
            request.setAttribute("specs", specs);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
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
                        model.deleteSpecification(BigInteger.valueOf(Long.valueOf(check)));
                    }
                    catch (DataNotFoundWarning dataNotFoundWarning)
                    {
                        dataNotFoundWarning.printStackTrace();
                    }
                }
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.ID));
            request.setAttribute("specs", specs);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("create")))
        {
            Model model = ModelFactory.getModel();
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/createView/createSpecification.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmCreate")))
        {
            Model model = ModelFactory.getModel();
            SpecificationDTO specificationDTO = new SpecificationDTO();
            specificationDTO.setName(request.getParameter("name"));
            if (!request.getParameter("price").equals(""))
            {
                specificationDTO.setPrice(Float.valueOf(request.getParameter("price")));
            }
            specificationDTO.setDescription(request.getParameter("description"));
            List<BigInteger> districtIds = new ArrayList<>();
            specificationDTO.setDistrictsIds(districtIds);
            if ("on".equals(request.getParameter("isAddressDepended")))
            {
                specificationDTO.setAddressDependence(true);
                String[] districts = request.getParameterValues("districtId");
                if (districts != null)
                {
                    for (final String district : districts)
                    {
                        specificationDTO.getDistrictsIds().add(BigInteger.valueOf(Long.valueOf(district)));
                    }
                }
            }
            else
            {
                specificationDTO.setAddressDependence(false);
            }

            try
            {
                CreateSpecificationValidator specificationValidator = new CreateSpecificationValidator();
                if (specificationValidator.validate(specificationDTO))
                {
                    model.createSpecification(specificationDTO);
                }
            }
            catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.ID));
            request.setAttribute("specs", specs);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmEdit")))
        {
            Model model = ModelFactory.getModel();
            SpecificationDTO specificationDTO = model
                    .getSpecification(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
            if (!request.getParameter("name").equals(""))
            {
                specificationDTO.setName(request.getParameter("name"));
            }
            if (!request.getParameter("price").equals(""))
            {
                specificationDTO.setPrice(Float.valueOf(request.getParameter("price")));
            }
            if (!request.getParameter("description").equals(""))
            {
                specificationDTO.setDescription(request.getParameter("description"));
            }
            if ("on".equals(request.getParameter("isAddressDepended")))
            {
                specificationDTO.setAddressDependence(true);
                specificationDTO.setDistrictsIds(new ArrayList<>());
                String[] districts = request.getParameterValues("districtId");
                if (districts.length != 0)
                {
                    for (final String district : districts)
                    {
                        specificationDTO.getDistrictsIds().add(BigInteger.valueOf(Long.valueOf(district)));
                    }
                }
            }
            else
            {
                specificationDTO.setAddressDependence(false);
                specificationDTO.setDistrictsIds(new ArrayList<>());
            }

            try
            {
                model.updateSpecification(specificationDTO);
            }
            catch (DataNotUpdatedWarning dataNotUpdatedWarning)
            {
                dataNotUpdatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            specs.sort(new SpecificationComparator(SpecificationComparator.SpecificationSortableField.ID));
            request.setAttribute("specs", specs);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Spec.jsp").forward(request, response);
        }
    }
}
