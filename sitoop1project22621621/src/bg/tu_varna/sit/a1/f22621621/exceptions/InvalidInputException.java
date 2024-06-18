package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type InvalidInputException. It occurs when the number of arguments for a command is incorrect.
 */
public class InvalidInputException extends RuntimeException {
    /**
     * Instantiates a new InvalidInputException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
