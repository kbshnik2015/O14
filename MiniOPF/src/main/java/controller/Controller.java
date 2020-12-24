package controller;


import lombok.Data;
import model.Model;
import model.entities.*;
import model.statuses.EmployeeStatus;
import model.statuses.OrderAim;
import model.statuses.OrderStatus;
import model.statuses.ServiceStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Controller
{

    Model model = Model.getInstance();

    public AbstractUser login(String login, String password){

    }

//    public Customer createCustomer(String firstName, String lastName, String login, String password, String number, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
//        Customer customer = new Customer(firstName, lastName, login, password, number, addres, balance, servicesIds, ordersIds);
//        model.createCustomer(customer);
//        return customer;
//    }
//
//    public Customer createCustomer(String firstName, String lastName, String login, String password, String number, String addres){
//        Customer customer = new Customer( firstName, lastName, login, password, number, addres);
//        model.createCustomer(customer);
//        return customer;
//    }
//
//    public void updateCustomer(BigInteger id, String firstName, String lastName, String password, String number, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
//        model.updateCustomer(id, firstName, lastName, password, number, addres, balance, servicesIds, ordersIds);
//    }
//
//    public void deleteCustomer(BigInteger id){
//        model.deleteCustomer(id);
//    }

    public Employee createEmployee(String login, String password){

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

    public List<Service> getCustomerConnectedServices(BigInteger customerId){

    }

    public List<Service> getCustomerNotFinishedOrders(BigInteger customerId){

    }

    public Order createNewOrder(BigInteger customerId, BigInteger specId, boolean isForced){
        Customer customer = model.getCustomers().get(customerId);
        Order order = new Order(customer, OrderAim.NEW, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);
        customer.getOrdersIds().add(order.getId());

        Service service = new Service(new Date(),model.getSpecifications().get(specId), isForced? (ServiceStatus.ACTIVE) : (ServiceStatus.SUSPENDED));
        customer.getServicesIds().add(service.getId());

        return order;
    }

    public Order createSuspendOrder(BigInteger customerId, BigInteger serviceId, boolean isForced){
        Customer customer = model.getCustomers().get(customerId);
        Order order = new Order(customer, OrderAim.SUSPENDED, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);
        customer.getOrdersIds().add(order.getId());

        if(isForced)
        {
            model.getServices().get(serviceId).setServStatus(ServiceStatus.SUSPENDED);
        }

        return order;
    }

    public Order createRestoreOrder(BigInteger customerId, BigInteger serviceId, boolean isForced){
        Customer customer = model.getCustomers().get(customerId);
        Order order = new Order(customer, OrderAim.RESTORE, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);
        customer.getOrdersIds().add(order.getId());

//        if(isForced)
//        {
//            model.getServices().get(serviceId).setServStatus(ServiceStatus.SUSPENDED); TODO: КАКОЙ СТАТУС СТАВИТЬ?
//        }

        return order;
    }

    public Order createDisconnectOrder(BigInteger customerId, BigInteger serviceId, boolean isForced){
        Customer customer = model.getCustomers().get(customerId);
        Order order = new Order(customer, OrderAim.DISCONNECT, isForced? (OrderStatus.COMPLETED) : (OrderStatus.ENTERING));
        model.createOrder(order);
        customer.getOrdersIds().add(order.getId());

        if(isForced)
        {
            model.getServices().get(serviceId).setServStatus(ServiceStatus.DISCONNECTED);
        }

        return order;
    }

    public Specification createSpecification(float price, String description, boolean isAddressDepended, ArrayList<BigInteger> districtsIds){
        Specification specification = new Specification(price, description, isAddressDepended, districtsIds);
        model.createSpecification(specification);
        return specification;
    }

    public District createDistrict(/*BigInteger id,*/ String name, ArrayList<BigInteger> childrenIds, BigInteger parentId){
        District district = new District(name, childrenIds, parentId);
        model.createDistrict(district);
        return district;
    }

    public  ArrayList<Order> getOrdersOfEmployeesOnVacation(){
        ArrayList<Order> orders = new ArrayList<>();
        for (Employee emp : model.getEmployees().values()) {
            if (emp.getEmpStatus().equals(EmployeeStatus.ON_VACATION))
            {
                for (int i = 0; i < emp.getOrdersIds().size(); i++){
                    orders.add(model.getOrders().get(emp.getOrdersIds().get(i)));
                }
            }
        }

        return orders;
    }

    public  void goOnVacation(BigInteger employeeId){
        model.getEmployees().get(employeeId).setEmpStatus(EmployeeStatus.ON_VACATION);
    }

    public  void returnFromVacation(BigInteger employeeId){
        model.getEmployees().get(employeeId).setEmpStatus(EmployeeStatus.WORKING);
    }

    public void retireEmployee(BigInteger employeeId){
        model.getEmployees().get(employeeId).setEmpStatus(EmployeeStatus.RETIRED);
    }

    public void subscribeToWork(BigInteger employeeId){
        model.getEmployeesWaitingForOrders().add(employeeId);
    }

    public void unsubscribeFromWork(BigInteger employeeId){
        model.getEmployeesWaitingForOrders().remove(employeeId);
    }

}
