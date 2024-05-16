public class SaveAsCommand implements Command{
    private FileHandler fileHandler;

    public SaveAsCommand(FileHandler fileHandler,GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            fileHandler.saveAs(input[0]);
        }else System.out.println("Please open a file first.");
    }
}
