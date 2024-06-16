package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.GrammarIDNotFoundException;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;

import java.io.FileNotFoundException;
import java.util.Set;

public class GrammarCommandsImpl implements GrammarCommands {
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
    public void removeRule(long id, int number) {
        try {
            Grammar grammar = getGrammar(id);
            grammar.removeRule(number);
        }catch(Exception e){System.out.println(e.getMessage());}
    }


    public Grammar getGrammar(long id){
            Grammar foundGrammar = null;
            for (Grammar grammar : grammarSet) {
                if (grammar.getId() == id) {
                    foundGrammar = grammar;
                }
            }
            if (foundGrammar == null) {
                throw new GrammarIDNotFoundException("Grammar ID: " + id + " not found");
            }
            return foundGrammar;
    }

    public Set<Grammar> getGrammarSet() {
        return grammarSet;
    }
}
