package model.entities;

import java.math.BigInteger;

import lombok.Data;

@Data
public abstract class AbstractUser
{

    private BigInteger id;

    private String firstName;

    private String lastName;

    //todo: unique
    private String login;

    private String password;

    public AbstractUser (String firstName, String lastName, String login, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

}
