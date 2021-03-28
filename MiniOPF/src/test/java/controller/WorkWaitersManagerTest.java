package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import controller.managers.WorkWaitersManager;
import model.ModelFactory;
import model.ModelJson;
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.enums.EmployeeStatus;

@SuppressWarnings("SpellCheckingInspection")
public class WorkWaitersManagerTest
{

    private Controller controller;

    {
        //noinspection AccessStaticViaInstance
        ModelFactory.setCurrentModel("modeljson");
        controller = new Controller();
        ModelJson.loadFromFile("test.json");
    }

    public WorkWaitersManagerTest() throws IOException, SQLException, ObjectNotFoundException
    {
    }

    @Before
    public void setUp()
    {
        ((ModelJson) controller.getModel()).clear();
    }

    @Test
    public void distributeOrders()
            throws IllegalLoginOrPasswordException, UserNotFoundException, IOException, DataNotCreatedWarning,
            SQLException, DataNotUpdatedWarning, ObjectNotFoundException
    {
        String empLogin1 = "Employee1login";
        String empPassword1 = "Employee1Password";
        String empLogin2 = "Employee2login";
        String empPassword2 = "Employee2Password";
        String login1 = "Customer1login";
        String password1 = "Customer1password";
        float balance1 = 100;

        CustomerDTO expected1 = controller.getModel()
                .createCustomer(new CustomerDTO(null, null, login1, password1, null, balance1));
        EmployeeDTO expected2 = controller.getModel()
                .createEmployee(new EmployeeDTO(null, null, empLogin1, empPassword1, EmployeeStatus.WORKING));
        EmployeeDTO expected3 = controller.getModel()
                .createEmployee(new EmployeeDTO(null, null, empLogin2, empPassword2, EmployeeStatus.WORKING));

        controller.getModel().createOrder(new OrderDTO(expected1.getId(), expected2.getId(), null, null, null, null));
        controller.getModel().createOrder(new OrderDTO(expected1.getId(), null, null, null, null, null));
        controller.getModel().createOrder(new OrderDTO(expected1.getId(), null, null, null, null, null));

        controller.setEmployeeWaitingStatus(expected2.getId(), true);
        controller.setEmployeeWaitingStatus(expected3.getId(), true);

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