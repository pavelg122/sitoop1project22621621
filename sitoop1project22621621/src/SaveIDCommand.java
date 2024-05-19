import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SaveIDCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public SaveIDCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler= fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        if(fileHandler.isFileOpen()) {
            /*File newFile = new File(newFilePath);
            try{
                PrintWriter writer = new PrintWriter(newFile);
                writer.write(fileContent.toString());
                writer.close();
                System.out.println("Successfully saved " + newFile.getName());
            }catch(IOException e){
                System.out.println("Error saving file: " + e.getMessage());
            }*/
            Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
            String newFilePath = input[1];
            File newFile = new File(newFilePath);
            StringBuilder grammarBuilder = new StringBuilder();
            String nonterminals = null;
            ArrayList<String> terminals = null;
            for (Rule rule : grammar.getRules()) {
                nonterminals = rule.getNonterminals();
                terminals = rule.getTerminals();
                assert nonterminals != null;
                grammarBuilder.append(nonterminals);
                grammarBuilder.append(" → ");
                assert terminals != null;
                for (String terminal : terminals) {
                    grammarBuilder.append(terminal).append(" | ");
                }
                grammarBuilder.append("\n");
            }

            PrintWriter writer = new PrintWriter(newFile);
            writer.write(grammarBuilder.toString());
            writer.close();
            System.out.println("Succesfully saved " + grammar.getId() + " to: " + newFile.getName());
        }else System.out.println("Please open a file first.");
    }

}
