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

    private String customerLogin;

    public Service(Date payDay, BigInteger spec, ServiceStatus serviceStatus, String customerLogin){
        this.payDay = payDay;
        this.specificationId = spec;
        this.serviceStatus = serviceStatus;
        this.customerLogin = customerLogin;
    }
}
