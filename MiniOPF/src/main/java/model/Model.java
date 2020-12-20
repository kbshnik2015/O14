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
