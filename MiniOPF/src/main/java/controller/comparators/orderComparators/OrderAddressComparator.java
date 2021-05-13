package controller.comparators.orderComparators;

import java.util.Comparator;

import model.dto.OrderDTO;

public class OrderAddressComparator implements Comparator<OrderDTO>
{
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
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
