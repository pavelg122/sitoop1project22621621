public class ChomskyCommand implements Command{
    private GrammarCommands grammarCommands;

    public ChomskyCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {

    }
}
