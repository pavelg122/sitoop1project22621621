public class AddRuleCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;

    public AddRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        StringBuilder inputString = new StringBuilder();
        StringBuilder ruleString = new StringBuilder();
        for (int i = 2; i < input.length; i++) {
            inputString.append(input[i]);
        }
        for (int i = 0; i < inputString.length(); i++) {
            if(i<inputString.lastIndexOf("|")){
                ruleString.append(inputString.charAt(i));
            }
        }
grammarCommands.addRule(Long.parseLong(input[0]), String.valueOf(ruleString));
        fileHandler.getFileContent().append(ruleString);
    }
}
