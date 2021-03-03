package model.database.exceptions;

import controller.exceptions.Warning;

public class DataNotUpdatedWarning extends Warning
{
    public DataNotUpdatedWarning(final String message)
    {
        super(message);
    }
}
