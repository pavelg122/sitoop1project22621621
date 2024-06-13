package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class ChomskyCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    public ChomskyCommand(GrammarCommands grammarCommands,FileHandler fileHandler,GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils =grammarUtils;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
            if (isCNF(grammar)) {
                System.out.println("Grammar " + grammar.getId() + " is in Chomsky Normal Form");
            } else {
                System.out.println("Grammar " + grammar.getId() + " isn't in Chomsky Normal Form");
            }
        }else throw new NoFileOpenedException("No file opened");
    }
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
