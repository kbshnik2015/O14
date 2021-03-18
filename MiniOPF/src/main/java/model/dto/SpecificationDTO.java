package model.dto;

import java.math.BigInteger;
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

    private boolean isAddressDepended;

    private List<BigInteger> districtsIds;
}
