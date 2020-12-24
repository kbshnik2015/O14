package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import model.generators.IdGenerator;
import model.statuses.OrderAim;
import model.statuses.OrderStatus;

@Data
public class Order
{

    private BigInteger id;

    private Customer customer;

    private Employee employee;

    private OrderAim orderAim;

    private OrderStatus orderStatus;

    private Address address;

    public Order(Customer customer, Employee employee, OrderAim orderAim, OrderStatus orderStatus, Address address){
        this.setId(IdGenerator.generateNextId());
        this.customer = customer;
        this.employee = employee;
        this.orderAim = orderAim;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public Order(Customer customer, OrderAim orderAim, OrderStatus orderStatus){
        this.setId(IdGenerator.generateNextId());
        this.customer = customer;
        this.orderAim = orderAim;
        this.orderStatus = orderStatus;
    }

}
