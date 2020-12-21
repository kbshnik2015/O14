package model.generators;

import java.math.BigInteger;

import lombok.Data;

@Data
public class IdGenerator
{
    private static BigInteger nextId = BigInteger.valueOf(1);

    public static BigInteger generateNextId(){
        return nextId.add(BigInteger.valueOf(1));
    }
}
