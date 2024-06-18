package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;

/**
 * The type HelpCommand. It displays all supported commands by the application
 */
public class HelpCommand implements Command {
    private final FileHandler fileHandler;

    /**
     * Instantiates a new HelpCommand.
     *
     * @param fileHandler the file handler
     */
    public HelpCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
    /**
     * Calls the implentation of the command which is contained in the implementation of the FileHandler class.
     * The method displays information about all supported commands by the application.
     *
     * @param input - the user input
     */
    @Override
    public void invoke(String[] input) {
            fileHandler.help();
    }
}
