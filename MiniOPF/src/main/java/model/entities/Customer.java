package model.entities;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Customer extends AbstractUser
{
    @Setter(onMethod_ = {@Synchronized})
    private BigInteger districtId;

    @Setter(onMethod_ = {@Synchronized})
    private String address;

    @Setter(onMethod_ = {@Synchronized})
    private float balance;

    public Customer(String firstName, String lastName, String login, String password, BigInteger districtId,
            String address, float balance)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.districtId = districtId;
        this.address = address;
        this.balance = balance;
    }

    public Customer(BigInteger id, String firstName, String lastName, String login, String password,
            BigInteger districtId, String address, float balance)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.districtId = districtId;
        this.address = address;
        this.balance = balance;
    }
}
