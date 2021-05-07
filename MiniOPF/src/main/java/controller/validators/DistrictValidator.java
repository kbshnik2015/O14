package controller.validators;

import java.math.BigInteger;

import controller.exceptions.IllegalTransitionException;
import model.Model;
import model.ModelFactory;
import model.dto.DistrictDTO;
import model.dto.EntityDTO;

public class DistrictValidator implements Validator
{
    @Override
    public boolean validate(final EntityDTO entity)
    {
        Model model = ModelFactory.getModel();
        String name = ((DistrictDTO) entity).getName();
        BigInteger parentId = ((DistrictDTO) entity).getParentId();

        if (name == null)
        {
            throw new IllegalTransitionException("District have to have a name");
        }

        if (parentId != null)
        {
            if (!model.getDistricts().keySet().contains(parentId))
            {
                throw new IllegalTransitionException(
                        "District you choose as the parent (id: " + parentId + ") doesn't exist");
            }
        }

        return true;
    }
}
