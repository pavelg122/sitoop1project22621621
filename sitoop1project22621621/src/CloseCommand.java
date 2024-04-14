public class CloseCommand implements Command{
    private FileHandler fileHandler;

    public CloseCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
fileHandler.close();
    }
}
