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
import controller.comparators.CustomerComparator;
import controller.validators.CreateCustomerValidator;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;

@WebServlet(name = "CustomersTableServlet", value = "/employee/CustomersTableServlet")
public class CustomersTableServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Model model = ModelFactory.getModel();
        CustomerDTO customerDTO = model.getCustomer(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
        request.setAttribute("customer", customerDTO);
        List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
        request.setAttribute("districts", districts);
        getServletContext().getRequestDispatcher("/view/employee/editView/editCustomer.jsp")
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
            filterParams.put("firstName", request.getParameter("firstName"));
            filterParams.put("lastName", request.getParameter("lastName"));
            filterParams.put("login", request.getParameter("login"));
            filterParams.put("districtId", request.getParameter("districtId"));
            filterParams.put("address", request.getParameter("address"));
            filterParams.put("balance", request.getParameter("balance"));

            List<CustomerDTO> customers = RegexParser.filterCustomers(model.getCustomers(), filterParams);

            if (request.getParameter("sort") != null)
            {
                switch (request.getParameter("sort"))
                {
                    case "idDescending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ID).reversed());
                        break;
                    case "firstNameAscending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.FIRST_NAME));
                        break;
                    case "firstNameDescending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.FIRST_NAME).reversed());
                        break;
                    case "lastNameAscending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.LAST_NAME));
                        break;
                    case "lastNameDescending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.LAST_NAME).reversed());
                        break;
                    case "loginAscending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.LOGIN));
                        break;
                    case "loginDescending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.LOGIN).reversed());
                        break;
                    case "districtIdAscending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.DISTRICT_ID));
                        break;
                    case "districtIdDescending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.DISTRICT_ID).reversed());
                        break;
                    case "addressAscending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ADDRESS));
                        break;
                    case "addressDescending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ADDRESS).reversed());
                        break;
                    case "balanceAscending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.BALANCE));
                        break;
                    case "balanceDescending":
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.BALANCE).reversed());
                        break;
                    default:
                        customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ID));
                        break;
                }
            }
            else
            {
                customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ID));
            }

            request.setAttribute("filterParams", filterParams);
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("discardFilter")))
        {
            Model model = ModelFactory.getModel();
            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
            customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ID));
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
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
                        model.deleteCustomer(BigInteger.valueOf(Long.valueOf(check)));
                    }
                    catch (DataNotFoundWarning dataNotFoundWarning)
                    {
                        dataNotFoundWarning.printStackTrace();
                    }
                }
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
            customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ID));
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("create")))
        {
            Model model = ModelFactory.getModel();
            List<DistrictDTO> districts = new ArrayList<>(model.getDistricts().values());
            request.setAttribute("districts", districts);
            getServletContext().getRequestDispatcher("/view/employee/createView/createCustomer.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmCreate")))
        {
            Model model = ModelFactory.getModel();
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstName(request.getParameter("firstName"));
            customerDTO.setLastName(request.getParameter("lastName"));
            customerDTO.setLogin(request.getParameter("login"));
            customerDTO.setPassword(request.getParameter("password"));
            if (!request.getParameter("districtId").equals(""))
            {
                customerDTO.setDistrictId(BigInteger.valueOf(Long.valueOf(request.getParameter("districtId"))));
            }
            customerDTO.setAddress(request.getParameter("address"));
            if (!request.getParameter("balance").equals(""))
            {
                customerDTO.setBalance(Float.valueOf(request.getParameter("balance")));
            }

            try
            {
                CreateCustomerValidator customerValidator = new CreateCustomerValidator();
                if (customerValidator.validate(customerDTO))
                {
                    model.createCustomer(customerDTO);
                }
            }
            catch (DataNotCreatedWarning dataNotCreatedWarning)
            {
                dataNotCreatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
            customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ID));
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("confirmEdit")))
        {
            Model model = ModelFactory.getModel();
            CustomerDTO customerDTO = model.getCustomer(BigInteger.valueOf(Long.valueOf(request.getParameter("id"))));
            if (!request.getParameter("firstName").equals(""))
            {
                customerDTO.setFirstName(request.getParameter("firstName"));
            }
            if (!request.getParameter("lastName").equals(""))
            {
                customerDTO.setLastName(request.getParameter("lastName"));
            }
            if (!request.getParameter("districtId").equals(""))
            {
                customerDTO.setDistrictId(BigInteger.valueOf(Long.valueOf(request.getParameter("districtId"))));
            }
            else
            {
                customerDTO.setDistrictId(null);
            }
            if (!request.getParameter("address").equals(""))
            {
                customerDTO.setAddress(request.getParameter("address"));
            }
            if (!request.getParameter("balance").equals(""))
            {
                customerDTO.setBalance(Float.valueOf(request.getParameter("balance")));
            }

            try
            {
                model.updateCustomer(customerDTO);
            }
            catch (DataNotUpdatedWarning dataNotUpdatedWarning)
            {
                dataNotUpdatedWarning.printStackTrace();
            }

            HashMap<String, String> filterParams = new HashMap<>();
            request.setAttribute("filterParams", filterParams);
            List<CustomerDTO> customers = new ArrayList<>(model.getCustomers().values());
            customers.sort(new CustomerComparator(CustomerComparator.CustomerSortableField.ID));
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
                    .forward(request, response);
        }

    }
}
