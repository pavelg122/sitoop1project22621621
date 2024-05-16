import java.util.ArrayList;
import java.util.Arrays;

public class ChomskyCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    String[] allNonterminals = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","s","t","u","v","w","x","y","z",
            "0","1","2","3","4","5","6","7","8","9"};
    String[] allTerminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public ChomskyCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
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
    private boolean isCNF(Grammar grammar){
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
        if(terminalCharArr.length == 1 && charsCount(terminal,allTerminals) == terminalCharArr.length){cnfTerminal = true;}
        else if(terminalCharArr.length == 2 && charsCount(terminal,allNonterminals) == terminalCharArr.length){
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
