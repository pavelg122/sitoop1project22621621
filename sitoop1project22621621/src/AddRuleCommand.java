public class AddRuleCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;

    public AddRuleCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            StringBuilder inputString = new StringBuilder();
            //StringBuilder ruleString = new StringBuilder();
            for (int i = 1; i < input.length; i++) {
                inputString.append(input[i]);
            }
            /*for (int i = 0; i < inputString.length(); i++) {
                if (i < inputString.lastIndexOf("|")) {
                    ruleString.append(inputString.charAt(i));
                }
                ruleString.append(inputString.charAt(i));
            }*/
            //System.out.println(inputString);
            //System.out.println(ruleString);
            int arrowIndex = inputString.indexOf("→");
            inputString.insert(arrowIndex," ");
            inputString.insert(arrowIndex+2," ");
            //System.out.println(inputString);
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
        }else {System.out.println("Please open a file first.");}
    }
}
