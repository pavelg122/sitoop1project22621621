package bg.tu_varna.sit.a1.f22621621.models;

import bg.tu_varna.sit.a1.f22621621.exceptions.GrammarIDNotFoundException;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

import java.util.Set;

/**
 * The type GrammarCommandsImpl. Contains implementation of the methods of the GrammarCommands interface.
 */
public class GrammarCommandsImpl implements GrammarCommands {
    private Set<Grammar> grammarSet;

    /**
     * Instantiates a new GrammarCommandsImpl.
     *
     * @param grammarSet the grammar set
     */
    public GrammarCommandsImpl(Set<Grammar> grammarSet) {
        this.grammarSet = grammarSet;
    }
    /**
     * Prints the IDs of all loaded Grammars in the application.
     */
    @Override
    public void list() {
        System.out.println("List of IDs:");
        if (grammarSet.isEmpty()) {
            System.out.println("No grammars found");
        }
        else {
            for (Grammar grammar:grammarSet){
            System.out.println(grammar.getId());}
        }
    }
    /**
    *  Displays the contents of a Grammar with ID provided by the user.
    */
    @Override
    public void print(long id) {
    Grammar grammar = getGrammar(id);
        if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + id + " not found. Please type " +
                "list to see all grammars.");
        }
    grammar.print();
    }
    /**
     *  Saves a Grammar in a file specified by the user.
     */
    @Override
    public void save(long id, String fileName) {
     Grammar grammar = getGrammar(id);
        grammar.save(fileName);
    }
    /**
     *  Adds a Rule to a Grammar specified by the user.
     */
    @Override
    public void addRule(long id, String rule) {
        Grammar grammar = getGrammar(id);
        grammar.addRule(rule);
    }
    /**
     *  Removes a Rule of a Grammar specified by the user.
     * @param id the ID of the Grammar
     * @param number number of the Rule
     */
    @Override
    public void removeRule(long id, int number) {
            Grammar grammar = getGrammar(id);
            grammar.removeRule(number);
    }

    /**
     *  Returns a Grammar with a specified ID.
     * @param id ID of the Grammar
     * @return the Grammar if a match is found
     */
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
    /**
     *  Returns the Grammar Set which contains all Grammars in the application
     * @return the Grammar Set
     */
    public Set<Grammar> getGrammarSet() {
        return grammarSet;
    }
}
