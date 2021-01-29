package controller.exceptions;

public class WrongCommandArgumentsException extends Exception
{
    public WrongCommandArgumentsException(final String message)
    {
        super(message);
    }
}
