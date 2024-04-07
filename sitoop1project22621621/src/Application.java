import java.io.IOException;
import java.util.*;

public class Application {
    public static void main(String[] args) throws Exception {
        /*FileHandlerImpl fileHandler= new FileHandlerImpl();
        fileHandler.open("C:\\Users\\pavel\\Desktop\\testgrammar.txt");
        fileHandler.printFileContent();
        fileHandler.saveAs("C:\\Users\\pavel\\Desktop\\testgrammar1.txt");
        fileHandler.close();*/
        //fileHandler.printFileContent();
        Grammar grammar = new Grammar();
        System.out.println(grammar.getId() + "\n");
        Grammar grammar1 = new Grammar();
        System.out.println(grammar1.getId());
        //grammar.addRule(,"A → aA");
        //grammar.addRule(123,"B → bB");
        //grammar.removeRule(123,2);
        Map<String,Command> commands = new HashMap<>();
        commands.put("open",new OpenCommand());
        commands.put("close",new CloseCommand());
        commands.put("saveas", new SaveAsCommand());
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split(" ",2);
            String commandName = parts[0];
            String[] input = new String[]{};
            if(parts.length>1)input = parts[1].split(" ");
            Command cmd = commands.get(commandName);
            if(cmd != null)cmd.invoke(input);
                else System.out.println("Unknown command " + commandName);
            for (int i = 0; i < input.length; i++) {
                System.out.println("i: " + i + input[i]);
            }

        }
    }
}