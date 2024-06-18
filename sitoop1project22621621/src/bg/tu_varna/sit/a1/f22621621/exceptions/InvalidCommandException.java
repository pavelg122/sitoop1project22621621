package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type InvalidCommandException. It occurs when the first element of the input String set isn't an already
 * defined keyword for a command.
 */
public class InvalidCommandException extends RuntimeException {
    /**
     * Instantiates a new InvalidCommandException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
