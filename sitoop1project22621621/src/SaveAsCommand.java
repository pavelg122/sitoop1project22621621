public class SaveAsCommand implements Command{
    private FileHandler fileHandler;

    public SaveAsCommand(FileHandler fileHandler,GrammarCommands grammarCommands) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
    fileHandler.saveAs(input[0]);
    }
}
