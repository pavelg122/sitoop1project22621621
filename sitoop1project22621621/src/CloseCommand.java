public class CloseCommand implements Command{
    private FileHandler fileHandler;

    private GrammarCommands grammarCommands;
    public CloseCommand(FileHandler fileHandler,GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
        this.grammarCommands=grammarCommands;
    }

    @Override
    public void invoke(String[] input) {
fileHandler.close();
    }
}
