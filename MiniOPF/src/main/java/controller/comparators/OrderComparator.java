package controller.comparators;

import java.util.Comparator;

import controller.exceptions.WrongCommandArgumentsException;
import lombok.Getter;
import lombok.Setter;
import model.dto.OrderDTO;

public class OrderComparator implements Comparator<OrderDTO>
{
    @Getter
    @Setter
    public OrderSortableField sortableField;

    public OrderComparator(final OrderSortableField sortableField)
    {
        this.sortableField = sortableField;
    }

    @Override
    public int compare(final OrderDTO o1, final OrderDTO o2)
    {
        if (OrderSortableField.ID.equals(sortableField))
        {
            return o1.getId().compareTo(o2.getId());
        }
        else if (OrderSortableField.CUSTOMER_ID.equals(sortableField))
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
        else if (OrderSortableField.EMPLOYEE_ID.equals(sortableField))
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
        else if (OrderSortableField.SPECIFICATION_ID.equals(sortableField))
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
        else if (OrderSortableField.SERVICE_ID.equals(sortableField))
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
        else if (OrderSortableField.ORDER_AIM.equals(sortableField))
        {
            return o1.getOrderAim().toString().compareTo(o2.getOrderAim().toString());
        }
        else if (OrderSortableField.ORDER_STATUS.equals(sortableField))
        {
            return o1.getOrderStatus().toString().compareTo(o2.getOrderStatus().toString());
        }
        else if (OrderSortableField.ADDRESS.equals(sortableField))
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

        throw new WrongCommandArgumentsException("Entities wasn't compared, wrong value of sortableField(" + sortableField + ")");
    }

    public enum OrderSortableField implements EntitySortableField
    {
        ID, CUSTOMER_ID, EMPLOYEE_ID, SPECIFICATION_ID, SERVICE_ID, ORDER_AIM, ORDER_STATUS, ADDRESS
    }
}
