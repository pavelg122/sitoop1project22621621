public class ListCommand implements Command{
    GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public ListCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        if(fileHandler.isFileOpen()) {
            grammarCommands.list();
        }else System.out.println("Please open a file first.");
    }
}
