package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;

import java.util.ArrayList;
import java.util.Set;

public class RemoveRuleCommand implements Command {
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;

    public RemoveRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        if(fileHandler.isFileOpen()){
            removeRule(input);
        }else System.out.println("Please open a file first.");

    }

    private void removeRule(String[] input) throws Exception {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        int number = Integer.parseInt(input[1]);
        StringBuilder ruleString = new StringBuilder();
        Set<Rule> rules = grammar.getRules();
        int counter = 0;
        String nonterminals = null;
        ArrayList<String> terminals = null;
        for (Rule rule : rules) {
            counter++;
            if (counter == number) {
                nonterminals = rule.getNonterminals();
                terminals = rule.getTerminals();
                break;
            }
        }
        assert nonterminals != null;
        ruleString.append(nonterminals);
        ruleString.append(" → ");
        assert terminals != null;
        for (String terminal : terminals) {
            ruleString.append(terminal).append(" | ");
        }
        ruleString.deleteCharAt(ruleString.lastIndexOf("|")+1);
        ruleString.deleteCharAt(ruleString.lastIndexOf("|"));
        ruleString.deleteCharAt(ruleString.length()-1);
        int index = fileHandler.getFileContent().indexOf(String.valueOf(ruleString));
        System.out.println(ruleString);
        if (index != -1) {
            StringBuilder newFileContent = new StringBuilder();
            fileHandler.getFileContent().delete(index, index + ruleString.length());
            String[] lines = fileHandler.getFileContent().toString().split("\n");
            for(String line:lines){
                if(!line.trim().isEmpty()){
                    newFileContent.append(line).append("\n");
                }
            }
            fileHandler.setFileContent(newFileContent);
            grammarCommands.removeRule(Long.parseLong(input[0]), Integer.parseInt(input[1]));
            System.out.println("Successfully removed rule " );
        }else throw new Exception("Failed to remove rule" );
    }
}
