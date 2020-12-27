package controller.exceptions;

public class IllegalTransitionException extends Exception
{
    public IllegalTransitionException() {
        super();
    }
    public IllegalTransitionException(String message) {
        super(message);
    }
}
