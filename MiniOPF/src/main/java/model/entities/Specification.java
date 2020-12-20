package model.entities;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class Specification
{

    private BigInteger specificationId;

    private float price;

    private String description;

    private boolean isAddressDepended;

    private List<BigInteger> districtsIds;
}
