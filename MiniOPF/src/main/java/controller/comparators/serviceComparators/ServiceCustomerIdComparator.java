package controller.comparators.serviceComparators;

import java.util.Comparator;

import model.dto.ServiceDTO;

public class ServiceCustomerIdComparator implements Comparator<ServiceDTO>
{
    @Override
    public int compare(final ServiceDTO o1, final ServiceDTO o2)
    {
        if (o1.getCustomerId() == null)
        {
            return -1;
        }
        if (o2.getCustomerId() == null)
        {
            return 1;
        }
        return o1.getCustomerId().compareTo(o2.getCustomerId());
    }
}