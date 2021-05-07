package controller.validators;

import model.dto.EntityDTO;

public interface Validator
{
    boolean validate(EntityDTO entity);
}
