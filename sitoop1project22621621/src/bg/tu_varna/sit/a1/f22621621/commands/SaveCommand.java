package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.GrammarIDNotFoundException;
import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * The type SaveCommand.
 */
public class SaveCommand implements Command {
    private final FileHandler fileHandler;
    private final GrammarCommands grammarCommands;

    /**
     * Instantiates a new SaveCommand.
     *
     * @param fileHandler     the file handler
     * @param grammarCommands the grammar commands
     */
    public SaveCommand(FileHandler fileHandler, GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
        this.grammarCommands = grammarCommands;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. If the input array has no elements it calls the implementation of the command which is contained in the implementation of the FileHandler class.
     * The method opens the current file and writes all content saved on the application in it.
     * If the input array has 2 elements another version of the save command is called which saves a Grammar in a file specified
     * by the user. The first element is the ID of the Grammar and the second is the file location.
     * If the Grammar isn't found a GrammarIDNotFoundException is thrown and an error message is displayed.
     * If it's found the content of the Grammar is converted to String and written in the file.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     * @throws GrammarIDNotFoundException - when a Grammar isn't found
     */
    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length == 2){
                    saveIDToFile(input);
                }
                else if(input.length == 0){fileHandler.saveInFile();}
                else throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the save command.");
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }
    private void saveIDToFile(String[] input) throws FileNotFoundException {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                "list to see all grammars.");
        }
        String newFilePath = input[1];
        File newFile = new File(newFilePath);
        StringBuilder grammarBuilder = new StringBuilder();
        for (Rule rule : grammar.getRules()) {
            grammarBuilder.append(rule.toString());
        }

        PrintWriter writer = new PrintWriter(newFile);
        writer.write(grammarBuilder.toString());
        writer.close();
        System.out.println("Succesfully saved " + grammar.getId() + " to: " + newFile.getName());
    }
}
