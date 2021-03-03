package model.database.exceptions;

import controller.exceptions.Warning;

public class DataNotCreatedWarning extends Warning
{
    public DataNotCreatedWarning(final String message)
    {
        super(message);
    }
}
