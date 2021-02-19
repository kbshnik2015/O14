package model.entities;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.OrderAim;
import model.enums.OrderStatus;

@Data
@NoArgsConstructor
public class Order
{

    private BigInteger id;

    private BigInteger customerId;

    private BigInteger employeeId;

    private BigInteger specId;

    private BigInteger serviceId;

    private OrderAim orderAim;

    private OrderStatus orderStatus;

    private String address;

    public Order(BigInteger customerId, BigInteger employeeId, BigInteger specId, BigInteger serviceId, OrderAim orderAim,
                 OrderStatus orderStatus)
    {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.specId = specId;
        this.serviceId = serviceId;
        this.orderAim = orderAim;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.ENTERING;
        this.orderAim = orderAim != null ? orderAim : OrderAim.NEW;
    }

    public Order(BigInteger id,BigInteger customerId, BigInteger employeeId, BigInteger specId, BigInteger serviceId, OrderAim orderAim,
                 OrderStatus orderStatus,String address )
    {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.specId = specId;
        this.serviceId = serviceId;
        this.orderStatus = orderStatus;
        this.orderAim = orderAim ;
        this.address =address;
    }

}
