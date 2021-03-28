package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;

@Data
@NoArgsConstructor
public class Specification implements Entity
{

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger id;

    @Setter(onMethod_ = {@Synchronized})
    private String name;

    @Setter(onMethod_ = {@Synchronized})
    private float price;

    @Setter(onMethod_ = {@Synchronized})
    private String description;

    @Setter(onMethod_ = {@Synchronized})
    private boolean isAddressDepended;

    @Setter(onMethod_ = {@Synchronized})
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
