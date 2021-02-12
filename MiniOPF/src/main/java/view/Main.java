package view;

import controller.Controller;
import controller.exceptions.IllegalLoginOrPasswordException;
import model.entities.AbstractUser;
import model.entities.Customer;
import model.enums.EmployeeStatus;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        LoginView.start();
    }




}
