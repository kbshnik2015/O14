package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import model.ModelFactory;
import model.ModelJson;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.AbstractUserDTO;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;
import model.enums.EmployeeStatus;
import model.enums.OrderAim;
import model.enums.OrderStatus;
import model.enums.ServiceStatus;

@SuppressWarnings("SpellCheckingInspection")
public class ControllerTest
{
    private Controller controller;

    {
        //noinspection AccessStaticViaInstance
        ModelFactory.setCurrentModel("modeljson");
        controller = new Controller();
        ModelJson.loadFromFile("test.json");
    }

    public ControllerTest() throws IOException, ObjectNotFoundException, SQLException
    {
    }

    @Before
    public void setUp()
    {
        ((ModelJson) controller.getModel()).clear();
    }

    @Test
    public void createCustomer()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO expected = new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        CustomerDTO actual = new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        actual = controller.getModel().createCustomer(actual);
        expected.setId(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createCustomer_WITH_EXISTED_LOGIN()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO customer = new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        CustomerDTO existedCustomer =
                new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        controller.getModel().createCustomer(customer);
        controller.getModel().createCustomer(existedCustomer);

    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createCustomer_LOGIN_IS_NULL()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO customerWithoutLogin =
                controller.getModel()
                        .createCustomer(new CustomerDTO(firstName1, lastName1, null, password1, address1, balance1));
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createCustomer_PASSWORD_IS_NULL()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 200;

        CustomerDTO customerWithoutPassword =
                controller.getModel()
                        .createCustomer(new CustomerDTO(firstName1, lastName1, login1, null, address1, balance1));
    }

    @Test
    public void updateCustomer()
            throws IllegalLoginOrPasswordException, UserNotFoundException, DataNotCreatedWarning,
            DataNotUpdatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO expected = new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        CustomerDTO actual = new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        actual = controller.getModel().createCustomer(actual);
        expected.setId(actual.getId());
        expected.setFirstName("newFirstName");
        actual.setFirstName("newFirstName");
        controller.getModel().updateCustomer(actual);
        actual = controller.getModel().getCustomer(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateCustomer_NOT_FOUND()
            throws IllegalLoginOrPasswordException, UserNotFoundException, DataNotUpdatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;
        BigInteger randomId = BigInteger.valueOf(666);

        CustomerDTO actual = new CustomerDTO(randomId, firstName1, lastName1, login1, password1, address1, balance1);
        controller.getModel().updateCustomer(actual);
    }

    @Test
    public void deleteCustomer()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning,
            DataNotFoundWarning, UserNotFoundException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO actual = new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        actual = controller.getModel().createCustomer(actual);
        controller.getModel().deleteCustomer(actual.getId());

