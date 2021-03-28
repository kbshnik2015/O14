package model.entities;

import java.math.BigInteger;

import lombok.Data;
import lombok.Setter;
import lombok.Synchronized;

@Data
public abstract class AbstractUser implements Entity
{

    @Setter(onMethod_ = {@Synchronized})
    BigInteger id;

    @Setter(onMethod_ = {@Synchronized})
    protected String firstName;

    @Setter(onMethod_ = {@Synchronized})
    protected String lastName;

    @Setter(onMethod_ = {@Synchronized})
    protected String login;

    @Setter(onMethod_ = {@Synchronized})
    protected String password;

}
