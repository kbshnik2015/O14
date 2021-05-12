package controller.comparators.specificationComparators;

import java.util.Comparator;

import model.dto.SpecificationDTO;

public class SpecificationNameComparator implements Comparator<SpecificationDTO>
{
    @Override
    public int compare(final SpecificationDTO o1, final SpecificationDTO o2)
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
