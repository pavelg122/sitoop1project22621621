import java.io.IOException;

public interface Command {
    void invoke(String[] input) throws Exception;
}
