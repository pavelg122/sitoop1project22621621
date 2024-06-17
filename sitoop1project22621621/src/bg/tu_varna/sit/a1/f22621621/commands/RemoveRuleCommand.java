package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.*;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;

import java.util.ArrayList;
import java.util.Set;

public class RemoveRuleCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;

    public RemoveRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=2){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the removeRule command.");
                }
                removeRule(input);
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }

    private void removeRule(String[] input) throws Exception {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                "list to see all grammars.");
        }
        int number = Integer.parseInt(input[1]);
        if(number<=0 || number > grammar.getRules().size()){throw new InvalidRuleNumberException("Invalid rule number. Please type help to see the correct syntax for " +
                "the print command which can show you the number of the rules.");
        }
        StringBuilder ruleString = new StringBuilder();
        Set<Rule> rules = grammar.getRules();
        int counter = 0;
        for (Rule rule : rules) {
            counter++;
            if (counter == number) {
                ruleString.append(rule.toString());
                break;
            }
        }

        int index = fileHandler.getFileContent().indexOf(String.valueOf(ruleString));
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
            System.out.println("Successfully removed rule: " + ruleString);
        }else throw new ContentSearchFailureException("Failed to find the rule to be removed in the current file content" );
    }
}
