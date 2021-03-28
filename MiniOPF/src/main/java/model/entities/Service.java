package model.entities;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;
import model.enums.ServiceStatus;

@Data
@NoArgsConstructor
public class Service implements Entity
{

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger id;

    @Setter(onMethod_ = {@Synchronized})
    private Date payDay;

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger specificationId;

    @Setter(onMethod_ = {@Synchronized})
    private ServiceStatus serviceStatus;

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger customerId;

    public Service(Date payDay, BigInteger spec, ServiceStatus serviceStatus, BigInteger customerId)
    {
        this.payDay = payDay;
        this.specificationId = spec;
        this.serviceStatus = serviceStatus;
        this.customerId = customerId;
    }

    public Service(BigInteger id, Date payDay, BigInteger spec, ServiceStatus serviceStatus, BigInteger customerId)
    {
        this.id = id;
        this.payDay = payDay;
        this.specificationId = spec;
        this.serviceStatus = serviceStatus;
        this.customerId = customerId;
    }
}
