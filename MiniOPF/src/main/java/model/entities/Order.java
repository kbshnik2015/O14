package model.entities;

import java.math.BigInteger;

import lombok.Data;
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

}
