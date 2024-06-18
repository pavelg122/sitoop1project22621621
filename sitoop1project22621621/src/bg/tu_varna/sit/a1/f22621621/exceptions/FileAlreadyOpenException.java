package bg.tu_varna.sit.a1.f22621621.exceptions;

/**
 * The type FileAlreadyOpenException. It occurs when the user tries to open a file
 * when another file is already open.
 */
public class FileAlreadyOpenException extends RuntimeException {
    /**
     * Instantiates a new FileAlreadyOpenException.
     *
     * @param message the message that is displayed when the exception is caught
     */
    public FileAlreadyOpenException(String message) {
        super(message);
    }
}
