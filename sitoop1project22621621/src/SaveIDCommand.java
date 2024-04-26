import java.io.File;
import java.io.PrintWriter;

public class SaveIDCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public SaveIDCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler= fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        StringBuilder grammarBuilder = new StringBuilder();
        String[] nonterminals = null;
        String[] terminals = null;
        for(Rule rule: grammar.getRules()){
            nonterminals = rule.getNonterminals();
            terminals = rule.getTerminals();
            assert nonterminals != null;
            for (String nonterminal : nonterminals) {
                grammarBuilder.append(nonterminal);
            }
            grammarBuilder.append(" → ");
            assert terminals != null;
            for (String terminal : terminals) {
                grammarBuilder.append(terminal).append(" | ");
            }
            grammarBuilder.append("\n");
        }
        File file = fileHandler.getCurrentFile();
        PrintWriter writer = new PrintWriter(file);
        writer.write(grammarBuilder.toString());
        writer.close();
    }

}
