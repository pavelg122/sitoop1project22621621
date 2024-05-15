import java.util.*;

public class IterCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    //String[] allTerminals = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","s","t","u","v","w","x","y","z",
            //"0","1","2","3","4","5","6","7","8","9"};
    String[] allNonterminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public IterCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
     Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
     Grammar iter = new Grammar();
     /*if(grammar == null){
         throw new Exception();
     }*/
        Set<Rule> rules = grammar.getRules();
        Iterator<Rule> iterator1 = rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();

        }
        boolean empty=true;
        String rule1Nonterminals;
        assert retrieved1 != null;
        rule1Nonterminals = retrieved1.getNonterminals();
        String newNonterminal = getNextNonterminal(rules,allNonterminals);
        ArrayList<String> iterFirstRuleTerminals = new ArrayList<>();
        iterFirstRuleTerminals.add(newNonterminal+rule1Nonterminals);
        iterFirstRuleTerminals.add("ε");
        Set<Rule> iterRules = new HashSet<>();
        iterRules.add(new Rule(newNonterminal,iterFirstRuleTerminals));
        iterRules.addAll(grammar.getRules());
        iter.setRules(iterRules);

        System.out.println("iter grammar id: " + iter.getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule rule : iter.getRules()) {
            String nonterminal  = rule.getNonterminals();
            stringBuilder.append(nonterminal);
            stringBuilder.append(" → ");
            ArrayList<String> terminals = rule.getTerminals();
            for (String terminal : terminals) {
                stringBuilder.append(terminal).append(" | ");
            }
            stringBuilder.append("\n");
        }
        fileHandler.getFileContent().append(stringBuilder);
        grammarCommands.getGrammarSet().add(iter);
    }
    private String getNextNonterminal(Set<Rule> rules,String[] allNonterminals){
        String nextNonterminal = null;
        Map<String, Boolean> freeTerminals = new HashMap<>();
        for(String letter:allNonterminals){
            freeTerminals.put(letter,false);
        }
        for(Rule rule:rules){
            freeTerminals.put(rule.getNonterminals(),true);
        }
        for(Map.Entry<String,Boolean> entry:freeTerminals.entrySet()){
            if(entry.getValue().equals(false)){
                nextNonterminal = entry.getKey();
                break;
            }
        }
        return nextNonterminal;
    }
}
