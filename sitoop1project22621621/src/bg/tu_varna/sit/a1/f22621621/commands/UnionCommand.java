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
 * The type Union command.
 */
public class UnionCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    /**
     * Instantiates a new Union command.
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
        Set<String> commonNonterminals = getCommonNonterminals(grammar1Rules, grammar2Rules);
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


    private static Set<String> getCommonNonterminals(Set<Rule> grammar1Rules, Set<Rule> grammar2Rules) {
        Set<String> grammar1Nonterminals = new LinkedHashSet<>();
        Set<String> grammar2Nonterminals = new LinkedHashSet<>();
        for(Rule rule: grammar1Rules){
            grammar1Nonterminals.add(rule.getNonterminals());
        }

        for(Rule rule: grammar2Rules){
            grammar2Nonterminals.add(rule.getNonterminals());
        }
        Set<String> commonNonterminals = new LinkedHashSet<>(grammar1Nonterminals);
        commonNonterminals.retainAll(grammar2Nonterminals);
        return commonNonterminals;
    }
}
