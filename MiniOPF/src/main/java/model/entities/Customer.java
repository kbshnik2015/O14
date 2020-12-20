package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Customer extends AbstractUser
{

    private String number;

    private String addres;

    private float balance;

    private ArrayList<BigInteger> servicesIds;

    private ArrayList<BigInteger> ordersIds;

}
