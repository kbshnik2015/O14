package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class District
{

    private BigInteger id;

    private String name;

    private BigInteger parentId;

    public District(String name, BigInteger parentId){
        this.name = name;
        this.parentId = parentId;
    }

}
