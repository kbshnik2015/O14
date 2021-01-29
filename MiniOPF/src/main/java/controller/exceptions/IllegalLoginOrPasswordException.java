package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalLoginOrPasswordException extends Exception
{
    public IllegalLoginOrPasswordException(final String message)
    {
        super(message);
    }
}
