package servlets.employeeServlets;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RegexParser;
import controller.validators.ServiceValidator;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;
import model.enums.ServiceStatus;

@WebServlet(name = "ServicesTableServlet", value = "/employee/ServicesTableServlet")
public class ServicesTableServlet extends HttpServlet
{
    private static ServiceStatus parseToServiceStatus(final String arg)
    {
        ServiceStatus serviceStatus = null;
        switch (arg)
        {
            case "ACTIVE":
                serviceStatus = ServiceStatus.ACTIVE;
                break;
            case "DISCONNECTED":
                serviceStatus = ServiceStatus.DISCONNECTED;
                break;
            case "PAY_MONEY_SUSPENDED":
                serviceStatus = ServiceStatus.PAY_MONEY_SUSPENDED;
                break;
            case "SUSPENDED":
                serviceStatus = ServiceStatus.SUSPENDED;
                break;
        }
        return serviceStatus;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Model model = ModelFactory.getModel();
        ServiceDTO serviceDTO = model.getService(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
        request.setAttribute("service", serviceDTO);
        List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
        request.setAttribute("specs", specs);
        List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
        request.setAttribute("customers", customers);
        getServletContext().getRequestDispatcher("/view/employee/editView/editService.jsp")
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
            filterParams.put("payDay", request.getParameter("payDay"));
            filterParams.put("specificationId", request.getParameter("specificationId"));
            filterParams.put("serviceStatus", request.getParameter("serviceStatus"));
            filterParams.put("customerId", request.getParameter("customerId"));

            request.setAttribute("filterParams", filterParams);
            request.setAttribute("services", RegexParser.filterServices(model.getServices(), filterParams));
            getServletContext().getRequestDispatcher("/view/employee/tableView/Services.jsp")
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
                        model.deleteService(BigInteger.valueOf(Long.valueOf(check)));
                    }
                    catch (DataNotFoundWarning dataNotFoundWarning)
                    {
                        dataNotFoundWarning.printStackTrace();
                    }
                }
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<ServiceDTO> services = new ArrayList<>(model.getServices().values());
            request.setAttribute("services", services);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Services.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("create")))
        {
            Model model = ModelFactory.getModel();
            List<SpecificationDTO> specs = new ArrayList<>(model.getSpecifications().values());
            request.setAttribute("specs", specs);
            List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/createView/createService.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmCreate")))
        {
            Model model = ModelFactory.getModel();
            ServiceDTO serviceDTO = new ServiceDTO();

            if (!request.getParameter("day").equals("") && !request.getParameter("month").equals("") &&
                    !request.getParameter("year").equals(""))
            {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String payDayString = request.getParameter("year") + "-" + request.getParameter("month") + "-" +
                        request.getParameter("day");
                try
                {
                    Date payDay = formatter.parse(payDayString);
                    serviceDTO.setPayDay(payDay);
                }
                catch (ParseException e)
                {
                    serviceDTO.setPayDay(null);
                }
            }
            if (!request.getParameter("specId").equals(""))
            {
                serviceDTO.setSpecificationId(BigInteger.valueOf(Long.valueOf(request.getParameter("specId"))));
            }
            if (!request.getParameter("status").equals(""))
            {
                serviceDTO.setServiceStatus(parseToServiceStatus(request.getParameter("status")));
            }
            if (!request.getParameter("customerId").equals(""))
            {
                serviceDTO.setCustomerId(BigInteger.valueOf(Long.valueOf(request.getParameter("customerId"))));
            }

            try
            {
                ServiceValidator serviceValidator = new ServiceValidator();
                if (serviceValidator.validate(serviceDTO))
                {
                    model.createService(serviceDTO);
                }
            }
            catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<ServiceDTO> services = new ArrayList<>(model.getServices().values());
            request.setAttribute("services", services);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Services.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmEdit")))
        {
            Model model = ModelFactory.getModel();
            ServiceDTO serviceDTO = model.getService(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));

            if (!request.getParameter("day").equals("") && !request.getParameter("month").equals("") &&
                    !request.getParameter("year").equals(""))
            {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String payDayString = request.getParameter("year") + "-" + request.getParameter("month") + "-" +
                        request.getParameter("day");
                try
                {
                    Date payDay = formatter.parse(payDayString);
                    serviceDTO.setPayDay(payDay);
                }
                catch (ParseException e)
                {
                    serviceDTO.setPayDay(null);
                }
            }
            if (!request.getParameter("specId").equals(""))
            {
                serviceDTO.setSpecificationId(BigInteger.valueOf(Long.valueOf(request.getParameter("specId"))));
            }
            if (!request.getParameter("status").equals(""))
            {
                serviceDTO.setServiceStatus(parseToServiceStatus(request.getParameter("status")));
            }
            if (!request.getParameter("customerId").equals(""))
            {
                serviceDTO.setCustomerId(BigInteger.valueOf(Long.valueOf(request.getParameter("customerId"))));
            }

            try
            {
                model.updateService(serviceDTO);
            }
            catch (DataNotUpdatedWarning dataNotUpdatedWarning)
            {
                dataNotUpdatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<ServiceDTO> services = new ArrayList<>(model.getServices().values());
            request.setAttribute("services", services);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Services.jsp")
                    .forward(request, response);
        }
    }
}
