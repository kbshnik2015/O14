package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import model.enums.EmployeeStatus;

@Data
public class Employee extends AbstractUser
{

    private ArrayList<BigInteger> ordersIds;

    private EmployeeStatus employeeStatus;

    public Employee (String firstName, String lastName, String login, String password, ArrayList<BigInteger> ordersIds, EmployeeStatus employeeStatus){
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.ordersIds = ordersIds;
        this.employeeStatus = employeeStatus;
    }
}
