package controller.comparators.serviceComparators;

import java.util.Comparator;

import model.dto.ServiceDTO;

public class ServicePayDayComparator implements Comparator<ServiceDTO>
{
    @Override
    public int compare(final ServiceDTO o1, final ServiceDTO o2)
    {
        if (o1.getPayDay() == null)
        {
            return -1;
        }
        if (o2.getPayDay() == null)
        {
            return 1;
        }
        return o1.getPayDay().compareTo(o2.getPayDay());
    }
}
