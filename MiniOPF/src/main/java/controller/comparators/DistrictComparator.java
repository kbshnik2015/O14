package controller.comparators;

import java.util.Comparator;

import controller.exceptions.WrongCommandArgumentsException;
import lombok.Getter;
import lombok.Setter;
import model.dto.DistrictDTO;

public class DistrictComparator implements Comparator<DistrictDTO>
{
    @Getter
    @Setter
    public DistrictSortableField sortableField;

    public DistrictComparator(final DistrictSortableField sortableField)
    {
        this.sortableField = sortableField;
    }

    @Override
    public int compare(final DistrictDTO o1, final DistrictDTO o2)
    {
        if (DistrictSortableField.ID.equals(sortableField))
        {
            return o1.getId().compareTo(o2.getId());
        }
        else if (DistrictSortableField.NAME.equals(sortableField))
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
        else if (DistrictSortableField.PARENT_ID.equals(sortableField))
        {
            if (o1.getParentId() == null)
            {
                return -1;
            }
            if (o2.getParentId() == null)
            {
                return 1;
            }
            return o1.getParentId().compareTo(o2.getParentId());
        }

        throw new WrongCommandArgumentsException("Entities wasn't compared, wrong value of sortableField(" + sortableField + ")");
    }

    public enum DistrictSortableField implements EntitySortableField
    {
        ID, NAME, PARENT_ID
    }
}
