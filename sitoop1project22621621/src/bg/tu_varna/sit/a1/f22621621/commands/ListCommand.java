package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

/**
 * The type ListCommand. Prints the IDs of all loaded Grammars in the application.
 */
public class ListCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;

    /**
     * Instantiates a new ListCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     */
    public ListCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Calls the implementation of the command which is contained in the implementation of the GrammarCommands class.
     * The method prints the IDs of all Grammars loaded in the application and
     * if there are no Grammars a message indicating that is displayed
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     */
    @Override
    public void invoke(String[] input) throws Exception {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=0){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the list command.");
                }
                grammarCommands.list();
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }
}
