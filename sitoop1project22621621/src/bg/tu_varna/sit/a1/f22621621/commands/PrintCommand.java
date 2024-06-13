package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

public class PrintCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    public PrintCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                grammarCommands.print(Long.parseLong(input[0]));
            } else throw new NoFileOpenedException("No file opened");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }
}
