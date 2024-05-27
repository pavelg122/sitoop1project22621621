package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

public class CloseCommand implements Command {
    private FileHandler fileHandler;

    public CloseCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            fileHandler.close();
        }else {System.out.println("Please open a file first.");}
    }
}
