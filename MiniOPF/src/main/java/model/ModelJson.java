package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;
import model.dto.transformers.CustomerTransformer;
import model.dto.transformers.DistrictTransformer;
import model.dto.transformers.EmployeeTransformer;
import model.dto.transformers.OrderTransformer;
import model.dto.transformers.ServiceTransformer;
import model.dto.transformers.SpecificationTransformer;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;

@SuppressWarnings({"Duplicates", "SynchronizeOnNonFinalField", "SynchronizationOnLocalVariableOrMethodParameter"})
@Data
public class ModelJson implements Model
{

    @Setter(onMethod_ = {@Synchronized})
    private static ModelJson instance; //Singleton

    @Setter(onMethod_ = {@Synchronized})
    @Getter
    private BigInteger nextId = BigInteger.valueOf(0);

    @Setter(onMethod_ = {@Synchronized})
    private Map<BigInteger, Customer> customers;

    @Setter(onMethod_ = {@Synchronized})
    private Map<BigInteger, Employee> employees;

    @Setter(onMethod_ = {@Synchronized})
    private Map<BigInteger, District> districts;

    @Setter(onMethod_ = {@Synchronized})
    private Map<BigInteger, Specification> specifications;

    @Setter(onMethod_ = {@Synchronized})
    private Map<BigInteger, Service> services;

    @Setter(onMethod_ = {@Synchronized})
    private Map<BigInteger, Order> orders;

    private static transient CustomerTransformer customerTransformer = new CustomerTransformer();

    private static transient EmployeeTransformer employeeTransformer = new EmployeeTransformer();

    private static transient DistrictTransformer districtTransformer = new DistrictTransformer();

    private static transient OrderTransformer orderTransformer = new OrderTransformer();

    private static transient ServiceTransformer serviceTransformer = new ServiceTransformer();

    private static transient SpecificationTransformer specificationTransformer = new SpecificationTransformer();

    private static transient String sourceFile = "model.json";

    private ModelJson()
    {
        this.orders = new HashMap<>();
        this.services = new HashMap<>();
        this.specifications = new HashMap<>();
        this.districts = new HashMap<>();
        this.employees = new HashMap<>();
        this.customers = new HashMap<>();
    }

    public static synchronized ModelJson getInstance()
    {
        if (instance == null)
        {
            loadFromFile();
        }
        return instance;
    }

    public synchronized void clear()
    {
        nextId = BigInteger.valueOf(0);
        customers.clear();
        employees.clear();
        services.clear();
        specifications.clear();
        orders.clear();
        districts.clear();
    }

    @Override
    public synchronized BigInteger generateNextId()
    {
        nextId = nextId.add(BigInteger.valueOf(1));

        return nextId;
    }

    @Override
    public Map<BigInteger, CustomerDTO> getCustomers()
    {
        Map<BigInteger, CustomerDTO> map = new HashMap<>();
        synchronized (customers)
        {
            for (Customer customer : customers.values())
            {
                map.put(customer.getId(), (CustomerDTO) customerTransformer.toDto(customer));
            }
        }

        return map;
    }

    @Override
    public Map<BigInteger, EmployeeDTO> getEmployees()
    {
        Map<BigInteger, EmployeeDTO> map = new HashMap<>();
        synchronized (employees)
        {
            for (Employee employee : employees.values())
            {
                map.put(employee.getId(), (EmployeeDTO) employeeTransformer.toDto(employee));
            }
        }

        return map;
    }

    @Override
    public Map<BigInteger, DistrictDTO> getDistricts()
    {
        Map<BigInteger, DistrictDTO> map = new HashMap<>();
        synchronized (districts)
        {
            for (District district : districts.values())
            {
                map.put(district.getId(), (DistrictDTO) districtTransformer.toDto(district));
            }
        }

        return map;
    }

    @Override
    public Map<BigInteger, SpecificationDTO> getSpecifications()
    {
        Map<BigInteger, SpecificationDTO> map = new HashMap<>();
        synchronized (specifications)
        {
            for (Specification specification : specifications.values())
            {
                map.put(specification.getId(), (SpecificationDTO) specificationTransformer.toDto(specification));
            }
        }

        return map;
    }

