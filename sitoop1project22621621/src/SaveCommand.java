public class SaveCommand implements Command{
    private FileHandler fileHandler;

    public SaveCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
    fileHandler.saveInFile();
    }
}
