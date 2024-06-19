package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.GrammarIDNotFoundException;
import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

/**
 * The type IterCommand. Finds the result of the "iteration" operation over the Grammar(Kleene star) and creates a new
 * Grammar. Prints the ID of the new Grammar.
 */
public class IterCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    /**
     * Instantiates a new IterCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     * @param grammarUtils    the grammar utils
     */
    public IterCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils = grammarUtils;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Gets the Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * The method creates a new Rule with the nonterminal to create the possibility of one or more repetitions of the first Rule and adds the new Rule
     * before the first Rule. After that adds the rest of the rules of the Grammar.
     * Appends the String representation of the new Grammar to the temp file content and adds the new Grammar to the Grammar Set.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     * @throws GrammarIDNotFoundException - when a Grammar isn't found
     */
    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=1){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the iter command.");
                }
                createIterGrammar(input);
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }

    private void createIterGrammar(String[] input) {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                "list to see all grammars.");
        }
        Grammar iter = new Grammar();
        Set<Rule> rules = grammar.getRules();
        Iterator<Rule> iterator1 = rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();

        }
        String rule1Nonterminals;
        assert retrieved1 != null;
        rule1Nonterminals = retrieved1.getNonterminals();
        String newNonterminal = grammarUtils.getNextNonterminal(rules, grammarUtils.getAllNonterminals());
        ArrayList<String> iterFirstRuleTerminals = new ArrayList<>();
        iterFirstRuleTerminals.add(newNonterminal + rule1Nonterminals);
        iterFirstRuleTerminals.add("ε");
        Set<Rule> iterRules = new LinkedHashSet<>();
        iterRules.add(new Rule(newNonterminal, iterFirstRuleTerminals));
        iterRules.addAll(rules);
        iter.setRules(iterRules);

        System.out.println("iter grammar id: " + iter.getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule rule : iter.getRules()) {
            stringBuilder.append(rule.toString());
        }
        fileHandler.getFileContent().append(stringBuilder);
        grammarCommands.getGrammarSet().add(iter);
    }

}
