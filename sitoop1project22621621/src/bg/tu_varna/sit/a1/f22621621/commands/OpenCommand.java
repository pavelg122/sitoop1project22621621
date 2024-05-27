package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

import java.io.IOException;


public class OpenCommand implements Command {
    private FileHandler fileHandler;
    private GrammarCommands grammarCommands;

    public OpenCommand(FileHandler fileHandler,GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
        this.grammarCommands=grammarCommands;
    }

    @Override
    public void invoke(String[] input) throws IOException {
    fileHandler.open(input[0],grammarCommands.getGrammarSet());
    }
}
