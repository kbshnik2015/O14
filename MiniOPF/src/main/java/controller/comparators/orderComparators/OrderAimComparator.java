package controller.comparators.orderComparators;

import java.util.Comparator;

import model.dto.OrderDTO;

public class OrderAimComparator implements Comparator<OrderDTO>
{
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
    {
        return o1.getOrderAim().toString().compareTo(o2.getOrderAim().toString());
    }
}
