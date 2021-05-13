package controller.comparators.orderComparators;

import java.util.Comparator;

import model.dto.OrderDTO;

public class OrderSpecIdComparator implements Comparator<OrderDTO>
{
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
    {
        if (o1.getSpecId() == null)
        {
            return -1;
        }
        if (o2.getSpecId() == null)
        {
            return 1;
        }
        return o1.getSpecId().compareTo(o2.getSpecId());
    }
}
