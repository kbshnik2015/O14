package controller.comparators.orderComparators;

import java.util.Comparator;

import model.dto.OrderDTO;

public class OrderCustomerIdComparator implements Comparator<OrderDTO>
{
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
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
