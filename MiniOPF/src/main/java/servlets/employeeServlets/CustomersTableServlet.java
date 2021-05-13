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
import controller.comparators.customerComparators.CustomerAddressComparator;
import controller.comparators.customerComparators.CustomerBalanceComparator;
import controller.comparators.customerComparators.CustomerFirstNameComparator;
import controller.comparators.customerComparators.CustomerIdComparator;
import controller.comparators.customerComparators.CustomerLastNameComparator;
import controller.comparators.customerComparators.CustomerLoginComparator;
import controller.comparators.districtComparators.DistrictIdComparator;
import controller.comparators.districtComparators.DistrictNameComparator;
import controller.comparators.districtComparators.DistrictParentIdComparator;
import controller.validators.CustomerValidator;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;

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
            filterParams.put("address", request.getParameter("address"));
            filterParams.put("balance", request.getParameter("balance"));

            List<CustomerDTO> customers = RegexParser.filterCustomers(model.getCustomers(), filterParams);

            if (request.getParameter("sort") != null)
            {
                switch (request.getParameter("sort"))
                {
                    case "idDescending":
                        customers.sort(new CustomerIdComparator().reversed());
                        break;
                    case "firstNameAscending":
                        customers.sort(new CustomerFirstNameComparator());
                        break;
                    case "firstNameDescending":
                        customers.sort(new CustomerFirstNameComparator().reversed());
                        break;
                    case "lastNameAscending":
                        customers.sort(new CustomerLastNameComparator());
                        break;
                    case "lastNameDescending":
                        customers.sort(new CustomerLastNameComparator().reversed());
                        break;
                    case "loginAscending":
                        customers.sort(new CustomerLoginComparator());
                        break;
                    case "loginDescending":
                        customers.sort(new CustomerLoginComparator().reversed());
                        break;
                    case "addressAscending":
                        customers.sort(new CustomerAddressComparator());
                        break;
                    case "addressDescending":
                        customers.sort(new CustomerAddressComparator().reversed());
                        break;
                    case "balanceAscending":
                        customers.sort(new CustomerBalanceComparator());
                        break;
                    case "balanceDescending":
                        customers.sort(new CustomerBalanceComparator().reversed());
                        break;
                    default:
                        customers.sort(new CustomerIdComparator());
                        break;
                }
            }
            else
            {
                customers.sort(new CustomerIdComparator());
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
            customers.sort(new CustomerIdComparator());
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
            customers.sort(new CustomerIdComparator());
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
                    .forward(request, response);
        }

        if ("click".equals(request.getParameter("create")))
        {
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
            customerDTO.setAddress(request.getParameter("address"));
            if (!request.getParameter("balance").equals(""))
            {
                customerDTO.setBalance(Float.valueOf(request.getParameter("balance")));
            }

            try
            {
                CustomerValidator customerValidator = new CustomerValidator();
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
            customers.sort(new CustomerIdComparator());
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
            customers.sort(new CustomerIdComparator());
            request.setAttribute("customers", customers);
            getServletContext().getRequestDispatcher("/view/employee/tableView/Customers.jsp")
                    .forward(request, response);
        }

    }
}
