package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;
import model.enums.EmployeeStatus;

@Data
@NoArgsConstructor
public class Employee extends AbstractUser
{

    private EmployeeStatus employeeStatus;

    public Employee (String firstName, String lastName, String login, String password, EmployeeStatus employeeStatus){
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.employeeStatus = employeeStatus;
    }
}
