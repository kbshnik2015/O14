package controller.comparators.districtComparators;

import java.util.Comparator;

import model.dto.DistrictDTO;

public class DistrictParentIdComparator implements Comparator<DistrictDTO>
{
    @Override
    public int compare(final DistrictDTO o1, final DistrictDTO o2)
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
}
