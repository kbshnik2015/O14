package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

import controller.Controller;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import model.ModelJson;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;
import model.enums.OrderStatus;
import model.enums.ServiceStatus;


public class CustomerView
{

    final static Scanner scanner = new Scanner(System.in);
    private static final String LINE_BREAKS = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    public static void start(CustomerDTO customer) throws Exception
    {
        int input;

        do
        {
            System.out.println(
                    LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                            customer.getBalance() + " RUB\n");
            System.out.println("What do you wanna do ?\n");
            System.out.println("1) Services management\n");
            System.out.println("2) Orders management\n");
            System.out.println("3) Specification management\n");
            System.out.println("4) Account settings\n\n");
            System.out.println("0) Exit\n");
            System.out.print("Enter the number to select: ");
            input = scanner.nextInt();

            switch (input)
            {
                case 1:
                {
                    showServices(customer);
                    break;
                }
                case 2:
                {
                    showOrders(customer);
                    break;
                }
                case 3:
                {
                    showSpecifications(customer);
                    break;
                }
                case 4:
                {
                    showAccountSettings(customer);
                    break;
                }
                case 0:
                {
                    break;
                }
                default:
                    throw new Exception("You entered invalid command. Try again");
            }

        } while (input != 0);
    }

    public static void showAccountSettings(CustomerDTO customer)
            throws IOException, UserNotFoundException, SQLException, ObjectNotFoundException, DataNotUpdatedWarning
    {
        int input;

        do
        {
            System.out.println(
                    LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                            customer.getBalance() + " RUB" + "\n\nAccount settings\n");
            System.out.println("1) Change password\n");
            System.out.println("2) Top up balance\n\n");
            System.out.println("0) Beck\n");
            System.out.print(" Enter the number to select: ");
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();

            switch (input)
            {
                case 1:
                    changePassword(customer);
                    break;
                case 2:
                    topUpBalance(customer);
                    break;
                case 0:
                    break;
                default:
                    System.out.println(" Error number, enter correct number ");
                    showAccountSettings(customer);
                    break;
            }
        } while (input != 0);
    }

