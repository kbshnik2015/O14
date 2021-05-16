package controller.validators;

import controller.exceptions.IllegalTransitionException;
import model.Model;
import model.ModelFactory;
import model.dto.CustomerDTO;
import model.dto.EntityDTO;

public class CreateCustomerValidator implements CreateEntityValidator
{
    private Model model = ModelFactory.getModel();

    private void checkLoginAndPasswordAreNulls(String login, String password)
    {
        if (login == null || password == null)
        {
            throw new IllegalTransitionException("Customer has to have login and password");
        }
    }

    @Override
    public boolean validate(final EntityDTO entity)
    {
        String login = ((CustomerDTO) entity).getLogin();
        String password = ((CustomerDTO) entity).getPassword();

        checkLoginAndPasswordAreNulls(login, password);

        return true;
    }
}
