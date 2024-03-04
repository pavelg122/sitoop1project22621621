import java.io.File;
import java.io.IOException;

public interface FileHandler {
    boolean isFileOpen();
    void open(String filePath) throws IOException;
    void close(File currentFile);
    void saveInFile(File currentFile);
    void saveAs(String newFilePath);
    void help();
    void exit();
}
