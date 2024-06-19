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
 * The type UnionCommand. It finds the union of two Grammars and creates a new Grammar. Prints the ID of the
 * new Grammar.
 */
public class UnionCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    /**
     * Instantiates a new UnionCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     * @param grammarUtils    the grammar utils
     */
    public UnionCommand(GrammarCommands grammarCommands,FileHandler fileHandler,GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils = grammarUtils;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Gets the first Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * Then it gets the second Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * The method creates a copy of the Rules of both Grammars and then finds the shared nonterminals in both Grammars. Replaces the common
     * nonterminals in both Grammars. After that it gets the start nonterminals of both Grammars and finds their union.
     * Creates a new Rule with the union of the start nonterminals and adds it first. After that adds the updated Rules of both Grammars.
     * Appends the String representation of the new Grammar to the temp file content and adds the new Grammar to the Grammar Set.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     * @throws GrammarIDNotFoundException - when a Grammar isn't found
     */
    @Override
    public void invoke(String[] input) throws Exception {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=2){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see " +
                            "the correct syntax for the union command.");
                }
                createUnionGrammar(input);
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }

    private void createUnionGrammar(String[] input) {
        Grammar grammar1 = grammarCommands.getGrammar(Long.parseLong(input[0]));
        Grammar grammar2 = grammarCommands.getGrammar(Long.parseLong(input[1]));
        if(grammar1 == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                "list to see all grammars.");
        }
        if(grammar2 == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                "list to see all grammars.");
        }
        Grammar union = new Grammar();
        Set<Rule> unionRules = new LinkedHashSet<>();
        Set<Rule> grammar1Rules = grammar1.getRules();
        Set<Rule> grammar2Rules = grammar2.getRules();

        Set<Rule> grammar1CopyRules = new LinkedHashSet<>();
        Set<Rule> grammar2CopyRules = new LinkedHashSet<>();
        for(Rule rule:grammar1Rules){
            grammar1CopyRules.add(rule.copy());
        }
        for(Rule rule:grammar2Rules){
            grammar2CopyRules.add(rule.copy());
        }
        Set<String> commonNonterminals = grammarUtils.getCommonNonterminals(grammar1Rules, grammar2Rules);
        for(Rule rule:grammar1CopyRules){
            for(String common:commonNonterminals){
                ArrayList<String> updatedTerminals1 = new ArrayList<>();
                if(rule.getNonterminals().equals(common)){rule.setNonterminals(rule.getNonterminals()+"1");}
                for(String terminal: rule.getTerminals()){
                    if(terminal.contains(common)){
                        String updated = terminal.replace(common,common+"1");
                        updatedTerminals1.add(updated);
                    }else updatedTerminals1.add(terminal);
                }
                rule.setTerminals(updatedTerminals1);
            }
        }

        for(Rule rule:grammar2CopyRules){
            for(String common:commonNonterminals){
                ArrayList<String> updatedTerminals2 = new ArrayList<>();
                if(rule.getNonterminals().equals(common)){rule.setNonterminals(rule.getNonterminals()+"2");}
                for(String terminal: rule.getTerminals()){
                    if(terminal.contains(common)){
                        String updated = terminal.replace(common,common+"2");
                        updatedTerminals2.add(updated);
                    }else updatedTerminals2.add(terminal);
                }
                rule.setTerminals(updatedTerminals2);
            }
        }

        Set<Rule> allRules = new LinkedHashSet<>();
        allRules.addAll(grammar1CopyRules);
        allRules.addAll(grammar2CopyRules);

        Iterator<Rule> iterator1 = grammar1CopyRules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();

        }
        Iterator<Rule> iterator2 = grammar2CopyRules.iterator();
        Rule retrieved2 = null;
        if (iterator2.hasNext()) {
            retrieved2 = iterator2.next();

        }
        String rule1Nonterminals;
        assert retrieved1 != null;
        rule1Nonterminals = retrieved1.getNonterminals();
        assert retrieved2 != null;
        String rule2Nonterminals = retrieved2.getNonterminals();

        ArrayList<String> unionTerminals = new ArrayList<>();
        unionTerminals.add(rule1Nonterminals);
        unionTerminals.add(rule2Nonterminals);

        String unionNonterminals = grammarUtils.getNextNonterminal(allRules,grammarUtils.getAllNonterminals());
        unionRules.add(new Rule(unionNonterminals, unionTerminals));
        unionRules.addAll(grammar1CopyRules);
        unionRules.addAll(grammar2CopyRules);
        union.setRules(unionRules);

        System.out.println("union grammar id: " + union.getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule rule : unionRules) {
            stringBuilder.append(rule.toString());
        }
        fileHandler.getFileContent().append(stringBuilder);
        grammarCommands.getGrammarSet().add(union);
    }
}
