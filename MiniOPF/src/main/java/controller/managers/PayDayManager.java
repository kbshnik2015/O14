package controller.managers;


import java.util.Collection;
import java.util.Date;

import controller.Controller;
import lombok.SneakyThrows;
import model.Model;
import model.entities.Customer;
import model.entities.Service;
import model.entities.Specification;
import model.enums.ServiceStatus;

@SuppressWarnings("FieldCanBeLocal")
public class PayDayManager extends Thread
{
    private final int ONE_MINUTE_IN_MILLI_SEC = 60000;
    private final long MONTH_IN_MILLI_SEC = 2592000000L;

    @SuppressWarnings("InfiniteLoopStatement")
    @SneakyThrows
    @Override
    public void run()
    {
        Model model = Model.getInstance();
        Controller controller = new Controller();
        Collection<Service> services = model.getServices().values();
        while (true)
        {
            Date currentDate = new Date();
            for (Service service : services)
            {
                boolean isPayDayComeForActiveService = service.getPayDay()
                        .before(currentDate) && ServiceStatus.ACTIVE.equals(service.getServiceStatus());
                if (isPayDayComeForActiveService ||
                        ServiceStatus.PAY_MONEY_SUSPENDED.equals(service.getServiceStatus()))
                {
                    Customer customer = model.getCustomer(service.getCustomerId());
                    Specification serviceSpec = model.getSpecification(service.getSpecificationId());

                    if (customer.getBalance() >= serviceSpec.getPrice())
                    {
                        controller.changeBalanceOn(customer.getId(), serviceSpec.getPrice() * (-1));
                        service.setServiceStatus(ServiceStatus.ACTIVE);
                        service.setPayDay(new Date(System.currentTimeMillis() + MONTH_IN_MILLI_SEC));
                    }
                    else
                    {
                        service.setServiceStatus(ServiceStatus.PAY_MONEY_SUSPENDED);
                    }
                }
            }
            sleepForOneMinute();
        }
    }

    private void sleepForOneMinute()
    {
        try
        {
            sleep(ONE_MINUTE_IN_MILLI_SEC);
        }
        catch (InterruptedException e)
        {
            //We don't need a notification about an interruption of this daemon thread
        }
    }
}
