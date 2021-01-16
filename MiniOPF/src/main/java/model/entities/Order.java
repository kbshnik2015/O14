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

    private String customerLogin;

    private String employeeLogin;

    private BigInteger specId;
    private BigInteger serviceId;
    private OrderAim orderAim;

    private OrderStatus orderStatus;

    private String address;

    public Order(String customerLogin, String employeeLogin, BigInteger specId, BigInteger serviceId, OrderAim orderAim,OrderStatus orderStatus)
    {
        this.customerLogin = customerLogin;
        this.employeeLogin = employeeLogin;
        this.specId = specId;
        this.serviceId = serviceId;
        this.orderAim = orderAim;
        this.orderStatus = orderStatus != null ? orderStatus : OrderStatus.ENTERING;
        this.orderAim = orderStatus != null ? orderAim : OrderAim.NEW ;
    }

}
