import java.io.File;
import java.io.IOException;

public interface FileHandler {
    boolean isFileOpen();
    void open(String filePath) throws IOException;
    void close();
    void saveInFile();
    void saveAs(String newFilePath);
    void help();
    void exit();
}
