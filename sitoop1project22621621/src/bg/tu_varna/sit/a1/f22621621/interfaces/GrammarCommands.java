package bg.tu_varna.sit.a1.f22621621.interfaces;

import bg.tu_varna.sit.a1.f22621621.models.Grammar;

import java.io.FileNotFoundException;
import java.util.Set;

/**
 * The interface Grammar commands. It provides methods for the simpler commands that work on a Grammar -
 * listing all Grammars, printing a Grammar, adding and removing a Rule from a Grammar, saving a Grammar to a new file.
 */
public interface GrammarCommands {
    /**
     * Lists all available Grammars.
     */
    void list();

    /**
     * Prints a Grammar.
     *
     * @param id the id
     */
    void print(long id);

    /**
     * Saves a Grammar to a file.
     *
     * @param id       the id
     * @param fileName the file name
     * @throws FileNotFoundException the file not found exception
     */
    void save(long id, String fileName) throws FileNotFoundException;

    /**
     * Adds a Rule to a Grammar.
     *
     * @param id   ID of the Grammar
     * @param rule String of the Rule to be added
     */
    void addRule(long id, String rule);

    /**
     * Removes a Rule from a Grammar.
     *
     * @param id     ID of the Grammar
     * @param number the number of the Rule to be removed
     * @throws Exception the exception
     */
    void removeRule(long id, int number) throws Exception;

    /**
     * Returns a Grammar based on its ID.
     *
     * @param id ID of the Grammar to be returned
     * @return the Grammar
     */
    Grammar getGrammar(long id);

    /**
     * Gets grammar set that contains all Grammars.
     *
     * @return the grammar set
     */
    Set<Grammar> getGrammarSet();
}
