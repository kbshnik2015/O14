package controller.comparators.customerComparators;

import java.util.Comparator;

import model.dto.CustomerDTO;

public class CustomerAddressComparator implements Comparator<CustomerDTO>
{
    @Override
    public int compare(final CustomerDTO o1, final CustomerDTO o2)
    {
        if (o1.getAddress() == null)
        {
            return -1;
        }
        if (o2.getAddress() == null)
        {
            return 1;
        }
        return o1.getAddress().compareTo(o2.getAddress());
    }
}
