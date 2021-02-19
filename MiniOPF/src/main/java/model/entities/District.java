package model.entities;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class District implements Entity
{

    private BigInteger id;

    private String name;

    private BigInteger parentId;

    public District(String name, BigInteger parentId)
    {
        this.name = name;
        this.parentId = parentId;
    }

    public District(BigInteger id, String name, BigInteger parentId)
    {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

}
