package controller.validators;

import model.dto.EntityDTO;

public interface CreateEntityValidator
{
    boolean validate(EntityDTO entity);
}
