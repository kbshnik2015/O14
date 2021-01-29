package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObjectNotFoundException extends Exception
{

    public ObjectNotFoundException(final String s)
    {
        super(s);
    }
}
