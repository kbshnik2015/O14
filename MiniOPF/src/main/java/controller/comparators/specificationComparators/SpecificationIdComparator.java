package controller.comparators.specificationComparators;

import java.util.Comparator;

import model.dto.SpecificationDTO;

public class SpecificationIdComparator implements Comparator<SpecificationDTO>
{
    @Override
    public int compare(final SpecificationDTO o1, final SpecificationDTO o2)
    {
        return o1.getId().compareTo(o2.getId());
    }
}
