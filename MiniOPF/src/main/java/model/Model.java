package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;


import lombok.Data;
import lombok.Getter;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;

@Data
public class Model
{

    private static Model instance; //Singleton

    @Getter
    private static HashMap<BigInteger,Customer> customers;

    @Getter
    private static HashMap<BigInteger,Employee> employees;

    @Getter
    private static HashMap<BigInteger,Specification> specifications;

    @Getter
    private static ArrayList<BigInteger> employeesWaitingForOrders;

    @Getter
    private static HashMap<BigInteger,Order> orders; //Этого поля нет на диаграмме

    @Getter
    private static HashMap<BigInteger,District> districts; //Этого поля нет на диаграмме

    @Getter
    private static HashMap<BigInteger,Service> services; //Этого поля нет на диаграмме



    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }


    public static Customer createCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }

    public static void updateCustomer(Customer customer){
        customers.put(customer.getId(), customer);
    }

    public static void deleteCustomer(Customer customer){
        customers.remove(customer.getId());
    }

    public static Employee createEmployee(Employee employee) {
        employees.put(employee.getId(), employee);
        return employee;
    }

    public static void updateEmployee(Employee employee){
        employees.put(employee.getId(), employee);
    }

    public static void deleteEmployee(Employee employee){
        employees.remove(employee.getId());
    }



    public static District createDistrict(District district){
        districts.put(district.getId(), district);
        return district;
    }

    public static void updateDistrict(District district){
        districts.put(district.getId(), district);
    }

    public static void deleteDistrict(District district){
        districts.remove(district.getId());
    }

    public static Specification createSpecification(Specification specification){
        specifications.put(specification.getSpecificationId(), specification);
        return specification;
    }

    public static void updateSpecification(Specification specification){
        specifications.put(specification.getSpecificationId(), specification);
    }

    public static void deleteSpecification(Specification specification){
        specifications.remove(specification.getSpecificationId());
    }

    public static Service createService(Service service){
        services.put(service.getId(), service);
        return service;
    }

    public static void updateService(Service service){
        services.put(service.getId(), service);
    }

    public static void deleteService(Service service){
        services.remove(service.getId());
    }

    public static Order createOrder(Order order){
        orders.put(order.getId(), order);
        return order;
    }

    public static void updateOrder(Order order){
        orders.put(order.getId(), order);
    }

    public static void deleteOrder(Order order){
        orders.remove(order.getId());
    }

    public void subscribeEmployeeToWaitingForOrders(BigInteger employeeId){

    }

    public void unsubscribeEmployeeFromWaitingForWork(BigInteger employeeId){

    }

    public void notifyEmployeesWaitingForWork(BigInteger newOrderId){

    }

    public void saveToFile(){

    }

    public void saveToFile(String filepath){

    }

    public void loadFromFile(){

    }

    public void loadFromFile(String filepath){

    }

}
