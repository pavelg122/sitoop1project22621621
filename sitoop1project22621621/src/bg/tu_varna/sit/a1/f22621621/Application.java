package bg.tu_varna.sit.a1.f22621621;

import bg.tu_varna.sit.a1.f22621621.commands.*;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

public class Application {
    public static void main(String[] args) throws Exception {
        Set<Grammar> grammars = new HashSet<>();
        FileHandler fileHandler= new FileHandlerImpl(grammars);
        GrammarCommands grammarCommands = new GrammarCommandsImpl(grammars);
        GrammarUtils grammarUtils = new GrammarUtils();
        /*fileHandler.open("C:\\Users\\pavel\\Desktop\\testgrammar.txt");
        fileHandler.saveAs("C:\\Users\\pavel\\Desktop\\testgrammar1.txt");
        fileHandler.close();*/
        //grammar.addRule(,"A → aA");
        //grammar.addRule(123,"B → bB");
        Map<String, Command> commands = new HashMap<>();
        commands.put("open",new OpenCommand(fileHandler,grammarCommands));
        commands.put("close",new CloseCommand(fileHandler));
        commands.put("saveas", new SaveAsCommand(fileHandler));
        commands.put("save", new SaveCommand(fileHandler));
        commands.put("help", new HelpCommand(fileHandler));
        commands.put("exit", new ExitCommand(fileHandler));
        commands.put("list", new ListCommand(grammarCommands,fileHandler));
        commands.put("print", new PrintCommand(grammarCommands,fileHandler));
        commands.put("saveid",new SaveIDCommand(grammarCommands,fileHandler));
        commands.put("addRule",new AddRuleCommand(grammarCommands,fileHandler));
        commands.put("removeRule", new RemoveRuleCommand(grammarCommands,fileHandler));
        commands.put("union", new UnionCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("concat", new ConcatCommand(grammarCommands,fileHandler));
        commands.put("chomsky", new ChomskyCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("cyk", new CykCommand(grammarCommands,fileHandler));
        commands.put("iter", new IterCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("empty", new EmptyCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("chomskify", new ChomskifyCommand(grammarCommands,fileHandler,grammarUtils));
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
        }
    }
}