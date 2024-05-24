import java.util.*;

public class EmptyCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    String[] allNonterminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
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
            ArrayList<String> allNonterminals1 = new ArrayList<>(List.of(allNonterminals));
            assert retrieved1 != null;
            boolean empty = isEmpty(retrieved1, allNonterminals1, grammar);
            if (empty) {
                System.out.println("Grammar " + grammar.getId() + " IS empty.");
            } else System.out.println("Grammar " + grammar.getId() + " IS NOT empty.");
        }else System.out.println("Please open a file first.");
    }

    private static boolean isEmpty(Rule retrieved1, ArrayList<String> allNonterminals1, Grammar grammar) {
        boolean empty = true;
        assert retrieved1 != null;
        Set<String> rule1Terminals = new LinkedHashSet<>();
        for(String term: retrieved1.getTerminals()){
            if(term.length()==1 && allNonterminals1.contains(term)){rule1Terminals.add(term.trim());}
            else{String[] arr = term.split("");
                for (String s : arr) {
                    if (allNonterminals1.contains(s)) {
                        rule1Terminals.add(s);
                    }
                }
            }
        }

        for(Rule rule: grammar.getRules()){
            if(rule1Terminals.contains(rule.getNonterminals().trim())){
                empty=false;
                break;
            }
        }
        if (rule1Terminals.contains("ε")) {
            empty = false;
        }
        return empty;
    }
}
