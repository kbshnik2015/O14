package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.exceptions.IllegalLoginException;
import controller.exceptions.UserNotFoundException;
import model.Model;
import model.entities.AbstractUser;
import model.entities.Customer;
import model.entities.Employee;
import model.enums.EmployeeStatus;

public class ControllerTest
{

    private Controller controller;

    private String firstName1;
    private String lastName1;
    private String login1;
    private String password1;
    private String address1;
    private float balance1;
    private Customer customer1;

    private String empFirstName;
    private String empLastName;
    private String empLogin;
    private String empPassword;
    private EmployeeStatus empStatus;
    private Employee employee1;

    @Before
    public void setUp() throws Exception
    {
        Model.setInstance(new Model());
        controller = new Controller();

        firstName1 = "Customer1firstName";
        lastName1 = "Customer1lastName";
        login1 = "Customer1login";
        password1 = "Customer1password";
        address1 = "Customer1address";
        balance1 = 100;
        customer1 = new Customer(firstName1, lastName1, login1, password1, address1, balance1);

        empFirstName = "Employee1firstName";
        empLastName = "Employee1lastName";
        empLogin = "Employee1login";
        empPassword = "Employee1Password";
        empStatus = EmployeeStatus.WORKING;
        employee1 = new Employee(empFirstName, empLastName, empLogin, empPassword, empStatus);
    }

    @Test
    public void createCustomer() throws IllegalLoginException
    {
        Customer expected = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Assert.assertEquals(expected, customer1);
    }

    @Test(expected = IllegalLoginException.class)
    public void createCustomer_WITH_EXISTED_LOGIN() throws IllegalLoginException
    {
        Customer expected = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);