        Assert.assertNull(controller.getModel().getCustomers().get(actual.getId()));
    }

    @Test
    public void getCustomerById()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning, UserNotFoundException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO expected = new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1);
        expected = controller.getModel().createCustomer(expected);
        CustomerDTO actual = controller.getModel().getCustomer(expected.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void getCustomerById_NOT_FOUND() throws UserNotFoundException
    {
        BigInteger randomId = BigInteger.valueOf(666);

        CustomerDTO actual = controller.getModel().getCustomer(randomId);
    }

    @Test
    public void createEmployee()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO expected = new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus);
        EmployeeDTO actual = new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus);
        actual = controller.getModel().createEmployee(actual);
        expected.setId(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createEmployee_WITH_EXISTED_LOGIN()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO expected = new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.getModel().createEmployee(expected);

        EmployeeDTO existedEmployee = new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.getModel().createEmployee(existedEmployee);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createEmployee_LOGIN_IS_NULL()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO employeeWithoutLogin = new EmployeeDTO(empFirstName, empLastName, null, empPassword, empStatus);
        controller.getModel().createEmployee(employeeWithoutLogin);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createEmployee_PASSWORD_IS_NULL()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO employeeWithoutLogin = new EmployeeDTO(empFirstName, empLastName, empLogin, null, empStatus);
        controller.getModel().createEmployee(employeeWithoutLogin);
    }

    @Test
    public void updateEmployee()
            throws IllegalLoginOrPasswordException, UserNotFoundException, DataNotCreatedWarning,
            DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO expected = new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus);
        EmployeeDTO actual = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));
        actual.setFirstName("newFirstName");
        controller.getModel().updateEmployee(actual);
        actual = controller.getModel().getEmployee(actual.getId());

        expected.setId(actual.getId());
        expected.setFirstName("newFirstName");

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateEmployee_NOT_FOUND()
            throws IllegalLoginOrPasswordException, UserNotFoundException, DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        BigInteger randomId = BigInteger.valueOf(666);

        EmployeeDTO actual = new EmployeeDTO(randomId, empFirstName, empLastName, empLogin, empPassword, empStatus,
                false);
        controller.getModel().updateEmployee(actual);
    }

    @Test
    public void deleteEmployee()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning,
            DataNotFoundWarning, UserNotFoundException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO actual = new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus);
        actual = controller.getModel().createEmployee(actual);
        controller.getModel().deleteEmployee(actual.getId());

        Assert.assertNull(controller.getModel().getCustomers().get(actual.getId()));
    }

    @Test
    public void getEmployeeByLogin()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning, UserNotFoundException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO actual = new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus);
        actual = controller.getModel().createEmployee(actual);
        EmployeeDTO expected = controller.getModel().getEmployee(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createDistrict() throws ObjectNotFoundException, DataNotCreatedWarning
    {
        DistrictDTO samaraDistrict = controller.getModel().createDistrict(new DistrictDTO("Samara", null));
        BigInteger districtSamaraId = samaraDistrict.getId();
        BigInteger expectedId = BigInteger.valueOf(2);

        DistrictDTO expected = new DistrictDTO("Name", districtSamaraId);
        expected.setId(expectedId);

        DistrictDTO actual = controller.getModel().createDistrict(new DistrictDTO("Name", districtSamaraId));

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void createDistrict_PARENT_NOT_EXISTS()
            throws ObjectNotFoundException, DataNotCreatedWarning
    {
        BigInteger notExistParentId = BigInteger.valueOf(666);

        controller.getModel().createDistrict(new DistrictDTO("Name", notExistParentId));
    }

    @Test
    public void createDistrict_PARENT_IS_NULL()
            throws ObjectNotFoundException, DataNotCreatedWarning
    {
        BigInteger expectedId = BigInteger.valueOf(1);

        DistrictDTO expected = new DistrictDTO("Name", null);
        expected.setId(expectedId);

        DistrictDTO actual = controller.getModel().createDistrict(new DistrictDTO("Name", null));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateDistrict()
            throws ObjectNotFoundException, DataNotCreatedWarning, DataNotUpdatedWarning
    {
        controller.getModel().createDistrict(new DistrictDTO("Samara", null));
        BigInteger districtSamaraId = BigInteger.valueOf(1);
        BigInteger expectedId = BigInteger.valueOf(2);


        DistrictDTO expected = new DistrictDTO("NewName", districtSamaraId);
        expected.setId(expectedId);

        DistrictDTO actual = controller.getModel().createDistrict(new DistrictDTO("Name", districtSamaraId));
        actual.setName("NewName");
        controller.getModel().updateDistrict(actual);
        actual = controller.getModel().getDistrict(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    //    @Test
    //    public void createSpecification() throws ObjectNotFoundException, IOException
    //    {
    //        controller.getModel().createDistrict("Samara", null);
    //        BigInteger districtSamaraId = BigInteger.valueOf(1);
    //        controller.getModel().createDistrict("Togliatti", null);
    //        BigInteger districtTogliattiId = BigInteger.valueOf(2);
    //        BigInteger expectedId = BigInteger.valueOf(3);
    //        ArrayList<BigInteger> districtIds = new ArrayList<>();
    //        districtIds.add(districtSamaraId);
    //        districtIds.add(districtTogliattiId);
    //
    //        Specification expected = new Specification("Spec name", 100, null, true, districtIds);
    //        expected.setId(expectedId);
    //        Specification actual = controller.createSpecification("Spec name", 100, null, true, districtIds);
    //
    //        Assert.assertEquals(expected, actual);
    //    }
    //
    //    @Test
    //    public void updateSpecification() throws ObjectNotFoundException, IOException
    //    {
    //        controller.getModel().createDistrict("Samara", null);
    //        BigInteger districtSamaraId = BigInteger.valueOf(1);
    //        controller.getModel().createDistrict("Togliatti", null);
    //        BigInteger districtTogliattiId = BigInteger.valueOf(2);
    //        BigInteger expectedId = BigInteger.valueOf(3);
    //        ArrayList<BigInteger> districtIds = new ArrayList<>();
    //        districtIds.add(districtSamaraId);
    //        districtIds.add(districtTogliattiId);
    //
    //        Specification expected = new Specification("Spec name", 200, null, true, districtIds);
    //        expected.setId(expectedId);
    //        Specification actual = controller.createSpecification("Spec name", 100, null, false, null);
    //        controller.updateSpecification(expectedId, "Spec name", 200, null, true, districtIds);
    //
    //        Assert.assertEquals(expected, actual);
    //    }
    //
    //    @Test(expected = ObjectNotFoundException.class)
    //    public void updateSpecification_NOT_EXISTS() throws ObjectNotFoundException, IOException
    //    {
    //        BigInteger notExistSpecId = BigInteger.valueOf(666);
    //
    //        controller.updateSpecification(notExistSpecId, "Spec name", 200, null, true, null);
    //    }

    @Test
    public void login_CUSTOMER()
            throws IllegalLoginOrPasswordException, DataNotCreatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO expected = controller.getModel()
                .createCustomer(new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1));
        AbstractUserDTO actual = controller.login(login1, password1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void login_EMPLOYEE()
            throws IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO expected = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));
        AbstractUserDTO actual = controller.login(empLogin, empPassword);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void login_WRONG_LOGIN()
            throws IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO expected1 = controller.getModel()
                .createCustomer(new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1));
        EmployeeDTO expected2 = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));

        AbstractUserDTO actual = controller.login("empLoginRandom", empPassword);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void login_EMPLOYEE_WRONG_PASSWORD()
            throws IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        CustomerDTO expected1 = controller.getModel()
                .createCustomer(new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1));
        EmployeeDTO expected2 = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));

        AbstractUserDTO actual = controller.login(empLogin, "empPasswordRandom");
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void login_CUSTOMER_WRONG_PASSWORD()
            throws IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 200;

        CustomerDTO expected1 = controller.getModel()
                .createCustomer(new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1));
        EmployeeDTO expected2 = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));
        AbstractUserDTO actual = controller.login(login1, "empPasswordRandom");
    }

    @Test
    public void startOrder()
            throws IllegalTransitionException, ObjectNotFoundException, IllegalLoginOrPasswordException,
            UserNotFoundException, IOException, DataNotCreatedWarning, SQLException, DataNotUpdatedWarning
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        OrderDTO order = controller.getModel().getOrder(orderId);

        Assert.assertEquals(OrderStatus.ENTERING, order.getOrderStatus());

        controller.startOrder(orderId);
        order = controller.getModel().getOrder(orderId);

        Assert.assertEquals(OrderStatus.IN_PROGRESS, order.getOrderStatus());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void startOrder_ORDER_NOT_EXIST()
            throws IllegalTransitionException, ObjectNotFoundException, IOException, SQLException, DataNotUpdatedWarning
    {
        BigInteger orderId = BigInteger.valueOf(666);

        controller.startOrder(orderId);
    }

    @Test(expected = IllegalTransitionException.class)
    public void startOrder_ILLEGAL_TRANSITION()
            throws IllegalTransitionException, ObjectNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning
    {
        OrderDTO order = new OrderDTO(null, null, null, null, OrderAim.NEW, OrderStatus.CANCELLED);
        controller.getModel().createOrder(order);
        BigInteger orderId = BigInteger.valueOf(1);

        controller.startOrder(orderId);
    }

    @Test
    public void suspendOrder()
            throws IllegalTransitionException, ObjectNotFoundException, IllegalLoginOrPasswordException,
            UserNotFoundException, IOException, DataNotCreatedWarning, SQLException, DataNotUpdatedWarning
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        OrderDTO order = controller.getModel().getOrder(orderId);

        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        controller.getModel().updateOrder(order);

        controller.suspendOrder(order.getId());
        order = controller.getModel().getOrder(order.getId());

        Assert.assertEquals(OrderStatus.SUSPENDED, order.getOrderStatus());
    }

    @Test
    public void restoreOrder() throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException, DataNotUpdatedWarning
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        OrderDTO order = controller.getModel().getOrder(orderId);

        order.setOrderStatus(OrderStatus.SUSPENDED);
        controller.getModel().updateOrder(order);

        controller.restoreOrder(orderId);
        order = controller.getModel().getOrder(order.getId());

        Assert.assertEquals(order.getOrderStatus(), OrderStatus.IN_PROGRESS);
    }

    @Test
    public void cancelOrder() throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException, DataNotUpdatedWarning
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        OrderDTO order = controller.getModel().getOrder(orderId);

        controller.cancelOrder(order.getId());
        order = controller.getModel().getOrder(order.getId());

        Assert.assertEquals(order.getOrderStatus(), OrderStatus.CANCELLED);
    }

    @Test(expected = IllegalTransitionException.class)
    public void cancelOrder_ILLEGAL_TRANSITION()
            throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException, DataNotUpdatedWarning
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        controller.
                createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        OrderDTO order = controller.getModel().getOrder(orderId);

        order.setOrderStatus(OrderStatus.COMPLETED);
        controller.getModel().updateOrder(order);

        controller.cancelOrder(orderId);
    }

    @Test
    public void completeOrder_NEW_ORDER()
            throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException, DataNotUpdatedWarning
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        OrderDTO order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        controller.getModel().updateOrder(order);

        controller.completeOrder(orderId);
        order = controller.getModel().getOrder(order.getId());

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.ACTIVE,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }

    @Test
    public void completeOrder_DISCONNECT_ORDER() throws Exception
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        ServiceDTO service = controller.getModel()
                .createService(new ServiceDTO(new Date(), specId, ServiceStatus.ACTIVE, customer.getId()));
        controller.createDisconnectOrder(customer.getId(), service.getId());
        BigInteger orderId = BigInteger.valueOf(4);

        OrderDTO order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        controller.getModel().updateOrder(order);

        controller.completeOrder(orderId);
        order = controller.getModel().getOrder(order.getId());

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.DISCONNECTED,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }

    @Test
    public void completeOrder_SUSPEND_ORDER() throws Exception
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        ServiceDTO service = controller.getModel()
                .createService(new ServiceDTO(new Date(), specId, ServiceStatus.ACTIVE, customer.getId()));
        controller.createSuspendOrder(customer.getId(), service.getId());
        BigInteger orderId = BigInteger.valueOf(4);

        OrderDTO order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        controller.getModel().updateOrder(order);

        controller.completeOrder(orderId);

        order = controller.getModel().getOrder(orderId);

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.SUSPENDED,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }

    @Test
    public void completeOrder_RESTORE_ORDER() throws Exception
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        ServiceDTO service = controller.getModel()
                .createService(new ServiceDTO(new Date(), specId, ServiceStatus.DISCONNECTED, customer.getId()));
        controller.createRestoreOrder(customer.getId(), service.getId());
        BigInteger orderId = BigInteger.valueOf(4);

        OrderDTO order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        controller.getModel().updateOrder(order);

        controller.completeOrder(orderId);
        order = controller.getModel().getOrder(orderId);

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.ACTIVE,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }


    @Test
    public void changeBalanceOn_UP()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, SQLException,
            DataNotUpdatedWarning, DataNotCreatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 200;

        CustomerDTO actual = controller.getModel()
                .createCustomer(new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1));
        controller.changeBalanceOn(actual.getId(), (float) 50);
        actual = controller.getModel().getCustomer(actual.getId());

        Assert.assertEquals(balance1 + 50, actual.getBalance(), 0);
    }

    @Test
    public void changeBalanceOn_DOWN()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, SQLException,
            DataNotUpdatedWarning, DataNotCreatedWarning
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 200;

        CustomerDTO actual = controller.getModel()
                .createCustomer(new CustomerDTO(firstName1, lastName1, login1, password1, address1, balance1));
        controller.changeBalanceOn(actual.getId(), (float) -50);
        actual = controller.getModel().getCustomer(actual.getId());

        Assert.assertEquals(balance1 - 50, actual.getBalance(), 0);
    }

    @Test(expected = UserNotFoundException.class)
    public void changeBalanceOn_NOT_EXISTED_CUSTOMER()
            throws UserNotFoundException, IOException, SQLException, DataNotUpdatedWarning
    {
        BigInteger randomId = BigInteger.valueOf(666);

        controller.changeBalanceOn(randomId, (float) -50);
    }

    @Test
    public void getCustomerConnectedServices()
    {
    }

    @Test
    public void getCustomerNotFinishedOrders()
    {
    }

    @Test
    public void getOrdersByEmployee()
    {
    }

    @Test(expected = Exception.class)
    public void createSuspendOrder_SERVICE_NOT_BELONGS_TO_CUSTOMER() throws Exception
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(1);
        ServiceDTO service = controller.getModel()
                .createService(new ServiceDTO(new Date(), specId, ServiceStatus.ACTIVE, customer.getId()));
        String newLogin = "newLogin";
        CustomerDTO otherCustomer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, newLogin, "pass", null, 0));

        controller.createSuspendOrder(otherCustomer.getId(), service.getId());
    }

    @Test(expected = Exception.class)
    public void createSuspendOrder_SERVICE_NOT_EXISTS() throws Exception
    {
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        BigInteger randomId = BigInteger.valueOf(666);

        OrderDTO suspendOrder = controller.createSuspendOrder(customer.getId(), randomId);
    }

    @Test
    public void getOrdersOfEmployeesOnVacation()
    {
    }

    @Test
    public void setEmployeeWaitingStatus_TRUE()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning, ObjectNotFoundException
    {
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO employee = controller.getModel()
                .createEmployee(new EmployeeDTO(null, null, empLogin, empPassword, empStatus));

        controller.setEmployeeWaitingStatus(employee.getId(), true);
        employee = controller.getModel().getEmployee(employee.getId());

        Assert.assertTrue(employee.isWaitingForOrders());
    }

    @Test
    public void setEmployeeWaitingStatus_FALSE()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning, ObjectNotFoundException
    {
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO employee = controller.getModel()
                .createEmployee(new EmployeeDTO(null, null, empLogin, empPassword, empStatus));

        controller.setEmployeeWaitingStatus(employee.getId(), false);
        employee = controller.getModel().getEmployee(employee.getId());

        Assert.assertFalse(employee.isWaitingForOrders());
    }

    @Test
    public void goOnVacation()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO actual = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));

        controller.goOnVacation(actual.getId());
        actual = controller.getModel().getEmployee(actual.getId());

        Assert.assertEquals(EmployeeStatus.ON_VACATION, actual.getEmployeeStatus());
    }

    @Test(expected = UserNotFoundException.class)
    public void goOnVacation_NOT_EXISTED_EMPLOYEE()
            throws UserNotFoundException, IOException, SQLException, DataNotUpdatedWarning
    {
        BigInteger randomId = BigInteger.valueOf(666);

        controller.goOnVacation(randomId);
    }

    @Test
    public void returnFromVacation()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";

        EmployeeDTO actual =
                controller.getModel().createEmployee(
                        new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, EmployeeStatus.ON_VACATION));

        controller.returnFromVacation(actual.getId());
        actual = controller.getModel().getEmployee(actual.getId());

        Assert.assertEquals(EmployeeStatus.WORKING, actual.getEmployeeStatus());
    }

    @Test
    public void retireEmployee()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO employee = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));

        controller.retireEmployee(employee.getId());
        employee = controller.getModel().getEmployee(employee.getId());

        Assert.assertEquals(EmployeeStatus.RETIRED, employee.getEmployeeStatus());
    }

    @Test
    public void retireEmployee_ORDERS_EXISTS()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        EmployeeDTO employee = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));
        controller.getModel().createOrder(new OrderDTO(null, employee.getId(), null, null, null, null));
        controller.getModel().createOrder(new OrderDTO(null, employee.getId(), null, null, null, null));
        controller.getModel().createOrder(new OrderDTO(null, employee.getId(), null, null, null, null));

        controller.retireEmployee(employee.getId());
        employee = controller.getModel().getEmployee(employee.getId());

        if (controller.getEmployeeOrders(employee.getId()).size() != 0)
        {
            Assert.fail("Orders of retired employee keep existing!!!");
        }
    }

    @Test
    public void assignOrder()
            throws UserNotFoundException, ObjectNotFoundException, IllegalLoginOrPasswordException, IOException,
            IllegalTransitionException, SQLException, DataNotUpdatedWarning, DataNotCreatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        OrderDTO order = controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);
        EmployeeDTO employee = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));

        controller.assignOrder(employee.getId(), orderId);

        employee = controller.getModel().getEmployee(employee.getId());
        order = controller.getModel().getOrder(order.getId());

        Assert.assertEquals(employee.getId(), order.getEmployeeId());
    }

    @Test
    public void processOrder() throws UserNotFoundException, IllegalTransitionException, ObjectNotFoundException,
            IllegalLoginOrPasswordException, IOException, DataNotCreatedWarning, SQLException, DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        OrderDTO order = controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);
        EmployeeDTO employee = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));

        controller.processOrder(employee.getId(), orderId);

        employee = controller.getModel().getEmployee(employee.getId());
        order = controller.getModel().getOrder(order.getId());

        Assert.assertEquals(employee.getId(), order.getEmployeeId());
        Assert.assertEquals(OrderStatus.IN_PROGRESS, order.getOrderStatus());
    }

    @Test
    public void usassignOrder() throws UserNotFoundException, ObjectNotFoundException,
            IllegalLoginOrPasswordException, IOException, IllegalTransitionException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String cust1_login = "cust1_login";
        CustomerDTO customer = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, cust1_login, "cust1_pass", null, 500));
        controller.getModel().createSpecification(new SpecificationDTO("Spec name", 100, "Internet100", false, null));
        BigInteger specId = BigInteger.valueOf(2);
        OrderDTO order = controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);
        EmployeeDTO employee = controller.getModel()
                .createEmployee(new EmployeeDTO(empFirstName, empLastName, empLogin, empPassword, empStatus));

        controller.assignOrder(employee.getId(), orderId);

        controller.unassignOrder(orderId);

        order = controller.getModel().getOrder(order.getId());

        Assert.assertNull(order.getEmployeeId());
    }

}