import java.util.ArrayList;
import java.util.Iterator;
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
        int number = Integer.parseInt(input[1]);
        StringBuilder ruleString = new StringBuilder();
        Set<Rule> rules = grammar.getRules();
        int counter = 0;
        String nonterminals = null;
        ArrayList<String> terminals = null;
        /*for (Rule rule : rules) {
            if (counter == number) {
                nonterminals = rule.getNonterminals();
                terminals = rule.getTerminals();
            }
            counter++;
        }*/
        for (Rule rule : rules) {
            counter++;
            if (counter == number) {
                nonterminals = rule.getNonterminals();
                terminals = rule.getTerminals();
                break;
            }
        }
        assert nonterminals != null;
            ruleString.append(nonterminals);
        ruleString.append(" → ");
        assert terminals != null;
        for (String terminal : terminals) {
            ruleString.append(terminal).append(" | ");
        }
        ruleString.deleteCharAt(ruleString.lastIndexOf("|")+1);
        //ruleString.deleteCharAt(2);
        //ruleString.deleteCharAt(4);
        ruleString.deleteCharAt(ruleString.indexOf("→")-1);
        ruleString.deleteCharAt(ruleString.indexOf("→")+1);
        //System.out.println(ruleString);
        int index = fileHandler.getFileContent().indexOf(String.valueOf(ruleString));
        if (index != -1) {
            fileHandler.getFileContent().delete(index, index + ruleString.length());
            System.out.println("Successfully removed rule " );
        }else throw new Exception("Failed to remove rule" );
        //char last = ruleString.charAt(ruleString.length() - 5);
        //System.out.println(last);
        //System.out.println(ruleString);
        grammarCommands.removeRule(Long.parseLong(input[0]), Integer.parseInt(input[1]));
        //System.out.println(fileHandler.getFileContent().toString());
    }
}
