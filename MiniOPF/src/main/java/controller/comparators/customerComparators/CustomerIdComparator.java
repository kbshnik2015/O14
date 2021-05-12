package controller.comparators.customerComparators;

import java.util.Comparator;

import model.dto.CustomerDTO;

public class CustomerIdComparator implements Comparator<CustomerDTO>
{
    @Override
    public int compare(final CustomerDTO o1, final CustomerDTO o2)
    {
        return o1.getId().compareTo(o2.getId());
    }
}
