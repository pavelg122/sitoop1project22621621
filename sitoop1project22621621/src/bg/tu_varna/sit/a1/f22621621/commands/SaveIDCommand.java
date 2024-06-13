package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaveIDCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    public SaveIDCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler= fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        try {
            if (fileHandler.isFileOpen()) {
                saveIDToFile(input);
            } else throw new NoFileOpenedException("No file opened");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }

    private void saveIDToFile(String[] input) throws FileNotFoundException {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        String newFilePath = input[1];
        File newFile = new File(newFilePath);
        StringBuilder grammarBuilder = new StringBuilder();
        String nonterminals;
        ArrayList<String> terminals;
        for (Rule rule : grammar.getRules()) {
            nonterminals = rule.getNonterminals();
            terminals = rule.getTerminals();
            assert nonterminals != null;
            grammarBuilder.append(nonterminals);
            grammarBuilder.append(" → ");
            assert terminals != null;
            for (String terminal : terminals) {
                grammarBuilder.append(terminal).append(" | ");
            }
            grammarBuilder.append("\n");
        }

        PrintWriter writer = new PrintWriter(newFile);
        writer.write(grammarBuilder.toString());
        writer.close();
        System.out.println("Succesfully saved " + grammar.getId() + " to: " + newFile.getName());
    }

}
