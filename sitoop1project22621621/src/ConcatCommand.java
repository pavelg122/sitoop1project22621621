public class ConcatCommand implements Command{
    private GrammarCommands grammarCommands;

    public ConcatCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {

    }
}
