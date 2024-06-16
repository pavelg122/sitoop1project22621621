package bg.tu_varna.sit.a1.f22621621.commands;

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

public class SaveCommand implements Command {
    private final FileHandler fileHandler;
    private final GrammarCommands grammarCommands;
    public SaveCommand(FileHandler fileHandler, GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length == 2){
                    saveIDToFile(input);
                }
                else if(input.length == 0){fileHandler.saveInFile();}
                else throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the save command.");
            } else throw new NoFileOpenedException("No file opened");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }
    private void saveIDToFile(String[] input) throws FileNotFoundException {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
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
