import java.io.FileNotFoundException;
import java.util.Set;

public interface GrammarCommands {
    void list();
    void print(long id);
    void save(long id, String fileName) throws FileNotFoundException;
    void addRule(long id, String rule);
    void removeRule(long id, int number) throws Exception;
    //void union(long id1, long id2);
    //void concat(long id1, long id2);
   // void chomsky(long id);
    //void cyk(long id);
    //void iter(long id);
    //void empty(long id);
    //void chomskify(long id);
    Grammar getGrammar(long id);
    public Set<Grammar> getGrammarSet();
}
