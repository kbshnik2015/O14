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
            float balance) throws IllegalLoginOrPasswordException
    {
        checkLoginAndPassword(login, password);

        Customer customer = new Customer(firstName, lastName, login, password, address, balance);

        return model.createCustomer(customer);
    }

    public Employee createEmployee(String firstName, String lastName, String login, String password,
            EmployeeStatus employeeStatus) throws IllegalLoginOrPasswordException
    {
        checkLoginAndPassword(login, password);

        Employee employee = new Employee(firstName, lastName, login, password, employeeStatus);

        return model.createEmployee(employee);
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
            float balance) throws UserNotFoundException
    {
        checkCustomerExists(login);

        model.updateCustomer(firstName, lastName, login, password, address, balance);
    }

    public void updateEmployee(String firstName, String lastName, String login, String password,
            EmployeeStatus employeeStatus) throws UserNotFoundException
    {
        checkEmployeeExists(login);

        model.updateEmployee(firstName, lastName, login, password, employeeStatus);
    }

    public District createDistrict(String name, BigInteger parentId)
            throws ObjectNotFoundException
    {
        if (parentId != null)
        {
            checkDistrictExists(parentId);
        }

        return model.createDistrict(name, parentId);
    }

    public void updateDistrict(BigInteger id, String name, BigInteger parentId)
            throws ObjectNotFoundException
    {
        checkDistrictExists(id);
        if (parentId != null)
        {
            checkDistrictExists(parentId);
        }

        model.updateDistrict(id, name, parentId);
    }

    public Specification createSpecification(String name, float price, String description, boolean isAddressDepended,
            ArrayList<BigInteger> districtsIds) throws ObjectNotFoundException
    {
        if (districtsIds != null)
        {
            for (BigInteger districtId : districtsIds)
            {
                checkDistrictExists(districtId);
            }
        }

        Specification specification = new Specification(name, price, description, isAddressDepended, districtsIds);

        return model.createSpecification(specification);
    }

    public void updateSpecification(BigInteger id, String name, float price, String description,
            boolean isAddressDepended,
            ArrayList<BigInteger> districtsIds) throws ObjectNotFoundException
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
    }

    public void deleteCustomerCascade(String login) throws UserNotFoundException
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
    }

    public void deleteEmployeeCascade(String login) throws UserNotFoundException
    {
        checkEmployeeExists(login);

        for (Order order : getEmployeeOrders(login))
        {
            model.deleteOrder(order);
        }
        model.deleteEmployee(model.getEmployee(login));
    }

    public void deleteEmployee(String login) throws UserNotFoundException
    {
        checkEmployeeExists(login);

        for (Order order : getEmployeeOrders(login))
        {
            order.setEmployeeLogin(null);
        }
        model.deleteEmployee(model.getEmployee(login));
    }

    public void deleteOrderCascade(BigInteger id) throws ObjectNotFoundException
    {
        checkOrderExists(id);

        model.deleteService(model.getService(model.getOrder(id).getServiceId()));
        model.deleteOrder(model.getOrder(id));
    }

    public void deleteSpecificationCascade(BigInteger id) throws ObjectNotFoundException
    {
        checkSpecificationExists(id);

        for (Service service : getSpecificationServices(id))
        {
            model.deleteService(service);
        }
        model.deleteSpecification(model.getSpecification(id));
    }

    public void deleteDistrictCascade(BigInteger id) throws ObjectNotFoundException
    {
        checkDistrictExists(id);

        if (getDistrictChildrens(id) != null)
        {
            for (District district : getDistrictChildrens(id))
            {
                model.deleteDistrict(model.getDistrict(district.getId()));
            }
        }

        model.deleteDistrict(model.getDistrict(id));
    }

    public void deleteDistrict(BigInteger id, boolean isLeaveChildrenInHierarchy) throws ObjectNotFoundException
    {
        checkDistrictExists(id);

        if (getDistrictChildrens(id) != null)
        {
            for (District district : getDistrictChildrens(id))
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
    }

    public Order createNewOrder(String customerLogin, BigInteger specId)
            throws UserNotFoundException, ObjectNotFoundException
    {
        checkCustomerExists(customerLogin);
        checkSpecificationExists(specId);

        Order order = new Order(customerLogin, null, specId, null, OrderAim.NEW, null);

        return model.createOrder(order);
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

        return model.createOrder(order);
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

        return model.createOrder(order);
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

        return model.createOrder(order);
    }

    public void startOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException
    {
        checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.ENTERING, OrderStatus.IN_PROGRESS);
    }

    public void suspendOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException
    {
        checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.SUSPENDED);
    }

    public void restoreOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException
    {
        checkOrderExists(orderId);

        moveOrderFromTo(orderId, OrderStatus.SUSPENDED, OrderStatus.IN_PROGRESS);
    }

    public void cancelOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException
    {
        checkOrderExists(orderId);

        Order order = model.getOrder(orderId);
        if (order.getOrderStatus() == OrderStatus.COMPLETED)
        {
            throw new IllegalTransitionException("Completed order (id: " + orderId + ") can't be cancelled!");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
    }

    public void completeOrder(BigInteger orderId) throws IllegalTransitionException, ObjectNotFoundException
    {
        checkOrderExists(orderId);
        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.COMPLETED);

        Order order = model.getOrder(orderId);
        if (OrderAim.NEW.equals(order.getOrderAim()))
        {
            checkSpecificationExists(order.getSpecId());

            Service service =
                    new Service(new Date(), order.getSpecId(), ServiceStatus.ACTIVE, order.getCustomerLogin());
            order.setServiceId(model.createService(service).getId());
        }
        if (OrderAim.DISCONNECT.equals(order.getOrderAim()))
        {
            checkServiceExists(order.getServiceId());

            model.getService(order.getServiceId()).setServiceStatus(ServiceStatus.DISCONNECTED);
        }
        if (OrderAim.SUSPEND.equals(order.getOrderAim()))
        {
            checkServiceExists(order.getServiceId());

            model.getService(order.getServiceId()).setServiceStatus(ServiceStatus.SUSPENDED);
        }
        if (OrderAim.RESTORE.equals(order.getOrderAim()))
        {
            checkServiceExists(order.getServiceId());

            model.getService(order.getServiceId()).setServiceStatus(ServiceStatus.ACTIVE);
        }

    }

    private void moveOrderFromTo(BigInteger orderId, OrderStatus entering, OrderStatus inProgress) throws
            IllegalTransitionException
    {
        Order order = model.getOrder(orderId);

        checkOrderInitialStatus(order, entering);

        order.setOrderStatus(inProgress);
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

    public void changeBalanceOn(String login, float amountOfMoney) throws UserNotFoundException
    {
        checkCustomerExists(login);

        Customer customer = model.getCustomer(login);
        customer.setBalance(customer.getBalance() + amountOfMoney);
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

    public List<District> getDistrictChildrens(BigInteger id)
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

    public void goOnVacation(String login) throws UserNotFoundException
    {
        checkEmployeeExists(login);

        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.ON_VACATION);
    }

    public void returnFromVacation(String login) throws UserNotFoundException
    {
        checkEmployeeExists(login);

        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.WORKING);
    }

    public void retireEmployee(String login) throws UserNotFoundException
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
    }

    public void assignOrder(String employeeLogin, BigInteger orderId)
            throws ObjectNotFoundException, UserNotFoundException
    {
        checkOrderExists(orderId);
        checkEmployeeExists(employeeLogin);

        Order order = model.getOrder(orderId);
        order.setEmployeeLogin(employeeLogin);
    }

    public void processOrder(String employeeLogin, BigInteger orderId)
            throws IllegalTransitionException, ObjectNotFoundException, UserNotFoundException
    {
        checkOrderExists(orderId);
        checkEmployeeExists(employeeLogin);

        assignOrder(employeeLogin, orderId);
        startOrder(orderId);
    }

    public void usassignOrder(BigInteger orderId) throws ObjectNotFoundException
    {
        checkOrderExists(orderId);

        Order order = model.getOrder(orderId);
        order.setEmployeeLogin(null);
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