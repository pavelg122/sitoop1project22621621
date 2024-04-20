public class IterCommand implements Command{
    private GrammarCommands grammarCommands;

    public IterCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {

    }
}
