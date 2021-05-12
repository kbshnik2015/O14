package controller.comparators.specificationComparators;

import java.util.Comparator;

import model.dto.SpecificationDTO;

public class SpecificationPriceComparator implements Comparator<SpecificationDTO>
{
    @Override
    public int compare(final SpecificationDTO o1, final SpecificationDTO o2)
    {
        return Float.compare(o1.getPrice(), o2.getPrice());
    }
}
