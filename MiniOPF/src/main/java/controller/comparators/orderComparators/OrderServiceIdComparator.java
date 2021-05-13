package controller.comparators.orderComparators;

import java.util.Comparator;

import model.dto.OrderDTO;

public class OrderServiceIdComparator implements Comparator<OrderDTO>
{
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
    {
        if (o1.getServiceId() == null)
        {
            return -1;
        }
        if (o2.getServiceId() == null)
        {
            return 1;
        }
        return o1.getServiceId().compareTo(o2.getServiceId());
    }
}
