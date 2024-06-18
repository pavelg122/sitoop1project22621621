package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

/**
 * The type CloseCommand. Closes the currently open file and clears all content.
 */
public class CloseCommand implements Command {
    private final FileHandler fileHandler;

    /**
     * Instantiates a new CloseCommand.
     *
     * @param fileHandler the file handler
     */
    public CloseCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Calls the implementation of the command which is contained in the implementation of the FileHandler class.
     * The method clears the file content and the Grammar Set, and clears the parameter keeping track of
     * the currently open file.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     */
    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=0){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the close command.");
                }
                fileHandler.close();
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }
}
