package controller;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;

public class RegexParser
{
    private static boolean matchesBigInteger(BigInteger value, String inputRegex)
    {
        if (inputRegex == null)
        {
            return true;
        }
        if ("-".equals(inputRegex))
        {
            return value == null;
        }
        if (value == null)
        {
            return false;
        }

        String tmp = inputRegex.replaceAll("\\*", "(\\\\d*)");
        String regex = tmp.replaceAll("\\.", "(\\\\d)");

        return value.toString().matches(regex);
    }

    private static boolean matchesFloat(float value, String inputRegex)
    {
        if (inputRegex == null)
        {
            return true;
        }

        String tmp = inputRegex.replaceAll("\\*", "(\\\\d*)");
        String regex = tmp.replaceAll("\\.", "(\\\\d)");
        tmp = regex.replaceAll(",", "(\\\\.)");

        return String.valueOf(value).matches(tmp);
    }

    private static boolean matchesString(String value, String inputRegex)
    {
        if (inputRegex == null)
        {
            return true;
        }
        if ("-".equals(inputRegex))
        {
            return value == null;
        }
        if (value == null)
        {
            return false;
        }

        String tmp = inputRegex.replaceAll("\\.", "(\\\\.)");
        String regex = tmp.replaceAll("\\*", "(\\\\.*)");

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);

