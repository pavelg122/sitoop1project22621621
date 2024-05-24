import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Set;

public class FileHandlerImpl implements FileHandler{
    private File currentFile;
    private StringBuilder fileContent = new StringBuilder();
   private boolean isFileOpen = false;
    private Set<Grammar> grammarSet;

    public FileHandlerImpl(Set<Grammar> grammarSet) {
        this.grammarSet = grammarSet;
    }

    @Override
    public boolean isFileOpen() {
        return isFileOpen;
    }

    @Override
    public void open(String filePath,Set<Grammar> grammars) throws IOException {
        currentFile = new File(filePath);
        currentFile.createNewFile();

        try {
            Grammar grammar = new Grammar();
            Scanner scanner = new Scanner(currentFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains("→")){
                grammar.addRule(line);}
                fileContent.append(line).append("\n");
            }
            isFileOpen = true;
            if(!fileContent.isEmpty()){fileContent.delete(fileContent.lastIndexOf("\n"),fileContent.length()-1);}
            scanner.close();
            System.out.println("Successfully opened file " + currentFile.getName());
            grammars.add(grammar);
            System.out.println(fileContent);
        }catch (IOException e){System.out.println("Error opening file "+ currentFile.getName());
            isFileOpen = false;
        System.exit(1);}
    }

    @Override
    public void close() {
        isFileOpen = false;
        fileContent = new StringBuilder();
        System.out.println("Successfully closed " + currentFile.getName());
        currentFile = null;
        System.out.println(fileContent);
        grammarSet.clear();
    }

    @Override
    public void saveInFile() {
try{
    PrintWriter writer = new PrintWriter(new FileWriter(currentFile,false));
    writer.write(fileContent.toString());
    writer.close();
    System.out.println("Successfully saved " + currentFile.getName());
}catch(IOException e){System.out.println("Error saving file " + e.getMessage());}

    }

    @Override
    public void saveAs(String newFilePath) {
        File newFile = new File(newFilePath);
        try{
            PrintWriter writer = new PrintWriter(newFile);
            writer.write(fileContent.toString());
            writer.close();
            System.out.println("Successfully saved " + newFile.getName());
        }catch(IOException e){
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @Override
    public void help() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The following commands are supported:\n");
        stringBuilder.append("open <file>             opens <file>\n");
        stringBuilder.append("close                   closes currently opened file\n");
        stringBuilder.append("save                    saves the currently open file\n");
        stringBuilder.append("saveas <file>           saves the currently open file in <file>\n");
        stringBuilder.append("help                    prints this information\n");
        stringBuilder.append("exit                    exists the program\n");

        stringBuilder.append("list                    list with the IDs of all grammars\n");
        stringBuilder.append("print <id>              prints grammar\n");
        stringBuilder.append("save <id> <filename>    saves grammar in file\n");
        stringBuilder.append("addRule <id> <rule>     adds a rule\n");
        stringBuilder.append("removeRule <id> <n>     removes a rule by number of rule\n");
        stringBuilder.append("union <id1> <id2>       finds union of two grammars and prints ID of the new grammar\n");
        stringBuilder.append("concat <id1> <id2>      finds concatenation of two grammars and prints ID of the new grammar\n");
        stringBuilder.append("chomsky <id>            checks if grammar is in Chomsky Normal Form\n");
        stringBuilder.append("cyk <id>                checks if a given word is in the language of the grammar\n");
        stringBuilder.append("iter <id>               finds result of iteration over grammar and prints ID of the new grammar\n");
        stringBuilder.append("empty <id>              checks if the language of a context-free grammar is empty\n");
        stringBuilder.append("chomskify <id>          transforms a grammar in Chomsky Normal Form and prints the ID of the new grammar\n");
        System.out.println(stringBuilder);
    }

    @Override
    public void exit() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }

    public StringBuilder getFileContent() {
        return fileContent;
    }

    public void setFileContent(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }

    public void setFileOpen(boolean fileOpen) {
        isFileOpen = fileOpen;
    }
}
