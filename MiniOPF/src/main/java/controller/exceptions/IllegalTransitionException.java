package controller.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalTransitionException extends RuntimeException
{
    public IllegalTransitionException(String message) {
        super(message);
    }
}
