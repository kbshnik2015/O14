package model.entities;

import lombok.Data;

import java.math.BigInteger;

@Data
public abstract class AbstractUser
{

    BigInteger id;

    protected String firstName;

    protected String lastName;

    protected String login;

    protected String password;

}
