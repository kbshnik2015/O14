package controller.command;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import controller.exceptions.WrongCommandArgumentsException;
import controller.managers.WorkWaitersManager;
import lombok.ToString;
import model.Model;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;
import model.enums.EmployeeStatus;

@ToString
public enum Command
{

    create_customer
            {
                @Override
                public String execute(String[] args)
                        throws IllegalLoginOrPasswordException, WrongCommandArgumentsException, IOException
                {
                    Controller controller = new Controller();
                    String firstName = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    String lastName = !args[2].equalsIgnoreCase("null") ? args[2] : null;
                    String login = !args[3].equalsIgnoreCase("null") ? args[3] : null;
                    String password = !args[4].equalsIgnoreCase("null") ? args[4] : null;
                    String address = !args[5].equalsIgnoreCase("null") ? args[5] : null;
                    Float balance = parseToFloat(args[6]);

                    Customer customer = controller
                            .createCustomer(firstName, lastName, login, password, address, balance);

                    return "Customer was created: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + customer.getLogin() + ").";
                }
            },

    create_employee
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, IllegalLoginOrPasswordException
                {
                    Controller controller = new Controller();
                    String firstName = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    String lastName = !args[2].equalsIgnoreCase("null") ? args[2] : null;
                    String login = !args[3].equalsIgnoreCase("null") ? args[3] : null;
                    String password = !args[4].equalsIgnoreCase("null") ? args[4] : null;
                    EmployeeStatus employeeStatus = parseToEmployeeStatus(args[5]);

                    Employee employee = controller.createEmployee(firstName, lastName, login, password, employeeStatus);

                    return "Employee was created: " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ").";
                }
            },

    update_customer
            {
                @Override
                public String execute(String[] args)
                        throws IOException, UserNotFoundException, WrongCommandArgumentsException
                {
                    Controller controller = new Controller();
                    String firstName = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    String lastName = !args[2].equalsIgnoreCase("null") ? args[2] : null;
                    String login = !args[3].equalsIgnoreCase("null") ? args[3] : null;
                    String password = !args[4].equalsIgnoreCase("null") ? args[4] : null;
                    String address = !args[5].equalsIgnoreCase("null") ? args[5] : null;
                    Float balance = Command.parseToFloat(args[6]);

                    Customer customer = controller.getModel().getCustomer(login);
                    controller.updateCustomer(firstName, lastName, login, password, address, balance);

                    return "Customer was updated: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + customer.getLogin() + ").";
                }
            },

    update_employee
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, UserNotFoundException, IOException
                {
                    Controller controller = new Controller();
                    String firstName = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    String lastName = !args[2].equalsIgnoreCase("null") ? args[2] : null;
                    String login = !args[3].equalsIgnoreCase("null") ? args[3] : null;
                    String password = !args[4].equalsIgnoreCase("null") ? args[4] : null;
                    EmployeeStatus employeeStatus = parseToEmployeeStatus(args[5]);

                    Employee employee = controller.getModel().getEmployee(login);
                    controller.updateEmployee(firstName, lastName, login, password, employeeStatus);

                    return "Employee was updated: " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ").";
                }
            },

    create_district
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    String name = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    BigInteger parentId = parseToBigInteger(args[2]);

                    District district = controller.createDistrict(name, parentId);

                    return "District was created: " + district.getName() + " (id: " + district.getId() + ").";
                }
            },

    update_district
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger districtId = parseToBigInteger(args[1]);
                    String name = !args[2].equalsIgnoreCase("null") ? args[2] : null;
                    BigInteger parentId = parseToBigInteger(args[3]);

                    District district = controller.getModel().getDistrict(districtId);
                    controller.updateDistrict(districtId, name, parentId);

                    return "District was updated: " + district.getName() + " (id: " + district.getId() + ").";
                }
            },

    create_specification
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    String name = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    float price = Command.parseToFloat(args[2]);
                    String description = !args[3].equalsIgnoreCase("null") ? args[3] : null;
                    boolean isAddressDepended = Command.parseToBoolean(args[4]);
                    ArrayList<BigInteger> districtsIds = Command.parseToBigIntegerArrayList(args[5]);

                    Specification specification = controller
                            .createSpecification(name, price, description, isAddressDepended, districtsIds);

                    return "Specification was created: name : " + specification.getName() +
                            ", id: " + specification.getId() + ")." + "price: " + specification.getPrice() +
                            " , description: " + specification.getDescription();
                }
            },

    update_specification
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger specId = Command.parseToBigInteger(args[1]);
                    String name = !args[2].equalsIgnoreCase("null") ? args[2] : null;
                    float price = Command.parseToFloat(args[3]);
                    String description = !args[4].equalsIgnoreCase("null") ? args[4] : null;
                    boolean isAddressDepended = Command.parseToBoolean(args[5]);
                    ArrayList<BigInteger> districtsIds = Command.parseToBigIntegerArrayList(args[6]);

                    controller.updateSpecification(specId, name, price, description, isAddressDepended, districtsIds);
                    Specification specification = controller.getModel().getSpecification(specId);

                    return "Specification was updated: name : " + specification.getName() +
                            ", id: " + specification.getId() + ")." + "price: " + specification.getPrice() +
                            " , description: " + specification.getDescription();
                }
            },

    delete_customer_cascade
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.deleteCustomerCascade(login);

                    return "Customer (login: " + login + ") was cascade deleted.";
                }
            },

    delete_employee_cascade
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.deleteEmployeeCascade(login);

                    return "Employee (login: " + login + ") was cascade deleted.";
                }
            },

    delete_employee
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.deleteEmployee(login);

                    return "Employee (login: " + login + ") was deleted.";
                }
            },

    delete_order_cascade
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger orderId = Command.parseToBigInteger(args[1]);

                    controller.deleteOrderCascade(orderId);

                    return "Order (id: " + orderId + ") was cascade deleted";
                }
            },

    delete_specification_cascade
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger specId = Command.parseToBigInteger(args[1]);

                    controller.deleteSpecificationCascade(specId);

                    return "Specification (id: " + specId + ") was cascade deleted";
                }
            },

    delete_district_cascade
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger districtId = Command.parseToBigInteger(args[1]);

                    controller.deleteDistrictCascade(districtId);

                    return "District (id: " + districtId + ") was cascade deleted";
                }
            },

    delete_district
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger districtId = Command.parseToBigInteger(args[1]);
                    boolean isLeaveChildrenInHierarchy = Command.parseToBoolean(args[2]);

                    controller.deleteDistrict(districtId, isLeaveChildrenInHierarchy);

                    return "District (id: " + districtId + ") was deleted.";
                }
            },

    delete_service
            {
                @Override
                public String execute(String[] args) throws IOException, WrongCommandArgumentsException
                {
                    Controller controller = new Controller();
                    BigInteger serviceId = Command.parseToBigInteger(args[1]);

                    controller.getModel().deleteService(serviceId);

                    return "Service (id: " + serviceId + ") was deleted";
                }
            },

    get_model
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    return "Model: " + controller.getModel().toString();
                }
            },

    get_customers
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    String result = "Customers: \n";
                    for (Customer customer : controller.getModel().getCustomers().values())
                    {
                        result = result.concat("\t" + customer.getLogin() + ": " + customer.getFirstName() + " " +
                                customer.getLastName() + "\n");
                    }

                    return result;
                }
            },

    get_employees
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    String result = "Employees: \n";
                    for (Employee employee : controller.getModel().getEmployees().values())
                    {
                        result = result.concat("\t" + employee.getLogin() + ": " + employee.getFirstName() + " " +
                                employee.getLastName() + ", status: " + employee.getEmployeeStatus() + "\n");
                    }

                    return result;
                }
            },

    get_specifications
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    String result = "Specifications: \n";
                    for (Specification specification : controller.getModel().getSpecifications().values())
                    {
                        result = result.concat("\t" + specification.getId() + ": name: " + specification.getName() +
                                ", description: " + specification.getDescription() + ", price: " +
                                specification.getPrice() + "\n");
                    }

                    return result;
                }
            },

    get_orders
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    String result = "Orders: \n";
                    for (Order order : controller.getModel().getOrders().values())
                    {
                        Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());
                        result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() +
                                ", status: " + order.getOrderStatus() +
                                ", Customer: " + customer.getFirstName() + " " + customer.getLastName() +
                                " (login: " + order.getCustomerLogin() +
                                "), specification id: " + order.getSpecId() + "\n");
                    }

                    return result;
                }
            },

    get_districts
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    String result = "Districts: \n";
                    for (District district : controller.getModel().getDistricts().values())
                    {
                        result = result.concat("\t" + district.getId() + ": " + district.getName() + "\n");
                    }

                    return result;
                }
            },

    get_services
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    String result = "Services: \n";
                    for (Service service : controller.getModel().getServices().values())
                    {
                        Customer customer = controller.getModel().getCustomer(service.getCustomerLogin());
                        result = result.concat("\t" + service.getId() + ": " + customer.getFirstName() + " " +
                                customer.getLastName() + " (login: " + service.getCustomerLogin() +
                                "), specification: " +
                                controller.getModel().getSpecification(service.getSpecificationId()).getName() +
                                " (id: " + service.getSpecificationId() + "), status: " + service.getServiceStatus() +
                                "\n");
                    }

                    return result;
                }
            },

    get_customer_info
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    Customer customer = controller.getModel().getCustomer(login);

                    return "Customer: " + customer.getLogin() + "\n"
                            + "\tFirst name: " + customer.getFirstName() + "\n"
                            + "\tLast name: " + customer.getLastName() + "\n"
                            + "\tPassword: " + customer.getPassword() + "\n"
                            + "\tAddress: " + customer.getAddress() + "\n"
                            + "\tBalance: " + customer.getBalance() + "\n";
                }
            },

    get_employee_info
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Employee: " + employee.getLogin() + "\n"
                            + "\tFirst name: " + employee.getFirstName() + "\n"
                            + "\tLast name: " + employee.getLastName() + "\n"
                            + "\tPassword: " + employee.getPassword() + "\n"
                            + "\tStatus: " + employee.getEmployeeStatus() + "\n"
                            + "\tWaiting for orders: " + employee.isWaitingForOrders() + "\n";
                }
            },

    get_order_info
            {
                @Override
                public String execute(String[] args) throws IOException, WrongCommandArgumentsException
                {
                    Controller controller = new Controller();
                    BigInteger id = Command.parseToBigInteger(args[1]);

                    Order order = controller.getModel().getOrder(id);
                    Specification specification = controller.getModel().getSpecification(order.getSpecId());
                    Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());

                    String result = "Order: " + order.getId() + "\tCustomer: " + customer.getFirstName() + " " +
                            customer.getLastName() + " (login: " + order.getCustomerLogin() + ")" + "\n\tEmployee: ";
                    if (order.getEmployeeLogin() != null)
                    {
                        Employee employee = controller.getModel().getEmployee(order.getEmployeeLogin());
                        result = result.concat(employee.getFirstName() + " " + employee.getLastName() +
                                " (login: " + order.getEmployeeLogin() + ")");
                    }
                    result = result.concat("\n\tAim: " + order.getOrderAim() +
                            "\n\tStatus: " + order.getOrderStatus() +
                            "\n\tSpecification:" +
                            "\n\t\tid: " + order.getSpecId() +
                            "\n\t\tname: " + specification.getName() +
                            "\n\t\tprice: " + specification.getPrice() +
                            "\n\t\tdescription: " + specification.getDescription() +
                            "\n\t\taddress depended: " + specification.isAddressDepended() +
                            "\n\tService:");
                    if (order.getServiceId() != null)
                    {
                        Service service = controller.getModel().getService(order.getServiceId());
                        result = result.concat(" id: " + order.getServiceId() + ", status: " +
                                service.getServiceStatus() + ", pay day: " + service.getPayDay() + "\n");
                    }

                    return result;
                }
            },

    get_service_info
            {
                @Override
                public String execute(String[] args) throws IOException, WrongCommandArgumentsException
                {
                    Controller controller = new Controller();
                    BigInteger id = Command.parseToBigInteger(args[1]);

                    Service service = controller.getModel().getService(id);
                    Specification specification = controller.getModel().getSpecification(service.getSpecificationId());
                    Customer customer = controller.getModel().getCustomer(service.getCustomerLogin());

                    return "Service: " + service.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + service.getCustomerLogin() + ")" + "\n"
                            + "\tSpecification: id: " + service.getSpecificationId() + ", name: " +
                            specification.getName() + ", price:" + specification.getPrice() +
                            ", description: " + specification.getDescription() +
                            ", address depended: " + specification.isAddressDepended() +
                            "\n\tStatus: " + service.getServiceStatus() +
                            "\n\tPay day: " + service.getPayDay() + "\n";
                }
            },

    get_specification_info
            {
                @Override
                public String execute(String[] args) throws IOException, WrongCommandArgumentsException
                {
                    Controller controller = new Controller();
                    BigInteger id = Command.parseToBigInteger(args[1]);

                    Specification specification = controller.getModel().getSpecification(id);

                    String result = "Specification: " + specification.getId() + "\n"
                            + "\tName: " + specification.getName() + "\n"
                            + "\tDescription: " + specification.getDescription() + "\n"
                            + "\tPrice: " + specification.getPrice() + "\n"
                            + "\tAddress depended : " + specification.isAddressDepended() + "\n";
                    if (specification.isAddressDepended() && specification.getDistrictsIds() != null)
                    {
                        result = result.concat("\tDistricts: ");
                        for (BigInteger districtId : specification.getDistrictsIds())
                        {
                            District district = controller.getModel().getDistrict(districtId);
                            result = result.concat(district.getName() + " (id: " + district.getId() + "), ");
                        }
                    }

                    return result;
                }
            },

    get_district_info
            {
                @Override
                public String execute(String[] args) throws IOException, WrongCommandArgumentsException
                {
                    Controller controller = new Controller();
                    BigInteger id = Command.parseToBigInteger(args[1]);

                    District district = controller.getModel().getDistrict(id);

                    String result = "District: " + district.getId() + "\n"
                            + "\tName: " + district.getName() + "\n"
                            + "\tParent: ";
                    if (district.getParentId() != null)
                    {
                        result = result
                                .concat(controller.getModel().getDistrict(district.getParentId()).getName() +
                                        " (id: " + district.getParentId() + ") ");
                    }
                    result = result.concat(" \n\tChildren: ");
                    if (controller.getDistrictChildrens(district.getId()) != null)
                    {
                        for (District children : controller.getDistrictChildrens(district.getId()))
                        {
                            result = result.concat(children.getName() + " (id: " + children.getId() + "), ");
                        }
                    }
                    else
                    {
                        result = result.concat(" \n");
                    }

                    return result;
                }
            },

    create_new_order
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, UserNotFoundException,
                        ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    String customerLogin = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    BigInteger specId = Command.parseToBigInteger(args[2]);

                    Order order = controller.createNewOrder(customerLogin, specId);

                    Specification specification = controller.getModel().getSpecification(order.getSpecId());
                    Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());

                    return "New order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerLogin() + ")" + "\n"
                            + "\tAim: " + order.getOrderAim() + "\n"
                            + "\tStatus: " + order.getOrderStatus() + "\n"
                            + "\tSpecification: id: " + order.getSpecId() + ", name: " + specification.getName() +
                            ", price: " + specification.getPrice() +
                            ", description: " + specification.getDescription() +
                            ", address depended: " + specification.isAddressDepended() + "\n";
                }
            },

    create_suspend_order
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    String customerLogin = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    BigInteger serviceId = Command.parseToBigInteger(args[2]);

                    Order order = controller.createSuspendOrder(customerLogin, serviceId);

                    Specification specification = controller.getModel().getSpecification(order.getSpecId());
                    Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());
                    Service service = controller.getModel().getService(order.getServiceId());

                    return "Suspend order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerLogin() + ")" + "\n"
                            + "\tAim: " + order.getOrderAim() + "\n"
                            + "\tStatus: " + order.getOrderStatus() + "\n"
                            + "\tSpecification: id: " + order.getSpecId() + ", name: " + specification.getName() +
                            ", price: " + specification.getPrice() +
                            ", description: " + specification.getDescription() +
                            ", address depended: " + specification.isAddressDepended() + "\n"
                            + "\tService: id: " + order.getServiceId() + ", status: " + service.getServiceStatus() +
                            ", pay day: " + service.getPayDay() + "\n";
                }
            },

    create_restore_order
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    String customerLogin = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    BigInteger serviceId = Command.parseToBigInteger(args[2]);

                    Order order = controller.createRestoreOrder(customerLogin, serviceId);

                    Specification specification = controller.getModel().getSpecification(order.getSpecId());
                    Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());
                    Service service = controller.getModel().getService(order.getServiceId());

                    return "Restore order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerLogin() + ")" + "\n"
                            + "\tAim: " + order.getOrderAim() + "\n"
                            + "\tStatus: " + order.getOrderStatus() + "\n"
                            + "\tSpecification: id: " + order.getSpecId() + ", name: " + specification.getName() +
                            ", price: " + specification.getPrice() +
                            ", description: " + specification.getDescription() +
                            ", address depended: " + specification.isAddressDepended() + "\n"
                            + "\tService: id: " + order.getServiceId() + ", status: " + service.getServiceStatus() +
                            ", pay day: " + service.getPayDay() + "\n";
                }
            },

    create_disconnect_order
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    String customerLogin = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    BigInteger serviceId = Command.parseToBigInteger(args[2]);

                    Order order = controller.createDisconnectOrder(customerLogin, serviceId);

                    Specification specification = controller.getModel().getSpecification(order.getSpecId());
                    Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());
                    Service service = controller.getModel().getService(order.getServiceId());

                    return "Disconnect order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerLogin() + ")" + "\n"
                            + "\tAim: " + order.getOrderAim() + "\n"
                            + "\tStatus: " + order.getOrderStatus() + "\n"
                            + "\tSpecification: id: " + order.getSpecId() + ", name: " + specification.getName() +
                            ", price: " + specification.getPrice() +
                            ", description: " + specification.getDescription() +
                            ", address depended: " + specification.isAddressDepended() + "\n"
                            + "\tService: id: " + order.getServiceId() + ", status: " + service.getServiceStatus() +
                            ", pay day: " + service.getPayDay() + "\n";
                }
            },

    start_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger orderId = Command.parseToBigInteger(args[1]);

                    controller.startOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was started.";
                }
            },

    suspend_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger orderId = Command.parseToBigInteger(args[1]);

                    controller.suspendOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was suspended.";
                }
            },

    restore_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger orderId = Command.parseToBigInteger(args[1]);

                    controller.restoreOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was restored.";
                }
            },

    cancel_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger orderId = Command.parseToBigInteger(args[1]);

                    controller.cancelOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was cancelled.";
                }
            },

    complete_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger orderId = Command.parseToBigInteger(args[1]);

                    controller.completeOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was completed.";
                }
            },

    change_balance_on
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    float amountOfMoney = Command.parseToFloat(args[2]);

                    controller.changeBalanceOn(login, amountOfMoney);

                    Customer customer = controller.getModel().getCustomer(login);

                    return "Balance of customer " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + customer.getLogin() + ") was changed by " + amountOfMoney;
                }
            },

    get_free_orders
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();

                    String result = "Free orders: \n";
                    for (Order order : controller.getFreeOrders())
                    {
                        Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());
                        result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + ", status: " +
                                order.getOrderStatus() + ", Customer: " + customer.getFirstName() + " " +
                                customer.getLastName() + " (login: " + order.getCustomerLogin() +
                                "), specification id: " + order.getSpecId() + "\n");
                    }

                    return result;
                }
            },

    get_free_order
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Controller controller = new Controller();
                    Order order = controller.getFreeOrder();

                    Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());

                    return "Free order: \n(id: " + order.getId() + ") " + order.getOrderAim() + ", status: " +
                            order.getOrderStatus() + ", Customer: " + customer.getFirstName() + " " +
                            customer.getLastName() + " (login: " + order.getCustomerLogin() +
                            "), specification id: " + order.getSpecId() + "\n";
                }
            },

    get_customer_orders
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    List<Order> orders = (List<Order>) controller.getCustomerOrders(login);

                    Customer customer = controller.getModel().getCustomer(login);
                    String result = "Orders of customer " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + login + "):\n";
                    if (orders != null)
                    {
                        for (Order order : orders)
                        {
                            result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + ", status: " +
                                    order.getOrderStatus() + ", specification id: " + order.getSpecId() + "\n");
                        }
                    }

                    return result;
                }
            },

    get_customer_services
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    List<Service> services = (List<Service>) controller.getCustomerServices(login);

                    Customer customer = controller.getModel().getCustomer(login);
                    String result = "Services of customer " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + login + "):\n";
                    if (services != null)
                    {
                        for (Service service : services)
                        {
                            result = result.concat("\t" + service.getId() + ": status: " + service.getServiceStatus() +
                                    ", specification id: " + service.getSpecificationId() + ", pay day:" +
                                    service.getPayDay() + "\n");
                        }
                    }

                    return result;
                }
            },

    get_employee_orders
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    List<Order> orders = (List<Order>) controller.getEmployeeOrders(login);

                    Employee employee = controller.getModel().getEmployee(login);
                    String result = "Orders of employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + login + "):\n";
                    if (orders != null)
                    {
                        for (Order order : orders)
                        {
                            Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());
                            result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + " customer: " +
                                    customer.getFirstName() + " " + customer.getLastName() +
                                    " (login: " + order.getCustomerLogin() + "), status: " +
                                    order.getOrderStatus() + ", specification id: " + order.getSpecId() + "\n");
                        }
                    }

                    return result;
                }
            },

    get_customer_connected_services
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    List<Service> services = (List<Service>) controller.getCustomerConnectedServices(login);

                    Customer customer = controller.getModel().getCustomer(login);
                    String result = "Connected services of customer " + customer.getFirstName() + " " +
                            customer.getLastName() + " (login: " + login + ")\n";
                    if (services != null)
                    {
                        for (Service service : services)
                        {
                            result = result.concat("\t" + service.getId() + ": status: " + service.getServiceStatus() +
                                    ", specification id: " + service.getSpecificationId() + ", pay day:" +
                                    service.getPayDay() + "\n");
                        }
                    }

                    return result;
                }
            },

    get_customer_not_finished_orders
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    List<Order> orders = (List<Order>) controller.getCustomerNotFinishedOrders(login);

                    Customer customer = controller.getModel().getCustomer(login);
                    String result = "Not finished orders of customer " + customer.getFirstName() + " " +
                            customer.getLastName() + " (login: " + login + "):\n";
                    if (orders != null)
                    {
                        for (Order order : orders)
                        {
                            result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + ", status: " +
                                    order.getOrderStatus() + ", specification id: " + order.getSpecId() + "\n");
                        }
                    }

                    return result;
                }
            },

    get_orders_of_employees_on_vacation
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();

                    List<Order> orders = controller.getOrdersOfEmployeesOnVacation();
                    String result = "Orders of employees on vacation: \n";
                    if (orders != null)
                    {
                        for (Order order : orders)
                        {
                            Customer customer = controller.getModel().getCustomer(order.getCustomerLogin());
                            result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + " customer: " +
                                    customer.getFirstName() + " " + customer.getLastName() +
                                    " (login: " + order.getCustomerLogin() + "), status: " +
                                    order.getOrderStatus() + ", specification id: " + order.getSpecId() + "\n");
                        }
                    }

                    return result;
                }
            },

    go_on_vacation
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.goOnVacation(login);

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ") went to vacation.";
                }
            },

    return_from_vacation
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.returnFromVacation(login);

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ") was returned from vacation.";
                }
            },

    retire_employee
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.retireEmployee(login);

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ") was retired.";
                }
            },

    assign_order
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, UserNotFoundException,
                        ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    BigInteger orderId = Command.parseToBigInteger(args[2]);

                    controller.assignOrder(login, orderId);

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() +
                            ") was assigned to employee " + employee.getFirstName() + " " +
                            employee.getLastName() + " (login: " + employee.getLogin() + ").";
                }
            },

    process_order
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, UserNotFoundException,
                        ObjectNotFoundException, IllegalTransitionException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;
                    BigInteger orderId = Command.parseToBigInteger(args[2]);

                    controller.processOrder(login, orderId);

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() +
                            ") was started by employee " + employee.getFirstName() + " " +
                            employee.getLastName() + " (login: " + employee.getLogin() + ").";
                }
            },

    usassign_order
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger orderId = Command.parseToBigInteger(args[1]);

                    controller.usassignOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() +
                            ") was usassigned from employee.";
                }
            },

    subscribe
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.setEmployeeWaitingStatus(login, true);

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() + " (login: " +
                            employee.getLogin() + ") was subscribed for getting free orders.";
                }
            },

    unsubscribe
            {
                @Override
                public String execute(String[] args) throws IOException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    String login = !args[1].equalsIgnoreCase("null") ? args[1] : null;

                    controller.setEmployeeWaitingStatus(login, false);

                    Employee employee = controller.getModel().getEmployee(login);

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() + " (login: " +
                            employee.getLogin() + ") was unsubscribed from work waiters.";
                }
            },

    distribute_orders
            {
                @Override
                public String execute(String[] args)
                {
                    WorkWaitersManager.distributeOrders();

                    return "Free orders were distributed between employees waiting for work.";
                }
            },

    clear
            {
                @Override
                public String execute(String[] args)
                {
                    return "\n \n \n \n \n \n \n \n \n \n";
                }
            },

    exit
            {
                @Override
                public String execute(String[] args) throws IOException
                {
                    Model.getInstance().saveToFile();
                    System.exit(0);
                    return "";
                }
            },

    easter_egg_666
            {
                @Override
                public String execute(String[] args)
                {
                    return "             $$$$$$\\   $$$$$$\\   $$$$$$\\                \n" +
                            "            $$  __$$\\ $$  __$$\\ $$  __$$\\               \n" +
                            "            $$ /  \\__|$$ /  \\__|$$ /  \\__|              \n" +
                            "            $$$$$$$\\  $$$$$$$\\  $$$$$$$\\                \n" +
                            "            $$  __$$\\ $$  __$$\\ $$  __$$\\               \n" +
                            "            $$ /  $$ |$$ /  $$ |$$ /  $$ |              \n" +
                            "             $$$$$$  | $$$$$$  | $$$$$$  |              \n" +
                            "             \\______/  \\______/  \\______/               \n" +
                            "                                                        \n" +
                            "                                                        \n" +
                            "                                                        \n" +
                            "$$$$$$$$\\ $$\\   $$\\ $$\\     $$\\ $$$$$$\\        $$$$$$\\  \n" +
                            "$$  _____|$$ | $$  |\\$$\\   $$  |\\_$$  _|      $$  __$$\\ \n" +
                            "$$ |      $$ |$$  /  \\$$\\ $$  /   $$ |        $$ /  \\__|\n" +
                            "$$$$$\\    $$$$$  /    \\$$$$  /    $$ |        $$ |$$$$\\ \n" +
                            "$$  __|   $$  $$<      \\$$  /     $$ |        $$ |\\_$$ |\n" +
                            "$$ |      $$ |\\$$\\      $$ |      $$ |        $$ |  $$ |\n" +
                            "$$ |      $$ | \\$$\\     $$ |$$\\ $$$$$$\\       \\$$$$$$  |\n" +
                            "\\__|      \\__|  \\__|    \\__|\\__|\\______|       \\______/ \n" +
                            "                                                        ";
                }
            };

    private static EmployeeStatus parseToEmployeeStatus(final String arg) throws WrongCommandArgumentsException
    {
        final EmployeeStatus employeeStatus;
        switch (arg.toUpperCase())
        {
            case "WORKING":
                employeeStatus = EmployeeStatus.WORKING;
                break;
            case "ON_VACATION":
                employeeStatus = EmployeeStatus.ON_VACATION;
                break;
            case "RETIRED":
                employeeStatus = EmployeeStatus.RETIRED;
                break;
            case "NULL":
                employeeStatus = null;
                break;
            default:
                throw new WrongCommandArgumentsException("Command argument " + arg + " isn't an Employee Status");
        }
        return employeeStatus;
    }

    private static float parseToFloat(String digit) throws WrongCommandArgumentsException
    {
        try
        {
            return Float.valueOf(digit);
        }
        catch (NumberFormatException e)
        {
            throw new WrongCommandArgumentsException(digit + " isn't a float number!");
        }
    }

    private static BigInteger parseToBigInteger(String digit) throws WrongCommandArgumentsException
    {
        try
        {
            if (digit.equalsIgnoreCase("null"))
            {
                return null;
            }
            long tmp = Long.valueOf(digit);
            return BigInteger.valueOf(tmp);
        }
        catch (NumberFormatException e)
        {
            throw new WrongCommandArgumentsException(digit + " isn't a big integer number!");
        }
    }

    private static ArrayList<BigInteger> parseToBigIntegerArrayList(String digits) throws WrongCommandArgumentsException
    {
        ArrayList<BigInteger> arrayList;

        if (digits.equalsIgnoreCase("null"))
        {
            arrayList = null;
        }
        else
        {
            String[] tmp = digits.split(",");
            arrayList = new ArrayList<>();
            for (String id : tmp)
            {
                arrayList.add(parseToBigInteger(id));
            }
        }

        return arrayList;
    }

    private static boolean parseToBoolean(String arg) throws WrongCommandArgumentsException
    {
        boolean bool;
        switch (arg.toUpperCase())
        {
            case "TRUE":
                bool = true;
                break;
            case "FALSE":
                bool = false;
                break;
            default:
                throw new WrongCommandArgumentsException("Command argument " + arg + " isn't a boolean");
        }
        return bool;
    }

    public abstract String execute(String[] args) throws Exception;

}