public class ExitCommand implements Command{
    @Override
    public void invoke(String[] input) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
