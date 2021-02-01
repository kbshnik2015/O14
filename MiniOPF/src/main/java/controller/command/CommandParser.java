package controller.command;

import java.util.regex.Pattern;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UnknownCommandException;
import controller.exceptions.UserNotFoundException;
import controller.exceptions.WrongCommandArgumentsException;

public class CommandParser
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private static void checkArgumentsLength(String[] words, int quantity) throws WrongCommandArgumentsException
    {
        if (words.length != quantity)
        {
            throw new WrongCommandArgumentsException("Wrong command arguments quantity!");
        }
    }

    public static String parse(String commandLine)
    {
        String result;
        commandLine = commandLine.trim();
        Pattern pattern = Pattern.compile("\\s*(\\s)\\s*");
        String[] words = pattern.split(commandLine);
        try
        {
            switch (words[0].toUpperCase())
            {
                case "CREATECUSTOMER":
                    checkArgumentsLength(words, 7);
                    result = Command.create_customer.execute(words);
                    break;
                case "CREATEEMPLOYEE":
                    checkArgumentsLength(words, 6);
                    result = Command.create_employee.execute(words);
                    break;
                case "UPDATECUSTOMER":
                    checkArgumentsLength(words, 7);
                    result = Command.update_customer.execute(words);
                    break;
                case "UPDATEEMPLOYEE":
                    checkArgumentsLength(words, 6);
                    result = Command.update_employee.execute(words);
                    break;
                case "CREATEDISTRICT":
                    checkArgumentsLength(words, 3);
                    result = Command.create_district.execute(words);
                    break;
                case "UPDATEDISTRICT":
                    checkArgumentsLength(words, 4);
                    result = Command.update_district.execute(words);
                    break;
                case "CREATESPECIFICATION":
                    checkArgumentsLength(words, 6);
                    result = Command.create_specification.execute(words);
                    break;
                case "UPDATESPECIFICATION":
                    checkArgumentsLength(words, 7);
                    result = Command.update_specification.execute(words);
                    break;
                case "DELETECUSTOMERCASCADE":
                    checkArgumentsLength(words, 2);
                    result = Command.delete_customer_cascade.execute(words);
                    break;
                case "DELETEEMPLOYEECASCADE":
                    checkArgumentsLength(words, 2);
                    result = Command.delete_employee_cascade.execute(words);
                    break;
                case "DELETEEMPLOYEE":
                    checkArgumentsLength(words, 2);
                    result = Command.delete_employee.execute(words);
                    break;
                case "DELETEORDERCASCADE":
                    checkArgumentsLength(words, 2);
                    result = Command.delete_order_cascade.execute(words);
                    break;
                case "DELETESPECEFICATIONCASCADE":
                    checkArgumentsLength(words, 2);
                    result = Command.delete_specification_cascade.execute(words);
                    break;
                case "DELETEDISTRICTCASCADE":
                    checkArgumentsLength(words, 2);
                    result = Command.delete_district_cascade.execute(words);
                    break;
                case "DELETEDISTRICT":
                    checkArgumentsLength(words, 3);
                    result = Command.delete_district.execute(words);
                    break;
                case "DELETESERVICE":
                    checkArgumentsLength(words, 2);
                    result = Command.delete_service.execute(words);
                    break;
                case "GETMODEL":
                    checkArgumentsLength(words, 1);
                    result = Command.get_model.execute(words);
                    break;
                case "GETCUSTOMERS":
                    checkArgumentsLength(words, 1);
                    result = Command.get_customers.execute(words);
                    break;
                case "GETEMPLOYEES":
                    checkArgumentsLength(words, 1);
                    result = Command.get_employees.execute(words);
                    break;
                case "GETSPECIFICATIONS":
                    checkArgumentsLength(words, 1);
                    result = Command.get_specifications.execute(words);
                    break;
                case "GETORDERS":
                    checkArgumentsLength(words, 1);
                    result = Command.get_orders.execute(words);
                    break;
                case "GETDISTRICTS":
                    checkArgumentsLength(words, 1);
                    result = Command.get_districts.execute(words);
                    break;
                case "GETSERVICES":
                    checkArgumentsLength(words, 1);
                    result = Command.get_services.execute(words);
                    break;
                case "GETCUSTOMERINFO":
                    checkArgumentsLength(words, 2);
                    result = Command.get_customer_info.execute(words);
                    break;
                case "GETEMPLOYEEINFO":
                    checkArgumentsLength(words, 2);
                    result = Command.get_employee_info.execute(words);
                    break;
                case "GETORDERINFO":
                    checkArgumentsLength(words, 2);
                    result = Command.get_order_info.execute(words);
                    break;
                case "GETSERVICEINFO":
                    checkArgumentsLength(words, 2);
                    result = Command.get_service_info.execute(words);
                    break;
                case "GETSPECIFICATIONINFO":
                    checkArgumentsLength(words, 2);
                    result = Command.get_specification_info.execute(words);
                    break;
                case "GETDISTRICTINFO":
                    checkArgumentsLength(words, 2);
                    result = Command.get_district_info.execute(words);
                    break;
                case "CREATENEWORDER":
                    checkArgumentsLength(words, 3);
                    result = Command.create_new_order.execute(words);
                    break;
                case "CREATESUSPENDORDER":
                    checkArgumentsLength(words, 3);
                    result = Command.create_suspend_order.execute(words);
                    break;
                case "CREATERESTOREORDER":
                    checkArgumentsLength(words, 3);
                    result = Command.create_restore_order.execute(words);
                    break;
                case "CREATEDISCONNECTORDER":
                    checkArgumentsLength(words, 3);
                    result = Command.create_disconnect_order.execute(words);
                    break;
                case "STARTORDER":
                    checkArgumentsLength(words, 2);
                    result = Command.start_order.execute(words);
                    break;
                case "SUSPENDORDER":
                    checkArgumentsLength(words, 2);
                    result = Command.suspend_order.execute(words);
                    break;
                case "RESTOREORDER":
                    checkArgumentsLength(words, 2);
                    result = Command.restore_order.execute(words);
                    break;
                case "CANCELORDER":
                    checkArgumentsLength(words, 2);
                    result = Command.cancel_order.execute(words);
                    break;
                case "COMPLETEORDER":
                    checkArgumentsLength(words, 2);
                    result = Command.complete_order.execute(words);
                    break;
                case "CHANGEBALANCEON":
                    checkArgumentsLength(words, 3);
                    result = Command.change_balance_on.execute(words);
                    break;
                case "GETFREEORDERS":
                    checkArgumentsLength(words, 1);
                    result = Command.get_free_orders.execute(words);
                    break;
                case "GETFREEORDER":
                    checkArgumentsLength(words, 1);
                    result = Command.get_free_order.execute(words);
                    break;
                case "GETCUSTOMERORDERS":
                    checkArgumentsLength(words, 2);
                    result = Command.get_customer_orders.execute(words);
                    break;
                case "GETCUSTOMERSERVICES":
                    checkArgumentsLength(words, 2);
                    result = Command.get_customer_services.execute(words);
                    break;
                case "GETEMPLOYEEORDERS":
                    checkArgumentsLength(words, 2);
                    result = Command.get_employee_orders.execute(words);
                    break;
                case "GETCUSTOMERCONNECTEDSERVICES":
                    checkArgumentsLength(words, 2);
                    result = Command.get_customer_connected_services.execute(words);
                    break;
                case "GETCUSTOMERNOTFINISHEDORDERS":
                    checkArgumentsLength(words, 2);
                    result = Command.get_customer_not_finished_orders.execute(words);
                    break;
                case "GETORDERSOFEMPLOYEESONVACATION":
                    checkArgumentsLength(words, 1);
                    result = Command.get_orders_of_employees_on_vacation.execute(words);
                    break;
                case "GOONVACATION":
                    checkArgumentsLength(words, 2);
                    result = Command.go_on_vacation.execute(words);
                    break;
                case "RETURNFROMVACATION":
                    checkArgumentsLength(words, 2);
                    result = Command.return_from_vacation.execute(words);
                    break;
                case "RETIREEMPLOYEE":
                    checkArgumentsLength(words, 2);
                    result = Command.retire_employee.execute(words);
                    break;
                case "ASSIGNORDER":
                    checkArgumentsLength(words, 3);
                    result = Command.assign_order.execute(words);
                    break;
                case "PROCESSORDER":
                    checkArgumentsLength(words, 3);
                    result = Command.process_order.execute(words);
                    break;
                case "USASSIGNORDER":
                    checkArgumentsLength(words, 2);
                    result = Command.usassign_order.execute(words);
                    break;
                case "SUBSCRIBE":
                    checkArgumentsLength(words, 2);
                    result = Command.subscribe.execute(words);
                    break;
                case "UNSUBSCRIBE":
                    checkArgumentsLength(words, 2);
                    result = Command.unsubscribe.execute(words);
                    break;
                case "DISTRIBUTEORDERS":
                    checkArgumentsLength(words, 1);
                    result = Command.distribute_orders.execute(words);
                    break;
                case "CLEAR":
                    checkArgumentsLength(words, 1);
                    result = Command.clear.execute(words);
                    break;
                case "EXIT":
                    checkArgumentsLength(words, 1);
                    result = Command.exit.execute(words);
                    break;
                case "666":
                    result = ANSI_RED + Command.easter_egg_666.execute(words) + ANSI_RESET;
                    break;
                default:
                    throw new UnknownCommandException("Command " + words[0] + " doesn't exist!");
            }
        }
        catch (IllegalLoginOrPasswordException | IllegalTransitionException | ObjectNotFoundException |
                UnknownCommandException | UserNotFoundException | WrongCommandArgumentsException e)
        {
            result = ANSI_RED + e.getMessage() + ANSI_RESET;
        }
        catch (Exception e)
        {
            result = ANSI_RED + e.getMessage() + ANSI_RESET;
        }
        return result;
    }

}
