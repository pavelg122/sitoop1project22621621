package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type NoFileOpenedException. It occurs when the user tries to invoke a command other than the
 * OpenCommand and a file is not open.
 */
public class NoFileOpenedException extends RuntimeException{
    /**
     * Instantiates a new NoFileOpenedException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public NoFileOpenedException(String message) {
        super(message);
    }
}
