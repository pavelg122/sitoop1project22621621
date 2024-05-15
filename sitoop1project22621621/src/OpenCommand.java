import java.io.IOException;
import java.util.Arrays;

public class OpenCommand implements Command{
    private FileHandler fileHandler;
    private GrammarCommands grammarCommands;

    public OpenCommand(FileHandler fileHandler,GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
        this.grammarCommands=grammarCommands;
    }

    @Override
    public void invoke(String[] input) throws IOException {
       // System.out.println(input[0]);
    fileHandler.open(input[0],grammarCommands.getGrammarSet());
    }
}
