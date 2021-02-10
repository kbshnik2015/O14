package controller.managers;


import controller.Controller;
import lombok.SneakyThrows;
import model.Model;
import model.entities.Customer;
import model.entities.Service;
import model.entities.Specification;
import model.enums.ServiceStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayDayManager extends Thread
{
    private final int ONE_MINUTE = 60000;
    private final long MONTH_IN_MILLI_SEC = 2592000000L;
    @SneakyThrows
    @Override
    public void run()
    {

        Model model = Model.getInstance();
        Controller controller = new Controller();
        List<Service> services = new ArrayList<>(model.getServices().values());
        while (true)
        {
            Date currentDate = new Date();
            for (Service service : services)
            {
                if (service.getPayDay().before(currentDate) && (ServiceStatus.ACTIVE.equals(service.getServiceStatus()) || ServiceStatus.PAY_MONEY_SUSPENDED.equals(service.getServiceStatus())))
                {
                    Customer customer = model.getCustomer(service.getCustomerLogin());
                    Specification serviceSpec = model.getSpecification(service.getSpecificationId());

                    if (customer.getBalance() >= serviceSpec.getPrice())
                    {
                        controller.changeBalanceOn(customer.getLogin(), serviceSpec.getPrice() * (-1));
                        service.setServiceStatus(ServiceStatus.ACTIVE);
                        service.setPayDay(new Date (service.getPayDay().getTime() + MONTH_IN_MILLI_SEC));
                    }
                    else
                    {
                        service.setServiceStatus(ServiceStatus.PAY_MONEY_SUSPENDED);
                    }
                }
            }
            try
            {
                sleep(ONE_MINUTE);
            }catch (InterruptedException e){
                //We don't need a notification about an interruption of this daemon thread
            }

        }
    }
}
