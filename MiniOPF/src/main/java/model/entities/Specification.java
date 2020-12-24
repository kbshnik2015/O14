package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import model.generators.IdGenerator;

@Data
public class Specification
{

    private BigInteger specificationId;

    private float price;

    private String description;

    private boolean isAddressDepended;

    private ArrayList<BigInteger> districtsIds;


    public Specification(float price, String description, boolean isAddressDepended, ArrayList<BigInteger> districtsIds){
        this.setSpecificationId(IdGenerator.generateNextId());
        this.price = price;
        this.description = description;
        this.isAddressDepended = isAddressDepended;
        this.districtsIds = districtsIds;
    }
}
