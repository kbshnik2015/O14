package controller.validators;

import java.math.BigInteger;
import java.util.List;

import controller.exceptions.IllegalTransitionException;
import model.Model;
import model.ModelFactory;
import model.dto.EntityDTO;
import model.dto.SpecificationDTO;

public class CreateSpecificationValidator implements CreateEntityValidator
{
    private Model model = ModelFactory.getModel();

    private void checkNameIsNull(String name)
    {
        if (name == null)
        {
            throw new IllegalTransitionException("Specification has to have a name");
        }
    }

    private void checkPriceLessThenZero(float price)
    {
        if (price < 0)
        {
            throw new IllegalTransitionException("Price of specification can't be less then 0");
        }
    }

    private void checkDistrictsExist(boolean addressDependence, List<BigInteger> districtsIds)
    {
        if (addressDependence)
        {
            if (!model.getDistricts().keySet().containsAll(districtsIds))
            {
                throw new IllegalTransitionException("A district you choose doesn't exist");
            }
        }
    }

    @Override
    public boolean validate(final EntityDTO entity)
    {
        String name = ((SpecificationDTO) entity).getName();
        float price = ((SpecificationDTO) entity).getPrice();
        boolean addressDependence = ((SpecificationDTO) entity).isAddressDependence();
        List<BigInteger> districtsIds = ((SpecificationDTO) entity).getDistrictsIds();

        checkNameIsNull(name);
        checkPriceLessThenZero(price);
        checkDistrictsExist(addressDependence, districtsIds);

        return true;
    }
}
