package controller;


import controller.exceptions.IllegalTransitionException;
import controller.exceptions.ObjectNotFoundException;
import controller.exceptions.UserNotFoundException;
import controller.exceptions.IllegalLoginException;
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
import java.util.stream.Collectors;

@Data
public class Controller
{

    Model model = Model.getInstance();

    private <T> boolean isLoginExisted(T t){
        boolean result = false;
        if (t instanceof Customer)
        {
            if (model.getCustomers().containsKey(((Customer) t).getLogin()))
            {
                result = true;
                new IllegalLoginException();
            }
        }
        if (t instanceof Employee)
        {
            if (model.getEmployees().containsKey(((Employee) t).getLogin()))
            {
                result = true;
                new IllegalLoginException();
            }
        }
        return result;
    }

    public Customer createCustomer(String firstName, String lastName, String login, String password, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
        Customer customer = new Customer(firstName, lastName, login, password, addres, balance);
        if(!isLoginExisted(customer))
        {
            return model.createCustomer(customer);
        }
        else {
            return null;
        }
    }

    public void updateCustomer(String firstName, String lastName, String login, String password, String address, float balance){
        if (model.getCustomers().containsKey(login))
        {
            Customer customer = model.getCustomerByLogin(login);
            if (firstName != null)
                customer.setFirstName(firstName);
            if(lastName != null)
                customer.setLastName(lastName);
            if(password != null)
                customer.setPassword(password);
            if(address != null)
                customer.setAddress(address);
            if(balance >= 0)
                customer.setBalance(balance);

            model.updateCustomer(customer);
        }
        else {
            new UserNotFoundException();
        }
    }

    public void deleteCustomer(Customer customer){
        model.deleteCustomer(customer);
    }

    public Customer getCustomerByLogin(String login) {
        return model.getCustomerByLogin(login);
    }

    public Employee createEmployee(String firstName, String lastName, String login, String password, ArrayList<BigInteger> ordersIds, EmployeeStatus employeeStatus){
        Employee employee = new Employee(firstName, lastName,login,password, employeeStatus);
        if(!isLoginExisted(employee))
        {
            return model.createEmployee(employee);
        }
        else {
            return null;
        }
    }

    public void updateEmployee(String firstName, String lastName, String login, String password, ArrayList<BigInteger> ordersIds, EmployeeStatus employeeStatus) {
        if (model.getEmployees().containsKey(login))
        {
            Employee employee = model.getEmployeeByLogin(login);
            if (firstName != null)
                employee.setFirstName(firstName);
            if(lastName != null)
                employee.setLastName(lastName);
            if(password != null)
                employee.setPassword(password);
            if(employeeStatus != null)
                employee.setEmployeeStatus(employeeStatus);
            model.updateEmployee(employee);
        }
        else {
            new UserNotFoundException();
        }
    }

    public void deleteEmployee(Employee employee){
        model.deleteEmployee(employee);
    }

    public Employee getEmployeeByLogin(String login) {
        return model.getEmployeeByLogin(login);
    }

    public District createDistrict(String name, ArrayList<BigInteger> childrenIds, BigInteger parentId){
        District district = new District(name, childrenIds, parentId);
        return model.createDistrict(district);
    }

    public void updateDistrict(BigInteger id, String name, ArrayList<BigInteger> childrenIds, BigInteger parentId){
        if (model.getDistricts().containsKey(id))
        {
            District district = model.getDistrictById(id);
            if (name != null)
                district.setName(name);
            if(childrenIds != null)
                district.setChildrenIds(childrenIds);
            if(parentId != null)
                district.setParentId(parentId);
            model.updateDistrict(district);
        }
        else {
            new ObjectNotFoundException();
        }
    }

    public void deleteDistrict(District district){
        model.deleteDistrict(district);
    }

    public District getDistrictById(BigInteger id) {
        return model.getDistrictById(id);
    }

    public Specification createSpecification(float price, String description, boolean isAddressDepended, ArrayList<BigInteger> districtsIds){
        Specification specification = new Specification(price, description, isAddressDepended, districtsIds);
        return model.createSpecification(specification);
    }

