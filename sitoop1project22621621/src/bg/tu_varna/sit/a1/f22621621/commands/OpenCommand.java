package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.FileAlreadyOpenException;
import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

import java.io.IOException;


public class OpenCommand implements Command {
    private final FileHandler fileHandler;
    private final GrammarCommands grammarCommands;

    public OpenCommand(FileHandler fileHandler,GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
        this.grammarCommands=grammarCommands;
    }

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
