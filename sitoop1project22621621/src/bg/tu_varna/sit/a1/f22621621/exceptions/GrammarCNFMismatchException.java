package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type GrammarCNFMismatchException. It occurs when a method requires a Grammar to be in Chomsky Normal Form
 * but the Grammar is not in normal form or the opposite - when a method requires a Grammar to not be in CNF but
 * the Grammar is in normal form.
 */
public class GrammarCNFMismatchException extends Exception{
    /**
     * Instantiates a new GrammarCNFMismatchException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public GrammarCNFMismatchException(String message) {
        super(message);
    }
}
