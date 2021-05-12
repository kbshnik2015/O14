package controller.comparators.districtComparators;

import java.util.Comparator;

import model.dto.DistrictDTO;

public class DistrictIdComparator implements Comparator<DistrictDTO>
{
    @Override
    public int compare(final DistrictDTO o1, final DistrictDTO o2)
    {
        return o1.getId().compareTo(o2.getId());
    }
}
