package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.*;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;

/**
 * The type AddRuleCommand. Adds a Rule to a Grammar specified by the user.
 */
public class AddRuleCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;

    /**
     * Instantiates a new AddRuleCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     */
    public AddRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Gets the Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * After that the String representation of the Grammar gets removed from the file content and appended at the end.
     * The rule is added to the file content and the content gets updated. Finally, the Rule gets added at the end of
     * the Grammar.
     * After that the implementation of the command from the implementation of the GrammarCommands class is called.
     * The method finds the Grammar in the Grammar Set and adds the rule to the Grammar.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     * @throws GrammarIDNotFoundException - when a Grammar isn't found
     */
    @Override
    public void invoke(String[] input) {
        try{
            if(fileHandler.isFileOpen()) {
                if(input.length <=2){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the addRule command.");
                }
                addRule(input);
            }else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }

    private void addRule(String[] input) throws Exception {
        StringBuilder inputString = new StringBuilder();
        for (int i = 1; i < input.length; i++) {
            inputString.append(input[i]);
        }
        int arrowIndex = inputString.indexOf("→");
        inputString.insert(arrowIndex," ");
        inputString.insert(arrowIndex+2," ");
        for(int i=0;i<inputString.length();i++){
            if(inputString.charAt(i) == '|'){
                inputString.insert(i," ");
                i++;
                inputString.insert(i+1," ");
                i++;
            }
        }
        Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
        if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                "list to see all grammars.");
        }
        String grammarString= grammar.toString();
        int index = fileHandler.getFileContent().indexOf(String.valueOf(grammarString));
        if (index != -1) {
            StringBuilder newFileContent = new StringBuilder();
            fileHandler.getFileContent().delete(index, index + grammarString.length());
            String[] lines = fileHandler.getFileContent().toString().split("\n");
            for(String line:lines){
                if(!line.trim().isEmpty()){
                    newFileContent.append(line).append("\n");
                }
            }
            newFileContent.append("\n");
            newFileContent.append(grammarString);
            fileHandler.setFileContent(newFileContent);
            System.out.println("Successfully added rule: " + inputString);
            grammarCommands.addRule(Long.parseLong(input[0]), inputString.toString());
            fileHandler.getFileContent().append(inputString).append("\n");
        }else throw new ContentSearchFailureException("Failed to find the old version of grammar in the current file content." );
    }
}
