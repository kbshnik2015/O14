package controller.comparators.customerComparators;

import java.util.Comparator;

import model.dto.CustomerDTO;

public class CustomerBalanceComparator implements Comparator<CustomerDTO>
{
    @Override
    public int compare(final CustomerDTO o1, final CustomerDTO o2)
    {
        return Float.compare(o1.getBalance(), o2.getBalance());
    }
}
