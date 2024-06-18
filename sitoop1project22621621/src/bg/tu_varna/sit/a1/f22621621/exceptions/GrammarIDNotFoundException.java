package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type GrammarIDNotFoundException. It occurs when the GrammarCommands method getGrammar tries
 * getting a Grammar from the Set that contains all Grammars in the application but doesn't find a match.
 */
public class GrammarIDNotFoundException extends RuntimeException {
    /**
     * Instantiates a new GrammarIDNotFoundException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public GrammarIDNotFoundException(String message) {
        super(message);
    }
}
