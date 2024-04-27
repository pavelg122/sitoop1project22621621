public class ChomskyCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public ChomskyCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
      Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));

    }
}
