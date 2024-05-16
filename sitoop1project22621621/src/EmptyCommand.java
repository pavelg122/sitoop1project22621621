import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class EmptyCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public EmptyCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
            Set<Rule> rules = grammar.getRules();
            Iterator<Rule> iterator1 = rules.iterator();
            Rule retrieved1 = null;
            if (iterator1.hasNext()) {
                retrieved1 = iterator1.next();

            }
            boolean empty = true;
            String rule1Nonterminals;
            assert retrieved1 != null;
            ArrayList<String> rule1Terminals = retrieved1.getTerminals();
            ArrayList<String> grammarNonterminals = new ArrayList<>();
            for (Rule rule : grammar.getRules()) {
                grammarNonterminals.add(rule.getNonterminals());
            }
            for (String nonterminal : grammarNonterminals) {
                if (rule1Terminals.contains(nonterminal)) {
                    empty = false;
                    break;
                }
            }
            if (rule1Terminals.contains("ε")) {
                empty = false;
            }
            if (empty) {
                System.out.println("Grammar " + grammar.getId() + " IS empty.");
            } else System.out.println("Grammar " + grammar.getId() + " IS NOT empty.");
        }else System.out.println("Please open a file first.");
    }
}
