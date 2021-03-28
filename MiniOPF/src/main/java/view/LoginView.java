package view;

import java.util.Scanner;

import controller.Controller;
import controller.exceptions.IllegalLoginOrPasswordException;
import controller.managers.StartAppManager;
import model.ModelFactory;
import model.dto.AbstractUserDTO;
import model.dto.CustomerDTO;
import model.dto.EmployeeDTO;

public class LoginView
{
    public static void start() throws Exception
    {
        ModelFactory.setCurrentModel("modeljson");
        Controller controller = new Controller();
        AbstractUserDTO user = null;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter login: ");
        String login = scanner.nextLine();
        System.out.print("\nEnter password: ");
        String password = scanner.nextLine();
        try
        {
            user = controller.login(login, password);
        }
        catch (IllegalLoginOrPasswordException e)
        {
            System.out.println("\nWrong login or password.\n");
            start();
        }
        StartAppManager.startApp();
        if (user instanceof CustomerDTO)
        {
            CustomerView.start((CustomerDTO) user);
        }
        else if (user instanceof EmployeeDTO)
        {
            EmployeeView.start((EmployeeDTO) user);
        }
    }
}
