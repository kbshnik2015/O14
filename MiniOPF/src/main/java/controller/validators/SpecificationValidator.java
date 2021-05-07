package controller.validators;

import java.math.BigInteger;
import java.util.List;

import controller.exceptions.IllegalTransitionException;
import model.Model;
import model.ModelFactory;
import model.dto.EntityDTO;
import model.dto.SpecificationDTO;

public class SpecificationValidator implements Validator
{
    @Override
    public boolean validate(final EntityDTO entity)
    {
        Model model = ModelFactory.getModel();
        String name = ((SpecificationDTO) entity).getName();
        float price = ((SpecificationDTO) entity).getPrice();
        boolean addressDependence = ((SpecificationDTO) entity).isAddressDependence();
        List<BigInteger> districtsIds = ((SpecificationDTO) entity).getDistrictsIds();

        if (name == null)
        {
            throw new IllegalTransitionException("Specification have to have a name");
        }

        if (price < 0 )
        {
            throw new IllegalTransitionException("Price of specification can't be less then 0");
        }

        if (addressDependence)
        {
            if (!model.getDistricts().keySet().containsAll(districtsIds))
            {
                throw new IllegalTransitionException("A district you choose doesn't exist");
            }
        }

        return true;
    }
}
