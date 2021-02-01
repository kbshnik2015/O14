import java.util.Scanner;

import controller.command.Command;
import controller.command.CommandParser;
import controller.exceptions.UnknownCommandException;
import model.Model;

public class Test
{
    public static void main(String[] args)
            throws Exception, UnknownCommandException
    {

        Model model = Model.getInstance();

        //        Customer customer1 = new Customer();
        //        customer1.setFirstName("Customer1");
        //        customer1.setLogin("cust1");
        //        Customer customer2 = new Customer();
        //        customer2.setFirstName("Customer2");
        //        customer2.setLogin("cust2");
        //        customer2.setAddress("ADDRESS 2 2 2");
        //        Customer customer3 = new Customer();
        //        customer3.setFirstName("Customer3");
        //        customer3.setLogin("cust3");
        //        model.createCustomer(customer1);
        //        model.createCustomer(customer2);
        //        model.createCustomer(customer3);
        //
        //        Employee employee1 = new Employee();
        //        employee1.setFirstName("Employee 1");
        //        employee1.setLogin("emp1");
        //        Employee employee2 = new Employee();
        //        employee2.setFirstName("Employee 2");
        //        employee2.setLogin("emp2");
        //        Employee employee3 = new Employee();
        //        employee3.setFirstName("Employee 3");
        //        employee3.setLogin("emp3");
        //        model.createEmployee(employee1);
        //        model.createEmployee(employee2);
        //        model.createEmployee(employee3);
        ////        model.getWorkWaiters().subscribe(employee1.getLogin());
        ////        model.getWorkWaiters().subscribe(employee2.getLogin());
        //
        //        Order order1 = new Order();
        //        order1.setCustomerLogin("custLogOrd1");
        //        order1.setEmployeeLogin("empLogOrd1");
        //        Order order2 = new Order();
        //        order2.setCustomerLogin("custLogOrd2");
        //        Order order3 = new Order();
        //        model.createOrder(order1);
        //        model.createOrder(order2);
        //        model.createOrder(order3);
        //
        //        Service service1 = new Service();
        //        service1.setServiceStatus(ServiceStatus.SUSPENDED);
        //        Service service2 = new Service();
        //        service2.setServiceStatus(ServiceStatus.DISCONNECTED);
        //        model.createService(service1);
        //        model.createService(service2);
        //
        //        model.saveToFile();
        //
        ////        System.out.println(model.toString());
        ////        System.out.println(model.getOrders().toString());
        ////        System.out.println(model.getServices().toString());
        //         model.saveToFile();
        //
        //        model.getCustomers().clear();
        //        model.getEmployees().clear();
        //
        //        System.out.println();

        //        System.out.println(model.toString());
        //
        //        model = model.loadFromFile();
        //
        ////        System.out.println(model.getCustomers().toString());
        //////        System.out.println(model.getCustomerByLogin("cust2").getFirstName());
        ////        System.out.println(model.getEmployees().toString());
        ////        System.out.println(model.getEmployeeByLogin("emp2").getFirstName());
        //        System.out.println();
        //        System.out.println(model.toString());
        //
        //        System.out.println();
        //                System.out.println(model.getCustomers().toString());
        //                System.out.println(model.getCustomerByLogin("cust2").getFirstName());
        //                System.out.println(model.getEmployees().toString());
        //                System.out.println(model.getEmployeeByLogin("emp2").getFirstName());
        //        System.out.println();
        //
        //        System.out.println(model.getOrders().toString());
        //        System.out.println(model.getServices().toString());

        //                Customer customer4 = new Customer();
        //                customer4.setFirstName("Customer4");
        //                customer4.setLogin("cust4");
        //        model.createCustomer(customer4);
        //        System.out.println();
        //        System.out.println(model.toString());
        //
        //        model.saveToFile();
        //
        //        model.getCustomers().clear();
        //        model.getEmployees().clear();
        //        model.getServices().clear();
        //        model.getOrders().clear();

        //        System.out.println();
        //        System.out.println(model.toString());
        //


        //        System.out.println();
        //        System.out.println(model.toString() + "\n");
        //        WorkWaiters workWaiters = new WorkWaiters();
        //        workWaiters.subscribe(model, "emp1");
        //        workWaiters.subscribe(model, "emp2");
        //        System.out.println(workWaiters.getEmployeesWaitingForOrders(model).toString() + "\n");
        //        workWaiters.distributeOrders(model);
        //
        //
        //        workWaiters.distributeOrders(model);
        //        System.out.println(model.toString() + "\n");
        //        System.out.println(workWaiters.getEmployeesWaitingForOrders(model).toString() + "\n");

        //        System.out.println(model.toString() + "\n");
        //
        //        CommandParser
        //                .parse("       createCustomer    firstName   lastName   newLoginCustomer passw Gorod,ulica,dom23 300          ");
        //
        //        System.out.println(model.toString() + "\n");
        //
        //        CommandParser.parse("creaTedisTRict District1 null null");
        //        CommandParser.parse("creaTedisTRict District1 null null");
        //        CommandParser.parse("creaTedisTRict District1 null null");
        //        CommandParser.parse("getdistriCts");
        //
        //        CommandParser.parse("UPDATEDISTRICT 1 District1 null null");
        //        CommandParser.parse("UPDATEDISTRICT 2 District1 null 1");
        //        CommandParser.parse("UPDATEDISTRICT 3 District1 2 1");
        //        CommandParser.parse("getdistriCts");

        //        CommandParser.parse("heLP");
        while(true){
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            System.out.println(CommandParser.parse(cmd));
        }


    }

}

