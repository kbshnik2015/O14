package controller.validators;

import controller.exceptions.IllegalTransitionException;
import model.dto.CustomerDTO;
import model.dto.EntityDTO;

public class CustomerValidator implements Validator
{
    @Override
    public boolean validate(final EntityDTO entity)
    {
        String login = ((CustomerDTO) entity).getLogin();
        String password = ((CustomerDTO) entity).getPassword();

        if (login == null || password == null)
        {
            throw new IllegalTransitionException("Customer have to have login and password");
        }

        return true;
    }
}
