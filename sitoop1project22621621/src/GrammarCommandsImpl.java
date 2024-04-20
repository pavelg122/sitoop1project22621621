import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    grammar.print();
    }

    @Override
    public void save(long id, String fileName) throws FileNotFoundException {
     Grammar grammar = getGrammar(id);
        grammar.save(fileName);
    }

    @Override
    public void addRule(long id, String rule) {
        Grammar grammar = getGrammar(id);
        grammar.addRule(rule);
    }

    @Override
    public void removeRule(long id, int number) throws Exception {
Grammar grammar =getGrammar(id);
grammar.removeRule(number);
    }

    public Grammar getGrammar(long id){
        Grammar foundGrammar = null;
        for (Grammar grammar:grammarSet){
            if(grammar.getId() == id){foundGrammar = grammar;}
        }
        return foundGrammar;
    }
}
