package controller.exceptions;

public class WrongCommandArgumentsException extends RuntimeException
{
    public WrongCommandArgumentsException(final String message)
    {
        super(message);
    }
}
