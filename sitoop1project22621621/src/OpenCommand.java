import java.util.Arrays;

public class OpenCommand implements Command{
    @Override
    public void invoke(String[] input) {
System.out.println("opened file: " + Arrays.toString(input));
    }
}
