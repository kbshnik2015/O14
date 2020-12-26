package controller;


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

    public AbstractUser login(String login, String password){
        return null;
    }

    public Customer createCustomer(String firstName, String lastName, String login, String password, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
        Customer customer = new Customer(firstName, lastName, login, password, addres, balance, servicesIds, ordersIds);
        if(!isLoginExisted(customer))
        {
            return model.createCustomer(customer);
        }
        else {
            return null;
        }
    }

    public void updateCustomer(String firstName, String lastName, String login, String password, String address, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
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
            if(servicesIds != null)
                customer.setServicesIds(servicesIds);
            if(ordersIds != null)
                customer.setOrdersIds(ordersIds);
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
        Employee employee = new Employee(firstName, lastName,login,password,ordersIds, employeeStatus);
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
            if(ordersIds != null)
                employee.setOrdersIds(ordersIds);
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

    public Service createService(Date payDay, BigInteger specificationId, ServiceStatus servStatus){
        Service service = new Service(payDay, specificationId, servStatus);
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

    public void startOrder(BigInteger employeeId, BigInteger orderId){

    }

    public void suspendOrder(BigInteger employeeId, BigInteger orderId){

    }

    public void restoreOrder(BigInteger employeeId, BigInteger orderId) {

    }

    public void cancelOrder (BigInteger employeeId, BigInteger orderId){

    }

    public void completeOrder (BigInteger employeeId, BigInteger orderId)
    {

    }

    public void topUpBalance(Float amountOfMoney){

    }

    public void topDownBalance(Float amountOfMoney)
    {

    }

    public ArrayList<Service> getCustomerConnectedServices(BigInteger customerId){
        return null;
    }

    public ArrayList<Service> getCustomerNotFinishedOrders(BigInteger customerId){
        return null;
    }

    public Order createNewOrder(String customerLogin, BigInteger specId, boolean isForced){
        Customer customer = model.getCustomers().get(customerLogin);
        Order order = new Order(customer.getLogin(), OrderAim.NEW, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);
        customer.getOrdersIds().add(order.getId());

        Service service = new Service(new Date(), specId, isForced? (ServiceStatus.ACTIVE) : (ServiceStatus.SUSPENDED));
        model.createService(service);
        customer.getServicesIds().add(service.getId());

        return order;
    }

    public Order createSuspendOrder(String customerLogin, BigInteger serviceId, boolean isForced){
        Customer customer = model.getCustomers().get(customerLogin);
        Order order = new Order(customer.getLogin(), OrderAim.SUSPENDED, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);
        customer.getOrdersIds().add(order.getId());

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
        customer.getOrdersIds().add(order.getId());

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
        customer.getOrdersIds().add(order.getId());

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
                for (int i = 0; i < emp.getOrdersIds().size(); i++){
                    orders.add(model.getOrders().get(emp.getOrdersIds().get(i)));
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