    @Override
    public Map<BigInteger, ServiceDTO> getServices()
    {
        Map<BigInteger, ServiceDTO> map = new HashMap<>();
        synchronized (services)
        {
            for (Service service : services.values())
            {
                map.put(service.getId(), (ServiceDTO) serviceTransformer.toDto(service));
            }
        }

        return map;
    }

    @Override
    public Map<BigInteger, OrderDTO> getOrders()
    {
        Map<BigInteger, OrderDTO> map = new HashMap<>();
        synchronized (orders)
        {
            for (Order order : orders.values())
            {
                map.put(order.getId(), (OrderDTO) orderTransformer.toDto(order));
            }
        }

        return map;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) throws IllegalLoginOrPasswordException
    {
        synchronized (customers)
        {
            if (customers == null)
            {
                customers = new HashMap<>();
            }

            checkLogin(customerDTO.getLogin());
            checkPassword(customerDTO.getPassword());

            Customer customer = (Customer) customerTransformer.toEntity(customerDTO);
            customer.setId(generateNextId());
            customers.put(customer.getId(), customer);

            saveToFile();
            return (CustomerDTO) customerTransformer.toDto(customer);
        }
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) throws UserNotFoundException
    {
        BigInteger id = customerDTO.getId();
        checkCustomerExists(id);

        Customer customer = customers.get(id);
        synchronized (customer)
        {
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setPassword(customerDTO.getPassword());
            customer.setAddress(customerDTO.getAddress());
            customer.setBalance(customerDTO.getBalance());
        }

        saveToFile();
    }

    @Override
    public void deleteCustomer(BigInteger id) throws UserNotFoundException
    {
        synchronized (customers)
        {
            checkCustomerExists(id);

            customers.remove(id);
        }

        saveToFile();
    }

