package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class ChomskyCommand implements Command {
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    private GrammarUtils grammarUtils;

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
        }else {System.out.println("Please open a file first.");}
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
        char[] terminalCharArr = terminal.toCharArray();
        if(terminalCharArr.length == 1 && charsCount(terminal,/*allTerminals*/grammarUtils.getAllTerminals()) == terminalCharArr.length){cnfTerminal = true;}
        else if(terminalCharArr.length == 2 && charsCount(terminal,/*allNonterminals*/grammarUtils.getAllNonterminals()) == terminalCharArr.length){
            cnfTerminal = true;
        }
        return cnfTerminal;
    }
    private int charsCount(String terminal,String[] characters){
        int count=0;
        char[] terminalCharArr = terminal.toCharArray();
        ArrayList<String> charsArrList = new ArrayList<>(Arrays.asList(characters));
        for(char ch:terminalCharArr){
            if(charsArrList.contains(String.valueOf(ch)))
            {count++;}
        }
        return count;
    }
}
