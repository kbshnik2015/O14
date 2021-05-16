package controller.validators;

import java.math.BigInteger;

import controller.exceptions.IllegalTransitionException;
import model.Model;
import model.ModelFactory;
import model.dto.DistrictDTO;
import model.dto.EntityDTO;

public class CreateDistrictValidator implements CreateEntityValidator
{
    private Model model = ModelFactory.getModel();

    private void checkNameIsNull(String name)
    {
        if (name == null)
        {
            throw new IllegalTransitionException("District has to have a name");
        }
    }

    private void checkParentIdExists(BigInteger parentId)
    {
        if (parentId != null)
        {
            if (!model.getDistricts().keySet().contains(parentId))
            {
                throw new IllegalTransitionException(
                        "District you choose as the parent (id: " + parentId + ") doesn't exist");
            }
        }
    }

    @Override
    public boolean validate(final EntityDTO entity)
    {
        String name = ((DistrictDTO) entity).getName();
        BigInteger parentId = ((DistrictDTO) entity).getParentId();

        checkNameIsNull(name);
        checkParentIdExists(parentId);

        return true;
    }
}
