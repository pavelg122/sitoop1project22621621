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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type ChomskyCommand. Checks if a Grammar is in Chomsky Normal Form. A Grammar is in Chomsky Normal Form when its Rules follow these patterns:
 * A → BC,A → a, or S → ε.
 */
public class ChomskyCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    /**
     * Instantiates a new ChomskyCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     * @param grammarUtils    the grammar utils
     */
    public ChomskyCommand(GrammarCommands grammarCommands,FileHandler fileHandler,GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils =grammarUtils;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Gets the Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * The method iterates over all terminals of all Rules and checks if the terminals are in the correct formats. If it finds
     * a single terminal that is not in normal form the checking process concludes and that means the Grammar is not in Chomsky Normal Form.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     * @throws GrammarIDNotFoundException - when a Grammar isn't found
     */
    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            if(input.length !=1){
                throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the chomsky command.");
            }
            Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
            if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                    "list to see all grammars.");
            }
            if (isCNF(grammar)) {
                System.out.println("Grammar " + grammar.getId() + " is in Chomsky Normal Form.");
            } else {
                System.out.println("Grammar " + grammar.getId() + " isn't in Chomsky Normal Form.");
            }
        }else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
    }

    /**
     * Checks if a Grammar is in Chomsky Normal Form.
     *
     * @param grammar the grammar
     * @return boolean - true or false. Returns true if all terminals of all Rules are in normal form. Returns false otherwise.
     */
    protected boolean isCNF(Grammar grammar){
        for(Rule rule: grammar.getRules()){
            for(String terminal: rule.getTerminals()){
                if(!cnfTerminalParts(terminal)){
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Checks if a terminal String is in Chomsky Normal Form.
     *
     * @param terminal the terminal String
     * @return boolean - true or false. Returns true if the terminal is in normal form. Returns false otherwise.
     */
    private boolean cnfTerminalParts(String terminal){
        boolean cnfTerminal = false;
        char[] terminalCharArr = terminal.trim().toCharArray();
        if(terminalCharArr.length == 1 && grammarUtils.charsCount(terminal,grammarUtils.getAllTerminals()) == terminalCharArr.length){cnfTerminal = true;}
        else if(terminalCharArr.length == 2 && grammarUtils.charsCount(terminal,grammarUtils.getAllNonterminals()) == terminalCharArr.length){
            cnfTerminal = true;
        }
        return cnfTerminal;
    }

}
