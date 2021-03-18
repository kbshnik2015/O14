package model.dto;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.ServiceStatus;

@Data
@NoArgsConstructor
public class ServiceDTO implements EntityDTO
{
    private BigInteger id;

    private Date payDay;

    private BigInteger specificationId;

    private ServiceStatus serviceStatus;

    private BigInteger customerId;
}
