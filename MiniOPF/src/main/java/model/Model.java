package model;

import java.math.BigInteger;
import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import lombok.Data;
import model.entities.Customer;
import model.entities.Employee;
import model.entities.Specification;

@Data
public class Model implements Observable
{

    private static Model instance; //Singleton

    private static ArrayList<Customer> customers;

    private static ArrayList<Employee> employees;

    private static ArrayList<Specification> specifications;

    private static ArrayList<BigInteger> employeesWaitingForOrders;


    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public static Customer createCustomer(String firstName, String lastName, String login, String password, String number, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
    Customer customer = new Customer(firstName, lastName, login, password, number, addres, balance, servicesIds, ordersIds);
    customers.add(customer);
    return customer;
}

    public static Customer createCustomer(String login, String password){
        Customer customer = new Customer(login, password);
        customers.add(customer);
        return customer;
    }

    public static void updateCustomer(BigInteger id, String firstName, String lastName, String password, String number, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
        for (Customer cust : customers)
        {
            if (cust.getId() == id)
            {
                if (firstName != null)
                    cust.setFirstName(firstName);
                if (lastName != null)
                    cust.setLastName(lastName);
                if (password != null)
                    cust.setPassword(password);
                if (number != null)
                    cust.setNumber(number);
                if (addres != null)
                    cust.setAddres(addres);
                if (balance >= 0)
                    cust.setBalance(balance);
                if (servicesIds != null)
                    cust.setServicesIds(servicesIds);
                if (ordersIds != null)
                    cust.setOrdersIds(ordersIds);
            }
        }
    }

    public static void deleteCustomer(BigInteger id){
        for (Customer cust : customers)
        {
            if (cust.getId() == id)
                customers.remove(cust);
        }
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

    @Override
    public void removeListener(InvalidationListener listener){

    }

    @Override
    public void addListener(InvalidationListener listener){

    }

}
