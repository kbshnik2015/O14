package controller;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import controller.exceptions.IllegalLoginOrPasswordException;
import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import controller.managers.WorkWaitersManager;
import lombok.Data;
import lombok.Getter;
import model.Model;
import model.entities.AbstractUser;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;
import model.enums.EmployeeStatus;
import model.enums.OrderAim;
import model.enums.OrderStatus;
import model.enums.ServiceStatus;

@Data
public class Controller
{

    @Getter
    private Model model;

    public Controller() throws IOException
    {
        model = Model.getInstance();
    }

    public AbstractUser login(String login, String password) throws IllegalLoginOrPasswordException
    {
        AbstractUser user = model.getCustomer(login);

        if (user == null)
        {
            user = model.getEmployee(login);
        }
        if (user == null || !password.equals(user.getPassword()))
        {
            throw new IllegalLoginOrPasswordException("Wrong login or password!");
        }

        return user;
    }

    public Customer createCustomer(String firstName, String lastName, String login, String password, String address,
            float balance) throws IllegalLoginOrPasswordException, IOException
    {
        checkLoginAndPassword(login, password);

        Customer customer = new Customer(firstName, lastName, login, password, address, balance);
        customer = model.createCustomer(customer);
        model.saveToFile();

        return customer;
    }

    public Employee createEmployee(String firstName, String lastName, String login, String password,
            EmployeeStatus employeeStatus) throws IllegalLoginOrPasswordException, IOException
    {
        checkLoginAndPassword(login, password);

        Employee employee = new Employee(firstName, lastName, login, password, employeeStatus);
        employee = model.createEmployee(employee);
        model.saveToFile();

        return employee;
    }

    private void checkLoginAndPassword(String login, String password) throws IllegalLoginOrPasswordException
    {
        if (login == null)
        {
            throw new IllegalLoginOrPasswordException("Login can't be nullable!");
        }
        if (password == null)
        {
            throw new IllegalLoginOrPasswordException("Password can't be nullable!");
        }
        if (model.getCustomers().containsKey(login))
        {
            throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
        }
        if (model.getEmployees().containsKey(login))
        {
            throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
        }
    }

    public void updateCustomer(String firstName, String lastName, String login, String password, String address,
            float balance) throws UserNotFoundException, IOException
    {
        checkCustomerExists(login);

        model.updateCustomer(firstName, lastName, login, password, address, balance);
        model.saveToFile();
    }

    public void updateEmployee(String firstName, String lastName, String login, String password,
            EmployeeStatus employeeStatus) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(login);

