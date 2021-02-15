package controller.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.SneakyThrows;
import model.Model;
import model.entities.Employee;
import model.entities.Order;

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

    public static void distributeOrders() throws IOException
    {
        List<Employee> freeEmployees = getEmployeesWaitingForOrders();
        for (int i = 0; i < freeEmployees.size() && getFreeOrder() != null; i++)
        {
            getFreeOrder().setEmployeeLogin(freeEmployees.get(i).getLogin());
            freeEmployees.get(i).setWaitingForOrders(false);
        }
    }

    public static List<Employee> getEmployeesWaitingForOrders() throws IOException
    {
        Model model = Model.getInstance();
        List<Employee> freeEmployees = new ArrayList<>();
        for (Employee employee : model.getEmployees().values())
        {
            if (employee.isWaitingForOrders())
            {
                freeEmployees.add(employee);
            }
        }
        return freeEmployees;
    }

    public static Order getFreeOrder() throws IOException
    {
        Model model = Model.getInstance();
        for (Order order : model.getOrders().values())
        {
            if (order.getEmployeeLogin() == null)
            {
                return order;
            }
        }
        return null;
    }

}
