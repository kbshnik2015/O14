package model.entities;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;
import lombok.ToString;
import model.enums.EmployeeStatus;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Employee extends AbstractUser
{

    @Setter(onMethod_ = {@Synchronized})
    private EmployeeStatus employeeStatus;
    
    @Setter(onMethod_ = {@Synchronized})
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

    public Employee(BigInteger id, String firstName, String lastName, String login, String password,
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
