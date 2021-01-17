package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class WorkWaiters
{

    @Setter
    @Getter
    private List<String> employeesWaitingForOrders;

    public WorkWaiters(){
        employeesWaitingForOrders = new ArrayList<>();
    }

    public void subscribe(String login){
        if (employeesWaitingForOrders == null){
            employeesWaitingForOrders = new ArrayList<>();
        }
        employeesWaitingForOrders.add(login);
    }

    public void unsubscribe(String login){
        employeesWaitingForOrders.remove(login);
    }

}
