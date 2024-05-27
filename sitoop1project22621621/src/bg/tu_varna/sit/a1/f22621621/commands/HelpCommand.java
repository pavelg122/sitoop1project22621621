package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;

public class HelpCommand implements Command {
    private FileHandler fileHandler;

    public HelpCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
            fileHandler.help();
    }
}
