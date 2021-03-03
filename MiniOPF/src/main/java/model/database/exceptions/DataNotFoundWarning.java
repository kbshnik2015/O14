package model.database.exceptions;

import controller.exceptions.Warning;

public class DataNotFoundWarning extends Warning
{
    public DataNotFoundWarning(final String s)
    {
        super(s);
    }
}
