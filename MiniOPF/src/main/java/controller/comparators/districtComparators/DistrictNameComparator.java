package controller.comparators.districtComparators;

import java.util.Comparator;

import model.dto.DistrictDTO;

public class DistrictNameComparator implements Comparator<DistrictDTO>
{
    @Override
    public int compare(final DistrictDTO o1, final DistrictDTO o2)
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
}
