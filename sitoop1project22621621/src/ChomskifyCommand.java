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
        String[] allNonterminals = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","s","t","u","v","w","x","y","z",
                "0","1","2","3","4","5","6","7","8","9"};
     String[] allTerminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
     //добавяне на ново правило в началото
        Iterator<Rule> iterator1 = rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();
        }
        String startNonterminals = "S0";
        assert retrieved1 != null;
        ArrayList<String> startTerminals = retrieved1.getTerminals();
        String startNonterminal = retrieved1.getNonterminals();
        /*String[] startTerminals1 = startTerminals.split("");
        ArrayList<String> startTerminals2 = new ArrayList<>(Arrays.asList(startTerminals1));*/
        cnfRules.add(new Rule(startNonterminals,startTerminals));
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
        String question = "?";
        while(iterator2.hasNext()){
            Rule retrieved = iterator2.next();
            //String[] ruleNonterminals = retrieved.getNonterminals();
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            String ruleNonterminals = retrieved.getNonterminals();
            if((stringContains(ruleTerminals,nullString) || stringContains(ruleTerminals,epsilon) || stringContains(ruleTerminals,question))
                    && !(ruleNonterminals.equals(startNonterminal)))
            {
                removedEmptyRules.add(retrieved);
                iterator2.remove();
            }
        }
        for(Rule removed:removedEmptyRules){
            iterator2 = rules.iterator();
            while(iterator2.hasNext()){
                Rule retrieved = iterator2.next();
                String ruleNonterminals = retrieved.getNonterminals();
                ArrayList<String> ruleTerminals1 = retrieved.getTerminals();
                ArrayList<String> removeSymbols = new ArrayList<>();
                removeSymbols.add(nullString);
                removeSymbols.add(epsilon);
                removeSymbols.add(question);
                if(ruleTerminals1.contains(removed.getNonterminals())){
                    ArrayList<String> newCombinations = getNewCombinations(ruleTerminals1,removed.getNonterminals());
                        ruleTerminals1.addAll(newCombinations);
                    ruleTerminals1.removeAll(removeSymbols);
                }
            }
        }
        //премахване на юнит правила (A → B)
        Iterator<Rule>iterator3 = rules.iterator();
        Set<Rule> removedUnitRules = new HashSet<>();

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

        for (Rule retrieved : rules) {
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            for (String terminal : ruleTerminals) {
                if ((charsCount(terminal, allTerminals) == 1 && charsCount(terminal, allNonterminals) == 1)
                        && terminal.length() == 2) {
                    String nextNonterminal = getNextNonterminal(rules, allNonterminals);
                    ArrayList<String> newRuleTerminal = getNewRuleTerminal(terminal, allNonterminals, nextNonterminal);
                    rules.add(new Rule(nextNonterminal, newRuleTerminal));
                    //iterator4.remove();
                }
            }
        }
        //премахване на правила с 2+ нетерминали отдясно (A → BCD)

        for (Rule retrieved : rules) {
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            for (String terminal : ruleTerminals) {
                if (charsCount(terminal, allTerminals) == terminal.length() && terminal.length() == 3) {
                    String nextNonterminal1 = getNextNonterminal(rules, allNonterminals);
                    char[] oldTerminalArr = terminal.toCharArray();
                    StringBuilder newTerminal = new StringBuilder();
                    for (int i = 0; i < terminal.length() - 1; i++) {
                        newTerminal.append(oldTerminalArr[i]);
                    }
                    ArrayList<String> newRuleTerminal1 = new ArrayList<>();
                    for (int i = 0; i < newTerminal.length(); i++) {
                        newRuleTerminal1.add(String.valueOf(newTerminal.charAt(i)));
                    }
                    rules.add(new Rule(nextNonterminal1, newRuleTerminal1));
                    ArrayList<String> oldRuleNewTerminals = new ArrayList<>();
                    String oldRuleNewTerminalBuilder = nextNonterminal1 +
                            oldTerminalArr[2];
                    oldRuleNewTerminals.add(oldRuleNewTerminalBuilder);
                    retrieved.setTerminals(oldRuleNewTerminals);
                }
            }
        }
        System.out.println("Chomskify grammar id: " + cnfGrammar.getId());
    }

    private static ArrayList<String> getNewRuleTerminal(String terminal, String[] allNonterminals, String nextNonterminal) {
        char[] terminalCharArr = terminal.toCharArray();
        char nonterminal;
        ArrayList<String> nonTerminalsArrList = new ArrayList<>(Arrays.asList(allNonterminals));
        if(nonTerminalsArrList.contains(String.valueOf(terminalCharArr[0]))){
             nonterminal = terminalCharArr[0];
        }
        else{ nonterminal = terminalCharArr[1];}
        String newTerminal = terminal.replace(terminal, nextNonterminal);
        ArrayList<String> newRuleTerminal = new ArrayList<>();
        newRuleTerminal.add(newTerminal);
        return newRuleTerminal;
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
    private String getNextNonterminal(Set<Rule> rules,String[] allNonterminals){
        String nextNonterminal = null;
        Map<String, Boolean> freeTerminals = new HashMap<>();
        for(String letter:allNonterminals){
            freeTerminals.put(letter,false);
        }
        for(Rule rule:rules){
            freeTerminals.put(rule.getNonterminals(),true);
        }
        for(Map.Entry<String,Boolean> entry:freeTerminals.entrySet()){
            if(entry.getValue().equals(false)){
                nextNonterminal = entry.getKey();
                break;
            }
        }
        return nextNonterminal;
    }
    private ArrayList<String> getNewCombinations(ArrayList<String> ruleTerminals1, String nonterminals){
        Set<String> newCombinations1 = new HashSet<>();
        //Map<String,Integer> originalTerminalsToChange = new HashMap<>();
        //ArrayList<String> indexesToRemove = new ArrayList<>();
        for(String terminal:ruleTerminals1){
            if(terminal.contains(nonterminals)){
               int frequency = countFreq(nonterminals,terminal);
                int[] combinations = new int[frequency];
               for(int i=0;i<frequency;i++){
               combinations[i]= i+1;
               }
               LinkedHashSet powerSet = powerset(combinations);
               for(int i=0;i< powerSet.size();i++){
                   char[] terminalCharArr = terminal.toCharArray();
                   StringBuilder sb = new StringBuilder();
                   sb.append(terminalCharArr);
                   for(int j=frequency;j>0;j--){
                   if(powerSet.contains(j)){
                       /*StringBuilder sb = new StringBuilder();
                       sb.append(terminalCharArr);*/
                       int indexToDelete = nthIndexOf2(terminal,nonterminals,j);
                       sb.deleteCharAt(indexToDelete);
                   }
                   }
                   newCombinations1.add(sb.toString());
               }
               //originalTerminalsToChange.put(terminal,frequency);
            }
        }
        return new ArrayList<>(newCombinations1);
    }
private static int countFreq(String pat, String txt)
{
    int M = pat.length();
    int N = txt.length();
    int res = 0;

    /* A loop to slide pat[] one by one */
    for (int i = 0; i <= N - M; i++) {
            /* For current index i, check for
        pattern match */
        int j;
        for (j = 0; j < M; j++) {
            if (txt.charAt(i + j) != pat.charAt(j)) {
                break;
            }
        }

        // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
        if (j == M) {
            res++;
            j = 0;
        }
    }
    return res;
}
    private static LinkedHashSet powerset(int[] set) {

        //create the empty power set
        LinkedHashSet power = new LinkedHashSet();

        //get the number of elements in the set
        int elements = set.length;

        //the number of members of a power set is 2^n
        int powerElements = (int) Math.pow(2,elements);

        //run a binary counter for the number of power elements
        for (int i = 0; i < powerElements; i++) {

            //convert the binary number to a string containing n digits
            String binary = intToBinary(i, elements);

            //create a new set
            LinkedHashSet innerSet = new LinkedHashSet();

            //convert each digit in the current binary number to the corresponding element
            //in the given set
            for (int j = 0; j < binary.length(); j++) {
                if (binary.charAt(j) == '1')
                    innerSet.add(set[j]);
            }

            //add the new set to the power set
            power.add(innerSet);

        }

        return power;
    }

    /**
     * Converts the given integer to a String representing a binary number
     * with the specified number of digits
     * For example when using 4 digits the binary 1 is 0001
     * @param binary int
     * @param digits int
     * @return String
     */
    private static String intToBinary(int binary, int digits) {

        String temp = Integer.toBinaryString(binary);
        int foundDigits = temp.length();
        String returner = temp;
        for (int i = foundDigits; i < digits; i++) {
            returner = "0" + returner;
        }

        return returner;
    }

    static int nthIndexOf2(String input, String substring, int nth) {
        int index = -1;
        while (nth > 0) {
            index = input.indexOf(substring, index + substring.length());
            if (index == -1) {
                return -1;
            }
            nth--;
        }
        return index;
    }

    private int charsCount(String terminal,String[] characters){
        int count=0;
        char[] terminalCharArr = terminal.toCharArray();
        ArrayList<String> charsArrList = new ArrayList<>(Arrays.asList(characters));
        for(char ch:terminalCharArr){
            if(charsArrList.contains(String.valueOf(ch)))
            {count++;}
        }
        return count;
    }
}
