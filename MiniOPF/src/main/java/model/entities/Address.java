package model.entities;

import java.math.BigInteger;

import lombok.Data;

@Data
public class Address
{

    private String address;

    private BigInteger districtId;

}
