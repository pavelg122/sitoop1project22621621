package bg.tu_varna.sit.a1.f22621621.interfaces;

import bg.tu_varna.sit.a1.f22621621.models.Grammar;

import java.io.FileNotFoundException;
import java.util.Set;

public interface GrammarCommands {
    void list();
    void print(long id);
    void save(long id, String fileName) throws FileNotFoundException;
    void addRule(long id, String rule);
    void removeRule(long id, int number) throws Exception;
    Grammar getGrammar(long id);
    Set<Grammar> getGrammarSet();
}