    @Override
    public CustomerDTO getCustomer(BigInteger id) throws UserNotFoundException
    {
        checkCustomerExists(id);

        return (CustomerDTO) customerTransformer.toDto(customers.get(id));
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws IllegalLoginOrPasswordException
    {
        synchronized (employees)
        {
            if (employees == null)
            {
                employees = new HashMap<>();
            }

            checkLogin(employeeDTO.getLogin());
            checkPassword(employeeDTO.getPassword());

            Employee employee = (Employee) employeeTransformer.toEntity(employeeDTO);
            employee.setId(generateNextId());
            employees.put(employee.getId(), employee);

            saveToFile();
            return (EmployeeDTO) employeeTransformer.toDto(employee);
        }

    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) throws UserNotFoundException
    {
        BigInteger id = employeeDTO.getId();
        checkEmployeeExists(id);

        Employee employee = employees.get(id);
        synchronized (employee)
        {
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setPassword(employeeDTO.getPassword());
            employee.setEmployeeStatus(employeeDTO.getEmployeeStatus());
            employee.setWaitingForOrders(employeeDTO.isWaitingForOrders());
        }

        saveToFile();
    }

    @Override
    public void deleteEmployee(BigInteger id) throws UserNotFoundException
    {
        synchronized (employees)
        {
            checkEmployeeExists(id);
            employees.remove(id);
        }

        saveToFile();
    }

    @Override
    public EmployeeDTO getEmployee(BigInteger id) throws UserNotFoundException
    {
        checkEmployeeExists(id);

        return (EmployeeDTO) employeeTransformer.toDto(employees.get(id));
    }

    @Override
    public DistrictDTO createDistrict(DistrictDTO districtDTO) throws ObjectNotFoundException
    {
        synchronized (districts)
        {
            if (districts == null)
            {
                districts = new HashMap<>();
            }

            if (districtDTO.getParentId() != null)
            {
                checkDistrictExists(districtDTO.getParentId());
            }

            District district = (District) districtTransformer.toEntity(districtDTO);
            district.setId(generateNextId());
            districts.put(district.getId(), district);

            saveToFile();
            return (DistrictDTO) districtTransformer.toDto(district);
        }

    }

    @Override
    public void updateDistrict(DistrictDTO districtDTO) throws ObjectNotFoundException
    {
        BigInteger id = districtDTO.getId();
        checkDistrictExists(id);

        District district = districts.get(id);
        synchronized (district)
        {
            district.setName(districtDTO.getName());
            district.setParentId(districtDTO.getParentId());
        }

        saveToFile();
    }

    @Override
    public void deleteDistrict(BigInteger id) throws ObjectNotFoundException
    {
        synchronized (districts)
        {
            checkDistrictExists(id);

            districts.remove(id);
        }

        saveToFile();
    }

    @Override
    public DistrictDTO getDistrict(BigInteger id) throws ObjectNotFoundException
    {
        checkDistrictExists(id);

        return (DistrictDTO) districtTransformer.toDto(districts.get(id));
    }

    @Override
    public SpecificationDTO createSpecification(SpecificationDTO specificationDTO)
    {
        synchronized (specifications)
        {
            if (specifications == null)
            {
                specifications = new HashMap<>();
            }
            Specification specification = (Specification) specificationTransformer.toEntity(specificationDTO);
            specification.setId(generateNextId());
            specifications.put(specification.getId(), specification);

            saveToFile();
            return (SpecificationDTO) specificationTransformer.toDto(specification);
        }
    }

    @Override
    public void updateSpecification(SpecificationDTO specificationDTO)
    {
        BigInteger id = specificationDTO.getId();
        checkSpecificationExists(id);

        Specification specification = specifications.get(id);
        synchronized (specification)
        {
            specification.setName(specificationDTO.getName());
            specification.setPrice(specificationDTO.getPrice());
            specification.setDescription(specificationDTO.getDescription());
            specification.setAddressDepended(specificationDTO.isAddressDepended());
            specification.setDistrictsIds(specificationDTO.getDistrictsIds());
        }

        saveToFile();
    }

    @Override
    public void deleteSpecification(BigInteger id) throws ObjectNotFoundException
    {
        synchronized (specifications)
        {
            checkSpecificationExists(id);

            specifications.remove(id);
        }

        saveToFile();
    }

    @Override
    public SpecificationDTO getSpecification(BigInteger id) throws ObjectNotFoundException
    {
        checkSpecificationExists(id);

        return (SpecificationDTO) specificationTransformer.toDto(specifications.get(id));
    }

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO)
    {
        synchronized (services)
        {
            if (services == null)
            {
                services = new HashMap<>();
            }
            Service service = (Service) serviceTransformer.toEntity(serviceDTO);
            service.setId(generateNextId());
            services.put(service.getId(), service);

            saveToFile();
            return (ServiceDTO) serviceTransformer.toDto(service);
        }
    }

    @Override
    public void updateService(ServiceDTO serviceDTO)
    {
        BigInteger id = serviceDTO.getId();
        checkServiceExists(id);

        Service service = services.get(id);
        synchronized (service)
        {
            service.setPayDay(serviceDTO.getPayDay());
            service.setSpecificationId(serviceDTO.getSpecificationId());
            service.setServiceStatus(serviceDTO.getServiceStatus());
            service.setCustomerId(serviceDTO.getCustomerId());
        }

        saveToFile();
    }

    @Override
    public void deleteService(BigInteger id) throws ObjectNotFoundException
    {
        synchronized (services)
        {
            checkServiceExists(id);

            services.remove(id);
        }

        saveToFile();
    }

    @Override
    public ServiceDTO getService(BigInteger id) throws ObjectNotFoundException
    {
        checkServiceExists(id);

        return (ServiceDTO) serviceTransformer.toDto(services.get(id));
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO)
    {
        synchronized (orders)
        {
            if (orders == null)
            {
                orders = new HashMap<>();
            }
            Order order = (Order) orderTransformer.toEntity(orderDTO);
            order.setId(generateNextId());
            orders.put(order.getId(), order);

            saveToFile();
            return (OrderDTO) orderTransformer.toDto(order);
        }
    }

