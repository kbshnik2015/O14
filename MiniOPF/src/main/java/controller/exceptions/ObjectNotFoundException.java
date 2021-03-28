package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObjectNotFoundException extends RuntimeException
{
    public ObjectNotFoundException(final String s)
    {
        super(s);
    }
}
