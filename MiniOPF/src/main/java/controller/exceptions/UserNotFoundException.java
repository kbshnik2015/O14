package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends Exception
{
    public UserNotFoundException(final String message)
    {
        super(message);
    }
}
