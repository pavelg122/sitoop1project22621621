package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

public class IterCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;
    public IterCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils = grammarUtils;
    }

    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                createIterGrammar(input);
            } else throw new NoFileOpenedException("No file opened");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }

    private void createIterGrammar(String[] input) {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
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
            String nonterminal = rule.getNonterminals();
            stringBuilder.append(nonterminal);
            stringBuilder.append(" → ");
            ArrayList<String> terminals = rule.getTerminals();
            for (String terminal : terminals) {
                stringBuilder.append(terminal).append(" | ");
            }
            stringBuilder.append("\n");
        }
        fileHandler.getFileContent().append(stringBuilder);
        grammarCommands.getGrammarSet().add(iter);
    }

}
