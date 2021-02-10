package view;

import controller.Controller;
import controller.exceptions.UserNotFoundException;
import model.Model;
import model.entities.Customer;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;
import model.enums.ServiceStatus;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CustomerView
{

    final static Scanner scanner = new Scanner(System.in);

    public static void start(Customer customer) throws Exception
    {
        int input;
        do
        {
            System.out.println( customer.getFirstName()+" "+customer.getLastName()+"        Balance: "+customer.getBalance()+" RUB");
            System.out.println( "What do you wanna do ?");
            System.out.println( "1) Services management");
            System.out.println( "2) Orders management");
            System.out.println( "3) Specification management");
            System.out.println( "4) Account settings");
            System.out.println( "0) Exit");
            System.out.println( " Enter the number to select: ");
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
                    //logout
                    break;
                }
                default:
                    throw new Exception("You entered invalid command. Try again");
            }

        } while (input != 0);
    }

    public static void showAccountSettings(Customer customer) throws IOException, UserNotFoundException
    {
        int chose;
        do
        {
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + "        Balance: " + customer.getBalance() + " RUB");
            System.out.println("1) Change password");
            System.out.println("2) Top up balance");
            System.out.println("0) Beck");
            System.out.println( " Enter the number to select: ");
            Scanner scanner = new Scanner(System.in);
            chose = scanner.nextInt();
            switch (chose){

                case 1 : changePassword(customer);
                    break;
                case 2 : topUpBalance(customer);
                    break;
                case 0 :
                    break;
                default: System.out.println( " Error number, enter correct number ");
                    showAccountSettings(customer);
                    break;
            }
        }while (chose!=0);

    }

    private static void changePassword(Customer customer) throws IOException, UserNotFoundException
    {
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        String password;
        do
        {
            String password2;
            System.out.println("Password change");
            System.out.println("0) Back");
            System.out.print("Enter new password:");
            password = scanner.nextLine();
            switch (password){
                case "0":break;
                default:{
                    System.out.print("Repeat new password:");
                    password2 = scanner.nextLine();
                    if(password.equals(password2)){
                        controller.updateCustomer(null,null, customer.getLogin(),password,null,0);
                    }else {
                        System.out.println("Error: mismatch of input values, try again");
                    }
                }
            }
        }while (!password.equals("0"));
    }

    private static void topUpBalance(Customer customer) throws IOException, UserNotFoundException
    {
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the replenishment amount: ");
        float topUp = scanner.nextFloat();
        controller.changeBalanceOn(customer.getLogin(),topUp);
    }

    private static void showSpecifications(Customer customer) throws Exception
    {
        String header = "Choose Specification to show info about:";
        Map<String, Callable> commands = new HashMap<>();
        Model model = Model.getInstance();
        List<Specification> specifications = new ArrayList<>(model.getSpecifications().values());
        for (Specification specification : specifications )
        {
            commands.put(
                    getStringForView(specification),
                    () -> {
                        showConcreteSpecification(specification, customer);
                        return  null;
                    });
        }

        new CustomerCommandsPaginator(header, commands).run( customer);
    }

    private static void showConcreteSpecification(Specification specification, Customer customer) throws Exception
    {
        int choose;
        Controller controller = new Controller();
        do
        {
            System.out.println(getStringForView(specification));
            System.out.println(specification.getDescription());
            System.out.println("1) Connect");
            System.out.println("0) back");
            System.out.println(" Enter the number to select: ");
            Scanner scanner = new Scanner(System.in);
            choose = scanner.nextInt();
            switch (choose){
                case 1:{
                    controller.createNewOrder(customer.getLogin(),specification.getId());
                    System.out.println("Order by connecting successfully created.");
                    choose =0;
                    break;
                }
                case 0:{
                    break;
                }
                default:{
                    throw new Exception("You entered invalid command. Try again");

                }
            }
        }while (choose!=0);
    }

    private static void showOrders(Customer customer) throws Exception
    {
        String header = "Choose Order to show info about:";
        Map<String, Callable> commands = new HashMap<>();
        Controller controller = new Controller();
        List<Order> orders = new ArrayList<>(controller.getCustomerOrders(customer.getLogin()));
        for (Order order : orders )
        {
            commands.put(
                    getStringForView(order),
                    () -> {
                        showConcreteOrder(order, customer);
                        return  null;
                    });
        }

        new CustomerCommandsPaginator(header, commands).run( customer);
    }

    private static void showConcreteOrder(Order order, Customer customer) throws Exception
    {
        int chose;
        Model model = Model.getInstance();
        do
        {
            System.out.println(getStringForView(order));
            System.out.println("1) Show specification info");
            System.out.println("2) Show service info");
            System.out.println("0) back");
            System.out.println(" Enter the number to select: ");
            Scanner scanner = new Scanner(System.in);
            chose = scanner.nextInt();
            switch(chose){
                case 1 :{
                    Specification specification =model.getSpecification(order.getSpecId());
                    System.out.println(getStringForView(specification));
                    System.out.println(specification.getDescription());
                    System.out.println("Enter any key for back");
                    chose = scanner.nextInt();
                    break;
                }
                case 2 :
                {
                    Service service = model.getService(order.getServiceId());
                    System.out.println(getStringForView(service));
                    System.out.println(model.getSpecification(service.getSpecificationId())
                            .getDescription());
                    System.out.println("Enter any key for back");
                    chose = scanner.nextInt();
                    break;
                }
                case 0 :
                {
                    break;
                }
                default:
                {
                    throw new Exception("You entered invalid command. Try again");
                }
            }
        }
        while (chose!=0);

    }

    private static void showServices(Customer customer) throws Exception
    {
        String header = "Choose Service to show info about:";
        Map<String, Callable> commands = new HashMap<>();
        Controller controller = new Controller();
        List<Service> services =(List<Service>) controller.getCustomerServices(customer.getLogin());
        for (Service service : services)
        {
            commands.put(
                    getStringForView(service),
                    () -> {
                        showConcreteService(service, customer);
                        return null;
                    });
        }

        new CustomerCommandsPaginator(header, commands).run( customer);
    }

    private static void showConcreteService(Service service, Customer customer) throws Exception
    {
        Model model = Model.getInstance();
        Controller controller = new Controller();
        int chose;
        do
        {

            System.out.println( customer.getFirstName()+" "+customer.getLastName()+"        Balance: "+customer.getBalance()+" RUB");
            System.out.println(getStringForView(service));
            System.out.println(model.getSpecification(service.getSpecificationId()).getDescription());
            if(service.getServiceStatus()== ServiceStatus.SUSPENDED){
                System.out.println("1) Resume suspended services");
                System.out.println("2) Disconnect");
                System.out.println("0) back");
                System.out.println(" Enter the number to select: ");
            }else if(service.getServiceStatus()== ServiceStatus.ACTIVE){
                System.out.println("1) Suspended");
                System.out.println("2) Disconnect");
                System.out.println("0) back");
                System.out.println(" Enter the number to select: ");
            }else if(service.getServiceStatus()== ServiceStatus.DISCONNECTED){
                System.out.println("1) Connect");
                System.out.println("0) back");
                System.out.println(" Enter the number to select: ");
            }
            Scanner scanner = new Scanner(System.in);
            chose = scanner.nextInt();
            if(service.getServiceStatus()== ServiceStatus.SUSPENDED){
                switch (chose){
                    case 1:{
                        controller.createRestoreOrder(customer.getLogin(),service.getId());//Resume suspended services
                        break;
                    }
                    case 2:{
                        controller.createDisconnectOrder(customer.getLogin(),service.getId());//Disconnect
                        break;
                    }
                    case 0:{
                        break;
                    }
                    default:{
                        throw new Exception("You entered invalid command. Try again");
                    }
                }
            }else if(service.getServiceStatus()== ServiceStatus.ACTIVE){
                switch (chose){
                    case 1:{
                        controller.createSuspendOrder(customer.getLogin(),service.getId());//Suspended
                        break;
                    }
                    case 2:{
                        controller.createDisconnectOrder(customer.getLogin(),service.getId());//Disconnect
                        break;
                    }
                    case 0:{
                        break;
                    }
                    default:{
                        throw new Exception("You entered invalid command. Try again");
                    }
                }
            }else if(service.getServiceStatus()== ServiceStatus.DISCONNECTED){
                switch (chose){
                    case 1:{
                        controller.createRestoreOrder(customer.getLogin(),service.getId());//Connect - правильно ли ???
                        break;
                    }
                    case 0:{
                        break;
                    }
                    default:{
                        throw new Exception("You entered invalid command. Try again");
                    }
                }
            }
        }while (chose !=0);


    }

    private static class CustomerCommandsPaginator
    {
        private final String header; // заголовок списка
        private final Map<String, Callable> commands;
        int pagesNumber;

        CustomerCommandsPaginator(String header, Map<String, Callable> commands)
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

        public void run(Customer customer) throws Exception
        {
            int input;
            int pagesNumber =1;
            do
            {
                printPage(pagesNumber); // отрисовывает страницу
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                ArrayList<String> commandNames = getNamesStringsByPageNumber(pagesNumber);

                if(input<=commandNames.size() && input!=0){
                    new Thread(new FutureTask(commands.get(commandNames.get(input-1)))).run();
                }else{
                    if(pagesNumber==1){
                        switch (input){
                            case 9:{
                                 ++pagesNumber;
                                break;
                            }
                            case 0:{

                                break;
                            }
                            default:
                                throw new Exception("You entered invalid command. Try again");
                        }
                    }else if(pagesNumber==this.pagesNumber){
                        switch (input){
                            case 8:{
                                 --pagesNumber;
                                break;
                            }
                            case 0:{
                                break;
                            }
                            default:
                                throw new Exception("You entered invalid command. Try again");
                        }
                    }else {
                        switch (input){
                            case 8:{
                                --pagesNumber;
                                break;
                            }
                            case 9:{
                               ++pagesNumber;
                                break;
                            }
                            case 0:{
                                break;
                            }
                            default:
                                throw new Exception("You entered invalid command. Try again");
                        }
                    }
                }

            }while (input !=0);
        }

        private void printPage(int pagesNumber)
        {
            System.out.println(header);
            int indexOfFistCommandNameOnPage=(pagesNumber-1)*7;
            int indexOfCommandName=0;
            int numberCommandNameOnPage =1;
            if(commands.size()==0){
                System.out.println("List is empty !");
            }
            if(this.pagesNumber>1){
                for (String commandName : commands.keySet())
                {
                    if(indexOfCommandName >= indexOfFistCommandNameOnPage && indexOfCommandName < indexOfFistCommandNameOnPage+7 ){
                        System.out.println(numberCommandNameOnPage++ + ") " + commandName);
                    }

                    indexOfCommandName++;
                }
                System.out.println("Number of current page: " + pagesNumber + "/" + this.pagesNumber);
                if(pagesNumber==1){
                    System.out.println("9) Next page");
                }else if(pagesNumber==this.pagesNumber){
                    System.out.println("8) Previous page");
                }else {
                    System.out.println("8) Previous page");
                    System.out.println("9) Next page");
                }
            }
            else{
                for (String commandName : commands.keySet())
                {
                        System.out.println(numberCommandNameOnPage++ + ") " + commandName);
                }
            }
            System.out.println("0) back");
            System.out.println(" Enter the number to select: ");
        }

        private  ArrayList<String> getNamesStringsByPageNumber(int pagesNumber){
            int indexOfFistCommandNameOnPage=(pagesNumber-1)*7;
            int indexOfCommandName=0;
            ArrayList<String> commandNames = new ArrayList<>();
            for (String commandName : commands.keySet())
            {
                if(indexOfCommandName >= indexOfFistCommandNameOnPage && indexOfCommandName < indexOfFistCommandNameOnPage+7 ){
                    commandNames.add(commandName);
                }

                indexOfCommandName++;
            }
            return commandNames;
        }
    }

    public static String getStringForView(Object object) throws IOException // мб вмето обджекта дб интерфейс аркер
    {
        Model model = Model.getInstance();
        String ret = null;
        if (object instanceof Order){
            Order order = (Order) object;
            ret ="Name of specification: " + model.getSpecification(order.getSpecId()).getName() + ", order status: " + order.getOrderStatus() + ", order aim: " + order.getOrderAim() ;
        }else if(object instanceof Service){
            Service service = (Service) object;
            ret = "Name of specification: " + model.getSpecification(service.getSpecificationId()).getName() +", pay day: " +service.getPayDay() +", price :" + model.getSpecification(service.getSpecificationId()).getPrice() + ", status:" +service.getServiceStatus();
        }else if(object instanceof Specification){
            Specification specification =(Specification) object;
            ret = specification.getName();
        }
        return ret;
    }
}
