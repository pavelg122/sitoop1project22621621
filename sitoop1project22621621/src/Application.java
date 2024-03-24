import java.io.IOException;

public class Application {
    public static void main(String[] args) throws Exception {
        FileHandlerImpl fileHandler= new FileHandlerImpl();
        fileHandler.open("C:\\Users\\pavel\\Desktop\\testgrammar.txt");
        fileHandler.printFileContent();
        fileHandler.saveAs("C:\\Users\\pavel\\Desktop\\testgrammar1.txt");
        fileHandler.close();
        //fileHandler.printFileContent();
        Grammar grammar = new Grammar(123);
        grammar.addRule(123,"A → aA");
        grammar.addRule(123,"B → bB");
        grammar.removeRule(123,2);
    }
}