package controller.exceptions;

public class UnknownCommandException extends Throwable
{
    public UnknownCommandException(String message)
    {
        super(message);
    }
}
