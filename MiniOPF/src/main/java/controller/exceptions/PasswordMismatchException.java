package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordMismatchException extends RuntimeException
{
    public PasswordMismatchException(final String s)
    {
        super(s);
    }
}
