package bg.tu_varna.sit.a1.f22621621;

import bg.tu_varna.sit.a1.f22621621.commands.*;
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
        Scanner scanner = new Scanner(System.in);
        /*fileHandler.open("C:\\Users\\pavel\\Desktop\\testgrammar.txt");
        fileHandler.saveAs("C:\\Users\\pavel\\Desktop\\testgrammar5.txt");
        fileHandler.close();*/
        //grammar.addRule(,"A → aA");
        //grammar.addRule(123,"B → bB");
        CommandHandler commandHandler = new CommandHandler(fileHandler,grammarCommands,grammarUtils,scanner);
        commandHandler.run();
    }
}