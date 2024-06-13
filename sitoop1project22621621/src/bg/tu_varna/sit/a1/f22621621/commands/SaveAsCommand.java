package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;

public class SaveAsCommand implements Command {
    private final FileHandler fileHandler;

    public SaveAsCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                fileHandler.saveAs(input[0]);
            } else throw new NoFileOpenedException("No file opened");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }
}
