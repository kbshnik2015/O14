package controller.exceptions;

public class UnknownCommandException extends RuntimeException
{
    public UnknownCommandException(String message)
    {
        super(message);
    }
}
