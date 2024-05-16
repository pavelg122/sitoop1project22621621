import java.io.IOException;
import java.util.*;

public class Application {
    public static void main(String[] args) throws Exception {
        Set<Grammar> grammars = new HashSet<>();
        FileHandler fileHandler= new FileHandlerImpl(grammars);
        GrammarCommands grammarCommands = new GrammarCommandsImpl(grammars);
        /*fileHandler.open("C:\\Users\\pavel\\Desktop\\testgrammar.txt");
        fileHandler.printFileContent();
        fileHandler.saveAs("C:\\Users\\pavel\\Desktop\\testgrammar1.txt");
        fileHandler.close();*/
        //fileHandler.printFileContent();
        /*Grammar grammar = new Grammar();
        System.out.println(grammar.getId() + "\n");
        Grammar grammar1 = new Grammar();
        System.out.println(grammar1.getId());*/
        //grammar.addRule(,"A → aA");
        //grammar.addRule(123,"B → bB");
        //grammar.removeRule(123,2);
        Map<String,Command> commands = new HashMap<>();
        commands.put("open",new OpenCommand(fileHandler,grammarCommands));
        commands.put("close",new CloseCommand(fileHandler,grammarCommands));
        commands.put("saveas", new SaveAsCommand(fileHandler,grammarCommands));
        commands.put("save", new SaveCommand(fileHandler));
        commands.put("help", new HelpCommand(fileHandler));
        commands.put("exit", new ExitCommand(fileHandler));
        commands.put("list", new ListCommand(grammarCommands,fileHandler));
        commands.put("print", new PrintCommand(grammarCommands,fileHandler));
        commands.put("saveid",new SaveIDCommand(grammarCommands,fileHandler));
        commands.put("addRule",new AddRuleCommand(grammarCommands,fileHandler));
        commands.put("removeRule", new RemoveRuleCommand(grammarCommands,fileHandler));
        commands.put("union", new UnionCommand(grammarCommands,fileHandler));
        commands.put("concat", new ConcatCommand(grammarCommands,fileHandler));
        commands.put("chomsky", new ChomskyCommand(grammarCommands,fileHandler));
        commands.put("cyk", new CykCommand(grammarCommands,fileHandler));
        commands.put("iter", new IterCommand(grammarCommands,fileHandler));
        commands.put("empty", new EmptyCommand(grammarCommands,fileHandler));
        commands.put("chomskify", new ChomskifyCommand(grammarCommands,fileHandler));
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
            /*for (int i = 0; i < input.length; i++) {
                System.out.println("i: " + i + input[i]);
            }*/

        }
    }
}