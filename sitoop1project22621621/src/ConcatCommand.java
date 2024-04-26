import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConcatCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public ConcatCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        Grammar grammar1 = grammarCommands.getGrammar(Long.parseLong(input[0]));
        Grammar grammar2 = grammarCommands.getGrammar(Long.parseLong(input[1]));
        Grammar concat = new Grammar();
        Set<Rule> concatRules = new HashSet<>();
       Set<Rule> grammar1Rules = grammar1.getRules();
       Set<Rule> grammar2Rules = grammar2.getRules();
        System.out.println("concat grammar id: " + concat.getId());
        StringBuilder stringBuilder=  new StringBuilder();
        Iterator<Rule> iterator1 = grammar1Rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();

        }
        Iterator<Rule> iterator2 = grammar2Rules.iterator();
        Rule retrieved2 = null;
        if (iterator2.hasNext()) {
            retrieved2 = iterator2.next();

        }

        assert retrieved1 != null;
        assert retrieved2 != null;
        String[] concatTerminals = getTerminals(retrieved1, retrieved2);
        String[] concatNonterminals = {"S"};
        concatRules.add(new Rule(concatNonterminals, concatTerminals));
        concatRules.addAll(grammar1Rules);
        concatRules.addAll(grammar2Rules);
        concat.setRules(concatRules);
        
        for(Rule rule:concatRules){
            for(String nonterminal: rule.getNonterminals()){
                stringBuilder.append(nonterminal);
            }
            stringBuilder.append(" → ");
            String[] terminals = rule.getTerminals();
            for(String terminal:terminals){
                stringBuilder.append(terminal).append(" | ");
            }
            stringBuilder.append("\n");
        }
        fileHandler.getFileContent().append(stringBuilder);
    }

    private static String[] getTerminals(Rule retrieved1, Rule retrieved2) {
        assert retrieved1 != null;
        String[] rule1Terminals = retrieved1.getTerminals();
        assert retrieved2 != null;
        String[] rule2Terminals = retrieved2.getTerminals();

        StringBuilder concatString = new StringBuilder();
        for (String rule1Terminal : rule1Terminals) {
            concatString.append(rule1Terminal);
        }
        for (String rule2Terminal : rule2Terminals) {
            concatString.append(rule2Terminal);
        }
        return new String[]{concatString.toString()};
    }
}
