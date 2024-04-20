public class CykCommand implements Command{
    private GrammarCommands grammarCommands;

    public CykCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {

    }
}
