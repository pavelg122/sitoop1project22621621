package bg.tu_varna.sit.a1.f22621621.utils;

import bg.tu_varna.sit.a1.f22621621.commands.*;
import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidCommandException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

import java.util.*;

public class CommandHandler {
    private final FileHandler fileHandler;
    private final GrammarCommands grammarCommands;
    private final GrammarUtils grammarUtils;
    private final Scanner scanner;

    public CommandHandler(FileHandler fileHandler, GrammarCommands grammarCommands, GrammarUtils grammarUtils, Scanner scanner) {
        this.fileHandler = fileHandler;
        this.grammarCommands = grammarCommands;
        this.grammarUtils = grammarUtils;
        this.scanner = scanner;
    }
    public void run() throws Exception {
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
        commands.put("cyk", new CykCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("iter", new IterCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("empty", new EmptyCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("chomskify", new ChomskifyCommand(grammarCommands,fileHandler,grammarUtils));
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split(" ",2);
            String commandName = parts[0];
            String[] input = new String[]{};
            if(parts.length>1)input = parts[1].split(" ");
            Command cmd = commands.get(commandName);
            if(cmd != null)cmd.invoke(input);
            else throw new InvalidCommandException("Invalid command " + commandName);
        }
    }
}
