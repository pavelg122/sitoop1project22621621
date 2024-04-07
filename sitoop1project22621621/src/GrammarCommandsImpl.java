import java.util.Set;

public class GrammarCommandsImpl implements GrammarCommands{
    private Set<Grammar> grammarSet;

    public GrammarCommandsImpl(Set<Grammar> grammarSet) {
        this.grammarSet = grammarSet;
    }

    @Override
    public void list() {
        System.out.println("List of IDs: \n");
for (Grammar grammar:grammarSet){
    System.out.println(grammar.getId());
}
    }

    @Override
    public void print(long id) {
    Grammar grammar = getGrammar(id);
    Set<Rule> rules = grammar.getRules();
    StringBuilder stringBuilder=  new StringBuilder();
    for(Rule rule:rules){
        stringBuilder.append(rule.getNonterminals()).append(" → ");
        String[] terminals = rule.getTerminals();
        for(String terminal:terminals){
            stringBuilder.append(terminal).append(" | ");
        }
    }
    System.out.println(stringBuilder.toString());
    }

    @Override
    public void save(long id, String fileName) {

    }

    @Override
    public void addRule(long id, String rule) {
    }

    @Override
    public void removeRule(long id, int number) throws Exception {

    }

    private Grammar getGrammar(long id){
        Grammar foundGrammar = null;
        for (Grammar grammar:grammarSet){
            if(grammar.getId() == id){foundGrammar = grammar;}
        }
        return foundGrammar;
    }
}
