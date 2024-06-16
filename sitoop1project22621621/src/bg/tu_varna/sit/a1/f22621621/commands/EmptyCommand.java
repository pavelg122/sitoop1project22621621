package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

public class EmptyCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;
    public EmptyCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils = grammarUtils;
    }

    @Override
    public void invoke(String[] input) {
        try {
            if (fileHandler.isFileOpen()) {
                emptyGrammarCheck(input);
            } else throw new NoFileOpenedException("No file opened");
        }catch (Exception e) {System.out.println(e.getMessage());
        }
    }

    private void emptyGrammarCheck(String[] input) {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
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
