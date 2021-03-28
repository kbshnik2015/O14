package view;

import java.util.Scanner;

import controller.command.CommandParser;
import model.dto.EmployeeDTO;


public class EmployeeView
{
    private static final String LINE_BREAKS = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    public static void start(EmployeeDTO employee)
    {
        while (true)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println(employee.getFirstName() + " " + employee.getLastName() + "\n");
            System.out.print("Enter command: ");
            String cmd = scanner.nextLine();
            if (cmd.equals("exit"))
            {
                break;
            }
            System.out.println(CommandParser.parse(cmd) + "\n");

        }
    }
}
