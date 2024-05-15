import java.util.*;

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
        ArrayList<String> concatTerminals1 = new ArrayList<>(Arrays.asList(concatTerminals));
        String concatNonterminals = "S";
        concatRules.add(new Rule(concatNonterminals, concatTerminals1));
        concatRules.addAll(grammar1Rules);
        concatRules.addAll(grammar2Rules);
        concat.setRules(concatRules);
        
        for(Rule rule:concatRules){
            String nonterminal =  rule.getNonterminals();
                stringBuilder.append(nonterminal);

            stringBuilder.append(" → ");
            ArrayList<String> terminals = rule.getTerminals();
            for(String terminal:terminals){
                stringBuilder.append(terminal).append(" | ");
            }
            stringBuilder.append("\n");
        }
        fileHandler.getFileContent().append(stringBuilder);
        grammarCommands.getGrammarSet().add(concat);
    }

    private static String[] getTerminals(Rule retrieved1, Rule retrieved2) {
        assert retrieved1 != null;
        String rule1Nonterminals = retrieved1.getNonterminals();
        assert retrieved2 != null;
        String rule2Nonterminals = retrieved2.getNonterminals();
        return new String[]{rule1Nonterminals,rule2Nonterminals};
    }
}
