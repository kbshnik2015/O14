package model;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import model.database.ConnectionPool;
import model.database.dao.CustomerDAO;
import model.database.dao.DistrictDAO;
import model.database.dao.EmployeeDAO;
import model.database.dao.OrderDAO;
import model.database.dao.ServiceDAO;
import model.database.dao.SpecificationDAO;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
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

public class ModelDB implements Model
{
    private CustomerDAO customerDAO;

    private EmployeeDAO employeeDAO;

    private DistrictDAO districtDAO;

    private SpecificationDAO specificationDAO;

    private ServiceDAO serviceDAO;

    private OrderDAO orderDAO;

    private static transient CustomerTransformer customerTransformer = new CustomerTransformer();

    private static transient EmployeeTransformer employeeTransformer = new EmployeeTransformer();

    private static transient DistrictTransformer districtTransformer = new DistrictTransformer();

    private static transient OrderTransformer orderTransformer = new OrderTransformer();

    private static transient ServiceTransformer serviceTransformer = new ServiceTransformer();

    private static transient SpecificationTransformer specificationTransformer = new SpecificationTransformer();

    public ModelDB() throws SQLException
    {
        final Connection connection = ConnectionPool.getConnection();
        customerDAO = new CustomerDAO(connection);
        employeeDAO = new EmployeeDAO(connection);
        districtDAO = new DistrictDAO(connection);
        specificationDAO = new SpecificationDAO(connection);
        serviceDAO = new ServiceDAO(connection);
        orderDAO = new OrderDAO(connection);
    }

