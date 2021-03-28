package model;

import java.math.BigInteger;
import java.util.Map;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;

public interface Model
{
    BigInteger generateNextId();

    Map<BigInteger, CustomerDTO> getCustomers();

    Map<BigInteger, EmployeeDTO> getEmployees();

    Map<BigInteger, DistrictDTO> getDistricts();

    Map<BigInteger, SpecificationDTO> getSpecifications();

    Map<BigInteger, ServiceDTO> getServices();

    Map<BigInteger, OrderDTO> getOrders();

    CustomerDTO createCustomer(CustomerDTO customer)
            throws DataNotCreatedWarning, IllegalLoginOrPasswordException;

    void updateCustomer(CustomerDTO customer)
            throws DataNotUpdatedWarning, UserNotFoundException;

    void deleteCustomer(BigInteger id) throws DataNotFoundWarning, UserNotFoundException;

    CustomerDTO getCustomer(BigInteger id) throws UserNotFoundException;

    EmployeeDTO createEmployee(EmployeeDTO employee)
            throws DataNotCreatedWarning, IllegalLoginOrPasswordException;

    void updateEmployee(EmployeeDTO employee)
            throws DataNotUpdatedWarning, UserNotFoundException;

    void deleteEmployee(BigInteger id) throws DataNotFoundWarning, UserNotFoundException;

    EmployeeDTO getEmployee(BigInteger id) throws UserNotFoundException;

    DistrictDTO createDistrict(DistrictDTO district)
            throws DataNotCreatedWarning, ObjectNotFoundException;

    void updateDistrict(DistrictDTO district)
            throws DataNotUpdatedWarning, ObjectNotFoundException;

    void deleteDistrict(BigInteger id) throws DataNotFoundWarning, ObjectNotFoundException;

    DistrictDTO getDistrict(BigInteger id) throws ObjectNotFoundException;

    SpecificationDTO createSpecification(SpecificationDTO specification)
            throws DataNotCreatedWarning;

    void updateSpecification(SpecificationDTO specification)
            throws DataNotUpdatedWarning, ObjectNotFoundException;

    void deleteSpecification(BigInteger id)
            throws DataNotFoundWarning, ObjectNotFoundException;

    SpecificationDTO getSpecification(BigInteger id) throws ObjectNotFoundException;

    ServiceDTO createService(ServiceDTO service) throws DataNotCreatedWarning;

    void updateService(ServiceDTO service)
            throws DataNotUpdatedWarning, ObjectNotFoundException;

    void deleteService(BigInteger id) throws DataNotFoundWarning, ObjectNotFoundException;

    ServiceDTO getService(BigInteger id) throws ObjectNotFoundException;

    OrderDTO createOrder(OrderDTO order) throws DataNotCreatedWarning;

    void updateOrder(OrderDTO order) throws DataNotUpdatedWarning;

    void deleteOrder(BigInteger id) throws DataNotFoundWarning, ObjectNotFoundException;

    OrderDTO getOrder(BigInteger id) throws ObjectNotFoundException;

    void checkLogin(String login) throws IllegalLoginOrPasswordException;

    void checkPassword(String password) throws IllegalLoginOrPasswordException;

    void checkCustomerExists(final BigInteger id) throws UserNotFoundException;

    void checkEmployeeExists(final BigInteger id) throws UserNotFoundException;

    void checkDistrictExists(BigInteger id) throws ObjectNotFoundException;

    void checkSpecificationExists(final BigInteger id) throws ObjectNotFoundException;

    void checkServiceExists(final BigInteger id) throws ObjectNotFoundException;

    void checkOrderExists(final BigInteger id) throws ObjectNotFoundException;
}
