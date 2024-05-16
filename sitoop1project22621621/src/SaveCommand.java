public class SaveCommand implements Command{
    private FileHandler fileHandler;

    public SaveCommand(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            fileHandler.saveInFile();
        }else System.out.println("Please open a file first.");
    }
}
