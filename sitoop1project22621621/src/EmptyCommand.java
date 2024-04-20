public class EmptyCommand implements Command{
    private GrammarCommands grammarCommands;

    public EmptyCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {

    }
}
