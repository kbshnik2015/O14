package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;
import model.enums.EmployeeStatus;
import model.enums.OrderAim;
import model.enums.OrderStatus;
import model.enums.ServiceStatus;

@Data
public class Model
{

    @Setter
    private static Model instance; //Singleton

    @Setter
    @Getter
    private BigInteger nextId = BigInteger.valueOf(0);

    @Setter
    @Getter
    private Map<BigInteger, Customer> customers;

    @Setter
    @Getter
    private Map<BigInteger, Employee> employees;

    @Setter
    @Getter
    private Map<BigInteger, Specification> specifications;

    @Setter
    @Getter
    private Map<BigInteger, Order> orders;

    @Setter
    @Getter
    private Map<BigInteger, District> districts;

    @Setter
    @Getter
    private Map<BigInteger, Service> services;

    private Model()
    {
        this.orders = new HashMap<>();
        this.services = new HashMap<>();
        this.specifications = new HashMap<>();
        this.districts = new HashMap<>();
        this.employees = new HashMap<>();
        this.customers = new HashMap<>();
    }

    public static synchronized Model getInstance() throws IOException
    {
        if (instance == null)
        {
            loadFromFile();
        }
        return instance;
    }

    public void clear()
    {
        nextId = BigInteger.valueOf(0);
        customers.clear();
        employees.clear();
        services.clear();
        specifications.clear();
        orders.clear();
        districts.clear();
    }

    public BigInteger generateNextId()
    {
        nextId = nextId.add(BigInteger.valueOf(1));
        return nextId;
    }

    public Customer createCustomer(Customer customer)
    {
        if (customers == null)
        {
            customers = new HashMap<>();
        }
        customers.put(customer.getId(), customer);
        return customer;
    }

    public void updateCustomer(Customer customer)
    {
        customers.put(customer.getId(), customer);
    }

    public void updateCustomer(BigInteger id,String firstName, String lastName, String login, String password, String address,
            float balance)
    {
        Customer customer = getCustomer(id);
        if (customer != null)
        {
            if (firstName != null)
            {
                customer.setFirstName(firstName);
            }
            if (lastName != null)
            {
                customer.setLastName(lastName);
            }
            if (password != null)
            {
                customer.setPassword(password);
            }
            if (address != null)
            {
                customer.setAddress(address);
            }
            if (balance >= 0)
            {
                customer.setBalance(balance);
            }
            if (login != null)
            {
                customer.setLogin(login);
            }
        }
    }

    public void deleteCustomer(Customer customer)
    {
        customers.remove(customer.getId());
    }

    public Customer getCustomer(BigInteger id)
    {
        return customers.get(id);
    }

    public Employee createEmployee(Employee employee)
    {
        if (employees == null)
        {
            employees = new HashMap<>();
        }
        employees.put(employee.getId(), employee);
        return employee;
    }

    public void updateEmployee(Employee employee)
    {
        employees.put(employee.getId(), employee);
    }

    public void updateEmployee(BigInteger id,String firstName, String lastName, String login, String password,
            EmployeeStatus employeeStatus)
    {
        Employee employee = getEmployee(id);
        if (employee != null)
        {
            if (firstName != null)
            {
                employee.setFirstName(firstName);
            }
            if (lastName != null)
            {
                employee.setLastName(lastName);
            }
            if (password != null)
            {
                employee.setPassword(password);
            }
            if (employeeStatus != null)
            {
                employee.setEmployeeStatus(employeeStatus);
            }
            if (login != null)
            {
                employee.setLogin(login);
            }
        }
    }

    public void deleteEmployee(Employee employee)
    {
        employees.remove(employee.getId());
    }

    public Employee getEmployee(BigInteger id)
    {
        return employees.get(id);
    }

    public District createDistrict(District district)
    {
        if (districts == null)
        {
            districts = new HashMap<>();
        }
        district.setId(generateNextId());
        districts.put(district.getId(), district);
        return district;
    }

    public District createDistrict(String name, BigInteger parentId)
    {
        District district = new District(name, parentId);
        return createDistrict(district);
    }

    public void updateDistrict(District district)
    {
        districts.put(district.getId(), district);
    }

    public void updateDistrict(BigInteger id, String name, BigInteger parentId)
    {
        District district = getDistrict(id);
        if (district != null)
        {
            if (name != null)
            {
                district.setName(name);
            }
            if (parentId != null)
            {
                district.setParentId(parentId);
            }
        }
    }

