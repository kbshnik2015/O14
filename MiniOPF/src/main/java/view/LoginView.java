package view;

import controller.Controller;
import controller.exceptions.IllegalLoginOrPasswordException;
import model.entities.AbstractUser;
import model.entities.Customer;
import model.entities.Employee;

import java.util.Scanner;

public class LoginView
{
    public static void start() throws Exception
    {
        Controller controller = new Controller();
        AbstractUser user = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter login :");
        String login = scanner.nextLine();
        System.out.print("Enter password :");
        String password = scanner.nextLine();
        try
        {
            user = controller.login(login,password);
        } catch (IllegalLoginOrPasswordException e)
        {
            System.out.println("Wrong login or password.");
            start();
        }

        if (user instanceof Customer){
            CustomerView.start((Customer) user);
        }
        else if(user instanceof Employee){
            EmployeeView.start((Employee) user);
        }
    }
}
