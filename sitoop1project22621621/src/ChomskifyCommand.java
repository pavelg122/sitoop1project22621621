public class ChomskifyCommand implements Command{
    private GrammarCommands grammarCommands;

    public ChomskifyCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {

    }
}
