package model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.Model;

import java.io.IOException;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Customer extends AbstractUser
{

    private String address;

    private float balance;

    public Customer (String firstName, String lastName, String login, String password, String address, float balance) throws
            IOException
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.balance = balance;
        this.id = Model.getInstance().generateNextId();
    }

    public Customer (BigInteger id,String firstName, String lastName, String login, String password, String address, float balance) throws
            IOException
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.address = address;
        this.balance = balance;
    }

}
