package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Specification implements Entity
{

    private BigInteger id;

    private String name;

    private float price;

    private String description;

    private boolean isAddressDepended;

    private List<BigInteger> districtsIds;


    public Specification(String name, float price, String description, boolean isAddressDepended,
            ArrayList<BigInteger> districtsIds)
    {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isAddressDepended = isAddressDepended;
        this.districtsIds = districtsIds;
    }

    public Specification(BigInteger id, String name, float price, String description, boolean isAddressDepended,
            List<BigInteger> districtsIds)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isAddressDepended = isAddressDepended;
        this.districtsIds = districtsIds;
    }
}
