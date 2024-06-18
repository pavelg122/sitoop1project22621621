package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type ContentSearchFailureException. It occurs when there is a search for a Rule or a Grammar in the file content but no match is found.
 */
public class ContentSearchFailureException extends Exception {
    /**
     * Instantiates a new ContentSearchFailureException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public ContentSearchFailureException(String message) {
        super(message);
    }
}