        model.updateEmployee(firstName, lastName, login, password, employeeStatus);
        model.saveToFile();
    }

    public District createDistrict(String name, BigInteger parentId)
            throws ObjectNotFoundException, IOException
    {
        if (parentId != null)
        {
            checkDistrictExists(parentId);
        }

        District district = model.createDistrict(name, parentId);
        model.saveToFile();

        return district;
    }

    public void updateDistrict(BigInteger id, String name, BigInteger parentId)
            throws ObjectNotFoundException, IOException
    {
        checkDistrictExists(id);
        if (parentId != null)
        {
            checkDistrictExists(parentId);
        }

        model.updateDistrict(id, name, parentId);
        model.saveToFile();
    }

    public Specification createSpecification(String name, float price, String description, boolean isAddressDepended,
            ArrayList<BigInteger> districtsIds) throws ObjectNotFoundException, IOException
    {
        if (districtsIds != null)
        {
            for (BigInteger districtId : districtsIds)
            {
                checkDistrictExists(districtId);
            }
        }

        Specification specification = new Specification(name, price, description, isAddressDepended, districtsIds);
        specification = model.createSpecification(specification);
        model.saveToFile();

        return specification;
    }

    public void updateSpecification(BigInteger id, String name, float price, String description,
            boolean isAddressDepended,
            ArrayList<BigInteger> districtsIds) throws ObjectNotFoundException, IOException
    {
        checkSpecificationExists(id);

        if (districtsIds != null)
        {
            for (BigInteger districtId : districtsIds)
            {
                checkDistrictExists(districtId);
            }
        }

        model.updateSpecification(id, name, price, description, isAddressDepended, districtsIds);
        model.saveToFile();
    }

    public void deleteCustomerCascade(String login) throws UserNotFoundException, IOException
    {
        checkCustomerExists(login);

        for (Service service : getCustomerServices(login))
        {
            model.deleteService(service);
        }
        for (Order order : getCustomerOrders(login))
        {
            model.deleteOrder(order);
        }
        model.deleteCustomer(model.getCustomer(login));
        model.saveToFile();
    }

    public void deleteEmployeeCascade(String login) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(login);

        for (Order order : getEmployeeOrders(login))
        {
            model.deleteOrder(order);
        }
        model.deleteEmployee(model.getEmployee(login));
        model.saveToFile();
    }

    public void deleteEmployee(String login) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(login);

        for (Order order : getEmployeeOrders(login))
        {
            order.setEmployeeLogin(null);
        }
        model.deleteEmployee(model.getEmployee(login));
        model.saveToFile();
    }

    public void deleteOrderCascade(BigInteger id) throws ObjectNotFoundException, IOException
    {
        checkOrderExists(id);

        model.deleteService(model.getService(model.getOrder(id).getServiceId()));
        model.deleteOrder(model.getOrder(id));
        model.saveToFile();
    }

    public void deleteSpecificationCascade(BigInteger id) throws ObjectNotFoundException, IOException
    {
        checkSpecificationExists(id);

        for (Service service : getSpecificationServices(id))
        {
            model.deleteService(service);
        }
        model.deleteSpecification(model.getSpecification(id));
        model.saveToFile();
    }

    public void deleteDistrictCascade(BigInteger id) throws ObjectNotFoundException, IOException
    {
        checkDistrictExists(id);

        if (getDistrictChildren(id) != null)
        {
            for (District district : getDistrictChildren(id))
            {
                model.deleteDistrict(model.getDistrict(district.getId()));
            }
        }

        model.deleteDistrict(model.getDistrict(id));
        model.saveToFile();
    }

    public void deleteDistrict(BigInteger id, boolean isLeaveChildrenInHierarchy)
            throws ObjectNotFoundException, IOException
    {
        checkDistrictExists(id);

        if (getDistrictChildren(id) != null)
        {
            for (District district : getDistrictChildren(id))
            {
                if (isLeaveChildrenInHierarchy)
                {
                    district.setParentId(model.getDistrict(id).getParentId());
                }
                else
                {
                    district.setParentId(null);
                }
            }
        }

        model.deleteDistrict(model.getDistrict(id));
        model.saveToFile();
    }

    public Order createNewOrder(String customerLogin, BigInteger specId)
            throws UserNotFoundException, ObjectNotFoundException, IOException
    {
        checkCustomerExists(customerLogin);
        checkSpecificationExists(specId);

        Order order = new Order(customerLogin, null, specId, null, OrderAim.NEW, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();
        model.saveToFile();

        return order;
    }

    public Order createSuspendOrder(String customerLogin, BigInteger serviceId)
            throws Exception
    {
        checkCustomerExists(customerLogin);
        checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerLogin, serviceId);
        checkSpecificationExists(model.getSpecification(model.getService(serviceId).getSpecificationId()).getId());

        Order order = new Order(customerLogin, null,
                model.getSpecification(model.getService(serviceId).getSpecificationId()).getId(), serviceId,
                OrderAim.SUSPEND, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();
        model.saveToFile();

        return order;
    }

    public Order createRestoreOrder(String customerLogin, BigInteger serviceId) throws Exception
    {
        checkCustomerExists(customerLogin);
        checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerLogin, serviceId);
        checkSpecificationExists(model.getSpecification(model.getService(serviceId).getSpecificationId()).getId());

        Order order = new Order(customerLogin, null,
                model.getSpecification(model.getService(serviceId).getSpecificationId()).getId(), serviceId,
                OrderAim.RESTORE, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();
        model.saveToFile();

        return order;
    }

    public Order createDisconnectOrder(String customerLogin, BigInteger serviceId) throws Exception
    {
        checkCustomerExists(customerLogin);
        checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerLogin, serviceId);
        checkSpecificationExists(model.getSpecification(model.getService(serviceId).getSpecificationId()).getId());

        Order order = new Order(customerLogin, null,
                model.getSpecification(model.getService(serviceId).getSpecificationId()).getId(), serviceId,
                OrderAim.DISCONNECT, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();
        model.saveToFile();

        return order;
    }

    public void startOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException, IOException
    {
        checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.ENTERING, OrderStatus.IN_PROGRESS);
    }

    public void suspendOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException, IOException
    {
        checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.SUSPENDED);
    }

    public void restoreOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException, IOException
    {
        checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.SUSPENDED, OrderStatus.IN_PROGRESS);
    }

    public void cancelOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException, IOException
    {
        checkOrderExists(orderId);

        Order order = model.getOrder(orderId);
        if (order.getOrderStatus() == OrderStatus.COMPLETED)
        {
            throw new IllegalTransitionException("Completed order (id: " + orderId + ") can't be cancelled!");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        model.saveToFile();
    }

    public void completeOrder(BigInteger orderId)
            throws IllegalTransitionException, ObjectNotFoundException, IOException
    {
        checkOrderExists(orderId);
        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.COMPLETED);

        Date payDayPlusMonth = new Date(System.currentTimeMillis() + 2592000000L);
        Order order = model.getOrder(orderId);

        if (OrderAim.NEW.equals(order.getOrderAim()))
        {
            checkSpecificationExists(order.getSpecId());

            Service service =
                    new Service(payDayPlusMonth, order.getSpecId(), ServiceStatus.ACTIVE, order.getCustomerLogin());
            order.setServiceId(model.createService(service).getId());
        }
        else
        {
            checkServiceExists(order.getServiceId());
            Service service = model.getService(order.getServiceId());
            if (OrderAim.DISCONNECT.equals(order.getOrderAim()))
            {
                service.setPayDay(null);
                service.setServiceStatus(ServiceStatus.DISCONNECTED);
            }
            else if (OrderAim.SUSPEND.equals(order.getOrderAim()))
            {
                service.setPayDay(null);
                service.setServiceStatus(ServiceStatus.SUSPENDED);
            }
            else if (OrderAim.RESTORE.equals(order.getOrderAim()))
            {
                service.setPayDay(payDayPlusMonth);
                service.setServiceStatus(ServiceStatus.ACTIVE);
            }
        }

        model.saveToFile();
    }

    private void moveOrderFromTo(BigInteger orderId, OrderStatus entering, OrderStatus inProgress) throws
            IllegalTransitionException, IOException
    {
        Order order = model.getOrder(orderId);

        checkOrderInitialStatus(order, entering);

        order.setOrderStatus(inProgress);

        model.saveToFile();
    }

    private void checkOrderInitialStatus(Order order, OrderStatus initialStatus) throws IllegalTransitionException
    {
        if (order != null)
        {
            if (order.getOrderStatus() != initialStatus)
            {
                throw new IllegalTransitionException("Illegal transition for order (id: " + order.getId() + ")!");
            }
        }
    }

    public void changeBalanceOn(String login, float amountOfMoney) throws UserNotFoundException, IOException
    {
        checkCustomerExists(login);

        Customer customer = model.getCustomer(login);
        customer.setBalance(customer.getBalance() + amountOfMoney);
        model.saveToFile();
    }

    public List<Order> getFreeOrders()
    {
        List<Order> list = new ArrayList<>();
        for (Order ord : model.getOrders().values())
        {
            if (ord.getEmployeeLogin() == null && !OrderStatus.COMPLETED.equals(ord.getOrderStatus()))
            {
                list.add(ord);
            }
        }

        return list;
    }

    public Order getFreeOrder()
    {
        for (Order ord : model.getOrders().values())
        {
            if (ord.getEmployeeLogin() == null && !OrderStatus.COMPLETED.equals(ord.getOrderStatus()))
            {
                return ord;
            }
        }

        return null;
    }

    public List<District> getDistrictChildren(BigInteger id)
    {
        List<District> list = new ArrayList<>();
        for (District district : model.getDistricts().values())
        {
            if (id.equals(district.getParentId()))
            {
                list.add(district);
            }
        }

        return list;
    }

    public Collection<Order> getCustomerOrders(String login) throws UserNotFoundException
    {
        checkCustomerExists(login);

        return model.getOrders().values().stream()
                .filter(order -> login.equals(order.getCustomerLogin()))
                .collect(Collectors.toList());
    }

    public Collection<Service> getCustomerServices(String login) throws UserNotFoundException
    {
        checkCustomerExists(login);

        return model.getServices().values().stream()
                .filter(service -> login.equals(service.getCustomerLogin()))
                .collect(Collectors.toList());
    }

    public  List<Specification> getCustomerSpecifications(final Customer customer)
            throws IOException, UserNotFoundException
    {
        List<Specification> customerSpecifications = getCustomerServices(customer.getLogin())
                .stream()
                .map(x -> model.getSpecification(x.getSpecificationId()))
                .collect(Collectors.toList());

        customerSpecifications.addAll(getCustomerOrders(customer.getLogin())
                .stream()
                .map(x -> model.getSpecification(x.getSpecId()))
                .collect(Collectors.toList()));

        List<Specification> specifications = filterSpecsFromCustomerOnes(customerSpecifications);

        return specifications;
    }

    private List<Specification> filterSpecsFromCustomerOnes(final List<Specification> customerSpecifications)
    {
        List<Specification> specifications = model.getSpecifications().values().stream()
                .filter(x -> !customerSpecifications.contains(x))
                .collect(Collectors.toList());
        return specifications;
    }

    public Collection<Order> getEmployeeOrders(String login) throws UserNotFoundException
    {
        checkEmployeeExists(login);

        return model.getOrders().values().stream()
                .filter(order -> login.equals(order.getEmployeeLogin()))
                .collect(Collectors.toList());
    }

    public Collection<Service> getCustomerConnectedServices(String login) throws UserNotFoundException
    {
        checkCustomerExists(login);

        return getCustomerServices(login)
                .stream()
                .filter(service -> ServiceStatus.DISCONNECTED != service.getServiceStatus())
                .collect(Collectors.toList());
    }

    public Collection<Order> getCustomerNotFinishedOrders(String login) throws UserNotFoundException
    {
        checkCustomerExists(login);

        return getCustomerOrders(login)
                .stream()
                .filter(order -> OrderStatus.COMPLETED != order.getOrderStatus())
                .collect(Collectors.toList());
    }

    public Collection<Service> getSpecificationServices(BigInteger id)
            throws ObjectNotFoundException
    {
        checkSpecificationExists(id);

        return model.getServices().values().stream()
                .filter(service -> id.equals(service.getSpecificationId()))
                .collect(Collectors.toList());
    }

    public ArrayList<Order> getOrdersOfEmployeesOnVacation() throws UserNotFoundException
    {
        ArrayList<Order> orders = new ArrayList<>();
        for (Employee employee : model.getEmployees().values())
        {
            if (EmployeeStatus.ON_VACATION.equals(employee.getEmployeeStatus()))
            {
                orders.addAll(getEmployeeOrders(employee.getLogin()));
            }
        }

        return orders;
    }

    public void setEmployeeWaitingStatus(String login, boolean isWaiting) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(login);

        model.getEmployee(login).setWaitingForOrders(isWaiting);

        if (isWaiting)
        {
            WorkWaitersManager.distributeOrders();
        }

        model.saveToFile();
    }

    public void goOnVacation(String login) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(login);

        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.ON_VACATION);
        model.saveToFile();
    }

    public void returnFromVacation(String login) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(login);

        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.WORKING);
        model.saveToFile();
    }

    public void retireEmployee(String login) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(login);

        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.RETIRED);
        ArrayList<Order> orders = (ArrayList<Order>) getEmployeeOrders(login);
        if (orders != null)
        {
            for (Order order : orders)
            {
                order.setEmployeeLogin(null);
            }
        }

        model.saveToFile();
    }

    public void assignOrder(String employeeLogin, BigInteger orderId)
            throws ObjectNotFoundException, UserNotFoundException, IOException
    {
        checkOrderExists(orderId);
        checkEmployeeExists(employeeLogin);

        Order order = model.getOrder(orderId);
        order.setEmployeeLogin(employeeLogin);
        model.saveToFile();
    }

    public void processOrder(String employeeLogin, BigInteger orderId)
            throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException, IOException
    {
        checkOrderExists(orderId);
        checkEmployeeExists(employeeLogin);

        assignOrder(employeeLogin, orderId);
        startOrder(orderId);
    }

    public void unassignOrder(BigInteger orderId) throws ObjectNotFoundException, IOException
    {
        checkOrderExists(orderId);

        Order order = model.getOrder(orderId);
        order.setEmployeeLogin(null);

        WorkWaitersManager.distributeOrders();
        model.saveToFile();
    }

    private void checkOrderExists(final BigInteger orderId) throws ObjectNotFoundException
    {
        if (!model.getOrders().containsKey(orderId))
        {
            throw new ObjectNotFoundException("Order (id: " + orderId + ") doesn't exist");
        }
    }

    private void checkSpecificationExists(final BigInteger id) throws ObjectNotFoundException
    {
        if (!model.getSpecifications().containsKey(id))
        {
            throw new ObjectNotFoundException("Specification (id: " + id + ") doesn't exist");
        }
    }

    private void checkServiceExists(final BigInteger id) throws ObjectNotFoundException
    {
        if (!model.getServices().containsKey(id))
        {
            throw new ObjectNotFoundException("Service (id: " + id + ") doesn't exist");
        }
    }

    private void checkEmployeeExists(final String login) throws UserNotFoundException
    {
        if (!model.getEmployees().containsKey(login))
        {
            throw new UserNotFoundException("Employee (login: " + login + ") doesn't exist!");
        }
    }

    private void checkCustomerExists(final String login) throws UserNotFoundException
    {
        if (!model.getCustomers().containsKey(login))
        {
            throw new UserNotFoundException("Customer (login: " + login + ") doesn't exist!");
        }
    }

    private void checkDistrictExists(BigInteger id) throws ObjectNotFoundException
    {
        if (!model.getDistricts().containsKey(id))
        {
            throw new ObjectNotFoundException("District (id: " + id + ") doesn't exist");
        }
    }

    private void checkServiceBelongsToCustomer(String customerLogin, BigInteger serviceId) throws Exception
    {
        if (!customerLogin.equals(model.getService(serviceId).getCustomerLogin()))
        {
            throw new Exception("Service doesn't belong to customer!");
        }
    }
}