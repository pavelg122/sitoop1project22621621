public class PrintCommand implements Command{
    private GrammarCommands grammarCommands;

    public PrintCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {
    grammarCommands.print(Long.parseLong(input[0]));
    }
}
