package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type InvalidRuleNumberException. It occurs when the number of a Rule to be removed in the RemoveRuleCommand
 * is not present in the Grammar from which the Rule is to be removed.
 */
public class InvalidRuleNumberException extends RuntimeException{
    /**
     * Instantiates a new InvalidRuleNumberException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public InvalidRuleNumberException(String message) {
        super(message);
    }
}
