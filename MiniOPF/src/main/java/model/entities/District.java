package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import model.generators.IdGenerator;


@Data
public class District
{

    private BigInteger id;

    private String name;

    private ArrayList<BigInteger> childrenIds;

    private BigInteger parentId;

    public District(String name, ArrayList<BigInteger> childrenIds, BigInteger parentId){
        this.setId(IdGenerator.generateNextId());
        this.name = name;
        this.childrenIds = childrenIds;
        this.parentId = parentId;
    }

}