        Customer existedCustomer =
                controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
    }

    @Test(expected = IllegalLoginException.class)
    public void createCustomer_LOGIN_IS_NULL() throws IllegalLoginException
    {
        Customer customerWithoutLogin = controller.createCustomer(firstName1, lastName1, null, password1, address1, balance1);
    }

    @Test(expected = IllegalLoginException.class)
    public void createCustomer_PASSWORD_IS_NULL() throws IllegalLoginException
    {
        Customer customerWithoutLogin = controller.createCustomer(firstName1, lastName1, login1, null, address1, balance1);
    }

    @Test
    public void updateCustomer() throws IllegalLoginException, UserNotFoundException
    {
        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.updateCustomer("newFirstName", null, login1, null, null, -1);
        customer1.setFirstName("newFirstName");
        Assert.assertEquals(actual, customer1);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateCustomer_NOT_FOUND() throws IllegalLoginException, UserNotFoundException
    {
        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.updateCustomer("newFirstName", null, "randomlogin", null, null, -1);
    }

    @Test
    public void deleteCustomer() throws IllegalLoginException
    {
        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.deleteCustomer(actual);
        Assert.assertNull(controller.getModel().getCustomers().get(login1));
    }

    @Test
    public void getCustomerByLogin() throws IllegalLoginException
    {
        Customer actual = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Customer expected = controller.getCustomerByLogin(login1);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void createEmployee() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        Assert.assertEquals(expected, employee1);
    }

    @Test(expected = IllegalLoginException.class)
    public void createEmployee_WITH_EXISTED_LOGIN() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);

        Employee existedEmployee = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
    }

    @Test(expected = IllegalLoginException.class)
    public void createEmployee_LOGIN_IS_NULL() throws IllegalLoginException
    {
        Employee employeeWithoutLogin = controller.createEmployee(empFirstName, empLastName, null, empPassword, empStatus);
    }

    @Test(expected = IllegalLoginException.class)
    public void createEmployee_PASSWORD_IS_NULL() throws IllegalLoginException
    {
        Employee employeeWithoutLogin = controller.createEmployee(empFirstName, empLastName, empLogin, null, empStatus);
    }

    @Test
    public void updateEmployee()throws IllegalLoginException, UserNotFoundException
    {
        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.updateEmployee("newFirstName", null, empLogin, null, null);
        employee1.setFirstName("newFirstName");
        Assert.assertEquals(actual, employee1);
    }

    @Test(expected = UserNotFoundException.class)
    public void updateEmployee_NOT_FOUND() throws IllegalLoginException, UserNotFoundException
    {
        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.updateEmployee("newFirstName", null, "nullLogin", null, null);
    }

    @Test
    public void deleteEmployee()throws IllegalLoginException
    {
        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.deleteEmployee(actual);
        Assert.assertNull(controller.getModel().getCustomers().get(empLogin));
    }

    @Test
    public void getEmployeeByLogin()throws IllegalLoginException
    {
        Employee actual = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        Employee expected = controller.getEmployeeByLogin(empLogin);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void login_CUSTOMER() throws IllegalLoginException
    {
        Employee expected2 = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        Customer expected = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        AbstractUser actual = controller.login(login1, password1);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void login_EMPLOYEE() throws IllegalLoginException
    {
        Customer expected1 = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        AbstractUser actual = controller.login(empLogin, empPassword);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = IllegalLoginException.class)
    public void login_WRONG_LOGIN() throws IllegalLoginException
    {
        Customer expected1 = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Employee expected2 = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        AbstractUser actual = controller.login("empLoginRandom", empPassword);
    }

    @Test(expected = IllegalLoginException.class)
    public void login_EMPLOYEE_WRONG_PASSWORD() throws IllegalLoginException
    {
        Customer expected1 = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Employee expected2 = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        AbstractUser actual = controller.login(empLogin, "empPasswordRandom");
    }

    @Test(expected = IllegalLoginException.class)
    public void login_CUSTOMER_WRONG_PASSWORD() throws IllegalLoginException
    {
        Customer expected1 = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        Employee expected2 = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        AbstractUser actual = controller.login(login1, "empPasswordRandom");
    }

    @Test
    public void startOrder()
    {
    }

    @Test
    public void suspendOrder()
    {
    }

    @Test
    public void restoreOrder()
    {
    }

    @Test
    public void cancelOrder()
    {
    }

    @Test
    public void completeOrder()
    {
    }

    @Test
    public void topUpBalance() throws IllegalLoginException
    {
        Customer expected = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.topUpBalance(login1, 50);
        Assert.assertEquals(balance1  + 50, expected.getBalance(), 0);
    }

    @Test
    public void topDownBalance() throws IllegalLoginException
    {
        Customer expected = controller.createCustomer(firstName1, lastName1, login1, password1, address1, balance1);
        controller.topDownBalance(login1, 50);
        Assert.assertEquals(balance1  - 50, expected.getBalance(), 0);
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
    public void createNewOrder()
    {
    }

    @Test
    public void createSuspendOrder()
    {
    }

    @Test
    public void createRestoreOrder()
    {
    }

    @Test
    public void createDisconnectOrder()
    {
    }

    @Test
    public void getOrdersOfEmployeesOnVacation()
    {
    }

    @Test
    public void goOnVacation() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.goOnVacation(empLogin);
        Assert.assertEquals(expected.getEmployeeStatus(), EmployeeStatus.ON_VACATION);
    }

    @Test
    public void returnFromVacation() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, EmployeeStatus.ON_VACATION);
        controller.returnFromVacation(empLogin);
        Assert.assertEquals(expected.getEmployeeStatus(), EmployeeStatus.WORKING);
    }

    @Test
    public void retireEmployee() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.retireEmployee(empLogin);
        Assert.assertEquals(expected.getEmployeeStatus(), EmployeeStatus.RETIRED);
    }

    @Test
    public void subscribeToWork() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.subscribeToWork(empLogin);
        boolean checker = false;
        if (controller.getModel().getWorkWaiters().getEmployeesWaitingForOrders().indexOf(empLogin) != -1)
            checker = true;
        Assert.assertTrue(checker);
    }

    @Test
    public void subscribeToWork_ALREADY_SUBSCRIBED() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        Employee tmp1 = controller.createEmployee(empFirstName, empLastName, "tmpEployee1", empPassword, empStatus);
        Employee tmp2 = controller.createEmployee(empFirstName, empLastName, "tmpEployee2", empPassword, empStatus);
        controller.subscribeToWork(empLogin);
        controller.subscribeToWork("tmpEmployee1");
        controller.subscribeToWork("tmpEmployee2");
        Assert.assertEquals(controller.getModel().getWorkWaiters().getEmployeesWaitingForOrders().indexOf(empLogin), controller.getModel().getWorkWaiters().getEmployeesWaitingForOrders().lastIndexOf(empLogin));
    }

    @Test
    public void unsubscribeFromWork() throws IllegalLoginException
    {
        Employee expected = controller.createEmployee(empFirstName, empLastName, empLogin, empPassword, empStatus);
        controller.unsubscribeFromWork(empLogin);
        Assert.assertEquals(controller.getModel().getWorkWaiters().getEmployeesWaitingForOrders().indexOf(empLogin), -1);
    }

    @Test
    public void distributeOrders()
    {
    }
}