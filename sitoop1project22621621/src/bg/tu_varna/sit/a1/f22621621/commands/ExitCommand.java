package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;

/**
 * The type ExitCommand. Implementation of the exit command that exits the program.
 */
public class ExitCommand implements Command {
    private final FileHandler fileHandler;

    /**
     * Instantiates a new ExitCommand.
     *
     * @param fileHandler the file handler
     */
    public ExitCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
    /**
     * Calls the implentation of the command which is contained in the implementation of the FileHandler class.
     * The method displays a message and exits the application.
     *
     * @param input - the user input
     */
    @Override
    public void invoke(String[] input) {
        fileHandler.exit();
    }
}
