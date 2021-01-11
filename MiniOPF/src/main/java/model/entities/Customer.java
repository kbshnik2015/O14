package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer extends AbstractUser
{

    private String address;

    private float balance;

    private ArrayList<BigInteger> servicesIds;

    private ArrayList<BigInteger> ordersIds;

    public Customer (String firstName, String lastName, String login, String password, String address, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.balance = balance;
        this.servicesIds = servicesIds;
        this.ordersIds = ordersIds;
    }

//    public Customer (String firstName, String lastName, String login, String password, String address){
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.login = login;
//        this.password = password;
//        this.address = address;
//    }

}
