package controller.command;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
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
import model.database.exceptions.DataNotCreatedWarning;
import model.database.exceptions.DataNotFoundWarning;
import model.database.exceptions.DataNotUpdatedWarning;
import model.dto.CustomerDTO;
import model.dto.DistrictDTO;
import model.dto.EmployeeDTO;
import model.dto.OrderDTO;
import model.dto.ServiceDTO;
import model.dto.SpecificationDTO;
import model.enums.EmployeeStatus;

@SuppressWarnings("SameParameterValue")
@ToString
public enum Command
{
    create_customer
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, ObjectNotFoundException, DataNotCreatedWarning,
                        IllegalLoginOrPasswordException
                {
                    Controller controller = new Controller();
                    CustomerDTO customerDTO = new CustomerDTO();

                    customerDTO.setFirstName(getValueInSting(args, 1));
                    customerDTO.setLastName(getValueInSting(args, 2));
                    customerDTO.setLogin(getValueInSting(args, 3));
                    customerDTO.setPassword(getValueInSting(args, 4));
                    customerDTO.setAddress(getValueInSting(args, 5));
                    customerDTO.setBalance(getValueInFloat(args, 6));

                    controller.getModel().createCustomer(customerDTO);

                    return "Customer was created: " + customerDTO.getFirstName() + " " + customerDTO.getLastName() +
                            " (id: " + customerDTO.getId() + ").";
                }


            },

    create_employee
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, ObjectNotFoundException,
                        DataNotCreatedWarning, IllegalLoginOrPasswordException
                {
                    Controller controller = new Controller();
                    EmployeeDTO employeeDTO = new EmployeeDTO();

                    employeeDTO.setFirstName(getValueInSting(args, 1));
                    employeeDTO.setLastName(getValueInSting(args, 2));
                    employeeDTO.setLogin(getValueInSting(args, 3));
                    employeeDTO.setPassword(getValueInSting(args, 4));
                    employeeDTO.setEmployeeStatus(parseToEmployeeStatus(args[5]));

                    controller.getModel().createEmployee(employeeDTO);

                    return "Employee was created: " + employeeDTO.getFirstName() + " " + employeeDTO.getLastName() +
                            " (id: " + employeeDTO.getId() + ").";
                }
            },

    update_customer
            {
                @Override
                public String execute(String[] args) throws DataNotUpdatedWarning
                {
                    Controller controller = new Controller();
                    CustomerDTO customerDTO = controller.getModel().getCustomer(getValueInBigInteger(args, 1));

                    if (getValueInSting(args, 2) != null)
                    {
                        customerDTO.setFirstName(getValueInSting(args, 2));
                    }
                    if (getValueInSting(args, 3) != null)
                    {
                        customerDTO.setLastName(getValueInSting(args, 3));
                    }
                    if (getValueInSting(args, 4) != null)
                    {
                        customerDTO.setPassword(getValueInSting(args, 4));
                    }
                    if (getValueInSting(args, 5) != null)
                    {
                        customerDTO.setAddress(getValueInSting(args, 5));
                    }
                    if (getValueInSting(args, 6) != null)
                    {
                        customerDTO.setBalance(getValueInFloat(args, 6));
                    }

                    controller.getModel().updateCustomer(customerDTO);

                    return "Customer was updated: " + customerDTO.getFirstName() + " " + customerDTO.getLastName() +
                            " (login: " + customerDTO.getLogin() + ").";
                }

            },

    update_employee
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    EmployeeDTO employeeDTO = controller.getModel().getEmployee(getValueInBigInteger(args, 1));

                    if (getValueInSting(args, 2) != null)
                    {
                        employeeDTO.setFirstName(getValueInSting(args, 2));
                    }
                    if (getValueInSting(args, 3) != null)
                    {
                        employeeDTO.setLastName(getValueInSting(args, 3));
                    }
                    if (getValueInSting(args, 4) != null)
                    {
                        employeeDTO.setPassword(getValueInSting(args, 4));
                    }
                    if (parseToEmployeeStatus(args[5]) != null)
                    {
                        employeeDTO.setEmployeeStatus(parseToEmployeeStatus(args[5]));
                    }

                    controller.getModel().updateEmployee(employeeDTO);

                    return "Employee was updated: " + employeeDTO.getFirstName() + " " + employeeDTO.getLastName() +
                            " (id: " + employeeDTO.getId() + ").";
                }

            },

    create_district
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException, SQLException,
                        DataNotCreatedWarning
                {
                    Controller controller = new Controller();
                    DistrictDTO districtDTO = new DistrictDTO();

                    districtDTO.setName(getValueInSting(args, 1));
                    districtDTO.setParentId(parseToBigInteger(args[2]));

                    controller.getModel().createDistrict(districtDTO);

                    return "DistrictDTO was created: " + districtDTO.getName() + " (id: " + districtDTO.getId() + ").";
                }
            },

    update_district
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException, SQLException,
                        DataNotUpdatedWarning
                {
                    Controller controller = new Controller();
                    DistrictDTO districtDTO = controller.getModel().getDistrict(parseToBigInteger(args[1]));

                    if (getValueInSting(args, 2) != null)
                    {
                        districtDTO.setName(getValueInSting(args, 2));
                    }
                    if (parseToBigInteger(args[3]) != null)
                    {
                        districtDTO.setParentId(parseToBigInteger(args[3]));
                    }

                    controller.getModel().updateDistrict(districtDTO);

                    return "DistrictDTO was updated: " + districtDTO.getName() + " (id: " + districtDTO.getId() + ").";
                }
            },

    create_specification
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, ObjectNotFoundException, SQLException,
                        DataNotCreatedWarning
                {
                    Controller controller = new Controller();
                    SpecificationDTO specificationDTO = new SpecificationDTO();

                    specificationDTO.setName(getValueInSting(args, 1));
                    specificationDTO.setPrice(parseToFloat(args[2]));
                    specificationDTO.setDescription(getValueInSting(args, 3));
                    specificationDTO.setAddressDepended(parseToBoolean(args[4]));
                    specificationDTO.setDistrictsIds(parseToBigIntegerArrayList(args[5]));

                    controller.getModel().createSpecification(specificationDTO);

                    return "Specification was created: name : " + specificationDTO.getName() +
                            ", id: " + specificationDTO.getId() + ")." + "price: " + specificationDTO.getPrice() +
                            " , description: " + specificationDTO.getDescription();
                }
            },

    update_specification
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, ObjectNotFoundException, SQLException,
                        DataNotUpdatedWarning
                {
                    Controller controller = new Controller();
                    SpecificationDTO specificationDTO = controller.getModel()
                            .getSpecification(parseToBigInteger(args[1]));

                    if (getValueInSting(args, 2) != null)
                    {
                        specificationDTO.setName(getValueInSting(args, 2));
                    }
                    if (getValueInSting(args, 3) != null)
                    {
                        specificationDTO.setPrice(parseToFloat(args[3]));
                    }
                    if (getValueInSting(args, 4) != null)
                    {
                        specificationDTO.setDescription(getValueInSting(args, 4));
                    }
                    if (getValueInSting(args, 5) != null)
                    {
                        specificationDTO.setAddressDepended(parseToBoolean(args[5]));
                    }
                    if (getValueInSting(args, 6) != null)
                    {
                        specificationDTO.setDistrictsIds(parseToBigIntegerArrayList(args[6]));
                    }

                    controller.getModel().updateSpecification(specificationDTO);

                    return "Specification was updated: name : " + specificationDTO.getName() +
                            ", id: " + specificationDTO.getId() + ")." + "price: " + specificationDTO.getPrice() +
                            " , description: " + specificationDTO.getDescription();
                }
            },

    delete_customer_cascade
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    BigInteger id = parseToBigInteger(args[1]);
                    controller.deleteCustomerCascade(id);

                    return "Customer (id: " + id + ") was cascade deleted.";
                }
            },

    delete_employee_cascade
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    BigInteger id = parseToBigInteger(args[1]);
                    controller.deleteEmployeeCascade(id);

                    return "Employee (id: " + id + ") was cascade deleted.";
                }
            },

    delete_employee
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    BigInteger id = parseToBigInteger(args[1]);
                    controller.deleteEmployee(id);

                    return "Employee (id: " + id + ") was deleted.";
                }
            },

    delete_order_cascade
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException, SQLException,
                        DataNotFoundWarning
                {
                    Controller controller = new Controller();

                    BigInteger orderId = parseToBigInteger(args[1]);
                    controller.deleteOrderCascade(orderId);

                    return "Order (id: " + orderId + ") was cascade deleted";
                }
            },

    delete_specification_cascade
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException, SQLException,
                        DataNotFoundWarning
                {
                    Controller controller = new Controller();

                    BigInteger specId = parseToBigInteger(args[1]);
                    controller.deleteSpecificationCascade(specId);

                    return "Specification (id: " + specId + ") was cascade deleted";
                }
            },

    delete_district_cascade
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException, SQLException,
                        DataNotFoundWarning
                {
                    Controller controller = new Controller();

                    BigInteger districtId = parseToBigInteger(args[1]);
                    controller.deleteDistrictCascade(districtId);

                    return "DistrictDTO (id: " + districtId + ") was cascade deleted";
                }
            },

    delete_district
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException, SQLException,
                        DataNotFoundWarning
                {
                    Controller controller = new Controller();

                    BigInteger districtId = parseToBigInteger(args[1]);
                    boolean isLeaveChildrenInHierarchy = parseToBoolean(args[2]);

                    controller.deleteDistrict(districtId, isLeaveChildrenInHierarchy);

                    return "DistrictDTO (id: " + districtId + ") was deleted.";
                }
            },

    delete_service
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, SQLException, ObjectNotFoundException,
                        DataNotFoundWarning
                {
                    Controller controller = new Controller();

                    BigInteger serviceId = parseToBigInteger(args[1]);
                    controller.getModel().deleteService(serviceId);

                    return "Service (id: " + serviceId + ") was deleted";
                }
            },

    get_model
            {
                @Override
                public String execute(String[] args) throws IOException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();

                    return "Model: " + controller.getModel().toString();
                }
            },

    get_customers
            {
                @Override
                public String execute(String[] args) throws IOException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();

                    String result = "Customers: \n";
                    for (CustomerDTO customer : controller.getModel().getCustomers().values())
                    {
                        result = result.concat("\t" + customer.getLogin() + ": " + customer.getFirstName() + " " +
                                customer.getLastName() + "(" + customer.getId() + ")\n");
                    }

                    return result;
                }
            },

    get_employees
            {
                @Override
                public String execute(String[] args) throws IOException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();

                    String result = "Employees: \n";
                    for (EmployeeDTO employee : controller.getModel().getEmployees().values())
                    {
                        result = result.concat("\t" + employee.getLogin() + ": " + employee.getFirstName() + " " +
                                employee.getLastName() + ", status: " + employee.getEmployeeStatus() + "(" +
                                employee.getId() + ")\n");
                    }

                    return result;
                }
            },

    get_specifications
            {
                @Override
                public String execute(String[] args) throws IOException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();

                    String result = "Specifications: \n";
                    for (SpecificationDTO specification : controller.getModel().getSpecifications().values())
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
                public String execute(String[] args)
                        throws IOException, SQLException, ObjectNotFoundException, UserNotFoundException
                {
                    Controller controller = new Controller();

                    String result = "Orders: \n";
                    for (OrderDTO order : controller.getModel().getOrders().values())
                    {
                        CustomerDTO customer = controller.getModel().getCustomer(order.getCustomerId());
                        result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() +
                                ", status: " + order.getOrderStatus() +
                                ", Customer: " + customer.getFirstName() + " " + customer.getLastName() +
                                " (login: " + order.getCustomerId() +
                                "), specification id: " + order.getSpecId() + "\n");
                    }

                    return result;
                }
            },

    get_districts
            {
                @Override
                public String execute(String[] args) throws IOException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();

                    String result = "Districts: \n";
                    for (DistrictDTO district : controller.getModel().getDistricts().values())
                    {
                        result = result.concat("\t" + district.getId() + ": " + district.getName() + "\n");
                    }

                    return result;
                }
            },

    get_services
            {
                @Override
                public String execute(String[] args)
                        throws IOException, SQLException, ObjectNotFoundException, UserNotFoundException
                {
                    Controller controller = new Controller();

                    String result = "Services: \n";
                    for (ServiceDTO service : controller.getModel().getServices().values())
                    {
                        CustomerDTO customer = controller.getModel().getCustomer(service.getCustomerId());
                        result = result.concat("\t" + service.getId() + ": " + customer.getFirstName() + " " +
                                customer.getLastName() + " (login: " + service.getCustomerId() +
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
                public String execute(String[] args)
                        throws SQLException, IOException, ObjectNotFoundException, WrongCommandArgumentsException,
                        UserNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    CustomerDTO customer = controller.getModel().getCustomer(id);
                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }

                    return "Customer: " + customer.getLogin() + "\n"
                            + "\tFirst name: " + customer.getFirstName() + "\n"
                            + "\tLast name: " + customer.getLastName() + "\n"
                            + "\tPassword: " + customer.getPassword() + "\n"
                            + "\tAddress: " + customer.getAddress() + "\n"
                            + "\tBalance: " + customer.getBalance() + "\n"
                            + "\t(" + customer.getId() + ")\n";
                }
            },

    get_employee_info
            {
                @Override
                public String execute(String[] args)
                        throws SQLException, IOException, ObjectNotFoundException, WrongCommandArgumentsException,
                        UserNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    EmployeeDTO employee = controller.getModel().getEmployee(id);
                    if (employee == null)
                    {
                        throw new WrongCommandArgumentsException("Employee wasn't found");
                    }

                    return "Employee: " + employee.getLogin() + "\n"
                            + "\tFirst name: " + employee.getFirstName() + "\n"
                            + "\tLast name: " + employee.getLastName() + "\n"
                            + "\tPassword: " + employee.getPassword() + "\n"
                            + "\tStatus: " + employee.getEmployeeStatus() + "\n"
                            + "\tWaiting for orders: " + employee.isWaitingForOrders() + "\n"
                            + "\t(" + employee.getId() + ")\n";
                }
            },

    get_order_info
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, SQLException, ObjectNotFoundException,
                        UserNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    OrderDTO order = controller.getModel().getOrder(id);
                    if (order == null)
                    {
                        throw new WrongCommandArgumentsException("Order wasn't found");
                    }
                    SpecificationDTO specification = controller.getModel().getSpecification(order.getSpecId());
                    CustomerDTO customer = controller.getModel().getCustomer(order.getCustomerId());

                    String result = "Order: " + order.getId() + "\tCustomer: " + customer.getFirstName() + " " +
                            customer.getLastName() + " (login: " + order.getCustomerId() + ")" + "\n\tEmployee: ";
                    if (order.getEmployeeId() != null)
                    {
                        EmployeeDTO employee = controller.getModel().getEmployee(order.getEmployeeId());
                        result = result.concat(employee.getFirstName() + " " + employee.getLastName() +
                                " (login: " + order.getEmployeeId() + ")");
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
                        ServiceDTO service = controller.getModel().getService(order.getServiceId());
                        result = result.concat(" id: " + order.getServiceId() + ", status: " +
                                service.getServiceStatus() + ", pay day: " + service.getPayDay() + "\n");
                    }

                    return result;
                }
            },

    get_service_info
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, SQLException, ObjectNotFoundException,
                        UserNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    ServiceDTO service = controller.getModel().getService(id);
                    if (service == null)
                    {
                        throw new WrongCommandArgumentsException("Service wasn't found");
                    }
                    SpecificationDTO specification = controller.getModel()
                            .getSpecification(service.getSpecificationId());
                    CustomerDTO customer = controller.getModel().getCustomer(service.getCustomerId());

                    return "Service: " + service.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + service.getCustomerId() + ")" + "\n"
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
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    SpecificationDTO specification = controller.getModel().getSpecification(id);
                    if (specification == null)
                    {
                        throw new WrongCommandArgumentsException("Specification wasn't found");
                    }

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
                            DistrictDTO district = controller.getModel().getDistrict(districtId);
                            result = result.concat(district.getName() + " (id: " + district.getId() + "), ");
                        }
                    }

                    return result;
                }
            },

    get_district_info
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    DistrictDTO district = controller.getModel().getDistrict(id);
                    if (district == null)
                    {
                        throw new WrongCommandArgumentsException("District wasn't found");
                    }

                    String result = "DistrictDTO: " + district.getId() + "\n"
                            + "\tName: " + district.getName() + "\n"
                            + "\tParent: ";
                    if (district.getParentId() != null)
                    {
                        result = result
                                .concat(controller.getModel().getDistrict(district.getParentId()).getName() +
                                        " (id: " + district.getParentId() + ") ");
                    }
                    result = result.concat(" \n\tChildren: ");
                    if (controller.getDistrictChildren(district.getId()) != null)
                    {
                        for (DistrictDTO children : controller.getDistrictChildren(district.getId()))
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
                        throws SQLException, IOException, ObjectNotFoundException, WrongCommandArgumentsException,
                        UserNotFoundException, DataNotUpdatedWarning, IllegalTransitionException, DataNotCreatedWarning
                {
                    Controller controller = new Controller();

                    CustomerDTO customer = controller.getModel().getCustomer(parseToBigInteger(args[1]));
                    SpecificationDTO specification = controller.getModel().getSpecification(parseToBigInteger(args[2]));

                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    if (specification == null)
                    {
                        throw new WrongCommandArgumentsException("Specification wasn't found");
                    }

                    OrderDTO order = controller.createNewOrder(customer.getId(), specification.getId());

                    return "New order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerId() + ")" + "\n"
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
                public String execute(String[] args)
                        throws Exception
                {
                    Controller controller = new Controller();

                    CustomerDTO customer = controller.getModel().getCustomer(parseToBigInteger(args[1]));
                    ServiceDTO service = controller.getModel().getService(parseToBigInteger(args[2]));

                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    if (service == null)
                    {
                        throw new WrongCommandArgumentsException("Service wasn't found");
                    }

                    OrderDTO order = controller.createSuspendOrder(customer.getId(), service.getId());

                    SpecificationDTO specification = controller.getModel().getSpecification(order.getSpecId());

                    return "Suspend order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerId() + ")" + "\n"
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

                    CustomerDTO customer = controller.getModel().getCustomer(parseToBigInteger(args[1]));
                    ServiceDTO service = controller.getModel().getService(parseToBigInteger(args[2]));

                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    if (service == null)
                    {
                        throw new WrongCommandArgumentsException("Service wasn't found");
                    }

                    OrderDTO order = controller.createRestoreOrder(customer.getId(), service.getId());

                    SpecificationDTO specification = controller.getModel().getSpecification(order.getSpecId());

                    return "Restore order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerId() + ")" + "\n"
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

                    CustomerDTO customer = controller.getModel().getCustomer(parseToBigInteger(args[1]));
                    ServiceDTO service = controller.getModel().getService(parseToBigInteger(args[2]));

                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    if (service == null)
                    {
                        throw new WrongCommandArgumentsException("Service wasn't found");
                    }

                    OrderDTO order = controller.createDisconnectOrder(customer.getId(), service.getId());

                    SpecificationDTO specification = controller.getModel().getSpecification(order.getSpecId());

                    return "Disconnect order was created: " + order.getId() + "\n"
                            + "\tCustomer: " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + order.getCustomerId() + ")" + "\n"
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
                        ObjectNotFoundException, SQLException, DataNotUpdatedWarning
                {
                    Controller controller = new Controller();

                    BigInteger orderId = parseToBigInteger(args[1]);
                    controller.startOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was started.";
                }
            },

    suspend_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException, SQLException, DataNotUpdatedWarning
                {
                    Controller controller = new Controller();

                    BigInteger orderId = parseToBigInteger(args[1]);
                    controller.suspendOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was suspended.";
                }
            },

    restore_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException, SQLException, DataNotUpdatedWarning
                {
                    Controller controller = new Controller();

                    BigInteger orderId = parseToBigInteger(args[1]);
                    controller.restoreOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was restored.";
                }
            },

    cancel_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException, UserNotFoundException, SQLException, DataNotUpdatedWarning
                {
                    Controller controller = new Controller();

                    BigInteger orderId = parseToBigInteger(args[1]);
                    controller.cancelOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was cancelled.";
                }
            },

    complete_order
            {
                @Override
                public String execute(String[] args)
                        throws WrongCommandArgumentsException, IOException, IllegalTransitionException,
                        ObjectNotFoundException, SQLException, DataNotCreatedWarning, DataNotUpdatedWarning
                {
                    Controller controller = new Controller();

                    BigInteger orderId = parseToBigInteger(args[1]);
                    controller.completeOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() + ") was completed.";
                }
            },

    change_balance_on
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    BigInteger id = parseToBigInteger(args[1]);
                    float amountOfMoney = parseToFloat(args[2]);

                    controller.changeBalanceOn(id, amountOfMoney);
                    CustomerDTO customer = controller.getModel().getCustomer(id);

                    return "Balance of customer " + customer.getFirstName() + " " + customer.getLastName() +
                            " (login: " + customer.getLogin() + ") was changed by " + amountOfMoney;
                }
            },

    get_free_orders
            {
                @Override
                public String execute(String[] args)
                        throws IOException, SQLException, ObjectNotFoundException, UserNotFoundException
                {
                    Controller controller = new Controller();

                    String result = "Free orders: \n";
                    for (OrderDTO order : controller.getFreeOrders())
                    {
                        CustomerDTO customer = controller.getModel().getCustomer(order.getCustomerId());
                        result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + ", status: " +
                                order.getOrderStatus() + ", Customer: " + customer.getFirstName() + " " +
                                customer.getLastName() + " (login: " + order.getCustomerId() +
                                "), specification id: " + order.getSpecId() + "\n");
                    }

                    return result;
                }
            },

    get_free_order
            {
                @Override
                public String execute(String[] args)
                        throws IOException, SQLException, ObjectNotFoundException, UserNotFoundException
                {
                    Controller controller = new Controller();
                    OrderDTO order = controller.getFreeOrder();

                    CustomerDTO customer = controller.getModel().getCustomer(order.getCustomerId());

                    return "Free order: \n(id: " + order.getId() + ") " + order.getOrderAim() + ", status: " +
                            order.getOrderStatus() + ", Customer: " + customer.getFirstName() + " " +
                            customer.getLastName() + " (login: " + order.getCustomerId() +
                            "), specification id: " + order.getSpecId() + "\n";
                }
            },

    get_customer_orders
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    CustomerDTO customer = controller.getModel().getCustomer(id);
                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    List<OrderDTO> orders = (List<OrderDTO>) controller.getCustomerOrders(customer.getId());

                    String result = "Orders of customer " + customer.getFirstName() + " " + customer.getLastName() +
                            " (id: " + id + "):\n";
                    if (orders != null)
                    {
                        for (OrderDTO order : orders)
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
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    CustomerDTO customer = controller.getModel().getCustomer(id);
                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    List<ServiceDTO> services = (List<ServiceDTO>) controller.getCustomerServices(customer.getId());

                    String result = "Services of customer " + customer.getFirstName() + " " + customer.getLastName() +
                            " (id: " + id + "):\n";
                    if (services != null)
                    {
                        for (ServiceDTO service : services)
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
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    EmployeeDTO employee = controller.getModel().getEmployee(id);
                    if (employee == null)
                    {
                        throw new WrongCommandArgumentsException("Employee wasn't found");
                    }
                    List<OrderDTO> orders = (List<OrderDTO>) controller.getEmployeeOrders(employee.getId());

                    String result = "Orders of employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (id: " + id + "):\n";
                    if (orders != null)
                    {
                        for (OrderDTO order : orders)
                        {
                            CustomerDTO customer = controller.getModel().getCustomer(order.getCustomerId());
                            result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + " customer: " +
                                    customer.getFirstName() + " " + customer.getLastName() +
                                    " (login: " + order.getCustomerId() + "), status: " +
                                    order.getOrderStatus() + ", specification id: " + order.getSpecId() + "\n");
                        }
                    }

                    return result;
                }
            },

    get_customer_connected_services
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    CustomerDTO customer = controller.getModel().getCustomer(id);
                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    List<ServiceDTO> services = (List<ServiceDTO>) controller
                            .getCustomerConnectedServices(customer.getId());

                    String result = "Connected services of customer " + customer.getFirstName() + " " +
                            customer.getLastName() + " (id: " + id + ")\n";
                    if (services != null)
                    {
                        for (ServiceDTO service : services)
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
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    CustomerDTO customer = controller.getModel().getCustomer(id);
                    if (customer == null)
                    {
                        throw new WrongCommandArgumentsException("Customer wasn't found");
                    }
                    List<OrderDTO> orders = (List<OrderDTO>) controller.getCustomerNotFinishedOrders(customer.getId());

                    String result = "Not finished orders of customer " + customer.getFirstName() + " " +
                            customer.getLastName() + " (id: " + id + "):\n";
                    if (orders != null)
                    {
                        for (OrderDTO order : orders)
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
                public String execute(String[] args)
                        throws IOException, UserNotFoundException, SQLException, ObjectNotFoundException
                {
                    Controller controller = new Controller();

                    List<OrderDTO> orders = controller.getOrdersOfEmployeesOnVacation();
                    String result = "Orders of employees on vacation: \n";
                    if (orders != null)
                    {
                        for (OrderDTO order : orders)
                        {
                            CustomerDTO customer = controller.getModel().getCustomer(order.getCustomerId());
                            result = result.concat("\t" + order.getId() + ": " + order.getOrderAim() + " customer: " +
                                    customer.getFirstName() + " " + customer.getLastName() +
                                    " (login: " + order.getCustomerId() + "), status: " +
                                    order.getOrderStatus() + ", specification id: " + order.getSpecId() + "\n");
                        }
                    }

                    return result;
                }
            },

    go_on_vacation
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    EmployeeDTO employee = controller.getModel().getEmployee(id);
                    if (employee == null)
                    {
                        throw new WrongCommandArgumentsException("Employee wasn't found");
                    }

                    controller.goOnVacation(employee.getId());


                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ") went to vacation.";
                }
            },

    return_from_vacation
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    EmployeeDTO employee = controller.getModel().getEmployee(id);
                    if (employee == null)
                    {
                        throw new WrongCommandArgumentsException("Employee wasn't found");
                    }

                    controller.returnFromVacation(employee.getId());

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ") was returned from vacation.";
                }
            },

    retire_employee
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();
                    BigInteger id = parseToBigInteger(args[1]);

                    EmployeeDTO employee = controller.getModel().getEmployee(id);
                    if (employee == null)
                    {
                        throw new WrongCommandArgumentsException("Employee wasn't found");
                    }
                    controller.retireEmployee(employee.getId());

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() +
                            " (login: " + employee.getLogin() + ") was retired.";
                }
            },

    assign_order
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    EmployeeDTO employee = controller.getModel().getEmployee(parseToBigInteger(args[1]));
                    OrderDTO order = controller.getModel().getOrder(parseToBigInteger(args[2]));
                    if (employee == null)
                    {
                        throw new WrongCommandArgumentsException("Employee wasn't found");
                    }
                    if (order == null)
                    {
                        throw new WrongCommandArgumentsException("Order wasn't found");
                    }

                    controller.assignOrder(employee.getId(), order.getId());

                    return "Order (id: " + order.getId() +
                            ") was assigned to employee " + employee.getFirstName() + " " +
                            employee.getLastName() + " (login: " + employee.getLogin() + ").";
                }
            },

    process_order
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    EmployeeDTO employee = controller.getModel().getEmployee(parseToBigInteger(args[1]));
                    OrderDTO order = controller.getModel().getOrder(parseToBigInteger(args[2]));
                    if (employee == null)
                    {
                        throw new WrongCommandArgumentsException("Employee wasn't found");
                    }
                    if (order == null)
                    {
                        throw new WrongCommandArgumentsException("Order wasn't found");
                    }

                    controller.processOrder(employee.getId(), order.getId());


                    return "Order (id: " + order.getId() +
                            ") was started by employee " + employee.getFirstName() + " " +
                            employee.getLastName() + " (login: " + employee.getLogin() + ").";
                }
            },

    unassign_order
            {
                @Override
                public String execute(String[] args)
                        throws IOException, WrongCommandArgumentsException, ObjectNotFoundException, SQLException,
                        DataNotUpdatedWarning
                {
                    Controller controller = new Controller();
                    BigInteger orderId = parseToBigInteger(args[1]);

                    controller.unassignOrder(orderId);

                    return "Order (id: " + controller.getModel().getOrder(orderId).getId() +
                            ") was unassigned from employee.";
                }
            },

    subscribe
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    EmployeeDTO employee = controller.getModel().getEmployee(parseToBigInteger(args[1]));

                    controller.setEmployeeWaitingStatus(parseToBigInteger(args[1]), true);

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() + " (login: " +
                            employee.getLogin() + ") was subscribed for getting free orders.";
                }
            },

    unsubscribe
            {
                @Override
                public String execute(String[] args) throws Exception
                {
                    Controller controller = new Controller();

                    EmployeeDTO employee = controller.getModel().getEmployee(parseToBigInteger(args[1]));

                    controller.setEmployeeWaitingStatus(parseToBigInteger(args[1]), false);

                    return "Employee " + employee.getFirstName() + " " + employee.getLastName() + " (login: " +
                            employee.getLogin() + ") was unsubscribed from work waiters.";
                }
            },

    distribute_orders
            {
                @Override
                public String execute(String[] args) throws IOException, SQLException, ObjectNotFoundException
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

    private static String getValueInSting(String[] args, int i)
    {
        return !args[i].equalsIgnoreCase("null") ? args[i] : null;
    }

    private static Float getValueInFloat(String[] args, int i) throws WrongCommandArgumentsException
    {
        return parseToFloat(args[i]);
    }

    private static BigInteger getValueInBigInteger(String[] args, int i) throws WrongCommandArgumentsException
    {
        return !args[1].equalsIgnoreCase("null") ? Command.parseToBigInteger(args[i]) : null;
    }

    public abstract String execute(String[] args) throws Exception;

}