    public void deleteDistrict(District district)
    {
        districts.remove(district.getId());
    }

    public District getDistrict(BigInteger Id)
    {
        return districts.get(Id);
    }

    public Specification createSpecification(Specification specification)
    {
        if (specifications == null)
        {
            specifications = new HashMap<>();
        }
        specification.setId(generateNextId());
        specifications.put(specification.getId(), specification);
        return specification;
    }

    public Specification createSpecification(String name, float price, String description, boolean isAddressDepended,
            ArrayList<BigInteger> districtsIds)
    {
        Specification specification = new Specification(name, price, description, isAddressDepended, districtsIds);

        return createSpecification(specification);
    }


    public void updateSpecification(Specification specification)
    {
        specifications.put(specification.getId(), specification);
    }

    public void updateSpecification(BigInteger id, String name, float price, String description,
            boolean isAddressDepended,
            ArrayList<BigInteger> districtsIds)
    {
        Specification specification = getSpecification(id);
        if (specification != null)
        {
            if (name != null)
            {
                specification.setName(name);
            }
            if (price >= 0)
            {
                specification.setPrice(price);
            }
            if (description != null)
            {
                specification.setDescription(description);
            }
            specification.setAddressDepended(isAddressDepended);
            if (isAddressDepended && districtsIds != null)
            {
                specification.setDistrictsIds(districtsIds);
            }
            else
            {
                specification.setDistrictsIds(new ArrayList<>());
            }
        }
    }

    public void deleteSpecification(Specification specification)
    {
        specifications.remove(specification.getId());
    }

    public Specification getSpecification(BigInteger Id)
    {
        return specifications.get(Id);
    }

    public Service createService(Service service)
    {
        if (services == null)
        {
            services = new HashMap<>();
        }
        service.setId(generateNextId());
        services.put(service.getId(), service);
        return service;
    }

    public void updateService(Service service)
    {
        services.put(service.getId(), service);
    }

    public void updateService(BigInteger id, Date payDay, BigInteger specificationId, ServiceStatus servStatus)
    {
        Service service = getService(id);
        if (service != null)
        {
            if (payDay != null)
            {
                service.setPayDay(payDay);
            }
            if (specificationId != null && getSpecifications().containsKey(specificationId))
            {
                service.setSpecificationId(specificationId);
            }
            if (servStatus != null)
            {
                service.setServiceStatus(servStatus);
            }
        }
    }

    public void deleteService(Service service)
    {
        services.remove(service.getId());
    }

    public void deleteService(BigInteger id)
    {
        services.remove(id);
    }

    public Service getService(BigInteger Id)
    {
        return services.get(Id);
    }

    public Order createOrder(Order order)
    {
        if (orders == null)
        {
            orders = new HashMap<>();
        }
        order.setId(generateNextId());
        orders.put(order.getId(), order);
        return order;
    }

    public void updateOrder(Order order)
    {
        orders.put(order.getId(), order);
    }

    public void updateOrder(BigInteger orderId, BigInteger customerId, BigInteger employeeId, OrderAim orderAim,
            OrderStatus orderStatus, String address)
    {
        Order order = getOrder(orderId);
        if (order != null)
        {
            if (customerId != null && getCustomers().containsKey(customerId))
            {
                order.setCustomerId(customerId);
            }
            if (employeeId != null && getEmployees().containsKey(employeeId))
            {
                order.setEmployeeId(employeeId);
            }
            if (orderAim != null)
            {
                order.setOrderAim(orderAim);
            }
            if (orderStatus != null)
            {
                order.setOrderStatus(orderStatus);
            }
            if (address != null)
            {
                order.setAddress(address);
            }
        }
    }

    public void deleteOrder(Order order)
    {
        orders.remove(order.getId());
    }

    public Order getOrder(BigInteger Id)
    {
        return orders.get(Id);
    }


    public void saveToFile() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter("test.json", false))
        {
            mapper.writeValue(writer, instance);
        }
    }

    public void saveToFile(String filepath) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter(filepath, false))
        {
            mapper.writeValue(writer, instance);
        }
    }

    public static void loadFromFile() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader("test.json"))
        {
            instance = mapper.readValue(reader, Model.class);
        }
    }

    public static void loadFromFile(String filepath) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(filepath))
        {
            instance = mapper.readValue(reader, Model.class);
        }
    }

}
