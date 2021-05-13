package controller.comparators.customerComparators;

import java.util.Comparator;

import model.dto.CustomerDTO;

public class CustomerLastNameComparator implements Comparator<CustomerDTO>
{
    @Override
    public int compare(final CustomerDTO o1, final CustomerDTO o2)
    {
        if (o1.getLastName() == null)
        {
            return -1;
        }
        if (o2.getLastName() == null)
        {
            return 1;
        }
        return o1.getLastName().compareTo(o2.getLastName());
    }
}
