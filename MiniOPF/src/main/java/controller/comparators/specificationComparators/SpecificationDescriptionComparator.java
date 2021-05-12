package controller.comparators.specificationComparators;

import java.util.Comparator;

import model.dto.SpecificationDTO;

public class SpecificationDescriptionComparator implements Comparator<SpecificationDTO>
{
    @Override
    public int compare(final SpecificationDTO o1, final SpecificationDTO o2)
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
}