        return matcher.find();
    }

    private static boolean matchesBoolean(boolean value, String inputRegex)
    {
        if (inputRegex == null)
        {
            return true;
        }

        return value == Boolean.valueOf(inputRegex);
    }

    private static boolean matchesEnum(String value, String inputRegex)
    {
        if (inputRegex == null)
        {
            return true;
        }

        return value.equals(inputRegex);
    }

    private static boolean matchesListId(List<BigInteger> values, String inputRegex)
    {
        if (inputRegex == null)
        {
            return true;
        }
        if ("-".equals(inputRegex))
        {
            return values == null;
        }
        if (values == null)
        {
            return false;
        }

        inputRegex = inputRegex.trim();
        Pattern pattern = Pattern.compile("\\s*(,)\\s*");
        String[] idsRegex = pattern.split(inputRegex);

        boolean isMatches = false;

        for (String id : idsRegex)
        {
            if (values.contains(BigInteger.valueOf(Long.valueOf(id))))
            {
                isMatches = true;
            }
            else
            {
                return false;
            }
        }

        return isMatches;
    }

    private static boolean matchesDate(Date value, String inputRegex)
    {
        if (inputRegex == null)
        {
            return true;
        }
        if ("-".equals(inputRegex))
        {
            return value == null;
        }
        if (value == null)
        {
            return false;
        }

        inputRegex = inputRegex.trim();
        Pattern pattern = Pattern.compile("\\s*(-)\\s*");
        String[] regexps = pattern.split(inputRegex);
        String[] dateParts = pattern.split(value.toString());

        return matchesBigInteger(BigInteger.valueOf(Long.valueOf(dateParts[0])), regexps[0]) &&
                matchesBigInteger(BigInteger.valueOf(Long.valueOf(dateParts[1])), regexps[1]) &&
                matchesBigInteger(BigInteger.valueOf(Long.valueOf(dateParts[2])), regexps[2]);
    }

    public static void main(String[] args)
    {
        String regex = "2.2*-3-*";

        System.out.println(matchesDate(null, "-"));
    }

    public static Map<BigInteger, CustomerDTO> filterCustomers(Map<BigInteger, CustomerDTO> customers,
            Map<String, String> regexps)
    {
        Map<BigInteger, CustomerDTO> filteredCustomers = new HashMap<>();

        for (CustomerDTO customer : customers.values())
        {
            if (matchesBigInteger(customer.getId(), regexps.get("id")) &&
                    matchesString(customer.getFirstName(), regexps.get("firstName")) &&
                    matchesString(customer.getLastName(), regexps.get("lastName")) &&
                    matchesString(customer.getLogin(), regexps.get("login")) &&
                    matchesString(customer.getAddress(), regexps.get("address")) &&
                    matchesFloat(customer.getBalance(), regexps.get("balance")))
            {
                filteredCustomers.put(customer.getId(), customer);
            }
        }

        return filteredCustomers;
    }

    public static Map<BigInteger, EmployeeDTO> filterEmployees(Map<BigInteger, EmployeeDTO> employees,
            Map<String, String> regexps)
    {
        Map<BigInteger, EmployeeDTO> filteredEmployees = new HashMap<>();

        for (EmployeeDTO employee : employees.values())
        {
            if (matchesBigInteger(employee.getId(), regexps.get("id")) &&
                    matchesString(employee.getFirstName(), regexps.get("firstName")) &&
                    matchesString(employee.getLastName(), regexps.get("lastName")) &&
                    matchesString(employee.getLogin(), regexps.get("login")) &&
                    matchesEnum(employee.getEmployeeStatus().toString(), regexps.get("employeeStatus")) &&
                    matchesBoolean(employee.isWaitingForOrders(), regexps.get("isWaitingForOrders")))
            {
                filteredEmployees.put(employee.getId(), employee);
            }
        }

        return filteredEmployees;
    }

    public static Map<BigInteger, DistrictDTO> filterDistricts(Map<BigInteger, DistrictDTO> districts,
            Map<String, String> regexps)
    {
        Map<BigInteger, DistrictDTO> filteredDistricts = new HashMap<>();

        for (DistrictDTO district : districts.values())
        {
            if (matchesBigInteger(district.getId(), regexps.get("id")) &&
                    matchesString(district.getName(), regexps.get("name")) &&
                    matchesBigInteger(district.getParentId(), regexps.get("parentId")))
            {
                filteredDistricts.put(district.getId(), district);
            }
        }

        return filteredDistricts;
    }

    public static Map<BigInteger, SpecificationDTO> filterSpecifications(
            Map<BigInteger, SpecificationDTO> specifications, Map<String, String> regexps)
    {
        Map<BigInteger, SpecificationDTO> filteredSpecifications = new HashMap<>();

        for (SpecificationDTO specification : specifications.values())
        {
            if (matchesBigInteger(specification.getId(), regexps.get("id")) &&
                    matchesString(specification.getName(), regexps.get("name")) &&
                    matchesFloat(specification.getPrice(), regexps.get("price")) &&
                    matchesString(specification.getDescription(), regexps.get("description")) &&
                    matchesBoolean(specification.isAddressDepended(), regexps.get("isAddressDepended")) &&
                    matchesListId(specification.getDistrictsIds(), regexps.get("districtsIds")))
            {
                filteredSpecifications.put(specification.getId(), specification);
            }
        }

        return filteredSpecifications;
    }

    public static Map<BigInteger, ServiceDTO> filterServices(
            Map<BigInteger, ServiceDTO> services, Map<String, String> regexps)
    {
        Map<BigInteger, ServiceDTO> filteredServices = new HashMap<>();

        for (ServiceDTO service : services.values())
        {
            if (matchesBigInteger(service.getId(), regexps.get("id")) &&
                    matchesDate(service.getPayDay(), regexps.get("payDay")) &&
                    matchesBigInteger(service.getSpecificationId(), regexps.get("specificationId")) &&
                    matchesEnum(service.getServiceStatus().toString(), regexps.get("serviceStatus")) &&
                    matchesBigInteger(service.getCustomerId(), regexps.get("customerId")))
            {
                filteredServices.put(service.getId(), service);
            }
        }

        return filteredServices;
    }

    public static Map<BigInteger, OrderDTO> filterOrders(
            Map<BigInteger, OrderDTO> orders, Map<String, String> regexps)
    {
        Map<BigInteger, OrderDTO> filteredOrders = new HashMap<>();

        for (OrderDTO order : orders.values())
        {
            if (matchesBigInteger(order.getId(), regexps.get("id")) &&
                    matchesBigInteger(order.getCustomerId(), regexps.get("customerId")) &&
                    matchesBigInteger(order.getEmployeeId(), regexps.get("employeeId")) &&
                    matchesBigInteger(order.getSpecId(), regexps.get("specId")) &&
                    matchesBigInteger(order.getServiceId(), regexps.get("serviceId")) &&
                    matchesEnum(order.getOrderAim().toString(), regexps.get("orderAim")) &&
                    matchesEnum(order.getOrderStatus().toString(), regexps.get("orderStatus")) &&
                    matchesString(order.getAddress(), regexps.get("address")))
            {
                filteredOrders.put(order.getId(), order);
            }
        }

        return filteredOrders;
    }

}