    @Override
    public void updateOrder(OrderDTO orderDTO)
    {
        BigInteger id = orderDTO.getId();
        checkOrderExists(id);

        Order order = orders.get(id);
        synchronized (order)
        {
            order.setCustomerId(orderDTO.getCustomerId());
            order.setEmployeeId(orderDTO.getEmployeeId());
            order.setSpecId(orderDTO.getSpecId());
            order.setServiceId(orderDTO.getServiceId());
            order.setOrderAim(orderDTO.getOrderAim());
            order.setOrderStatus(orderDTO.getOrderStatus());
            order.setAddress(orderDTO.getAddress());
        }

        saveToFile();
    }

    @Override
    public void deleteOrder(BigInteger id) throws ObjectNotFoundException
    {
        synchronized (orders)
        {
            checkOrderExists(id);

            orders.remove(id);
        }

        saveToFile();
    }

    @Override
    public OrderDTO getOrder(BigInteger id) throws ObjectNotFoundException
    {
        checkOrderExists(id);

        return (OrderDTO) orderTransformer.toDto(orders.get(id));
    }

    @Override
    public void checkLogin(String login) throws IllegalLoginOrPasswordException
    {
        if (login == null)
        {
            throw new IllegalLoginOrPasswordException("Login can't be nullable!");
        }

        synchronized (customers)
        {
            for (Customer customer : customers.values())
            {
                if (customer.getLogin().equals(login))
                {
                    throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
                }
            }
        }

        synchronized (employees)
        {
            for (Employee employee : employees.values())
            {
                if (employee.getLogin().equals(login))
                {
                    throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
                }
            }
        }
    }

    @Override
    public void checkPassword(String password) throws IllegalLoginOrPasswordException
    {
        if (password == null)
        {
            throw new IllegalLoginOrPasswordException("Password can't be nullable!");
        }
    }

    @Override
    public void checkCustomerExists(final BigInteger id) throws UserNotFoundException
    {
        synchronized (customers)
        {
            if (!customers.containsKey(id))
            {
                throw new UserNotFoundException("Customer (id: " + id + ") doesn't exist!");
            }
        }
    }

    @Override
    public void checkEmployeeExists(final BigInteger id) throws UserNotFoundException
    {
        synchronized (employees)
        {
            if (!employees.containsKey(id))
            {
                throw new UserNotFoundException("Employee (id: " + id + ") doesn't exist!");
            }
        }
    }

    @Override
    public void checkDistrictExists(BigInteger id) throws ObjectNotFoundException
    {
        synchronized (districts)
        {
            if (!districts.containsKey(id))
            {
                throw new ObjectNotFoundException("District (id: " + id + ") doesn't exist");
            }
        }
    }

    @Override
    public void checkSpecificationExists(final BigInteger id) throws ObjectNotFoundException
    {
        synchronized (specifications)
        {
            if (!specifications.containsKey(id))
            {
                throw new ObjectNotFoundException("Specification (id: " + id + ") doesn't exist");
            }
        }
    }

    @Override
    public void checkServiceExists(final BigInteger id) throws ObjectNotFoundException
    {
        synchronized (services)
        {
            if (!services.containsKey(id))
            {
                throw new ObjectNotFoundException("Service (id: " + id + ") doesn't exist");
            }
        }
    }

    @Override
    public void checkOrderExists(final BigInteger id) throws ObjectNotFoundException
    {
        synchronized (orders)
        {
            if (!orders.containsKey(id))
            {
                throw new ObjectNotFoundException("Order (id: " + id + ") doesn't exist");
            }
        }
    }

    public synchronized void saveToFile()
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter(sourceFile, false))
        {
            mapper.writeValue(writer, instance);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized void saveToFile(String filepath)
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileWriter writer = new FileWriter(filepath, false))
        {
            mapper.writeValue(writer, instance);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized static void loadFromFile()
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(sourceFile))
        {
            instance = mapper.readValue(reader, ModelJson.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized static void loadFromFile(String filepath)
    {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(filepath))
        {
            instance = mapper.readValue(reader, ModelJson.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        sourceFile = filepath;
    }
}
