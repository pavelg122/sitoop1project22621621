package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;

/**
 * The type SaveAsCommand. Saves the file content to another file defined by the user.
 */
public class SaveAsCommand implements Command {
    private final FileHandler fileHandler;

    /**
     * Instantiates a new SaveAsCommand.
     *
     * @param fileHandler the file handler
     */
    public SaveAsCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Calls the implementation of the command which is contained in the implementation of the FileHandler class.
     * The method opens the new file and writes all content saved on the application in the new file.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     */
    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=1){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the saveas command.");
                }
                fileHandler.saveAs(input[0]);
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }
}
