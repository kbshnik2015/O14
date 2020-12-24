package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import model.generators.IdGenerator;
import model.statuses.ServiceStatus;

@Data
public class Service
{

    private BigInteger id;

    private Date payDay;

    private Specification spec;

    private ServiceStatus servStatus;

    public Service(Date payDay, Specification spec, ServiceStatus servStatus){
        this.setId(IdGenerator.generateNextId());
        this.payDay = payDay;
        this.spec = spec;
        this.servStatus = servStatus;
    }

}