    private static void changePassword(CustomerDTO customer)
            throws IOException, SQLException, ObjectNotFoundException, DataNotUpdatedWarning, UserNotFoundException
    {
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();

        System.out.println(LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB\n");
        System.out.print("Enter old password (0 - back):");
        String oldPassword = scanner.nextLine();

        if (!"0".equals(oldPassword))
        {
            if (oldPassword.equals(customer.getPassword()))
            {
                System.out.print("Enter new password (0 - back):");
                String newPassword = scanner.nextLine();
                if (!newPassword.equals("0"))
                {
                    System.out.print("Repeat new password (0 - back):");
                    String newPassword2 = scanner.nextLine();
                    if (!newPassword2.equals("0"))
                    {
                        if (newPassword.equals(newPassword2))
                        {
                            customer.setPassword(newPassword);
                            controller.getModel().updateCustomer(customer);
                            System.out.println("\nPassword changed successful\n");
                        }
                        else
                        {
                            System.out.println("Error: mismatch of input values, try again");
                        }
                    }
                }
            }
            else
            {
                System.out.println("Password is wrong");
            }
        }
    }

    private static void topUpBalance(CustomerDTO customer)
            throws IOException, UserNotFoundException, SQLException, ObjectNotFoundException, DataNotUpdatedWarning
    {
        Scanner scanner = new Scanner(System.in);
        Controller controller = new Controller();

        System.out.println(LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB\n");
        System.out.print("Enter the replenishment amount: ");

        float topUp = scanner.nextFloat();
        controller.changeBalanceOn(customer.getId(), topUp);
    }

    private static void showSpecifications(CustomerDTO customer) throws Exception
    {
        ModelJson model = ModelJson.getInstance();
        Controller controller = new Controller();
        Map<String, Callable<Void>> commands = new HashMap<>();
        String header = LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB" + "\n\nChoose Specification to show info about:\n";
        List<SpecificationDTO> specifications = controller.getCustomerSpecifications(customer);

        for (SpecificationDTO specification : specifications)
        {
            commands.put(getStringForView(specification), () -> {
                showConcreteSpecification(specification, customer);
                return null;
            });
        }
        new CustomerCommandsPaginator(header, commands).run();
    }


    private static void showConcreteSpecification(SpecificationDTO specification, CustomerDTO customer) throws Exception
    {
        Controller controller = new Controller();

        System.out.println(LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB\n");
        System.out.println(getStringForView(specification) + "\n");
        System.out.println(specification.getDescription());
        System.out.println("\n1) Connect\n\n");
        System.out.println("0) Back\n");
        System.out.print(" Enter the number to select: ");
        Scanner scanner = new Scanner(System.in);

        int input = scanner.nextInt();
        if (input == 1)
        {
            controller.createNewOrder(customer.getId(), specification.getId());
            System.out.println("Order by connecting successfully created.");
        }
        else if (input != 0)
        {
            throw new Exception("You entered invalid command. Try again");
        }
    }

    private static void showOrders(CustomerDTO customer) throws Exception
    {
        Map<String, Callable<Void>> commands = new HashMap<>();
        Controller controller = new Controller();
        List<OrderDTO> orders = new ArrayList<>(controller.getCustomerOrders(customer.getId()));
        String header = LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB" + "\n\nChoose Order to show info about:\n";

        for (OrderDTO order : orders)
        {
            commands.put(getStringForView(order), () -> {
                showConcreteOrder(order, customer);
                return null;
            });
        }

        new CustomerCommandsPaginator(header, commands).run();
    }

    private static void showConcreteOrder(OrderDTO order, CustomerDTO customer) throws Exception
    {
        int chose;
        ModelJson model = ModelJson.getInstance();
        System.out.println(LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB\n");

        do
        {
            System.out.println(getStringForView(order));
            System.out.println("\n1) Show specification info\n");
            System.out.println("2) Show service info\n\n");
            System.out.println("0) Back\n");
            System.out.print(" Enter the number to select: ");
            Scanner scanner = new Scanner(System.in);
            chose = scanner.nextInt();
            switch (chose)
            {
                case 1:
                {
                    System.out.println(
                            LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                                    customer.getBalance() + " RUB\n");
                    SpecificationDTO specification = model.getSpecification(order.getSpecId());
                    System.out.println(getStringForView(specification) + "\n");
                    System.out.println(specification.getDescription() + "\n\n");
                    System.out.print("Enter any key for back: ");
                    chose = scanner.nextInt();
                    break;
                }
                case 2:
                {
                    System.out.println(
                            LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                                    customer.getBalance() + " RUB\n");
                    ServiceDTO service = model.getService(order.getServiceId());
                    if (service == null)
                    {
                        System.out.println("Service information not available, please contact later" + "\n");
                    }
                    else
                    {
                        System.out.println(getStringForView(service) + "\n");
                        System.out.println(model.getSpecification(service.getSpecificationId())
                                .getDescription() + "\n\n");
                    }
                    System.out.print("Enter any key for back: ");
                    chose = scanner.nextInt();
                    break;
                }
                case 0:
                {
                    break;
                }
                default:
                {
                    throw new Exception("You entered invalid command. Try again");
                }
            }
        } while (chose != 0);
    }

    private static void showServices(CustomerDTO customer) throws Exception
    {
        Map<String, Callable<Void>> commands = new HashMap<>();
        Controller controller = new Controller();
        List<ServiceDTO> services = (List<ServiceDTO>) controller.getCustomerServices(customer.getId());
        String header = LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB" + "\n\nChoose Service to show info about:\n";

        for (ServiceDTO service : services)
        {
            commands.put(getStringForView(service), () -> {
                showConcreteService(service, customer);
                return null;
            });
        }

        new CustomerCommandsPaginator(header, commands).run();
    }

    private static void showConcreteService(ServiceDTO service, CustomerDTO customer) throws Exception
    {
        int chose;
        ModelJson model = ModelJson.getInstance();
        Controller controller = new Controller();
        System.out.println(LINE_BREAKS + customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                customer.getBalance() + " RUB\n");

        do
        {
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + "        Balance: " +
                    customer.getBalance() + " RUB");
            System.out.println(getStringForView(service) + "\n");
            System.out.println(model.getSpecification(service.getSpecificationId())
                    .getDescription());
            if (service.getServiceStatus() == ServiceStatus.SUSPENDED)
            {
                System.out.println("\n1) Resume suspended services\n");
                System.out.println("2) Disconnect\n\n");
                System.out.println("0) Back\n");
                System.out.print(" Enter the number to select: ");
            }
            else if (service.getServiceStatus() == ServiceStatus.ACTIVE)
            {
                System.out.println("\n1) Suspended\n");
                System.out.println("2) Disconnect \n\n");
                System.out.println("0) Back\n");
                System.out.print(" Enter the number to select: ");
            }
            else if (service.getServiceStatus() == ServiceStatus.DISCONNECTED)
            {
                System.out.println("\n1) Connect\n");
                System.out.println("0) Back\n");
                System.out.print(" Enter the number to select: ");
            }
            Scanner scanner = new Scanner(System.in);
            chose = scanner.nextInt();
            if (service.getServiceStatus() == ServiceStatus.SUSPENDED)
            {
                switch (chose)
                {
                    case 1:
                    {
                        controller.createRestoreOrder(customer.getId(), service.getId());
                        break;
                    }
                    case 2:
                    {
                        controller.createDisconnectOrder(customer.getId(), service.getId());
                        break;
                    }
                    case 0:
                    {
                        break;
                    }
                    default:
                    {
                        throw new Exception("You entered invalid command. Try again");
                    }
                }
            }
            else if (service.getServiceStatus() == ServiceStatus.ACTIVE)
            {
                switch (chose)
                {
                    case 1:
                    {
                        controller.createSuspendOrder(customer.getId(), service.getId());
                        break;
                    }
                    case 2:
                    {
                        controller.createDisconnectOrder(customer.getId(), service.getId());
                    }
                    case 0:
                    {
                        break;
                    }
                    default:
                    {
                        throw new Exception("You entered invalid command. Try again");
                    }
                }
            }
            else if (service.getServiceStatus() == ServiceStatus.DISCONNECTED)
            {
                switch (chose)
                {
                    case 1:
                    {
                        controller.createRestoreOrder(customer.getId(), service.getId());
                        break;
                    }
                    case 0:
                    {
                        break;
                    }
                    default:
                    {
                        throw new Exception("You entered invalid command. Try again");
                    }
                }
            }
        } while (chose != 0);
    }

    private static class CustomerCommandsPaginator
    {
        private final String header;
        private final Map<String, Callable<Void>> commands;
        int pagesNumber;

        CustomerCommandsPaginator(String header, Map<String, Callable<Void>> commands)
        {
            this.header = header;
            this.commands = commands;
            if (commands.size() > 9)
            {
                pagesNumber = (commands.size() % 7) == 0 ? commands.size() / 7 : (commands.size() / 7 + 1);
            }
            else
            {
                pagesNumber = 1;
            }
        }

        public void run() throws Exception
        {
            int input;
            int pagesNumber = 1;

            do
            {
                printPage(pagesNumber);
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                ArrayList<String> commandNames = getNamesStringsByPageNumber(pagesNumber);

                if (input <= commandNames.size() && input != 0)
                {
                    commands.get(commandNames.get(input - 1)).call();
                }
                else
                {
                    if (pagesNumber == 1)
                    {
                        switch (input)
                        {
                            case 9:
                            {
                                ++pagesNumber;
                                break;
                            }
                            case 0:
                            {

                                break;
                            }
                            default:
                                throw new Exception("You entered invalid command. Try again");
                        }
                    }
                    else if (pagesNumber == this.pagesNumber)
                    {
                        switch (input)
                        {
                            case 8:
                            {
                                --pagesNumber;
                                break;
                            }
                            case 0:
                            {
                                break;
                            }
                            default:
                                throw new Exception("You entered invalid command. Try again");
                        }
                    }
                    else
                    {
                        switch (input)
                        {
                            case 8:
                            {
                                --pagesNumber;
                                break;
                            }
                            case 9:
                            {
                                ++pagesNumber;
                                break;
                            }
                            case 0:
                            {
                                break;
                            }
                            default:
                                throw new Exception("You entered invalid command. Try again");
                        }
                    }
                }
            } while (input != 0);
        }

        private void printPage(int pagesNumber)
        {
            int indexOfFistCommandNameOnPage = (pagesNumber - 1) * 7;
            int indexOfCommandName = 0;
            int numberCommandNameOnPage = 1;
            System.out.println(header);

            if (commands.size() == 0)
            {
                System.out.println("List is empty !\n");
            }
            if (this.pagesNumber > 1)
            {
                for (String commandName : commands.keySet())
                {
                    if (indexOfCommandName >= indexOfFistCommandNameOnPage &&
                            indexOfCommandName < indexOfFistCommandNameOnPage + 7)
                    {
                        System.out.println(numberCommandNameOnPage++ + ") " + commandName + "\n");
                    }

                    indexOfCommandName++;
                }
                System.out.println("Number of current page: " + pagesNumber + "/" + this.pagesNumber + "\n");
                if (pagesNumber == 1)
                {

                    System.out.println("\n9) Next page\n");
                }
                else if (pagesNumber == this.pagesNumber)
                {
                    System.out.println("\n8) Previous page\n");
                }
                else
                {
                    System.out.println("\n8) Previous page\n");
                    System.out.println("9) Next page\n");
                }
            }
            else
            {
                for (String commandName : commands.keySet())
                {
                    System.out.println(numberCommandNameOnPage++ + ") " + commandName + "\n");
                }
            }
            System.out.println("\n0) Back\n");
            System.out.print(" Enter the number to select: ");
        }

        private ArrayList<String> getNamesStringsByPageNumber(int pagesNumber)
        {
            ArrayList<String> commandNames = new ArrayList<>();
            int indexOfFistCommandNameOnPage = (pagesNumber - 1) * 7;
            int indexOfCommandName = 0;

            for (String commandName : commands.keySet())
            {
                if (indexOfCommandName >= indexOfFistCommandNameOnPage &&
                        indexOfCommandName < indexOfFistCommandNameOnPage + 7)
                {
                    commandNames.add(commandName);
                }

                indexOfCommandName++;
            }

            return commandNames;
        }
    }

    public static String getStringForView(Object object) throws IOException, ObjectNotFoundException
    {
        ModelJson model = ModelJson.getInstance();
        String ret = null;

        if (object instanceof OrderDTO)
        {
            OrderDTO order = (OrderDTO) object;
            ret = "Name of specification: " + model.getSpecification(order.getSpecId())
                    .getName() + ", order status: ";
            if (order.getOrderStatus()
                    .equals(OrderStatus.ENTERING))
            {
                ret += ("processed");
            }
            else if (OrderStatus.SUSPENDED.equals(order.getOrderStatus()))
            {
                ret += ("suspend");
            }
            else if (OrderStatus.COMPLETED.equals(order.getOrderStatus()))
            {
                ret += ("completed");
            }
            else if (OrderStatus.CANCELLED.equals(order.getOrderStatus()))
            {
                ret += ("cancelled");
            }
        }
        else if (object instanceof ServiceDTO)
        {
            ServiceDTO service = (ServiceDTO) object;
            ret = "Name of specification: " + model.getSpecification(service.getSpecificationId())
                    .getName() + ", pay day: " + service.getPayDay() + ", price: " +
                    model.getSpecification(service.getSpecificationId())
                            .getPrice() + ", status: ";
            if (ServiceStatus.ACTIVE.equals(service.getServiceStatus()))
            {
                ret += " active";
            }
            else if (ServiceStatus.PAY_MONEY_SUSPENDED.equals(service.getServiceStatus()))
            {
                ret += " not enough money\n\n Please top up your balance to activate the service";
            }
            else if (ServiceStatus.SUSPENDED.equals(service.getServiceStatus()))
            {
                ret += " suspend";
            }
            else if (ServiceStatus.DISCONNECTED.equals(service.getServiceStatus()))
            {
                ret += " disconnected";
            }
        }
        else if (object instanceof SpecificationDTO)
        {
            SpecificationDTO specification = (SpecificationDTO) object;
            ret = specification.getName();
        }

        return ret;
    }
}
