package model.entities;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.ServiceStatus;

@Data
@NoArgsConstructor
public class Service
{

    private BigInteger id;

    private Date payDay;

    private BigInteger specificationId;

    private ServiceStatus serviceStatus;

    private BigInteger customerId;

    public Service(Date payDay, BigInteger spec, ServiceStatus serviceStatus, BigInteger customerId){
        this.payDay = payDay;
        this.specificationId = spec;
        this.serviceStatus = serviceStatus;
        this.customerId = customerId;
    }

    public Service(BigInteger id,Date payDay, BigInteger spec, ServiceStatus serviceStatus, BigInteger customerId){
        this.id = id;
        this.payDay = payDay;
        this.specificationId = spec;
        this.serviceStatus = serviceStatus;
        this.customerId = customerId;
    }
}
