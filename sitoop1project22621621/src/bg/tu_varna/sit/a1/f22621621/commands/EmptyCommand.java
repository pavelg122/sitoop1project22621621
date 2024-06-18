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
 * The type EmptyCommand. Checks if the terminals of the first Rule of the Grammar do not contain any of the nonterminals of the rest of the rules.
 */
public class EmptyCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    /**
     * Instantiates a new EmptyCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     * @param grammarUtils    the grammar utils
     */
    public EmptyCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils = grammarUtils;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Gets the Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * The method iterates over every single terminal of the Rule and gets all nonterminals. After that it checks if
     * the Rule nonterminal of the rest of the Rules are contained in the nonterminals from the first Rule. If even a
     * single nonterminal from the other Rules is contained in the terminals of the first Rule the Grammar is not empty.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     */
    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=1){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the empty command.");
                }
                emptyGrammarCheck(input);
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch (Exception e) {System.out.println(e.getMessage());
        }
    }

    private void emptyGrammarCheck(String[] input) {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                "list to see all grammars.");
        }
        Set<Rule> rules = grammar.getRules();
        Iterator<Rule> iterator1 = rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();
        }
        ArrayList<String> allNonterminals1 = new ArrayList<>(List.of(grammarUtils.getAllNonterminals()));
        assert retrieved1 != null;
        boolean empty = isEmpty(retrieved1, allNonterminals1, grammar);
        if (empty) {
            System.out.println("Grammar " + grammar.getId() + " IS empty.");
        } else System.out.println("Grammar " + grammar.getId() + " IS NOT empty.");
    }

    private static boolean isEmpty(Rule retrieved1, ArrayList<String> allNonterminals1, Grammar grammar) {
        boolean empty = true;
        assert retrieved1 != null;
        Set<String> rule1Terminals = new LinkedHashSet<>();
        for(String term: retrieved1.getTerminals()){
            if(term.length()==1 && allNonterminals1.contains(term)){rule1Terminals.add(term.trim());}
            else{String[] arr = term.split("");
                for (String s : arr) {
                    if (allNonterminals1.contains(s)) {
                        rule1Terminals.add(s);
                    }
                }
            }
        }

        for(Rule rule: grammar.getRules()){
            if(rule1Terminals.contains(rule.getNonterminals().trim())){
                empty=false;
                break;
            }
        }
        if (rule1Terminals.contains("ε")) {
            empty = false;
        }
        return empty;
    }
}
