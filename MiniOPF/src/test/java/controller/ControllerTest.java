package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import model.entities.AbstractUser;
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

@SuppressWarnings("SpellCheckingInspection")
public class ControllerTest
{
    private Controller controller = new Controller();

    {
        //noinspection AccessStaticViaInstance
        controller.getModel().loadFromFile("test.json");
    }

    public ControllerTest() throws IOException
    {
    }

    @Before
    public void setUp()
    {
        controller.getModel().clear();
    }

    @Test
    public void createCustomer() throws IllegalLoginOrPasswordException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        Customer expected = new Customer(firstName1, lastName1, login1, password1, address1, balance1);
        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        expected.setId(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createCustomer_WITH_EXISTED_LOGIN() throws IllegalLoginOrPasswordException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        Customer customer = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Customer existedCustomer =
                controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);

    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createCustomer_LOGIN_IS_NULL() throws IllegalLoginOrPasswordException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        Customer customerWithoutLogin =
                controller.createCustomer(firstName1, lastName1, null, password1, address1, balance1);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createCustomer_PASSWORD_IS_NULL() throws IllegalLoginOrPasswordException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 200;

        Customer customerWithoutPassword =
                controller.createCustomer(firstName1, lastName1, login1, null, address1, balance1);
    }

    @Test
    public void updateCustomer() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        Customer expected = new Customer(firstName1, lastName1, login1, password1, address1, balance1);
        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        expected.setId(actual.getId());

        controller.updateCustomer(actual.getId(), "newFirstName", null, null, null, -1);
        expected.setFirstName("newFirstName");

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateCustomer_NOT_FOUND() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;
        BigInteger randomId = BigInteger.valueOf(666);

        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.updateCustomer(randomId, "newFirstName", null, null, null, -1);
    }

    @Test
    public void deleteCustomer() throws IllegalLoginOrPasswordException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.getModel().deleteCustomer(actual.getId());

        Assert.assertNull(controller.getModel().getCustomers().get(actual.getId()));
    }

    @Test
    public void getCustomerByLogin() throws IllegalLoginOrPasswordException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        Customer expected = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Customer actual = controller.getModel().getCustomer(expected.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCustomerByLogin_NOT_FOUND()
    {
        BigInteger randomId = BigInteger.valueOf(666);

        Customer actual = controller.getModel().getCustomer(randomId);
    }

    @Test
    public void createEmployee() throws IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee expected = new Employee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        expected.setId(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createEmployee_WITH_EXISTED_LOGIN() throws IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);

        Employee existedEmployee =
                controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createEmployee_LOGIN_IS_NULL() throws IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employeeWithoutLogin =
                controller.createEmployee(empFirstName, empLastName, null, empPassword, empStatus);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void createEmployee_PASSWORD_IS_NULL() throws IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employeeWithoutLogin = controller.createEmployee(empFirstName, empLastName, empLogin, null, empStatus);
    }

    @Test
    public void updateEmployee() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee expected = new Employee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);

        controller.updateEmployee(actual.getId(), "newFirstName", null, null, null);
        expected.setId(actual.getId());
        expected.setFirstName("newFirstName");

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateEmployee_NOT_FOUND() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        BigInteger randomId = BigInteger.valueOf(666);

        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.updateEmployee(randomId, "newFirstName", null, null, null);
    }

    @Test
    public void deleteEmployee() throws IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.getModel().deleteEmployee(actual.getId());

        Assert.assertNull(controller.getModel().getCustomers().get(actual.getId()));
    }

    @Test
    public void getEmployeeByLogin() throws IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        Employee expected = controller.getModel().getEmployee(actual.getId());

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createDistrict() throws ObjectNotFoundException, IOException
    {
        controller.getModel().createDistrict("Samara", null);
        BigInteger districtSamaraId = BigInteger.valueOf(1);
        BigInteger expectedId = BigInteger.valueOf(2);

        District expected = new District("Name", districtSamaraId);
        expected.setId(expectedId);

        District actual = controller.createDistrict("Name", districtSamaraId);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void createDistrict_PARENT_NOT_EXISTS() throws ObjectNotFoundException, IOException
    {
        BigInteger notExistParentId = BigInteger.valueOf(666);

        District actual = controller.createDistrict("Name", notExistParentId);
    }

    @Test
    public void createDistrict_PARENT_IS_NULL() throws ObjectNotFoundException, IOException
    {
        BigInteger expectedId = BigInteger.valueOf(1);

        District expected = new District("Name", null);
        expected.setId(expectedId);

        District actual = controller.createDistrict("Name", null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateDistrict() throws ObjectNotFoundException, IOException
    {
        controller.getModel().createDistrict("Samara", null);
        BigInteger districtSamaraId = BigInteger.valueOf(1);
        BigInteger expectedId = BigInteger.valueOf(2);


        District expected = new District("NewName", districtSamaraId);
        expected.setId(expectedId);

        District actual = controller.createDistrict("Name", null);
        controller.updateDistrict(expectedId, "NewName", districtSamaraId);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createSpecification() throws ObjectNotFoundException, IOException
    {
        controller.getModel().createDistrict("Samara", null);
        BigInteger districtSamaraId = BigInteger.valueOf(1);
        controller.getModel().createDistrict("Togliatti", null);
        BigInteger districtTogliattiId = BigInteger.valueOf(2);
        BigInteger expectedId = BigInteger.valueOf(3);
        ArrayList<BigInteger> districtIds = new ArrayList<>();
        districtIds.add(districtSamaraId);
        districtIds.add(districtTogliattiId);

        Specification expected = new Specification("Spec name", 100, null, true, districtIds);
        expected.setId(expectedId);
        Specification actual = controller.createSpecification("Spec name", 100, null, true, districtIds);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateSpecification() throws ObjectNotFoundException, IOException
    {
        controller.getModel().createDistrict("Samara", null);
        BigInteger districtSamaraId = BigInteger.valueOf(1);
        controller.getModel().createDistrict("Togliatti", null);
        BigInteger districtTogliattiId = BigInteger.valueOf(2);
        BigInteger expectedId = BigInteger.valueOf(3);
        ArrayList<BigInteger> districtIds = new ArrayList<>();
        districtIds.add(districtSamaraId);
        districtIds.add(districtTogliattiId);

        Specification expected = new Specification("Spec name", 200, null, true, districtIds);
        expected.setId(expectedId);
        Specification actual = controller.createSpecification("Spec name", 100, null, false, null);
        controller.updateSpecification(expectedId, "Spec name", 200, null, true, districtIds);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void updateSpecification_NOT_EXISTS() throws ObjectNotFoundException, IOException
    {
        BigInteger notExistSpecId = BigInteger.valueOf(666);

        controller.updateSpecification(notExistSpecId, "Spec name", 200, null, true, null);
    }

    @Test
    public void login_CUSTOMER() throws IllegalLoginOrPasswordException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 100;

        Customer expected = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        AbstractUser actual = controller.login(login1, password1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void login_EMPLOYEE() throws IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        AbstractUser actual = controller.login(empLogin, empPassword);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void login_WRONG_LOGIN() throws IllegalLoginOrPasswordException, IOException
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

        Customer expected1 = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Employee expected2 = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);

        AbstractUser actual = controller.login("empLoginRandom", empPassword);
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void login_EMPLOYEE_WRONG_PASSWORD() throws IllegalLoginOrPasswordException, IOException
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

        Customer expected1 = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Employee expected2 = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);

        AbstractUser actual = controller.login(empLogin, "empPasswordRandom");
    }

    @Test(expected = IllegalLoginOrPasswordException.class)
    public void login_CUSTOMER_WRONG_PASSWORD() throws IllegalLoginOrPasswordException, IOException
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

        Customer expected1 = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Employee expected2 = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        AbstractUser actual = controller.login(login1, "empPasswordRandom");
    }

    @Test
    public void startOrder()
            throws IllegalTransitionException, ObjectNotFoundException, IllegalLoginOrPasswordException,
            UserNotFoundException, IOException
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        Order order = controller.getModel().getOrder(orderId);

        Assert.assertEquals(OrderStatus.ENTERING, order.getOrderStatus());

        controller.startOrder(orderId);

        Assert.assertEquals(OrderStatus.IN_PROGRESS, order.getOrderStatus());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void startOrder_ORDER_NOT_EXIST()
            throws IllegalTransitionException, ObjectNotFoundException, IOException
    {
        BigInteger orderId = BigInteger.valueOf(666);

        controller.startOrder(orderId);
    }

    @Test(expected = IllegalTransitionException.class)
    public void startOrder_ILLEGAL_TRANSITION()
            throws IllegalTransitionException, ObjectNotFoundException, IOException
    {
        Order order = new Order(null, null, null, null, OrderAim.NEW, OrderStatus.CANCELLED);
        controller.getModel().createOrder(order);
        BigInteger orderId = BigInteger.valueOf(1);

        controller.startOrder(orderId);
    }

    @Test
    public void suspendOrder()
            throws IllegalTransitionException, ObjectNotFoundException, IllegalLoginOrPasswordException,
            UserNotFoundException, IOException
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        Order order = controller.getModel().getOrder(orderId);

        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        controller.suspendOrder(orderId);

        Assert.assertEquals(order.getOrderStatus(), OrderStatus.SUSPENDED);
    }

    @Test
    public void restoreOrder() throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        Order order = controller.getModel().getOrder(orderId);

        order.setOrderStatus(OrderStatus.SUSPENDED);

        controller.restoreOrder(orderId);

        Assert.assertEquals(order.getOrderStatus(), OrderStatus.IN_PROGRESS);
    }

    @Test
    public void cancelOrder() throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        Order order = controller.getModel().getOrder(orderId);

        controller.cancelOrder(orderId);

        Assert.assertEquals(order.getOrderStatus(), OrderStatus.CANCELLED);
    }

    @Test(expected = IllegalTransitionException.class)
    public void cancelOrder_ILLEGAL_TRANSITION()
            throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        Order order = controller.getModel().getOrder(orderId);

        order.setOrderStatus(OrderStatus.COMPLETED);

        controller.cancelOrder(orderId);
    }

    @Test
    public void completeOrder_NEW_ORDER()
            throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException,
            IllegalLoginOrPasswordException, IOException
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);

        Order order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        controller.completeOrder(orderId);

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.ACTIVE,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }

    @Test
    public void completeOrder_DISCONNECT_ORDER() throws Exception
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        Service service = controller.getModel()
                .createService(new Service(new Date(), specId, ServiceStatus.ACTIVE, customer.getId()));
        controller.createDisconnectOrder(customer.getId(), service.getId());
        BigInteger orderId = BigInteger.valueOf(4);

        Order order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        controller.completeOrder(orderId);

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.DISCONNECTED,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }

    @Test
    public void completeOrder_SUSPEND_ORDER() throws Exception
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        Service service = controller.getModel()
                .createService(new Service(new Date(), specId, ServiceStatus.ACTIVE, customer.getId()));
        controller.createSuspendOrder(customer.getId(), service.getId());
        BigInteger orderId = BigInteger.valueOf(4);

        Order order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        controller.completeOrder(orderId);

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.SUSPENDED,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }

    @Test
    public void completeOrder_RESTORE_ORDER() throws Exception
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        Service service = controller.getModel()
                .createService(new Service(new Date(), specId, ServiceStatus.DISCONNECTED, customer.getId()));
        controller.createRestoreOrder(customer.getId(), service.getId());
        BigInteger orderId = BigInteger.valueOf(4);

        Order order = controller.getModel().getOrder(orderId);
        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        controller.completeOrder(orderId);

        Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        Assert.assertEquals(ServiceStatus.ACTIVE,
                controller.getModel().getService(order.getServiceId()).getServiceStatus());
    }


    @Test
    public void changeBalanceOn_UP() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 200;

        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.changeBalanceOn(actual.getId(), (float) 50);

        Assert.assertEquals(balance1 + 50, actual.getBalance(), 0);
    }

    @Test
    public void changeBalanceOn_DOWN() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String firstName1 = "Customer1firstName";
        String lastName1 = "Customer1lastName";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        String address1 = "Customer1address";
        float balance1 = 200;

        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.changeBalanceOn(actual.getId(), (float) -50);

        Assert.assertEquals(balance1 - 50, actual.getBalance(), 0);
    }

    @Test(expected = UserNotFoundException.class)
    public void changeBalanceOn_NOT_EXISTED_CUSTOMER() throws UserNotFoundException, IOException
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
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(1);
        Service service = controller.getModel()
                .createService(new Service(new Date(), specId, ServiceStatus.ACTIVE, customer.getId()));
        String newLogin = "newLogin";
        Customer otherCustomer = controller.createCustomer(null, null, newLogin, "pass", null, 0);

        controller.createSuspendOrder(otherCustomer.getId(), service.getId());
    }

    @Test(expected = Exception.class)
    public void createSuspendOrder_SERVICE_NOT_EXISTS() throws Exception
    {
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        BigInteger randomId = BigInteger.valueOf(666);

        Order suspendOrder = controller.createSuspendOrder(customer.getId(), randomId);
    }

    @Test
    public void getOrdersOfEmployeesOnVacation()
    {
    }

    @Test
    public void setEmployeeWaitingStatus_TRUE()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employee = controller.createEmployee(null, null, empLogin, empPassword, empStatus);
        employee.setWaitingForOrders(false);

        controller.setEmployeeWaitingStatus(employee.getId(), true);

        Assert.assertTrue(employee.isWaitingForOrders());
    }

    @Test
    public void setEmployeeWaitingStatus_FALSE()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employee = controller.createEmployee(null, null, empLogin, empPassword, empStatus);
        employee.setWaitingForOrders(true);

        controller.setEmployeeWaitingStatus(employee.getId(), false);

        Assert.assertFalse(employee.isWaitingForOrders());
    }

    @Test
    public void goOnVacation() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword,
                empStatus);

        controller.goOnVacation(actual.getId());

        Assert.assertEquals(EmployeeStatus.ON_VACATION, actual.getEmployeeStatus());
    }

    @Test(expected = UserNotFoundException.class)
    public void goOnVacation_NOT_EXISTED_EMPLOYEE() throws UserNotFoundException, IOException
    {
        BigInteger randomId = BigInteger.valueOf(666);

        controller.goOnVacation(randomId);
    }

    @Test
    public void returnFromVacation() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";

        Employee actual =
                controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, EmployeeStatus
                        .ON_VACATION);

        controller.returnFromVacation(actual.getId());

        Assert.assertEquals(EmployeeStatus.WORKING, actual.getEmployeeStatus());
    }

    @Test
    public void retireEmployee() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employee = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword,
                empStatus);

        controller.retireEmployee(employee.getId());

        Assert.assertEquals(EmployeeStatus.RETIRED, employee.getEmployeeStatus());
    }

    @Test
    public void retireEmployee_ORDERS_EXISTS()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employee = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword,
                empStatus);
        controller.getModel().createOrder(new Order(null, employee.getId(), null, null, null, null));
        controller.getModel().createOrder(new Order(null, employee.getId(), null, null, null, null));
        controller.getModel().createOrder(new Order(null, employee.getId(), null, null, null, null));

        controller.retireEmployee(employee.getId());

        if (controller.getEmployeeOrders(employee.getId()).size() != 0)
        {
            Assert.fail("Orders of retired employee keep existing!!!");
        }
    }

    @Test
    public void assignOrder()
            throws UserNotFoundException, ObjectNotFoundException, IllegalLoginOrPasswordException, IOException,
            IllegalTransitionException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        Order order = controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);
        Employee employee = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword,
                empStatus);

        controller.assignOrder(employee.getId(), orderId);

        Assert.assertEquals(employee.getId(), order.getEmployeeId());
    }

    @Test
    public void processOrder() throws UserNotFoundException, IllegalTransitionException, ObjectNotFoundException,
            IllegalLoginOrPasswordException, IOException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        Order order = controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);
        Employee employee = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword,
                empStatus);

        controller.processOrder(employee.getId(), orderId);
        Assert.assertEquals(employee.getId(), order.getEmployeeId());
        Assert.assertEquals(OrderStatus.IN_PROGRESS, order.getOrderStatus());
    }

    @Test
    public void usassignOrder() throws UserNotFoundException, ObjectNotFoundException,
            IllegalLoginOrPasswordException, IOException, IllegalTransitionException
    {
        String empFirstName = "Employee1firstName";
        String empLastName = "Employee1lastName";
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;
        String cust1_login = "cust1_login";
        Customer customer = controller.createCustomer(null, null, cust1_login, "cust1_pass", null, 500);
        controller.createSpecification("Spec name", 100, "Internet100", false, null);
        BigInteger specId = BigInteger.valueOf(2);
        Order order = controller.createNewOrder(customer.getId(), specId);
        BigInteger orderId = BigInteger.valueOf(3);
        Employee employee = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword,
                empStatus);

        controller.assignOrder(employee.getId(), orderId);

        controller.unassignOrder(orderId);

        Assert.assertNull(order.getEmployeeId());
    }

}