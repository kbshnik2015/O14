package controller.exceptions;

/**
 * Yellow pop-ups, which don't block the work with system
 */
public class Warning extends Exception
{
    public Warning(final String message)
    {
        super(message);
    }
}
