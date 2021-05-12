package controller.comparators.serviceComparators;

import java.util.Comparator;

import model.dto.ServiceDTO;

public class ServiceSpecIdComparator implements Comparator<ServiceDTO>
{
    @Override
    public int compare(final ServiceDTO o1, final ServiceDTO o2)
    {
        if (o1.getSpecificationId() == null)
        {
            return -1;
        }
        if (o2.getSpecificationId() == null)
        {
            return 1;
        }
        return o1.getSpecificationId().compareTo(o2.getSpecificationId());
    }
}
