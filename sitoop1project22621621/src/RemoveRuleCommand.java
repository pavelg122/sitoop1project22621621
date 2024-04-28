import java.util.ArrayList;
import java.util.Set;

public class RemoveRuleCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;

    public RemoveRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        int number = Integer.parseInt(input[2]);
        StringBuilder ruleString = new StringBuilder();
        Set<Rule> rules = grammar.getRules();
        int counter = 1;
        String nonterminals = null;
        ArrayList<String> terminals = null;
        for (Rule rule : rules) {
            if (counter == number) {
                nonterminals = rule.getNonterminals();
                terminals = rule.getTerminals();
            }
            counter++;
        }
        assert nonterminals != null;
            ruleString.append(nonterminals);

        ruleString.append(" → ");
        assert terminals != null;
        for (String terminal : terminals) {
            ruleString.append(terminal).append(" | ");
        }
        int index = fileHandler.getFileContent().indexOf(String.valueOf(ruleString));
        if (index != -1) {
            fileHandler.getFileContent().delete(index, index + ruleString.length());
        }
        grammarCommands.removeRule(Long.parseLong(input[0]), Integer.parseInt(input[1]));
    }
}
