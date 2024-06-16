package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
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
                removeRule(input);
            } else throw new NoFileOpenedException("No file opened");
        }catch (Exception e) {System.out.println(e.getMessage());}
    }

    private void removeRule(String[] input) throws Exception {
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        int number = Integer.parseInt(input[1]);
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
        }else throw new Exception("Failed to remove rule" );
    }
}
