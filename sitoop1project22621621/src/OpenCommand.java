import java.io.IOException;
import java.util.Arrays;

public class OpenCommand implements Command{
    private FileHandler fileHandler;

    public OpenCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws IOException {
    fileHandler.open(input[0]);
    }
}
