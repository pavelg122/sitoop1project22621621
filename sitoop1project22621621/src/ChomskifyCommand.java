import java.util.*;

public class ChomskifyCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public ChomskifyCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) {
     Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
     Grammar cnfGrammar = new Grammar();
     Set<Rule> rules = grammar.getRules();
     Set<Rule> cnfRules = new HashSet<>();
     //добавяне на ново правило в началото
        Iterator<Rule> iterator1 = rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();
        }
        String startNonterminals = "S0";
        assert retrieved1 != null;
        String startTerminals = retrieved1.getNonterminals();
        String[] startTerminals1 = startTerminals.split("");
        ArrayList<String> startTerminals2 = new ArrayList<>(Arrays.asList(startTerminals1));
        cnfRules.add(new Rule(startNonterminals,startTerminals2));
        //премахване на безполезни правила - правила с терминали, които ги няма в началното правило
        ArrayList<String> usefulNonterminals = retrieved1.getTerminals();
        while(iterator1.hasNext()){
            Rule retrieved = iterator1.next();
            String nonterminals = retrieved.getNonterminals();
        if(!stringContains(usefulNonterminals,nonterminals)){
            //rules.remove(retrieved);
            iterator1.remove();
        }
        }
        //премахване на правила с празен символ  (A → ε)
        Iterator<Rule>iterator2 = rules.iterator();
        Set<Rule> removedEmptyRules = new HashSet<>();
        String nullString = "";
        String epsilon = "ε";
        while(iterator2.hasNext()){
            Rule retrieved = iterator2.next();
            //String[] ruleNonterminals = retrieved.getNonterminals();
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            if(stringContains(ruleTerminals,nullString) || stringContains(ruleTerminals,epsilon)){
                removedEmptyRules.add(retrieved);
                iterator2.remove();
            }
        }
        for(Rule removed:removedEmptyRules){
            iterator2 = rules.iterator();
            while(iterator2.hasNext()){
                Rule retrieved = iterator2.next();
                String ruleNonterminals = retrieved.getNonterminals();
                //if(Arrays.equals(removed.getTerminals(), ruleNonterminals))
                    /*if(removed.getNonterminals().e){
                    cnfRules.add(new Rule(removed.getNonterminals(), retrieved.getTerminals()));
                }*/
            }
        }
        //премахване на юнит правила (A → B)
        Iterator<Rule>iterator3 = rules.iterator();
        Set<Rule> removedUnitRules = new HashSet<>();
        String[] allTerminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        ArrayList<String> allTerminals1 = new ArrayList<>(Arrays.asList(allTerminals));
        while(iterator3.hasNext()){
            Rule retrieved = iterator3.next();
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            if(stringContains(allTerminals1, String.valueOf(ruleTerminals)) && ruleTerminals.size() == 1 ){
                removedUnitRules.add(retrieved);
                iterator3.remove();
            }
        }
        for(Rule removed:removedUnitRules){
            iterator3 = rules.iterator();
            while(iterator3.hasNext()){
                Rule retrieved = iterator3.next();
                String ruleNonterminals = retrieved.getNonterminals();
                if(removed.getTerminals().toString().equals(ruleNonterminals)){
                    cnfRules.add(new Rule(removed.getNonterminals(), retrieved.getTerminals()));
                }
            }
        }
        //премахване на правила с несамотни терминали (A → aB)
        Iterator<Rule>iterator4 = rules.iterator();

        while(iterator4.hasNext()){
            Rule retrieved = iterator4.next();
        }
        //премахване на правила с 2+ нетерминали отдясно (A → BCD)
        Iterator<Rule>iterator5 = rules.iterator();

        while(iterator5.hasNext()){
            Rule retrieved = iterator5.next();
        }
        System.out.println("Chomskify grammar id: " + cnfGrammar.getId());
    }
    private boolean stringContains(ArrayList<String> arr,String value){
        boolean useful = false;
        for (String nonTerminal : arr) {
            /*StringBuilder nonTerminalString = new StringBuilder();
            for (String Nonterminal : value) {
                nonTerminalString.append(Nonterminal);
            }*/
            if (arr.contains(value)) {
                useful = true;
                break;
            }
        }
     return useful;
    }
}
