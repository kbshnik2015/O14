package controller.comparators.customerComparators;

import java.util.Comparator;

import model.dto.CustomerDTO;

public class CustomerFirstNameComparator implements Comparator<CustomerDTO>
{
    @Override
    public int compare(final CustomerDTO o1, final CustomerDTO o2)
    {
        if (o1.getFirstName() == null)
        {
            return -1;
        }
        if (o2.getFirstName() == null)
        {
            return 1;
        }
        return o1.getFirstName().compareTo(o2.getFirstName());
    }
}
