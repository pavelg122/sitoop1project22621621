public class ChomskifyCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public ChomskifyCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {

    }
}
