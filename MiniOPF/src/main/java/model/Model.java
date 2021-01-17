package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.entities.Customer;
import model.entities.District;
import model.entities.Employee;
import model.entities.Order;
import model.entities.Service;
import model.entities.Specification;
import model.enums.EmployeeStatus;

@Data
public class Model
{

    @Setter
    private static Model instance; //Singleton

    @Setter
    @Getter
    private BigInteger nextId = BigInteger.valueOf(1);

    @Setter
    @Getter
    private Map<String,Customer> customers;

    @Setter
    @Getter
    private Map<String,Employee> employees;

    @Setter
    @Getter
    private Map<BigInteger,Specification> specifications;

    @Setter
    @Getter
    private Map<BigInteger,Order> orders;

    @Setter
    @Getter
    private Map<BigInteger,District> districts;

    @Setter
    @Getter
    private Map<BigInteger,Service> services;

    @Setter
    @Getter
    private WorkWaiters workWaiters;

    public Model(){
        this.orders = new HashMap<>();
        this.services = new HashMap<>();
        this.specifications = new HashMap<>();
        this.districts = new HashMap<>();
        this.employees = new HashMap<>();
        this.customers = new HashMap<>();
        this.workWaiters = new WorkWaiters();
    }

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
        if (customers == null){
            customers = new HashMap<>();
        }
        customers.put(customer.getLogin(), customer);
        return customer;
    }

    public void updateCustomer(Customer customer){
        customers.put(customer.getLogin(), customer);
    }

    public void deleteCustomer(Customer customer){
        customers.remove(customer.getLogin());
    }

    public Customer getCustomer(String login) {
        return customers.get(login);
    }

    public Employee createEmployee(Employee employee) {
        if (employees == null){
            employees = new HashMap<>();
        }
        employees.put(employee.getLogin(), employee);
        return employee;
    }

    public void updateEmployee(Employee employee){
        employees.put(employee.getLogin(), employee);
    }

    public void deleteEmployee(Employee employee){
        employees.remove(employee.getLogin());
    }

    public Employee getEmployee(String login) {
        return employees.get(login);
    }

    public District createDistrict(District district){
        if (districts == null){
            districts = new HashMap<>();
        }
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

    public District getDistrict(BigInteger Id) {
        return districts.get(Id);
    }

    public Specification createSpecification(Specification specification){
        if (specifications == null){
            specifications = new HashMap<>();
        }
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

    public Specification getSpecification(BigInteger Id) {
        return specifications.get(Id);
    }

    public Service createService(Service service){
        if (services == null){
            services = new HashMap<>();
        }
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

    public Service getService(BigInteger Id) {
        return services.get(Id);
    }

    public Order createOrder(Order order){
        if (orders == null){
            orders = new HashMap<>();
        }
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

    public Order getOrder(BigInteger Id) {
        return orders.get(Id);
    }

    @JsonIgnore
    public List<Order> getFreeOrders(){
        List<Order> list = new ArrayList<>();
        for (Order ord : orders.values()) {
            if (ord.getEmployeeLogin() == null)
            {
                    list.add(ord);
            }
        }
        return list;
    }

    @JsonIgnore
    public Order getFreeOrder(){
        for (Order ord : orders.values()) {
            if (ord.getEmployeeLogin() == null)
            {
                return ord;
            }
        }
        return null;
    }

    public List<Order> getCustomerOrders(String login){
        List<Order> list = orders.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(e -> e.getCustomerLogin().equals(login))
                .collect(Collectors.toList());
         return list;
    }

    public List<Service> getCustomerServices(String login){
        List<Service> list = services.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(e -> e.getCustomerLogin().equals(login))
                .collect(Collectors.toList());
        return list;
    }

    public List<Order> getEmployeeOrders(String login){
        List<Order> list = orders.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(e -> e.getEmployeeLogin().equals(login))
                .collect(Collectors.toList());
        return list;
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
