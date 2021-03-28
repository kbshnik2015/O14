package controller.managers;

import java.util.ArrayList;
import java.util.List;

import controller.exceptions.ObjectNotFoundException;
import lombok.Data;
import lombok.SneakyThrows;
import model.Model;
import model.ModelFactory;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;

@Data
public class WorkWaitersManager
{
    @SuppressWarnings("InfiniteLoopStatement")
    @SneakyThrows
    public static void distributeOrdersBackground()
    {
        while (true)
        {
            try
            {
                Thread.sleep(10000);
                distributeOrders();
            }
            catch (InterruptedException e)
            {
                //We don't need a notification about an interruption of this daemon thread
            }
        }
    }

    public static void distributeOrders() throws ObjectNotFoundException
    {
        List<EmployeeDTO> freeEmployees = getEmployeesWaitingForOrders();
        for (int i = 0; i < freeEmployees.size() && getFreeOrder() != null; i++)
        {
            getFreeOrder().setEmployeeId(freeEmployees.get(i).getId());
            freeEmployees.get(i).setWaitingForOrders(false);
        }
    }

    public static List<EmployeeDTO> getEmployeesWaitingForOrders()
            throws ObjectNotFoundException
    {
        Model model = ModelFactory.getModel();
        List<EmployeeDTO> freeEmployees = new ArrayList<>();
        for (EmployeeDTO employee : model.getEmployees().values())
        {
            if (employee.isWaitingForOrders())
            {
                freeEmployees.add(employee);
            }
        }
        return freeEmployees;
    }

    public static OrderDTO getFreeOrder() throws ObjectNotFoundException
    {
        Model model = ModelFactory.getModel();
        for (OrderDTO order : model.getOrders().values())
        {
            if (order.getEmployeeId() == null)
            {
                return order;
            }
        }
        return null;
    }

}
