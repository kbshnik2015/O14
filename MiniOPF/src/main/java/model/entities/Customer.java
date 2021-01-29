package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends AbstractUser
{

    private String address;

    private float balance;

    public Customer (String firstName, String lastName, String login, String password, String address, float balance){
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.balance = balance;

    }

}