package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

public class SaveAsCommand implements Command {
    private FileHandler fileHandler;

    public SaveAsCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            fileHandler.saveAs(input[0]);
        }else System.out.println("Please open a file first.");
    }
}
