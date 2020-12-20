package model.entities;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;
import model.statuses.ServiceStatus;

@Data
public class Service
{

    private BigInteger id;

    private Date payDay;

    private Specification spec;

    private ServiceStatus servStatus;

}
