package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

public class ListCommand implements Command {
    GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public ListCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        if(fileHandler.isFileOpen()) {
            grammarCommands.list();
        }else System.out.println("Please open a file first.");
    }
}
