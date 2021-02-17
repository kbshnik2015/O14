package model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.enums.EmployeeStatus;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Employee extends AbstractUser
{

    private EmployeeStatus employeeStatus;

    @Getter
    private boolean isWaitingForOrders;

    public Employee(String firstName, String lastName, String login, String password, EmployeeStatus employeeStatus)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.employeeStatus = employeeStatus;
        this.isWaitingForOrders = false;
    }

    public Employee(BigInteger id,String firstName, String lastName, String login, String password, EmployeeStatus employeeStatus)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.employeeStatus = employeeStatus;
        this.isWaitingForOrders = false;
    }
}
