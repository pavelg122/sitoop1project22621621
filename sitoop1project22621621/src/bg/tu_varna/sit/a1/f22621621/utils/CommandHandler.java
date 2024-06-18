package bg.tu_varna.sit.a1.f22621621.utils;

import bg.tu_varna.sit.a1.f22621621.commands.*;
import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidCommandException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;

import java.util.*;

/**
 * The type CommandHandler. It handles all user input and calls the necessary command.
 */
public class CommandHandler {
    private final FileHandler fileHandler;
    private final GrammarCommands grammarCommands;
    private final GrammarUtils grammarUtils;
    private final Scanner scanner;

    /**
     * Instantiates a new CommandHandler.
     *
     * @param fileHandler     the file handler
     * @param grammarCommands the grammar commands
     * @param grammarUtils    the grammar utils
     * @param scanner         the scanner
     */
    public CommandHandler(FileHandler fileHandler, GrammarCommands grammarCommands, GrammarUtils grammarUtils, Scanner scanner) {
        this.fileHandler = fileHandler;
        this.grammarCommands = grammarCommands;
        this.grammarUtils = grammarUtils;
        this.scanner = scanner;
    }

    /**
     * Handles the user's input. Initializes a Map that contains all commands of the application and the key word used to invoke every command.
     * Prints a welcome message and then uses the scanner to read each line of the user's input.
     * Invokes a command if the line starts with a key word. Throws an InvalidCommandException and displays an error message if it fails
     * to find a command.
     * @throws InvalidCommandException if the first element of the user input is not a key word in the commands Map
     */
    public void run() throws InvalidCommandException {
        Map<String, Command> commands = new HashMap<>();
        commands.put("open",new OpenCommand(fileHandler,grammarCommands));
        commands.put("close",new CloseCommand(fileHandler));
        commands.put("saveas", new SaveAsCommand(fileHandler));
        commands.put("save", new SaveCommand(fileHandler,grammarCommands));
        commands.put("help", new HelpCommand(fileHandler));
        commands.put("exit", new ExitCommand(fileHandler));
        commands.put("list", new ListCommand(grammarCommands,fileHandler));
        commands.put("print", new PrintCommand(grammarCommands,fileHandler));
        commands.put("addRule",new AddRuleCommand(grammarCommands,fileHandler));
        commands.put("removeRule", new RemoveRuleCommand(grammarCommands,fileHandler));
        commands.put("union", new UnionCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("concat", new ConcatCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("chomsky", new ChomskyCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("cyk", new CykCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("iter", new IterCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("empty", new EmptyCommand(grammarCommands,fileHandler,grammarUtils));
        commands.put("chomskify", new ChomskifyCommand(grammarCommands,fileHandler,grammarUtils));

        System.out.println("Welcome to the Context Free Grammar program. Type help to see all commands.");
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ", 2);
                String commandName = parts[0];
                String[] input = new String[]{};
                if (parts.length > 1) input = parts[1].split(" ");
                Command cmd = commands.get(commandName);
                if (cmd != null) cmd.invoke(input);
                else throw new InvalidCommandException("Invalid command " + commandName + ". Please type help to see all commands.");
            }
        }catch (Exception e) {System.out.println(e.getMessage());}
    }
}
