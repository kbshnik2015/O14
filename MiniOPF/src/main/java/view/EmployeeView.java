package view;

import controller.command.CommandParser;
import model.entities.Employee;

import java.util.Scanner;

public class EmployeeView
{
    public static void start(Employee employee){
        while(true){
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            System.out.println(CommandParser.parse(cmd)+ "\n");
        }
    }
}
