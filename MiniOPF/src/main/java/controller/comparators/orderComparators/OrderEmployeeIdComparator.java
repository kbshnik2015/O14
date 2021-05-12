package controller.comparators.orderComparators;

import java.util.Comparator;

import model.dto.OrderDTO;

public class OrderEmployeeIdComparator implements Comparator<OrderDTO>
{
    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
    {
        if (o1.getEmployeeId() == null)
        {
            return -1;
        }
        if (o2.getEmployeeId() == null)
        {
            return 1;
        }
        return o1.getEmployeeId().compareTo(o2.getEmployeeId());
    }
}