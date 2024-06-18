package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.FileAlreadyOpenException;
import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

import java.io.IOException;


/**
 * The type OpenCommand. Opens the file specified by the user and if it doesn't exist
 * the file is created. Then the file content is read using a scanner.
 */
public class OpenCommand implements Command {
    private final FileHandler fileHandler;
    private final GrammarCommands grammarCommands;

    /**
     * Instantiates a new OpenCommand.
     *
     * @param fileHandler     the file handler
     * @param grammarCommands the grammar commands
     */
    public OpenCommand(FileHandler fileHandler,GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
        this.grammarCommands=grammarCommands;
    }
    /**
     * Checks if a file is open and if it is a FileAlreadyOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Calls the implementation of the command which is contained in the implementation of the FileHandler class.
     * The method tries opening the file specified by the user and if the file doesn't exist a new file is created. Then the
     * file content is transformed into a Grammar object.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws FileAlreadyOpenException - when a file is already open
     */
    @Override
    public void invoke(String[] input) throws IOException {
        try {
            if (fileHandler.isFileOpen()) {
                throw new FileAlreadyOpenException("File already open.");
            }
            if(input.length !=1){
                throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the open command.");
            }
            fileHandler.open(input[0], grammarCommands.getGrammarSet());
        }catch (Exception e) {System.out.println(e.getMessage());}
    }
}
