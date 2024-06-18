package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.*;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;

import java.util.ArrayList;
import java.util.Set;

/**
 * The type RemoveRuleCommand. Removes a rule of a Grammar with an index specified by the user.
 */
public class RemoveRuleCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;

    /**
     * Instantiates a new RemoveRuleCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     */
    public RemoveRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Gets the Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * If the number of the Rule to be removed is invalid a InvalidRuleNumberException is thrown. Then the method finds the rule
     * with an index(starting from 1) equal to the number. The rule is removed from the file content and the content gets updated.
     * After that the implementation of the command from the implementation of the GrammarCommands class is called.
     * The method finds the Grammar in the Grammar Set and removes the rule from the Grammar.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     * @throws GrammarIDNotFoundException - when a Grammar isn't found
     * @throws InvalidRuleNumberException - when the number of the Rule to be removed is invalid
     */
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