    public void updateSpecification(BigInteger id, float price, String description, boolean isAddressDepended, ArrayList<BigInteger> districtsIds){
        if (model.getSpecifications().containsKey(id))
        {
            Specification specification = model.getSpecificationById(id);
            if (price >= 0)
                specification.setPrice(price);
            if(description != null)
                specification.setDescription(description);
            if(districtsIds != null)
                specification.setDistrictsIds(districtsIds);
            specification.setAddressDepended(isAddressDepended);
            model.updateSpecification(specification);
        }
        else {
            new ObjectNotFoundException();
        }
    }

    public void deleteSpecification(Specification specification){
        model.deleteSpecification(specification);
    }

    public Specification getSpecificationById(BigInteger id) {
        return model.getSpecificationById(id);
    }

    public Service createService(Date payDay, BigInteger specificationId, ServiceStatus servStatus, String customerLogin){
        Service service = new Service(payDay, specificationId, servStatus, customerLogin);
        return model.createService(service);
    }

    public void updateService(BigInteger id, Date payDay, BigInteger specificationId, ServiceStatus servStatus){
        if (model.getServices().containsKey(id))
        {
            Service service = model.getServiceById(id);
            if (payDay != null)
                service.setPayDay(payDay);
            if(specificationId != null)
                service.setSpecificationId(specificationId);
            if(servStatus != null)
                service.setServiceStatus(servStatus);
            model.updateService(service);
        }
        else {
            new ObjectNotFoundException();
        }
    }

    public void deleteService(Service service){
        model.deleteService(service);
    }

    public Service getServiceById(BigInteger id) {
        return model.getServiceById(id);
    }

    public Order createOrder(String customerLogin, String employeeLogin, OrderAim orderAim, OrderStatus orderStatus, String address){
        Order order = new Order(customerLogin, employeeLogin, orderAim, orderStatus, address);
        return model.createOrder(order);
    }

    public void updateOrder(BigInteger id, String customerLogin, String employeeLogin, OrderAim orderAim, OrderStatus orderStatus, String address){
        if (model.getOrders().containsKey(id))
        {
            Order order = model.getOrderById(id);
            if (customerLogin != null)
                order.setCustomerLogin(customerLogin);
            if(employeeLogin!= null)
                order.setEmployeeLogin(employeeLogin);
            if(orderAim != null)
                order.setOrderAim(orderAim);
            if(orderStatus != null)
                order.setOrderStatus(orderStatus);
            if(address != null)
                order.setAddress(address);
            model.updateOrder(order);
        }
        else {
            new ObjectNotFoundException();
        }
    }

    public void deleteOrder(Order order){
        model.deleteOrder(order);
    }

    public Order getOrderById(BigInteger id) {
        return model.getOrderById(id);
    }

    public AbstractUser login(String login, String password) throws IllegalLoginException
    {
        AbstractUser user;
        if((user = model.getCustomerByLogin(login))==null)
            user = model.getEmployeeByLogin(login);
        if((user!=null)&&(user.getPassword().equals(password))){
            return user;
        }
        else
            throw new IllegalLoginException();
    }

    public void startOrder( BigInteger orderId) throws IllegalTransitionException
    {
        Order order = model.getOrderById(orderId);
        if(order.getOrderStatus()==OrderStatus.ENTERING)
            order.setOrderStatus(OrderStatus.IN_PROGRESS);
        else
            throw new IllegalTransitionException();
    }

    public void suspendOrder( BigInteger orderId) throws IllegalTransitionException
    {
        Order order = model.getOrderById(orderId);
        if(order.getOrderStatus()==OrderStatus.IN_PROGRESS)
            order.setOrderStatus(OrderStatus.SUSPENDED);
        else
            throw new IllegalTransitionException();
    }

    public void restoreOrder(BigInteger orderId) throws IllegalTransitionException
    {
        Order order = model.getOrderById(orderId);
        if(order.getOrderStatus()==OrderStatus.SUSPENDED)
            order.setOrderStatus(OrderStatus.IN_PROGRESS);
        else
            throw new IllegalTransitionException();
    }

    public void cancelOrder (BigInteger orderId) throws IllegalTransitionException
    {
        Order order = model.getOrderById(orderId);
        if(order.getOrderStatus()!=OrderStatus.COMPLETED)
            order.setOrderStatus(OrderStatus.CANCELLED);
        else
            throw new IllegalTransitionException();
    }

