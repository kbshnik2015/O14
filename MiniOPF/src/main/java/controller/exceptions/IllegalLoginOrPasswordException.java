package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalLoginOrPasswordException extends RuntimeException
{
    public IllegalLoginOrPasswordException(final String message)
    {
        super(message);
    }
}
