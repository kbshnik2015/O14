package model.dto;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerDTO extends AbstractUserDTO
{
    private BigInteger districtId;

    private String address;

    private float balance;

    public CustomerDTO(String firstName, String lastName, String login, String password, BigInteger districtId,
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

    public CustomerDTO(BigInteger id, String firstName, String lastName, String login, String password,
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
