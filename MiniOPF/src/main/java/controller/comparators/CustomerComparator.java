package controller.comparators;

import java.util.Comparator;

import controller.exceptions.WrongCommandArgumentsException;
import lombok.Getter;
import lombok.Setter;
import model.dto.CustomerDTO;

public class CustomerComparator implements Comparator<CustomerDTO>
{
    @Getter
    @Setter
    public CustomerSortableField sortableField;

    public CustomerComparator(final CustomerSortableField sortableField)
    {
        this.sortableField = sortableField;
    }

    @Override
    public int compare(final CustomerDTO o1, final CustomerDTO o2)
    {
        if (CustomerSortableField.ID.equals(sortableField))
        {
            return o1.getId().compareTo(o2.getId());
        }
        else if (CustomerSortableField.FIRST_NAME.equals(sortableField))
        {
            if (o1.getFirstName() == null)
            {
                return -1;
            }
            if (o2.getFirstName() == null)
            {
                return 1;
            }
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
        else if (CustomerSortableField.LAST_NAME.equals(sortableField))
        {
            if (o1.getLastName() == null)
            {
                return -1;
            }
            if (o2.getLastName() == null)
            {
                return 1;
            }
            return o1.getLastName().compareTo(o2.getLastName());
        }
        else if (CustomerSortableField.LOGIN.equals(sortableField))
        {
            return o1.getLogin().compareTo(o2.getLogin());
        }
        else if (CustomerSortableField.BALANCE.equals(sortableField))
        {
            return Float.compare(o1.getBalance(), o2.getBalance());
        }
        else if (CustomerSortableField.DISTRICT_ID.equals(sortableField))
        {
            if (o1.getDistrictId() == null)
            {
                return -1;
            }
            if (o2.getDistrictId() == null)
            {
                return 1;
            }
            return o1.getDistrictId().compareTo(o2.getDistrictId());
        }
        else if (CustomerSortableField.ADDRESS.equals(sortableField))
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

    public enum CustomerSortableField implements EntitySortableField
    {
        ID, FIRST_NAME, LAST_NAME, LOGIN, BALANCE, DISTRICT_ID, ADDRESS
    }
}
