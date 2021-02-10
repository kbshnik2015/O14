package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.UserNotFoundException;
import controller.managers.WorkWaitersManager;
import model.entities.Customer;
import model.entities.Employee;
import model.entities.Order;
import model.enums.EmployeeStatus;

public class WorkWaitersManagerTest
{

    private Controller controller;

    @Before
    public void setUp() throws Exception
    {
        controller = new Controller();
        controller.getModel().clear();
    }

    @Test
    public void distributeOrders() throws IllegalLoginOrPasswordException, UserNotFoundException, IOException
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

        controller.setEmployeeWaitingStatus(empLogin1, true);
        controller.setEmployeeWaitingStatus(empLogin2, true);

        WorkWaitersManager.distributeOrders();

        Assert.assertEquals(0, WorkWaitersManager.getEmployeesWaitingForOrders().size());
        Assert.assertNull(WorkWaitersManager.getFreeOrder());
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