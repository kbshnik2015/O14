package view;

import controller.Controller;
import controller.exceptions.IllegalLoginOrPasswordException;
import model.entities.AbstractUser;
import model.entities.Customer;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws Exception
    {

        Controller controller = new Controller();
        controller.createCustomer("Nikita","Kozlov","agent_K", "1","Gostinaya 5",0);
        controller.createSpecification("internet100", 100, "blablabla", false, null);
        controller.createSpecification("internet200", 200, "blablabla", false, null);
        controller.createSpecification("internet300", 300, "blablabla", false, null);
        controller.createSpecification("internet400", 400, "blablabla", false, null);
        controller.createSpecification("internet500", 500, "blablabla", false, null);
        controller.createSpecification("internet600", 600, "blablabla", false, null);
        controller.createSpecification("internet700", 700, "blablabla", false, null);
        controller.createSpecification("internet800", 800, "blablabla", false, null);
        controller.createSpecification("internet900", 900, "blablabla", false, null);
        controller.createSpecification("phone100", 100, "blublublu", false, null);
        controller.createSpecification("phone200", 200, "blublublu", false, null);
        controller.createSpecification("phone300", 300, "blublublu", false, null);
        controller.createSpecification("phone400", 400, "blublublu", false, null);
        controller.createSpecification("phone500", 500, "blublublu", false, null);
        controller.createSpecification("phone600", 600, "blublublu", false, null);
        controller.createSpecification("phone700", 700, "blublublu", false, null);
        controller.createSpecification("phone800", 800, "blublublu", false, null);
        controller.createSpecification("phone900", 900, "blublublu", false, null);
        controller.createSpecification("wifi100", 100, "blublublu", false, null);
        controller.createSpecification("wifi200", 200, "blublublu", false, null);
        controller.createSpecification("wifi300", 300, "blublublu", false, null);
        controller.createSpecification("wifi400", 400, "blublublu", false, null);
        controller.createSpecification("wifi500", 500, "blublublu", false, null);
        controller.createSpecification("wifi600", 600, "blublublu", false, null);
        controller.createSpecification("wifi700", 700, "blublublu", false, null);
        controller.createSpecification("wifi800", 800, "blublublu", false, null);
        controller.createSpecification("wifi900", 900, "blublublu", false, null);

        LoginView.start();


    }




}
