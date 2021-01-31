package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.UserNotFoundException;
import model.entities.Customer;
import model.entities.Employee;
import model.entities.Order;
import model.enums.EmployeeStatus;

public class WorkWaitersTest
{

    private Controller controller;

    @Before
    public void setUp() throws Exception
    {
        controller = new Controller();
        controller.getModel().clear();
    }

    @Test
    public void subscribe() throws IllegalLoginOrPasswordException, UserNotFoundException
    {
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employee = controller.createEmployee(null, null, empLogin, empPassword, empStatus);
        employee.setWaitingForOrders(false);
        WorkWaiters workWaiters = new WorkWaiters();

        workWaiters.subscribe(controller.getModel(), empLogin);

        Assert.assertTrue(employee.isWaitingForOrders());
    }

    @Test(expected = UserNotFoundException.class)
    public void subscribe_EMPLOYEE_NOT_EXISTS() throws UserNotFoundException
    {
        WorkWaiters workWaiters = new WorkWaiters();

        workWaiters.subscribe(controller.getModel(), "randomEmpLogin");
    }

    @Test
    public void unsubscribe() throws IllegalLoginOrPasswordException, UserNotFoundException
    {
        String empLogin = "Employee1login";
        String empPassword = "Employee1Password";
        EmployeeStatus empStatus = EmployeeStatus.WORKING;

        Employee employee = controller.createEmployee(null, null, empLogin, empPassword, empStatus);
        employee.setWaitingForOrders(true);
        WorkWaiters workWaiters = new WorkWaiters();

        workWaiters.unsubscribe(controller.getModel(), empLogin);

        Assert.assertFalse(employee.isWaitingForOrders());
    }

    @Test(expected = UserNotFoundException.class)
    public void unsubscribe_EMPLOYEE_NOT_EXISTS() throws UserNotFoundException
    {
        WorkWaiters workWaiters = new WorkWaiters();

        workWaiters.unsubscribe(controller.getModel(), "randomEmpLogin");
    }

    @Test
    public void distributeOrders() throws IllegalLoginOrPasswordException, UserNotFoundException
    {
        String empLogin1 = "Employee1login";
        String empPassword1 = "Employee1Password";
        String empLogin2 = "Employee2login";
        String empPassword2 = "Employee2Password";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        float balance1 = 100;

        Customer expected1 = controller.createCustomer(null, null, login1, password1, null, balance1);
        Employee expected2 = controller.createEmployee(null, null, empLogin1, empPassword1, EmployeeStatus.WORKING);
        Employee expected3 = controller.createEmployee(null, null, empLogin2, empPassword2, EmployeeStatus.WORKING);

        controller.getModel().createOrder(new Order(login1, empLogin1, null, null, null, null));
        controller.getModel().createOrder(new Order(login1, null, null, null, null, null));
        controller.getModel().createOrder(new Order(login1, null, null, null, null, null));

        WorkWaiters workWaiters = new WorkWaiters();

        workWaiters.subscribe(controller.getModel(), empLogin1);
        workWaiters.subscribe(controller.getModel(), empLogin2);

        workWaiters.distributeOrders(controller.getModel());

        Assert.assertEquals(0, workWaiters.getEmployeesWaitingForOrders(controller.getModel()).size());
        Assert.assertNull(workWaiters.getFreeOrder(controller.getModel()));
    }

    @Test
    public void getEmployeesWaitingForOrders()
    {
    }

    @Test
    public void getFreeOrder()
    {
    }
}