    public void completeOrder (BigInteger orderId) throws IllegalTransitionException
    {
        Order order = model.getOrderById(orderId);
        if(order.getOrderStatus()!=OrderStatus.CANCELLED)
            order.setOrderStatus(OrderStatus.COMPLETED);
        else
            throw new IllegalTransitionException();
    }

    public void topUpBalance(Float amountOfMoney, String login)
    {
        Customer customer = getCustomerByLogin(login);
        customer.setBalance(customer.getBalance()+amountOfMoney);
    }

    public void topDownBalance(Float amountOfMoney, String login)
    {
        Customer customer = getCustomerByLogin(login);
        customer.setBalance(customer.getBalance()-amountOfMoney);
    }

    public ArrayList<Service> getCustomerConnectedServices(String login){
        ArrayList<Service> connectedServices = (ArrayList<Service>) model.getCustomerServices(login).stream()
                .filter(x->x.getServiceStatus()==ServiceStatus.ACTIVE)
                .collect(Collectors.toList());
        return connectedServices;
    }

    public ArrayList<Order> getCustomerNotFinishedOrders(String login){
        ArrayList<Order> notFinishedOrder = (ArrayList<Order>) model.getCustomerOrders(login).stream()
                .filter(x->x.getOrderStatus()!=OrderStatus.COMPLETED)
                .collect(Collectors.toList());
        return notFinishedOrder;
    }

    public Order createNewOrder(String customerLogin, BigInteger specId, boolean isForced){
        Customer customer = model.getCustomers().get(customerLogin);
        Order order = new Order(customer.getLogin(), OrderAim.NEW, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);


        Service service = new Service(new Date(), specId, isForced? (ServiceStatus.ACTIVE) : (ServiceStatus.SUSPENDED), customerLogin);
        model.createService(service);


        return order;
    }

    public Order createSuspendOrder(String customerLogin, BigInteger serviceId, boolean isForced){
        Customer customer = model.getCustomers().get(customerLogin);
        Order order = new Order(customer.getLogin(), OrderAim.SUSPENDED, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);

        if(isForced)
        {
            model.getServices().get(serviceId).setServiceStatus(ServiceStatus.SUSPENDED);
        }

        return order;
    }

    public Order createRestoreOrder(String customerLogin, BigInteger serviceId, boolean isForced){
        Customer customer = model.getCustomers().get(customerLogin);
        Order order = new Order(customer.getLogin(), OrderAim.RESTORE, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);


        if(isForced && ((model.getServiceById(serviceId).getServiceStatus().equals(ServiceStatus.SUSPENDED) || (model.getServiceById(serviceId).getServiceStatus().equals(ServiceStatus.DISCONNECTED)))))
        {
            model.getServices().get(serviceId).setServiceStatus(ServiceStatus.ACTIVE);
        }

        return order;
    }

    public Order createDisconnectOrder(String customerLogin, BigInteger serviceId, boolean isForced){
        Customer customer = model.getCustomers().get(customerLogin);
        Order order = new Order(customer.getLogin(), OrderAim.DISCONNECT, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);


        if(isForced)
        {
            model.getServices().get(serviceId).setServiceStatus(ServiceStatus.DISCONNECTED);
        }

        return order;
    }


    public  ArrayList<Order> getOrdersOfEmployeesOnVacation(){
        ArrayList<Order> orders = new ArrayList<>();
        for (Employee emp : model.getEmployees().values()) {
            if (emp.getEmployeeStatus().equals(EmployeeStatus.ON_VACATION))
            {
                for (int i = 0; i < model.getEmployeeOrders(emp.getLogin()).size(); i++){
                    orders.add( model.getEmployeeOrders(emp.getLogin()).get(i));
                }
            }
        }
        return orders;
    }

    public  void goOnVacation(String login){
        model.getEmployees().get(login).setEmployeeStatus(EmployeeStatus.ON_VACATION);
    }

    public  void returnFromVacation(String login){
        model.getEmployees().get(login).setEmployeeStatus(EmployeeStatus.WORKING);
    }

    public void retireEmployee(String login){
        model.getEmployees().get(login).setEmployeeStatus(EmployeeStatus.RETIRED);
    }

    public void subscribeToWork(BigInteger employeeId){
        model.getEmployeesWaitingForOrders().add(employeeId);
    }

    public void unsubscribeFromWork(BigInteger employeeId){
        model.getEmployeesWaitingForOrders().remove(employeeId);
    }

}
