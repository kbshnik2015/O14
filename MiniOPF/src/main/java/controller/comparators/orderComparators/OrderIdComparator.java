package controller.comparators.orderComparators;

import java.util.Comparator;

import model.dto.OrderDTO;

public class OrderIdComparator implements Comparator<OrderDTO>
{
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
    {
        return o1.getId().compareTo(o2.getId());
    }
}
