package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import model.statuses.EmployeeStatus;

@Data
public class Employee extends AbstractUser
{

//    private WorkingCalendar workCalen;

    private ArrayList<BigInteger> ordersIds;

    private EmployeeStatus empStatus;

}
