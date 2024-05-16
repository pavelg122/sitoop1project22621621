public class PrintCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public PrintCommand(GrammarCommands grammarCommands, FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            grammarCommands.print(Long.parseLong(input[0]));
        }else System.out.println("Please open a file first.");
    }
}
