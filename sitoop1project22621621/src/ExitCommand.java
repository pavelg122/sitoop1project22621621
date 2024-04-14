public class ExitCommand implements Command{
    private FileHandler fileHandler;

    public ExitCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        fileHandler.exit();
    }
}
