package model;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;

public class ModelDB implements ModelInterface
{
    private CustomerDAO customerDAO;

    private EmployeeDAO employeeDAO;

    private DistrictDAO districtDAO;

    private SpecificationDAO specificationDAO;

    private ServiceDAO serviceDAO;

    private OrderDAO orderDAO;

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

    public Map<BigInteger, Customer> getCustomers() throws SQLException
    {
        List<Customer> list = customerDAO.findAll();
        Map<BigInteger, Customer> map = new HashMap<>();
        for (Customer customer : list)
        {
            map.put(customer.getId(), customer);
        }

        return map;
    }

    public Map<BigInteger, Employee> getEmployees() throws SQLException
    {
        List<Employee> list = employeeDAO.findAll();
        Map<BigInteger, Employee> map = new HashMap<>();
        for (Employee employee : list)
        {
            map.put(employee.getId(), employee);
        }

        return map;
    }

    public Map<BigInteger, District> getDistricts() throws SQLException
    {
        List<District> list = districtDAO.findAll();
        Map<BigInteger, District> map = new HashMap<>();
        for (District district : list)
        {
            map.put(district.getId(), district);
        }

        return map;
    }

    public Map<BigInteger, Specification> getSpecifications() throws SQLException
    {
        List<Specification> list = specificationDAO.findAll();
        Map<BigInteger, Specification> map = new HashMap<>();
        for (Specification specification : list)
        {
            map.put(specification.getId(), specification);
        }

        return map;
    }

    public Map<BigInteger, Service> getServices() throws SQLException
    {
        List<Service> list = serviceDAO.findAll();
        Map<BigInteger, Service> map = new HashMap<>();
        for (Service service : list)
        {
            map.put(service.getId(), service);
        }

        return map;
    }

    public Map<BigInteger, Order> getOrders() throws SQLException
    {
        List<Order> list = orderDAO.findAll();
        Map<BigInteger, Order> map = new HashMap<>();
        for (Order order : list)
        {
            map.put(order.getId(), order);
        }

        return map;
    }

    @Override
    public Customer createCustomer(final Customer customer) throws DataNotCreatedWarning, SQLException
    {
        customerDAO.create(customer);
        return null;
    }

    @Override
    public void updateCustomer(final Customer customer) throws SQLException, DataNotUpdatedWarning
    {
        customerDAO.update(customer);
    }

    @Override
    public void deleteCustomer(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        customerDAO.delete(id);
    }

    @Override
    public Customer getCustomer(final BigInteger id) throws SQLException
    {
        return customerDAO.findById(id);
    }

    @Override
    public Employee createEmployee(final Employee employee) throws DataNotCreatedWarning, SQLException
    {
        employeeDAO.create(employee);
        return null;
    }

    @Override
    public void updateEmployee(final Employee employee) throws SQLException, DataNotUpdatedWarning
    {
        employeeDAO.update(employee);
    }

    @Override
    public void deleteEmployee(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        employeeDAO.delete(id);
    }

    @Override
    public Employee getEmployee(final BigInteger id) throws SQLException
    {
        return employeeDAO.findById(id);
    }

    @Override
    public District createDistrict(final District district) throws DataNotCreatedWarning, SQLException
    {
        districtDAO.create(district);
        return null;
    }

    @Override
    public void updateDistrict(final District district) throws SQLException, DataNotUpdatedWarning
    {
        districtDAO.update(district);
    }

    @Override
    public void deleteDistrict(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        districtDAO.delete(id);
    }

    @Override
    public District getDistrict(final BigInteger Id) throws SQLException
    {
        return districtDAO.findById(Id);
    }

    @Override
    public Specification createSpecification(final Specification specification)
            throws DataNotCreatedWarning, SQLException
    {
        specificationDAO.create(specification);
        return null;
    }

    @Override
    public void updateSpecification(final Specification specification) throws SQLException, DataNotUpdatedWarning
    {
        specificationDAO.update(specification);
    }

    @Override
    public void deleteSpecification(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        specificationDAO.delete(id);
    }

    @Override
    public Specification getSpecification(final BigInteger Id) throws SQLException
    {
        return specificationDAO.findById(Id);
    }

    @Override
    public Service createService(final Service service) throws DataNotCreatedWarning, SQLException
    {
        serviceDAO.create(service);
        return null;
    }

    @Override
    public void updateService(final Service service) throws SQLException, DataNotUpdatedWarning
    {
        serviceDAO.update(service);
    }

    @Override
    public void deleteService(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        serviceDAO.delete(id);
    }

    @Override
    public Service getService(final BigInteger Id) throws SQLException
    {
        return serviceDAO.findById(Id);
    }

    @Override
    public Order createOrder(final Order order) throws DataNotCreatedWarning, SQLException
    {
        orderDAO.create(order);
        return null;
    }

    @Override
    public void updateOrder(final Order order) throws SQLException, DataNotUpdatedWarning
    {
        orderDAO.update(order);
    }

    @Override
    public void deleteOrder(final BigInteger id) throws SQLException, DataNotFoundWarning
    {
        orderDAO.delete(id);
    }

    @Override
    public Order getOrder(final BigInteger Id) throws SQLException
    {
        return orderDAO.findById(Id);
    }
}
