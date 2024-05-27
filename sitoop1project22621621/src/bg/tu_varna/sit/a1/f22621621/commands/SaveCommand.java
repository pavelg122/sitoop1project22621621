package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;

public class SaveCommand implements Command {
    private FileHandler fileHandler;

    public SaveCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            fileHandler.saveInFile();
        }else System.out.println("Please open a file first.");
    }
}
