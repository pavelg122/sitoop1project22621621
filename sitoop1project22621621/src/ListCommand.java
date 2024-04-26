public class ListCommand implements Command{
    GrammarCommands grammarCommands;

    public ListCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        grammarCommands.list();
    }
}
