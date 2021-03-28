package model.entities;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;
import model.enums.OrderAim;
import model.enums.OrderStatus;

@Data
@NoArgsConstructor
public class Order implements Entity
{

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger id;

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger customerId;

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger employeeId;

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger specId;

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger serviceId;

    @Setter(onMethod_ = {@Synchronized})
    private OrderAim orderAim;

    @Setter(onMethod_ = {@Synchronized})
    private OrderStatus orderStatus;

    @Setter(onMethod_ = {@Synchronized})
    private String address;

    public Order(BigInteger customerId, BigInteger employeeId, BigInteger specId, BigInteger serviceId,
            OrderAim orderAim, OrderStatus orderStatus)
    {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.specId = specId;
        this.serviceId = serviceId;
        this.orderAim = orderAim;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.ENTERING;
        this.orderAim = orderAim != null ? orderAim : OrderAim.NEW;
    }

    public Order(BigInteger id, BigInteger customerId, BigInteger employeeId, BigInteger specId, BigInteger serviceId,
            OrderAim orderAim, OrderStatus orderStatus, String address)
    {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.specId = specId;
        this.serviceId = serviceId;
        this.orderStatus = orderStatus;
        this.orderAim = orderAim;
        this.address = address;
    }

}
