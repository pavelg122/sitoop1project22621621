package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

public class ConcatCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;
    String[] allNonterminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public ConcatCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils = grammarUtils;
    }

    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                createConcatGrammar(input);
            } else throw new NoFileOpenedException("No file opened");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }

    private void createConcatGrammar(String[] input) {
        Grammar grammar1 = grammarCommands.getGrammar(Long.parseLong(input[0]));
        Grammar grammar2 = grammarCommands.getGrammar(Long.parseLong(input[1]));
        Grammar concat = new Grammar();
        Set<Rule> concatRules = new LinkedHashSet<>();
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

        System.out.println("concat grammar id: " + concat.getId());
        StringBuilder stringBuilder = new StringBuilder();
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
        assert retrieved1 != null;
        assert retrieved2 != null;
        String concatTerminals = getTerminals(retrieved1, retrieved2);
        ArrayList<String> concatTerminals1 = new ArrayList<>();
        concatTerminals1.add(concatTerminals);
        String concatNonterminals = grammarUtils.getNextNonterminal(allRules,allNonterminals);
        concatRules.add(new Rule(concatNonterminals, concatTerminals1));
        concatRules.addAll(grammar1CopyRules);
        concatRules.addAll(grammar2CopyRules);
        concat.setRules(concatRules);

        for (Rule rule : concatRules) {
            stringBuilder.append(rule.toString());
        }
        fileHandler.getFileContent().append(stringBuilder);
        grammarCommands.getGrammarSet().add(concat);
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

    private static String getTerminals(Rule retrieved1, Rule retrieved2) {
        assert retrieved1 != null;
        String rule1Nonterminals = retrieved1.getNonterminals();
        assert retrieved2 != null;
        String rule2Nonterminals = retrieved2.getNonterminals();
        return rule1Nonterminals+rule2Nonterminals;
    }

}
