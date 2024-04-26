public class IterCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public IterCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {

    }
}
