package controller.comparators.specificationComparators;

import java.util.Comparator;

import model.dto.SpecificationDTO;

public class SpecificationAddressDependenceComparator implements Comparator<SpecificationDTO>
{
    @Override
    public int compare(final SpecificationDTO o1, final SpecificationDTO o2)
    {
        return Boolean.toString(o1.isAddressDependence()).compareTo(Boolean.toString(o2.isAddressDependence()));
    }
}
