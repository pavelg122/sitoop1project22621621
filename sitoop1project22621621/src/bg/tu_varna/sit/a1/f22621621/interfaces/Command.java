package bg.tu_varna.sit.a1.f22621621.interfaces;

/**
 * The interface Command. It's a main part of the CLI and defines basic behaviour of a command class.
 */
public interface Command {
    /**
     * Invokes a command. The command logic can be either in the command implementation class
     * or in another class that contains the implementation logic of the command.
     *
     * @param input the user input
     * @throws Exception the exception if the invocation fails
     */
    void invoke(String[] input) throws Exception;
}
