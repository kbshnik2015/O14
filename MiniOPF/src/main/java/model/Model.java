package model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;


import jdk.nashorn.internal.parser.JSONParser;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;

@Data
@NoArgsConstructor
public class Model
{

    private static Model instance; //Singleton

    @Setter
    @Getter
    private BigInteger nextId = BigInteger.valueOf(1);

    @Setter
    @Getter
    private HashMap<String,Customer> customers = new HashMap<>();

    @Setter
    @Getter
    private HashMap<String,Employee> employees = new HashMap<>();

    @Setter
    @Getter
    private HashMap<BigInteger,Specification> specifications = new HashMap<>();

    @Setter
    @Getter
    private ArrayList<BigInteger> employeesWaitingForOrders;

    @Setter
    @Getter
    private HashMap<BigInteger,Order> orders = new HashMap<>();

    @Setter
    @Getter
    private HashMap<BigInteger,District> districts = new HashMap<>();

    @Setter
    @Getter
    private HashMap<BigInteger,Service> services = new HashMap<>();



    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public BigInteger generateNextId(){
        nextId = nextId.add(BigInteger.valueOf(1));
        return nextId;
    }


    public Customer createCustomer(Customer customer) {
        customers.put(customer.getLogin(), customer);
        return customer;
    }

    public void updateCustomer(Customer customer){
        customers.put(customer.getLogin(), customer);
    }

    public void deleteCustomer(Customer customer){
        customers.remove(customer.getLogin());
    }

    public Customer getCustomerByLogin(String login) {
        return customers.get(login);
    }

    public Employee createEmployee(Employee employee) {
        employees.put(employee.getLogin(), employee);
        return employee;
    }

    public void updateEmployee(Employee employee){
        employees.put(employee.getLogin(), employee);
    }

    public void deleteEmployee(Employee employee){
        employees.remove(employee.getLogin());
    }

    public Employee getEmployeeByLogin(String login) {
        return employees.get(login);
    }

    public District createDistrict(District district){
        district.setId(generateNextId());
        districts.put(district.getId(), district);
        return district;
    }

    public void updateDistrict(District district){
        districts.put(district.getId(), district);
    }

    public void deleteDistrict(District district){
        districts.remove(district.getId());
    }

    public District getDistrictById(BigInteger Id) {
        return districts.get(Id);
    }

    public Specification createSpecification(Specification specification){
        specification.setId(generateNextId());
        specifications.put(specification.getId(), specification);
        return specification;
    }

    public void updateSpecification(Specification specification){
        specifications.put(specification.getId(), specification);
    }

    public void deleteSpecification(Specification specification){
        specifications.remove(specification.getId());
    }

    public Specification getSpecificationById(BigInteger Id) {
        return specifications.get(Id);
    }

    public Service createService(Service service){
        service.setId(generateNextId());
        services.put(service.getId(), service);
        return service;
    }

    public void updateService(Service service){
        services.put(service.getId(), service);
    }

    public void deleteService(Service service){
        services.remove(service.getId());
    }

    public Service getServiceById(BigInteger Id) {
        return services.get(Id);
    }

    public Order createOrder(Order order){
        order.setId(generateNextId());
        orders.put(order.getId(), order);
        return order;
    }

    public void updateOrder(Order order){
        orders.put(order.getId(), order);
    }

    public void deleteOrder(Order order){
        orders.remove(order.getId());
    }

    public Order getOrderById(BigInteger Id) {
        return orders.get(Id);
    }

    public void subscribeEmployeeToWaitingForOrders(BigInteger employeeId){

    }

    public void unsubscribeEmployeeFromWaitingForWork(BigInteger employeeId){

    }

    public void notifyEmployeesWaitingForWork(BigInteger newOrderId){

    }

    public void saveToFile() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try(FileWriter writer = new FileWriter("test.json", false)){
            mapper.writeValue(writer, instance);
        }
    }

    public void saveToFile(String filepath)throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try(FileWriter writer = new FileWriter(filepath, false)){
            mapper.writeValue(writer, instance);
        }
    }

    public Model loadFromFile() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try(FileReader reader = new FileReader("test.json")){
            instance = mapper.readValue(reader, Model.class);
        }
        return instance;
    }

    public Model loadFromFile(String filepath)throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        try(FileReader reader = new FileReader(filepath)){
            instance = mapper.readValue(reader, Model.class);
        }
        return instance;
    }

}