    @Override
    public BigInteger generateNextId()
    {
        try
        {
            return customerDAO.getNextId();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<BigInteger, CustomerDTO> getCustomers()
    {
        List<Customer> list = new ArrayList<>();
        try
        {
            list = customerDAO.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Map<BigInteger, CustomerDTO> map = new HashMap<>();
        for (Customer customer : list)
        {
            map.put(customer.getId(), (CustomerDTO) customerTransformer.toDto(customer));
        }

        return map;
    }

    @Override
    public Map<BigInteger, EmployeeDTO> getEmployees()
    {
        List<Employee> list = new ArrayList<>();
        try
        {
            list = employeeDAO.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Map<BigInteger, EmployeeDTO> map = new HashMap<>();
        for (Employee employee : list)
        {
            map.put(employee.getId(), (EmployeeDTO) employeeTransformer.toDto(employee));
        }

        return map;
    }

    @Override
    public Map<BigInteger, DistrictDTO> getDistricts()
    {
        List<District> list = new ArrayList<>();
        try
        {
            list = districtDAO.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Map<BigInteger, DistrictDTO> map = new HashMap<>();
        for (District district : list)
        {
            map.put(district.getId(), (DistrictDTO) districtTransformer.toDto(district));
        }

        return map;
    }

    @Override
    public Map<BigInteger, SpecificationDTO> getSpecifications()
    {
        List<Specification> list = new ArrayList<>();
        try
        {
            list = specificationDAO.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Map<BigInteger, SpecificationDTO> map = new HashMap<>();
        for (Specification specification : list)
        {
            map.put(specification.getId(), (SpecificationDTO) specificationTransformer.toDto(specification));
        }

        return map;
    }

    @Override
    public Map<BigInteger, ServiceDTO> getServices()
    {
        List<Service> list = new ArrayList<>();
        try
        {
            list = serviceDAO.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Map<BigInteger, ServiceDTO> map = new HashMap<>();
        for (Service service : list)
        {
            map.put(service.getId(), (ServiceDTO) serviceTransformer.toDto(service));
        }

        return map;
    }

    @Override
    public Map<BigInteger, OrderDTO> getOrders()
    {
        List<Order> list = new ArrayList<>();
        try
        {
            list = orderDAO.findAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Map<BigInteger, OrderDTO> map = new HashMap<>();
        for (Order order : list)
        {
            map.put(order.getId(), (OrderDTO) orderTransformer.toDto(order));
        }

        return map;
    }

    @Override
    public CustomerDTO createCustomer(final CustomerDTO customerDTO)
            throws DataNotCreatedWarning, IllegalLoginOrPasswordException
    {
        checkLogin(customerDTO.getLogin());
        checkPassword(customerDTO.getPassword());

        BigInteger id = generateNextId();
        customerDTO.setId(id);
        try
        {
            customerDAO.create((Customer) customerTransformer.toEntity(customerDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            return (CustomerDTO) customerTransformer.toDto(customerDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateCustomer(final CustomerDTO customerDTO)
            throws DataNotUpdatedWarning, UserNotFoundException
    {
        checkCustomerExists(customerDTO.getId());

        try
        {
            customerDAO.update((Customer) customerTransformer.toEntity(customerDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(final BigInteger id) throws DataNotFoundWarning, UserNotFoundException
    {
        checkCustomerExists(id);

        try
        {
            customerDAO.delete(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public CustomerDTO getCustomer(final BigInteger id) throws UserNotFoundException
    {
        checkCustomerExists(id);

        try
        {
            return (CustomerDTO) customerTransformer.toDto(customerDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public EmployeeDTO createEmployee(final EmployeeDTO employeeDTO)
            throws DataNotCreatedWarning, IllegalLoginOrPasswordException
    {
        checkLogin(employeeDTO.getLogin());
        checkPassword(employeeDTO.getPassword());

        BigInteger id = generateNextId();
        employeeDTO.setId(id);
        try
        {
            employeeDAO.create((Employee) employeeTransformer.toEntity(employeeDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            return (EmployeeDTO) employeeTransformer.toDto(employeeDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateEmployee(final EmployeeDTO employeeDTO)
            throws DataNotUpdatedWarning, UserNotFoundException
    {
        checkEmployeeExists(employeeDTO.getId());

        try
        {
            employeeDAO.update((Employee) employeeTransformer.toEntity(employeeDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(final BigInteger id) throws DataNotFoundWarning, UserNotFoundException
    {
        checkEmployeeExists(id);

        try
        {
            employeeDAO.delete(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public EmployeeDTO getEmployee(final BigInteger id) throws UserNotFoundException
    {
        checkEmployeeExists(id);

        try
        {
            return (EmployeeDTO) employeeTransformer.toDto(employeeDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public DistrictDTO createDistrict(final DistrictDTO districtDTO)
            throws DataNotCreatedWarning, ObjectNotFoundException
    {
        if (districtDTO.getParentId() != null)
        {
            checkDistrictExists(districtDTO.getParentId());
        }

        BigInteger id = generateNextId();
        districtDTO.setId(id);
        try
        {
            districtDAO.create((District) districtTransformer.toEntity(districtDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            return (DistrictDTO) districtTransformer.toDto(districtDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateDistrict(final DistrictDTO districtDTO)
            throws DataNotUpdatedWarning, ObjectNotFoundException
    {
        checkDistrictExists(districtDTO.getId());

        try
        {
            districtDAO.update((District) districtTransformer.toEntity(districtDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDistrict(final BigInteger id) throws DataNotFoundWarning, ObjectNotFoundException
    {
        checkDistrictExists(id);

        try
        {
            districtDAO.delete(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public DistrictDTO getDistrict(final BigInteger id) throws ObjectNotFoundException
    {
        checkDistrictExists(id);

        try
        {
            return (DistrictDTO) districtTransformer.toDto(districtDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SpecificationDTO createSpecification(final SpecificationDTO specificationDTO)
            throws DataNotCreatedWarning
    {
        BigInteger id = generateNextId();
        specificationDTO.setId(id);
        try
        {
            specificationDAO.create((Specification) specificationTransformer.toEntity(specificationDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            return (SpecificationDTO) specificationTransformer.toDto(specificationDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateSpecification(final SpecificationDTO specificationDTO)
            throws DataNotUpdatedWarning, ObjectNotFoundException
    {
        checkSpecificationExists(specificationDTO.getId());

        try
        {
            specificationDAO.update((Specification) specificationTransformer.toEntity(specificationDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSpecification(final BigInteger id)
            throws DataNotFoundWarning, ObjectNotFoundException
    {
        checkSpecificationExists(id);

        try
        {
            specificationDAO.delete(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public SpecificationDTO getSpecification(final BigInteger id) throws ObjectNotFoundException
    {
        checkSpecificationExists(id);

        try
        {
            return (SpecificationDTO) specificationTransformer.toDto(specificationDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ServiceDTO createService(final ServiceDTO serviceDTO) throws DataNotCreatedWarning
    {
        BigInteger id = generateNextId();
        serviceDTO.setId(id);
        try
        {
            serviceDAO.create((Service) serviceTransformer.toEntity(serviceDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            return (ServiceDTO) serviceTransformer.toDto(serviceDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateService(final ServiceDTO serviceDTO)
            throws DataNotUpdatedWarning, ObjectNotFoundException
    {
        checkServiceExists(serviceDTO.getId());

        try
        {
            serviceDAO.update((Service) serviceTransformer.toEntity(serviceDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteService(final BigInteger id) throws DataNotFoundWarning, ObjectNotFoundException
    {
        checkServiceExists(id);

        try
        {
            serviceDAO.delete(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ServiceDTO getService(final BigInteger id) throws ObjectNotFoundException
    {
        checkServiceExists(id);

        try
        {
            return (ServiceDTO) serviceTransformer.toDto(serviceDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public OrderDTO createOrder(final OrderDTO orderDTO) throws DataNotCreatedWarning
    {
        BigInteger id = generateNextId();
        orderDTO.setId(id);
        try
        {
            orderDAO.create((Order) orderTransformer.toEntity(orderDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            return (OrderDTO) orderTransformer.toDto(orderDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateOrder(final OrderDTO orderDTO) throws DataNotUpdatedWarning
    {
        try
        {
            orderDAO.update((Order) orderTransformer.toEntity(orderDTO));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(final BigInteger id) throws DataNotFoundWarning, ObjectNotFoundException
    {
        checkOrderExists(id);

        try
        {
            orderDAO.delete(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public OrderDTO getOrder(final BigInteger id) throws ObjectNotFoundException
    {
        checkOrderExists(id);

        try
        {
            return (OrderDTO) orderTransformer.toDto(orderDAO.findById(id));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void checkLogin(String login) throws IllegalLoginOrPasswordException
    {
        if (login == null)
        {
            throw new IllegalLoginOrPasswordException("Login can't be nullable!");
        }

        for (CustomerDTO customer : getCustomers().values())
        {
            if (customer.getLogin().equals(login))
            {
                throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
            }
        }

        for (EmployeeDTO employee : getEmployees().values())
        {
            if (employee.getLogin().equals(login))
            {
                throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
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
        if (!getCustomers().containsKey(id))
        {
            throw new UserNotFoundException("Customer (id: " + id + ") doesn't exist!");
        }
    }

    @Override
    public void checkEmployeeExists(final BigInteger id) throws UserNotFoundException
    {
        if (!getEmployees().containsKey(id))
        {
            throw new UserNotFoundException("Employee (id: " + id + ") doesn't exist!");
        }
    }

    @Override
    public void checkDistrictExists(BigInteger id) throws ObjectNotFoundException
    {
        if (!getDistricts().containsKey(id))
        {
            throw new ObjectNotFoundException("District (id: " + id + ") doesn't exist");
        }
    }

    @Override
    public void checkSpecificationExists(final BigInteger id) throws ObjectNotFoundException
    {
        if (!getSpecifications().containsKey(id))
        {
            throw new ObjectNotFoundException("Specification (id: " + id + ") doesn't exist");
        }
    }

    @Override
    public void checkServiceExists(final BigInteger id) throws ObjectNotFoundException
    {
        if (!getServices().containsKey(id))
        {
            throw new ObjectNotFoundException("Service (id: " + id + ") doesn't exist");
        }
    }

    @Override
    public void checkOrderExists(final BigInteger id) throws ObjectNotFoundException
    {
        if (!getOrders().containsKey(id))
        {
            throw new ObjectNotFoundException("Order (id: " + id + ") doesn't exist");
        }
    }
}
