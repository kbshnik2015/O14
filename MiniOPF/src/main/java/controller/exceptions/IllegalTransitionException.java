package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalTransitionException extends Exception
{
    public IllegalTransitionException(String message) {
        super(message);
    }
}
