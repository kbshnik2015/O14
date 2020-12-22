package model.entities;

import java.math.BigInteger;
import java.util.ArrayList;

import lombok.Data;
import model.generators.IdGenerator;

@Data
public class Customer extends AbstractUser
{

    private String number;

    private String addres;

    private float balance;

    private ArrayList<BigInteger> servicesIds;

    private ArrayList<BigInteger> ordersIds;

    public Customer (String firstName, String lastName, String login, String password, String number, String addres, float balance, ArrayList<BigInteger> servicesIds, ArrayList<BigInteger> ordersIds){
        super(firstName, lastName, login, password);
        this.setId(IdGenerator.generateNextId());
        this.number = number;
        this.addres = addres;
        this.balance = balance;
        this.servicesIds = servicesIds;
        this.ordersIds = ordersIds;
    }

    public Customer (String firstName, String lastName, String login, String password, String number, String addres){
        super(firstName, lastName, login, password);
        this.setId(IdGenerator.generateNextId());
        this.number = number;
        this.addres = addres;
        this.servicesIds = servicesIds;
        this.ordersIds = ordersIds;
    }

}
