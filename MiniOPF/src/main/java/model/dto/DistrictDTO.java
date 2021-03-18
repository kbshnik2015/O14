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
}
