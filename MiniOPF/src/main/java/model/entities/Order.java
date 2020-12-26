package model.entities;

import java.math.BigInteger;

import lombok.Data;
import model.enums.OrderAim;
import model.enums.OrderStatus;

@Data
public class Order
{

    private BigInteger id;

    private String customerLogin;

    private String employeeLogin;

    private OrderAim orderAim;

    private OrderStatus orderStatus;

    private String address;

    public Order(String customerLogin, String employeeLogin, OrderAim orderAim, OrderStatus orderStatus, String address){
        this.customerLogin = customerLogin;
        this.employeeLogin = employeeLogin;
        this.orderAim = orderAim;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public Order(String customerLogin, OrderAim orderAim, OrderStatus orderStatus){
        this.customerLogin = customerLogin;
        this.orderAim = orderAim;
        this.orderStatus = orderStatus;
    }

}
