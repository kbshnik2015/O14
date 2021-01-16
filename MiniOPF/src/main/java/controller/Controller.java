package controller;


import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import controller.exceptions.IllegalLoginOrPasswordException;
import lombok.Data;
import model.Model;
import model.entities.*;
import model.enums.EmployeeStatus;
import model.enums.OrderAim;
import model.enums.OrderStatus;
import model.enums.ServiceStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Controller
{

    Model model = Model.getInstance();

    private <T> boolean isLoginExisted(T t)
    {
        boolean result = false;
        if (t instanceof Customer)
        {
            if (model.getCustomers()
                    .containsKey(((Customer) t).getLogin()))
            {
                result = true;
                new IllegalLoginOrPasswordException();
            }
        }
        if (t instanceof Employee)
        {
            if (model.getEmployees()
                    .containsKey(((Employee) t).getLogin()))
            {
                result = true;
                new IllegalLoginOrPasswordException();
            }
        }
        return result;
    }

    public Customer createCustomer(String firstName, String lastName, String login, String password, String addres,
                                   float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds)
    {
        Customer customer = new Customer(firstName, lastName, login, password, addres, balance);
        if (!isLoginExisted(customer))
        {
            return model.createCustomer(customer);
        }
        else
        {
            return null;
        }
    }

    public void updateCustomer(String firstName, String lastName, String login, String password, String address,
                               float balance)
    {
        if (model.getCustomers()
                .containsKey(login))
        {
            Customer customer = model.getCustomer(login);
            if (firstName != null)
            {
                customer.setFirstName(firstName);
            }
            if (lastName != null)
            {
                customer.setLastName(lastName);
            }
            if (password != null)
            {
                customer.setPassword(password);
            }
            if (address != null)
            {
                customer.setAddress(address);
            }
            if (balance >= 0)
            {
                customer.setBalance(balance);
            }

            model.updateCustomer(customer);
        }
        else
        {
            new UserNotFoundException();
        }
    }

    public void deleteCustomer(Customer customer)
    {
        model.deleteCustomer(customer);
    }

    public Customer getCustomerByLogin(String login)
    {
        return model.getCustomer(login);
    }

    public Employee createEmployee(String firstName, String lastName, String login, String password,
                                   ArrayList<BigInteger> ordersIds, EmployeeStatus employeeStatus)
    {
        Employee employee = new Employee(firstName, lastName, login, password, employeeStatus);
        if (!isLoginExisted(employee))
        {
            return model.createEmployee(employee);
        }
        else
        {
            return null;
        }
    }

    public void updateEmployee(String firstName, String lastName, String login, String password,
                               ArrayList<BigInteger> ordersIds, EmployeeStatus employeeStatus)
    {
        if (model.getEmployees()
                .containsKey(login))
        {
            Employee employee = model.getEmployee(login);
            if (firstName != null)
            {
                employee.setFirstName(firstName);
            }
            if (lastName != null)
            {
                employee.setLastName(lastName);
            }
            if (password != null)
            {
                employee.setPassword(password);
            }
            if (employeeStatus != null)
            {
                employee.setEmployeeStatus(employeeStatus);
            }
            model.updateEmployee(employee);
        }
        else
        {
            new UserNotFoundException();
        }
    }

    public void deleteEmployee(Employee employee)
    {
        model.deleteEmployee(employee);
    }

    public Employee getEmployeeByLogin(String login)
    {
        return model.getEmployee(login);
    }

    public District createDistrict(String name, ArrayList<BigInteger> childrenIds, BigInteger parentId)
    {
        District district = new District(name, childrenIds, parentId);
        return model.createDistrict(district);
    }

    public void updateDistrict(BigInteger id, String name, ArrayList<BigInteger> childrenIds, BigInteger parentId)
    {
        if (model.getDistricts()
                .containsKey(id))
        {
            District district = model.getDistrict(id);
            if (name != null)
            {
                district.setName(name);
            }
            if (childrenIds != null)
            {
                district.setChildrenIds(childrenIds);
            }
            if (parentId != null)
            {
                district.setParentId(parentId);
            }
            model.updateDistrict(district);
        }
        else
        {
            new ObjectNotFoundException();
        }
    }

    public void deleteDistrict(District district)
    {
        model.deleteDistrict(district);
    }

    public District getDistrictById(BigInteger id)
    {
        return model.getDistrict(id);
    }

    public Specification createSpecification(float price, String description, boolean isAddressDepended,
                                             ArrayList<BigInteger> districtsIds)
    {
        Specification specification = new Specification(price, description, isAddressDepended, districtsIds);
        return model.createSpecification(specification);
    }

    public void updateSpecification(BigInteger id, float price, String description, boolean isAddressDepended,
                                    ArrayList<BigInteger> districtsIds)
    {
        if (model.getSpecifications()
                .containsKey(id))
        {
            Specification specification = model.getSpecification(id);
            if (price >= 0)
            {
                specification.setPrice(price);
            }
            if (description != null)
            {
                specification.setDescription(description);
            }
            if (districtsIds != null)
            {
                specification.setDistrictsIds(districtsIds);
            }
            specification.setAddressDepended(isAddressDepended);
            model.updateSpecification(specification);
        }
        else
        {
            new ObjectNotFoundException();
        }
    }

    public void deleteSpecification(Specification specification)
    {
        model.deleteSpecification(specification);
    }

    public Specification getSpecificationById(BigInteger id)
    {
        return model.getSpecification(id);
    }

    public Service createService(Date payDay, BigInteger specificationId, ServiceStatus servStatus,
                                 String customerLogin)
    {
        Service service = new Service(payDay, specificationId, servStatus, customerLogin);
        return model.createService(service);
    }

    public void updateService(BigInteger id, Date payDay, BigInteger specificationId, ServiceStatus servStatus)
    {
        if (model.getServices()
                .containsKey(id))
        {
            Service service = model.getService(id);
            if (payDay != null)
            {
                service.setPayDay(payDay);
            }
            if (specificationId != null)
            {
                service.setSpecificationId(specificationId);
            }
            if (servStatus != null)
            {
                service.setServiceStatus(servStatus);
            }
            model.updateService(service);
        }
        else
        {
            new ObjectNotFoundException();
        }
    }

    public void deleteService(Service service)
    {
        model.deleteService(service);
    }

    public void updateOrder(BigInteger id, String customerLogin, String employeeLogin, OrderAim orderAim,
                            OrderStatus orderStatus, String address)
    {
        if (model.getOrders()
                .containsKey(id))
        {
            Order order = model.getOrder(id);
            if (customerLogin != null)
            {
                order.setCustomerLogin(customerLogin);
            }
            if (employeeLogin != null)
            {
                order.setEmployeeLogin(employeeLogin);
            }
            if (orderAim != null)
            {
                order.setOrderAim(orderAim);
            }
            if (orderStatus != null)
            {
                order.setOrderStatus(orderStatus);
            }
            if (address != null)
            {
                order.setAddress(address);
            }
            model.updateOrder(order);
        }
        else
        {
            new ObjectNotFoundException();
        }
    }

    public AbstractUser login(String login, String password) throws IllegalLoginOrPasswordException
    {
        AbstractUser user = model.getCustomer(login);
        if (user == null)
        {
            user = model.getEmployee(login);
        }
        if (user != null && user.getPassword()
                .equals(password))
        {
            return user;
        }
        else
        {
            throw new IllegalLoginOrPasswordException();
        }
    }

    public void startOrder(BigInteger orderId) throws IllegalTransitionException
    {
        moveOrderFromTo(orderId, OrderStatus.ENTERING, OrderStatus.IN_PROGRESS);
    }

    public void suspendOrder(BigInteger orderId) throws IllegalTransitionException
    {
        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.SUSPENDED);
    }

    public void restoreOrder(BigInteger orderId) throws IllegalTransitionException
    {
        moveOrderFromTo(orderId, OrderStatus.SUSPENDED, OrderStatus.IN_PROGRESS);
    }

    public void cancelOrder(BigInteger orderId) throws IllegalTransitionException
    {
        Order order = model.getOrder(orderId);
        if (order.getOrderStatus() == OrderStatus.COMPLETED)
        {
            throw new IllegalTransitionException();
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
    }

    public void completeOrder(BigInteger orderId) throws IllegalTransitionException
    {
        moveOrderFromTo(orderId, OrderStatus.IN_PROGRESS, OrderStatus.COMPLETED);
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
        if (order.getOrderStatus() != initialStatus)
        {
            throw new IllegalTransitionException();
        }
    }

    public void changeBalanceOn(Float amountOfMoney, String login)
    {
        Customer customer = getCustomerByLogin(login);
        customer.setBalance(customer.getBalance() + amountOfMoney);
    }

    /**
     * Method for Customer to get his Services
     */
    public ArrayList<Service> getCustomerConnectedServices(String login)
    {
        ArrayList<Service> connectedServices = (ArrayList<Service>) model.getCustomerServices(login)
                .stream()
                .filter(x -> x.getServiceStatus() != ServiceStatus.DISCONNECTED)
                .collect(Collectors.toList());

        return connectedServices;
    }

    public ArrayList<Order> getCustomerNotFinishedOrders(String login)
    {
        ArrayList<Order> notFinishedOrder = (ArrayList<Order>) model.getCustomerOrders(login)
                .stream()
                .filter(x -> x.getOrderStatus() != OrderStatus.COMPLETED)
                .collect(Collectors.toList());

        return notFinishedOrder;
    }

    public List<Order> getOrdersByEmployee(String employeeLogin){
        ArrayList <Order> orders = (ArrayList<Order>) model.getOrders().entrySet()
                .stream()
                .map(x->x.getValue())
                .filter(x->x.getEmployeeLogin().equals(employeeLogin))
                .collect(Collectors.toList());
        return null;
    }

    public Order createNewOrder(String customerLogin, BigInteger specId)
    {
        Order order = new Order(customerLogin, null, specId, null, OrderAim.NEW, null);
        return model.createOrder(order);
    }

    public Order createSuspendOrder(String customerLogin, BigInteger serviceId)
    {
        Order order = new Order(customerLogin, null, null, serviceId, OrderAim.SUSPEND, null);
        return model.createOrder(order);
    }

    public Order createRestoreOrder(String customerLogin, BigInteger serviceId)
    {
        Order order = new Order(customerLogin,null, null,serviceId,OrderAim.RESTORE, null );
        return model.createOrder(order);
    }

    public Order createDisconnectOrder(String customerLogin, BigInteger serviceId)
    {
        Order order = new Order(customerLogin, null,null,serviceId,OrderAim.DISCONNECT,null);
        return model.createOrder(order);
    }

    public ArrayList<Order> getOrdersOfEmployeesOnVacation()
    {
        ArrayList<Order> orders = new ArrayList<>();
        for (Employee employee : model.getEmployees().values())
        {
            if (EmployeeStatus.ON_VACATION.equals(employee.getEmployeeStatus()))
            {
                orders.addAll(model.getEmployeeOrders(employee.getLogin()));
            }
        }
        return orders;
    }

    public void goOnVacation(String login)
    {
        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.ON_VACATION);
    }

    public void returnFromVacation(String login)
    {
        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.WORKING);
    }

    public void retireEmployee(String login)
    {
        model.getEmployee(login).setEmployeeStatus(EmployeeStatus.RETIRED);
        ArrayList<Order> orders = (ArrayList)getOrdersByEmployee(login);
        for(Order order : orders)
            order.setEmployeeLogin(null);
    }

    public void assignOrder(String employeeLogin, BigInteger orderId)
    {
        Order order = model.getOrder(orderId);
        order.setCustomerLogin(employeeLogin);
    }

    public void processOrder(String employeeLogin, BigInteger orderId) throws IllegalTransitionException
    {
        assignOrder(employeeLogin, orderId);
        startOrder(orderId);
    }

    public void usassignOrder(BigInteger orderId)
    {
        Order order = model.getOrder(orderId);
        order.setEmployeeLogin(null);
    }
}
