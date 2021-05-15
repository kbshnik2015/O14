package controller.comparators;

import java.util.Comparator;

import controller.exceptions.WrongCommandArgumentsException;
import lombok.Getter;
import lombok.Setter;
import model.dto.SpecificationDTO;

public class SpecificationComparator implements Comparator<SpecificationDTO>
{
    @Getter
    @Setter
    public SpecificationSortableField sortableField;

    public SpecificationComparator(final SpecificationSortableField sortableField)
    {
        this.sortableField = sortableField;
    }

    @Override
    public int compare(final SpecificationDTO o1, final SpecificationDTO o2)
    {
        if (SpecificationSortableField.ID.equals(sortableField))
        {
            return o1.getId().compareTo(o2.getId());
        }
        else if (SpecificationSortableField.NAME.equals(sortableField))
        {
            if (o1.getName() == null)
            {
                return -1;
            }
            if (o2.getName() == null)
            {
                return 1;
            }
            return o1.getName().compareTo(o2.getName());
        }
        else if (SpecificationSortableField.PRICE.equals(sortableField))
        {
            return Float.compare(o1.getPrice(), o2.getPrice());
        }
        else if (SpecificationSortableField.DESCRIPTION.equals(sortableField))
        {
            if (o1.getDescription() == null)
            {
                return -1;
            }
            if (o2.getDescription() == null)
            {
                return 1;
            }
            return o1.getDescription().compareTo(o2.getDescription());
        }
        else if (SpecificationSortableField.ADDRESS_DEPENDENCE.equals(sortableField))
        {
            return Boolean.toString(o1.isAddressDependence()).compareTo(Boolean.toString(o2.isAddressDependence()));
        }

        throw new WrongCommandArgumentsException("Entities wasn't compared, wrong value of sortableField(" + sortableField + ")");
    }

    public enum SpecificationSortableField implements EntitySortableField
    {
        ID, NAME, PRICE, DESCRIPTION, ADDRESS_DEPENDENCE
    }
}
