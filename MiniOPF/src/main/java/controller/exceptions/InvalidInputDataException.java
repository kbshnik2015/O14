package controller.exceptions;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidInputDataException extends RuntimeException
{
    public InvalidInputDataException(final String s){
        super(s);
    }
}
