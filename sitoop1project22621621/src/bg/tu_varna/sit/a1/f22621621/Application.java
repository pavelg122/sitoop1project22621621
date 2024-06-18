package bg.tu_varna.sit.a1.f22621621;

import bg.tu_varna.sit.a1.f22621621.commands.*;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.utils.CommandHandler;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

/**
 * The type Application.
 */
public class Application {
    /**
     * The entry point of the application.
     * Initializes the necessary objects for the CommandHandler class and runs the application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Set<Grammar> grammars = new HashSet<>();
        FileHandler fileHandler= new FileHandlerImpl(grammars);
        GrammarCommands grammarCommands = new GrammarCommandsImpl(grammars);
        GrammarUtils grammarUtils = new GrammarUtils();
        Scanner scanner = new Scanner(System.in);
        //fileHandler.saveAs("C:\\Users\\pavel\\Desktop\\testgrammar5.txt");
        //grammar.addRule(,"A → aA");
        CommandHandler commandHandler = new CommandHandler(fileHandler,grammarCommands,grammarUtils,scanner);
        commandHandler.run();
    }
}