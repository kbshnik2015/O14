package model.entities;

import java.math.BigInteger;

import lombok.Data;

@Data
public abstract class AbstractUser
{

    protected String firstName;

    protected String lastName;

    protected String login;

    protected String password;

}
