package controller.comparators.serviceComparators;

import java.util.Comparator;

import model.dto.ServiceDTO;

public class ServiceStatusComparator implements Comparator<ServiceDTO>
{
    @Override
    public int compare(final ServiceDTO o1, final ServiceDTO o2)
    {
        return o1.getServiceStatus().toString().compareTo(o2.getServiceStatus().toString());
    }
}
