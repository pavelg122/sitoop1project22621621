package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

public class PrintCommand implements Command {
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public PrintCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            grammarCommands.print(Long.parseLong(input[0]));
        }else System.out.println("Please open a file first.");
    }
}
