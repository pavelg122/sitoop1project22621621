package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

public class AddRuleCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;

    public AddRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        try{
            if(fileHandler.isFileOpen()) {
                addRule(input);
            }else throw new NoFileOpenedException("No file opened");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }

    private void addRule(String[] input) {
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
        grammarCommands.addRule(Long.parseLong(input[0]), inputString.toString());
        fileHandler.getFileContent().append(inputString).append("\n");
        System.out.println("Rule " + inputString + " added successfully");
    }
}
