package model.dto;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.OrderAim;
import model.enums.OrderStatus;

@Data
@NoArgsConstructor
public class OrderDTO implements EntityDTO
{
    private BigInteger id;

    private BigInteger customerId;

    private BigInteger employeeId;

    private BigInteger specId;

    private BigInteger serviceId;

    private OrderAim orderAim;

    private OrderStatus orderStatus;

    private String address;
}
