package bg.tu_varna.sit.a1.f22621621.interfaces;

import bg.tu_varna.sit.a1.f22621621.models.Grammar;

import java.io.IOException;
import java.util.Set;

/**
 * The interface FileHandler. It provides methods for opening a file and closing it, methods to save
 * the changes to the file content of the current file and to save the file content to a new file.
 * The interface also provides the following utility methods:
 * a method that displays all available commands and a method to exit the application.
 */
public interface FileHandler {
    /**
     * Checks if a file is currently open.
     *
     * @return boolean - true if a file is open and false if it isn't
     */
    boolean isFileOpen();

    /**
     * Opens a file, coverts the read content to a Grammar and adds the created Grammar to a
     * Set that contains all Grammars in the application.
     *
     * @param filePath the file path
     * @param grammars the Grammar Set
     * @throws IOException the io exception
     */
    void open(String filePath, Set<Grammar> grammars) throws IOException;

    /**
     * Closes the currently open file.
     */
    void close();

    /**
     * Saves the file content in the current file.
     */
    void saveInFile();

    /**
     * Save the file content in a new file.
     *
     * @param newFilePath the new file path
     */
    void saveAs(String newFilePath);

    /**
     * Displays information for all supported commands.
     */
    void help();

    /**
     * Exits the application.
     */
    void exit();


    /**
     * Returns the current file content.
     *
     * @return the file content
     */
    StringBuilder getFileContent();

    /**
     * Sets the current file content.
     *
     * @param fileContent the file content
     */
    void setFileContent(StringBuilder fileContent);

}