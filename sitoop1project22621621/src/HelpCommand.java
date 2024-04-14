public class HelpCommand implements Command{
    private FileHandler fileHandler;

    public HelpCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
            fileHandler.help();
    }
}
