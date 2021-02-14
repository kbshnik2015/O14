package view;

import com.sun.javafx.sg.prism.web.NGWebView;

import controller.Controller;
import controller.exceptions.IllegalLoginOrPasswordException;
import model.Model;
import model.entities.AbstractUser;
import model.entities.Customer;
import model.entities.Service;
import model.enums.EmployeeStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        LoginView.start();
    }




}
