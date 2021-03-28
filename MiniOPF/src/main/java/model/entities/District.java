package model.entities;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;


@Data
@NoArgsConstructor
public class District implements Entity
{

    @Setter(onMethod_ = {@Synchronized})
    private BigInteger id;

    @Setter(onMethod_ = {@Synchronized})
    private String name;

    @Setter(onMethod_ = {@Synchronized})
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
