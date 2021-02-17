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

    public AbstractUser login(String login, String password) throws Exception
    {
        AbstractUser user = getUserByLogin(login);

        if (password.equals(user.getPassword()))
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
        for (Customer customer : model.getCustomers().values()){
            if (customer.getLogin().equals(login)){
                throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
            }
        }

        for (Employee employee : model.getEmployees().values()){
            if (employee.getLogin().equals(login)){
                throw new IllegalLoginOrPasswordException("Login (" + login + ") already exist!");
            }
        }
    }

    public void updateCustomer(BigInteger id,String firstName, String lastName, String login, String password, String address,
            float balance) throws UserNotFoundException, IOException
    {
        checkCustomerExists(id);

        model.updateCustomer(id, firstName, lastName, login, password, address, balance);
        model.saveToFile();
    }

    public void updateEmployee(BigInteger id,String firstName, String lastName, String login, String password,
            EmployeeStatus employeeStatus) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(id);

        model.updateEmployee(id,firstName, lastName, login, password, employeeStatus);
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

    public void deleteCustomerCascade(BigInteger id) throws UserNotFoundException, IOException
    {
        checkCustomerExists(id);

        for (Service service : getCustomerServices(id))
        {
            model.deleteService(service);
        }
        for (Order order : getCustomerOrders(id))
        {
            model.deleteOrder(order);
        }
        model.deleteCustomer(model.getCustomer(id));
        model.saveToFile();
    }

    public void deleteEmployeeCascade(BigInteger id) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(id);

        for (Order order : getEmployeeOrders(id))
        {
            model.deleteOrder(order);
        }
        model.deleteEmployee(model.getEmployee(id));
        model.saveToFile();
    }

    public void deleteEmployee(BigInteger id) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(id);

        for (Order order : getEmployeeOrders(id))
        {
            order.setEmployeeId(null);
        }
        model.deleteEmployee(model.getEmployee(id));
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

    public Order createNewOrder(BigInteger customerId, BigInteger specId)
            throws UserNotFoundException, ObjectNotFoundException, IOException
    {
        checkCustomerExists(customerId);
        checkSpecificationExists(specId);

        Order order = new Order(customerId, null, specId, null, OrderAim.NEW, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();
        model.saveToFile();

        return order;
    }

    public Order createSuspendOrder(BigInteger customerId, BigInteger serviceId)
            throws Exception
    {
        checkCustomerExists(customerId);
        checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerId, serviceId);
        checkSpecificationExists(model.getSpecification(model.getService(serviceId).getSpecificationId()).getId());

        Order order = new Order(customerId, null,
                model.getSpecification(model.getService(serviceId).getSpecificationId()).getId(), serviceId,
                OrderAim.SUSPEND, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();
        model.saveToFile();

        return order;
    }

    public Order createRestoreOrder(BigInteger customerId, BigInteger serviceId) throws Exception
    {
        checkCustomerExists(customerId);
        checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerId, serviceId);
        checkSpecificationExists(model.getSpecification(model.getService(serviceId).getSpecificationId()).getId());

        Order order = new Order(customerId, null,
                model.getSpecification(model.getService(serviceId).getSpecificationId()).getId(), serviceId,
                OrderAim.RESTORE, null);
        order = model.createOrder(order);
        WorkWaitersManager.distributeOrders();
        model.saveToFile();

        return order;
    }

    public Order createDisconnectOrder(BigInteger customerId, BigInteger serviceId) throws Exception
    {
        checkCustomerExists(customerId);
        checkServiceExists(serviceId);
        checkServiceBelongsToCustomer(customerId, serviceId);
        checkSpecificationExists(model.getSpecification(model.getService(serviceId).getSpecificationId()).getId());

        Order order = new Order(customerId, null,
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
                    new Service(payDayPlusMonth, order.getSpecId(), ServiceStatus.ACTIVE, order.getCustomerId());
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

    public void changeBalanceOn(BigInteger customerId, float amountOfMoney) throws UserNotFoundException, IOException
    {
        checkCustomerExists(customerId);

        Customer customer = model.getCustomer(customerId);
        customer.setBalance(customer.getBalance() + amountOfMoney);
        model.saveToFile();
    }

    public List<Order> getFreeOrders()
    {
        List<Order> list = new ArrayList<>();
        for (Order ord : model.getOrders().values())
        {
            if (ord.getEmployeeId() == null && !OrderStatus.COMPLETED.equals(ord.getOrderStatus()))
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
            if (ord.getEmployeeId() == null && !OrderStatus.COMPLETED.equals(ord.getOrderStatus()))
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

    public Collection<Order> getCustomerOrders(BigInteger id) throws UserNotFoundException
    {
        checkCustomerExists(id);

        return model.getOrders().values().stream()
                .filter(order -> id.equals(order.getCustomerId()))
                .collect(Collectors.toList());
    }

    public Collection<Service> getCustomerServices(BigInteger id) throws UserNotFoundException
    {
        checkCustomerExists(id);

        return model.getServices().values().stream()
                .filter(service -> id.equals(service.getCustomerId()))
                .collect(Collectors.toList());
    }

    public  List<Specification> getCustomerSpecifications(final Customer customer)
            throws IOException, UserNotFoundException
    {
        List<Specification> customerSpecifications = getCustomerServices(customer.getId())
                .stream()
                .map(x -> model.getSpecification(x.getSpecificationId()))
                .collect(Collectors.toList());

        customerSpecifications.addAll(getCustomerOrders(customer.getId())
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

    public Collection<Order> getEmployeeOrders(BigInteger id) throws UserNotFoundException
    {
        checkEmployeeExists(id);

        return model.getOrders().values().stream()
                .filter(order -> id.equals(order.getEmployeeId()))
                .collect(Collectors.toList());
    }

    public Collection<Service> getCustomerConnectedServices(BigInteger id) throws UserNotFoundException
    {
        checkCustomerExists(id);

        return getCustomerServices(id)
                .stream()
                .filter(service -> ServiceStatus.DISCONNECTED != service.getServiceStatus())
                .collect(Collectors.toList());
    }

    public Collection<Order> getCustomerNotFinishedOrders(BigInteger id) throws UserNotFoundException
    {
        checkCustomerExists(id);

        return getCustomerOrders(id)
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
                orders.addAll(getEmployeeOrders(employee.getId()));
            }
        }

        return orders;
    }

    public void setEmployeeWaitingStatus(BigInteger id, boolean isWaiting) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(id);

        model.getEmployee(id).setWaitingForOrders(isWaiting);

        if (isWaiting)
        {
            WorkWaitersManager.distributeOrders();
        }

        model.saveToFile();
    }

    public void goOnVacation(BigInteger id) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(id);

        model.getEmployee(id).setEmployeeStatus(EmployeeStatus.ON_VACATION);
        model.saveToFile();
    }

    public void returnFromVacation(BigInteger id) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(id);

        model.getEmployee(id).setEmployeeStatus(EmployeeStatus.WORKING);
        model.saveToFile();
    }

    public void retireEmployee(BigInteger id) throws UserNotFoundException, IOException
    {
        checkEmployeeExists(id);

        model.getEmployee(id).setEmployeeStatus(EmployeeStatus.RETIRED);
        ArrayList<Order> orders = (ArrayList<Order>) getEmployeeOrders(id);
        if (orders != null)
        {
            for (Order order : orders)
            {
                order.setEmployeeId(null);
            }
        }

        model.saveToFile();
    }

    public void assignOrder(BigInteger employeeId, BigInteger orderId)
            throws ObjectNotFoundException, UserNotFoundException, IOException
    {
        checkOrderExists(orderId);
        checkEmployeeExists(employeeId);

        Order order = model.getOrder(orderId);
        order.setEmployeeId(employeeId);
        model.saveToFile();
    }

    public void processOrder(BigInteger employeeId, BigInteger orderId)
            throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException, IOException
    {
        checkOrderExists(orderId);
        checkEmployeeExists(employeeId);

        assignOrder(employeeId, orderId);
        startOrder(orderId);
    }

    public void unassignOrder(BigInteger orderId) throws ObjectNotFoundException, IOException
    {
        checkOrderExists(orderId);

        Order order = model.getOrder(orderId);
        order.setEmployeeId(null);

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

    private void checkEmployeeExists(final BigInteger id) throws UserNotFoundException
    {
        if (!model.getEmployees().containsKey(id))
        {
            throw new UserNotFoundException("Employee (login: " + id + ") doesn't exist!");
        }
    }

    private void checkCustomerExists(final BigInteger id) throws UserNotFoundException
    {
        if (!model.getCustomers().containsKey(id))
        {
            throw new UserNotFoundException("Customer (login: " + id + ") doesn't exist!");
        }
    }

    private void checkDistrictExists(BigInteger id) throws ObjectNotFoundException
    {
        if (!model.getDistricts().containsKey(id))
        {
            throw new ObjectNotFoundException("District (id: " + id + ") doesn't exist");
        }
    }

    private void checkServiceBelongsToCustomer(BigInteger customerId, BigInteger serviceId) throws Exception
    {
        if (customerId!=model.getService(serviceId).getCustomerId())
        {
            throw new Exception("Service doesn't belong to customer!");
        }
    }

    public AbstractUser getUserByLogin(String login) throws Exception
    {
        for(Customer customer : model.getCustomers().values()){
            if(login.equals(customer.getLogin())){
                return (AbstractUser) customer;
            }
        }
        for(Employee employee : model.getEmployees().values()){
            if(login.equals(employee.getLogin())){
                return (AbstractUser) employee;
            }
        }
        throw new Exception("User (login: " + login + ") doesn't exist!");
    }
}