package model.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpecificationDTO implements EntityDTO
{
    private BigInteger id;

    private String name;

    private float price;

    private String description;

    private boolean addressDependence;

    private List<BigInteger> districtsIds;

    public SpecificationDTO(String name, float price, String description, boolean addressDependence,
            ArrayList<BigInteger> districtsIds)
    {
        this.name = name;
        this.price = price;
        this.description = description;
        this.addressDependence = addressDependence;
        this.districtsIds = districtsIds;
    }

    public SpecificationDTO(BigInteger id, String name, float price, String description, boolean addressDependence,
            List<BigInteger> districtsIds)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.addressDependence = addressDependence;
        this.districtsIds = districtsIds;
    }

}
