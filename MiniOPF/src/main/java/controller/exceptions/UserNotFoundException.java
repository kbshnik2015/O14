package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(final String message)
    {
        super(message);
    }
}
