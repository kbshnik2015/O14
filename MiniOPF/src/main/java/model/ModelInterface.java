package model;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;

import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;

public interface ModelInterface
{
    Map<BigInteger, Customer> getCustomers() throws SQLException;

    Map<BigInteger, Employee> getEmployees() throws SQLException;

    Map<BigInteger, District> getDistricts() throws SQLException;

    Map<BigInteger, Specification> getSpecifications() throws SQLException;

    Map<BigInteger, Service> getServices() throws SQLException;

    Map<BigInteger, Order> getOrders() throws SQLException;

    Customer createCustomer(Customer customer) throws DataNotCreatedWarning, SQLException;

    void updateCustomer(Customer customer) throws SQLException, DataNotUpdatedWarning;

    void deleteCustomer(Customer customer) throws SQLException, DataNotFoundWarning;

    Customer getCustomer(BigInteger id) throws SQLException;

    Employee createEmployee(Employee employee) throws DataNotCreatedWarning, SQLException;

    void updateEmployee(Employee employee) throws SQLException, DataNotUpdatedWarning;

    void deleteEmployee(Employee employee) throws SQLException, DataNotFoundWarning;

    Employee getEmployee(BigInteger id) throws SQLException;

    District createDistrict(District district) throws DataNotCreatedWarning, SQLException;

    void updateDistrict(District district) throws SQLException, DataNotUpdatedWarning;

    void deleteDistrict(District district) throws SQLException, DataNotFoundWarning;

    District getDistrict(BigInteger Id) throws SQLException;

    Specification createSpecification(Specification specification) throws DataNotCreatedWarning, SQLException;

    void updateSpecification(Specification specification) throws SQLException, DataNotUpdatedWarning;

    void deleteSpecification(Specification specification) throws SQLException, DataNotFoundWarning;

    Specification getSpecification(BigInteger Id) throws SQLException;

    Service createService(Service service) throws DataNotCreatedWarning, SQLException;

    void updateService(Service service) throws SQLException, DataNotUpdatedWarning;

    void deleteService(Service service) throws SQLException, DataNotFoundWarning;

    Service getService(BigInteger Id) throws SQLException;

    Order createOrder(Order order) throws DataNotCreatedWarning, SQLException;

    void updateOrder(Order order) throws SQLException, DataNotUpdatedWarning;

    void deleteOrder(Order order) throws SQLException, DataNotFoundWarning;

    Order getOrder(BigInteger Id) throws SQLException;
}
