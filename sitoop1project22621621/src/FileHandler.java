import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface FileHandler {
    boolean isFileOpen();

    void open(String filePath, Set<Grammar> grammars) throws IOException;

    void close();

    void saveInFile();

    void saveAs(String newFilePath);

    void help();

    void exit();

    File getCurrentFile();

    void setCurrentFile(File currentFile);

    StringBuilder getFileContent();

    void setFileContent(StringBuilder fileContent);

    void setFileOpen(boolean fileOpen);
}