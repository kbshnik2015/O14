package controller.comparators;

import java.util.Comparator;

import controller.exceptions.WrongCommandArgumentsException;
import lombok.Getter;
import lombok.Setter;
import model.dto.ServiceDTO;

public class ServiceComparator implements Comparator<ServiceDTO>
{
    @Getter
    @Setter
    public ServiceSortableField sortableField;

    public ServiceComparator(final ServiceSortableField sortableField)
    {
        this.sortableField = sortableField;
    }

    @Override
    public int compare(final ServiceDTO o1, final ServiceDTO o2)
    {
        if (ServiceSortableField.ID.equals(sortableField))
        {
            return o1.getId().compareTo(o2.getId());
        }
        else if (ServiceSortableField.PAYDAY.equals(sortableField))
        {
            if (o1.getPayDay() == null)
            {
                return -1;
            }
            if (o2.getPayDay() == null)
            {
                return 1;
            }
            return o1.getPayDay().compareTo(o2.getPayDay());
        }
        else if (ServiceSortableField.SPECIFICATION_ID.equals(sortableField))
        {
            if (o1.getSpecificationId() == null)
            {
                return -1;
            }
            if (o2.getSpecificationId() == null)
            {
                return 1;
            }
            return o1.getSpecificationId().compareTo(o2.getSpecificationId());
        }
        else if (ServiceSortableField.SERVICE_STATUS.equals(sortableField))
        {
            return o1.getServiceStatus().toString().compareTo(o2.getServiceStatus().toString());
        }
        else if (ServiceSortableField.CUSTOMER_ID.equals(sortableField))
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

        throw new WrongCommandArgumentsException(
                "Entities wasn't compared, wrong value of sortableField(" + sortableField + ")");
    }

    public enum ServiceSortableField implements EntitySortableField
    {
        ID, PAYDAY, SPECIFICATION_ID, SERVICE_STATUS, CUSTOMER_ID
    }
}
