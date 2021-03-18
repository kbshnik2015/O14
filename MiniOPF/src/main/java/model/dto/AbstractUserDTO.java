package model.dto;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractUserDTO implements EntityDTO
{
    BigInteger id;

    protected String firstName;

    protected String lastName;

    protected String login;

    protected String password;
}
