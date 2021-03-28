package model.dto;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DistrictDTO implements EntityDTO
{
    private BigInteger id;

    private String name;

    private BigInteger parentId;

    public DistrictDTO(String name, BigInteger parentId)
    {
        this.name = name;
        this.parentId = parentId;
    }

    public DistrictDTO(BigInteger id, String name, BigInteger parentId)
    {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
