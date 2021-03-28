package model.dto;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.enums.EmployeeStatus;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeDTO extends AbstractUserDTO
{
    private EmployeeStatus employeeStatus;

    private boolean isWaitingForOrders;

    public EmployeeDTO(String firstName, String lastName, String login, String password, EmployeeStatus employeeStatus)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.employeeStatus = employeeStatus;
        this.isWaitingForOrders = false;
    }

    public EmployeeDTO(BigInteger id, String firstName, String lastName, String login, String password,
            EmployeeStatus employeeStatus, boolean isWaitingForOrders)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.employeeStatus = employeeStatus;
        this.isWaitingForOrders = isWaitingForOrders;
    }
}
