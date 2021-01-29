package controller;

import java.util.ArrayList;
import java.util.List;

import controller.exceptions.UserNotFoundException;
import lombok.Data;
import model.Model;
import model.entities.Employee;
import model.entities.Order;

@Data
public class WorkWaiters
{

    public WorkWaiters()
    {
    }

    public void subscribe(Model model, String login) throws UserNotFoundException
    {
        if (!model.getEmployees().containsKey(login))
        {
            throw new UserNotFoundException("Employee login " + login + " doesn't exist!");
        }
        model.getEmployee(login).setWaitingForOrders(true);
    }

    public void unsubscribe(Model model, String login) throws UserNotFoundException
    {
        if (!model.getEmployees().containsKey(login))
        {
            throw new UserNotFoundException("Employee login " + login + " doesn't exist!");
        }
        model.getEmployee(login).setWaitingForOrders(false);
    }

    public void distributeOrders(Model model) throws UserNotFoundException
    {
        List<Employee> list = getEmployeesWaitingForOrders(model);
        for (int i = 0; i < list.size() && getFreeOrder(model) != null; i++)
        {
            getFreeOrder(model).setEmployeeLogin(list.get(i).getLogin());
            unsubscribe(model, list.get(i).getLogin());
        }
    }

    public List<Employee> getEmployeesWaitingForOrders(Model model)
    {
        List<Employee> list = new ArrayList<>();
        for (Employee employee : model.getEmployees().values())
        {
            if (employee.isWaitingForOrders())
            {
                list.add(employee);
            }
        }
        return list;
    }

    public Order getFreeOrder(Model model)
    {
        for (Order ord : model.getOrders().values())
        {
            if (ord.getEmployeeLogin() == null)
            {
                return ord;
            }
        }
        return null;
    }

}
