package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Specification
{

    private BigInteger Id;

    private float price;

    private String description;

    private boolean isAddressDepended;

    private ArrayList<BigInteger> districtsIds;


    public Specification(float price, String description, boolean isAddressDepended, ArrayList<BigInteger> districtsIds){
        this.price = price;
        this.description = description;
        this.isAddressDepended = isAddressDepended;
        this.districtsIds = districtsIds;
    }
}
