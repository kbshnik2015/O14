package controller.managers;

import controller.exceptions.ObjectNotFoundException;
import lombok.Data;
import lombok.SneakyThrows;
import model.Model;
import model.ModelFactory;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkWaitersManager
{
    @SuppressWarnings ("InfiniteLoopStatement")
    @SneakyThrows
    public static void distributeOrdersBackground()
    {
        while (true)
        {
            try
            {
                Thread.sleep(10000);
                distributeOrders();
            } catch (InterruptedException e)
            {
                //We don't need a notification about an interruption of this daemon thread
            }
        }
    }

    public static void distributeOrders() throws ObjectNotFoundException
    {
        Model model = ModelFactory.getModel();
        List<EmployeeDTO> freeEmployees = getEmployeesWaitingForOrders();
        for (EmployeeDTO employee : freeEmployees)
        {
            OrderDTO order = getFreeOrder();
            if (order != null)
            {
                order.setEmployeeId(employee.getId());
                employee.setWaitingForOrders(false);
                try
                {
                    model.updateEmployee(employee);
                } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
                {
                    dataNotUpdatedWarning.printStackTrace();
                }
                try
                {
                    model.updateOrder(order);
                } catch (DataNotUpdatedWarning dataNotUpdatedWarning)
                {
                    dataNotUpdatedWarning.printStackTrace();
                }
            }
        }
    }

    public static List<EmployeeDTO> getEmployeesWaitingForOrders() throws ObjectNotFoundException
    {
        Model model = ModelFactory.getModel();
        List<EmployeeDTO> freeEmployees = new ArrayList<>();
        for (EmployeeDTO employee : model.getEmployees()
                .values())
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
        for (OrderDTO order : model.getOrders()
                .values())
        {
            if (order.getEmployeeId() == null)
            {
                return order;
            }
        }
        return null;
    }

}
