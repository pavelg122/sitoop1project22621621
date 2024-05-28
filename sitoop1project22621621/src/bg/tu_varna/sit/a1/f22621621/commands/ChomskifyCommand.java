package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

public class ChomskifyCommand implements Command {
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    private GrammarUtils grammarUtils;
    public ChomskifyCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils =grammarUtils;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
            ChomskyCommand chomskyCommand = new ChomskyCommand(grammarCommands,fileHandler,grammarUtils);
            if (chomskyCommand.isCNF(grammar)) {
                System.out.println("Grammar is already in Chomsky Normal Form.");
                return;
            }
            Grammar cnfGrammar = new Grammar();
        Set<Rule> rules = grammar.getRules();

            Set<Rule> grammarCopyRules = new LinkedHashSet<>();
            /*for(Rule rule:rules){
                grammarCopyRules.add(rule.copy());
            }*/

        //добавяне на ново правило в началото
        Iterator<Rule> iterator1 = rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();
        }
        String startNonterminals = "S0";
        boolean newRule = false;
        assert retrieved1 != null;
        String firstRuleNonterminal = retrieved1.getNonterminals();
        for(Rule rule:rules){
            for(String terminal:rule.getTerminals()){
                if (terminal.contains(firstRuleNonterminal)) {
                    newRule = true;
                    break;
                }
            }
        }
        ArrayList<String> firstRuleTerminal = new ArrayList<>();
            if(newRule){firstRuleTerminal.add(firstRuleNonterminal);
        grammarCopyRules.add(new Rule(startNonterminals, firstRuleTerminal));}

            for(Rule rule:rules){
                grammarCopyRules.add(rule.copy());
            }

        System.out.println("Rules after start:");
            StringBuilder startPrint = new StringBuilder();
            for(Rule rulerule:grammarCopyRules){
                startPrint.append(rulerule.getNonterminals());
                startPrint.append(" → ");
                ArrayList<String> terminals = rulerule.getTerminals();
                for (String terminal : terminals) {
                    startPrint.append(terminal).append(" | ");
                }
                startPrint.deleteCharAt(startPrint.lastIndexOf("|")+1);
                startPrint.deleteCharAt(startPrint.lastIndexOf("|")-1);
                startPrint.deleteCharAt(startPrint.lastIndexOf("|"));
                startPrint.append("\n");
            }
            System.out.println(startPrint);
        //премахване на правила с празен символ  (A → ε)
        Iterator<Rule> iterator2 = grammarCopyRules.iterator();
        Set<Rule> removedEmptyRules = new HashSet<>();
        String epsilon = "ε";
        while (iterator2.hasNext()) {
            Rule retrieved = iterator2.next();
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            String ruleNonterminals = retrieved.getNonterminals();
            if (( stringContains(ruleTerminals, epsilon))
                    && !(ruleNonterminals.equals(startNonterminals))) {
                removedEmptyRules.add(retrieved);
            }
        }
        for (Rule removed : removedEmptyRules) {
            iterator2 = grammarCopyRules.iterator();
            while (iterator2.hasNext()) {
                Rule retrieved = iterator2.next();
                String ruleNonterminals = retrieved.getNonterminals();
                ArrayList<String> ruleTerminals1 = retrieved.getTerminals();

                if (ruleTerminals1.contains(removed.getNonterminals())) {
                    Set<String> newRuleTerminalsSet = new LinkedHashSet<>(ruleTerminals1);
                    ArrayList<String> newCombinations = getNewCombinations(ruleTerminals1, removed.getNonterminals(), grammarUtils.getAllNonterminals());
                    newRuleTerminalsSet.addAll(newCombinations);
                    newRuleTerminalsSet.remove(epsilon);
                    //ruleTerminals1.addAll(newCombinations);
                    //ruleTerminals1.remove(epsilon);
                    ArrayList<String> updatedTerminals = new ArrayList<>(newRuleTerminalsSet);
                    retrieved.setTerminals(updatedTerminals);
                }
            }
        }
            System.out.println("Rules after del:");
            StringBuilder delPrint = new StringBuilder();
            for(Rule rulerule:grammarCopyRules){
                delPrint.append(rulerule.getNonterminals());
                delPrint.append(" → ");
                ArrayList<String> terminals = rulerule.getTerminals();
                for (String terminal : terminals) {
                    delPrint.append(terminal).append(" | ");
                }
                delPrint.deleteCharAt(delPrint.lastIndexOf("|")+1);
                delPrint.deleteCharAt(delPrint.lastIndexOf("|")-1);
                delPrint.deleteCharAt(delPrint.lastIndexOf("|"));
                delPrint.append("\n");
            }
            System.out.println(delPrint);
        //премахване на юнит правила (A → B)
        Iterator<Rule> iterator3 = grammarCopyRules.iterator();
        Set<Rule> removedUnitRules = new HashSet<>();

        ArrayList<String> allNonterminals1 = new ArrayList<>(Arrays.asList(/*allNonterminals*/grammarUtils.getAllNonterminals()));
        while (iterator3.hasNext()) {
            Rule retrieved = iterator3.next();
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            if (stringContains(allNonterminals1, String.valueOf(ruleTerminals)) && ruleTerminals.size() == 1 && !String.valueOf(ruleTerminals).equals(startNonterminals)) {
                removedUnitRules.add(retrieved);
                //iterator3.remove();
            }

        }
        for (Rule removed : removedUnitRules) {
            iterator3 = grammarCopyRules.iterator();
            while (iterator3.hasNext()) {
                Rule retrieved = iterator3.next();
                String ruleNonterminals = retrieved.getNonterminals();
                if (removed.getTerminals().toString().trim().equals(ruleNonterminals.trim())) {

                    grammarCopyRules.add(new Rule(removed.getNonterminals(), retrieved.getTerminals()));
                }
            }
        }
            System.out.println("Rules after unit:");
            StringBuilder unitPrint = new StringBuilder();
            for(Rule rulerule:grammarCopyRules){
                unitPrint.append(rulerule.getNonterminals());
                unitPrint.append(" → ");
                ArrayList<String> terminals = rulerule.getTerminals();
                for (String terminal : terminals) {
                    unitPrint.append(terminal).append(" | ");
                }
                unitPrint.deleteCharAt(unitPrint.lastIndexOf("|")+1);
                unitPrint.deleteCharAt(unitPrint.lastIndexOf("|")-1);
                unitPrint.deleteCharAt(unitPrint.lastIndexOf("|"));
                unitPrint.append("\n");
            }
            System.out.println(unitPrint);
            //премахване на безполезни правила - правила с терминали, които ги няма в никое правило

            ArrayList<String> firstRuleTerminals = retrieved1.getTerminals();
            Set<String> usefulNonterminals = new LinkedHashSet<>();
            ArrayList<String> allNonterminals = new ArrayList<>(List.of(grammarUtils.getAllNonterminals()));
        /*for(String terminal:firstRuleTerminals){
            String[] chars = terminal.split("");
            for(String char1:chars){
                if(allNonterminals.contains(char1)){
                    usefulNonterminals.add(char1);
                }
            }
        }*/
            Set<String> uniqueNonterminals = new LinkedHashSet<>();
            Map<String,Boolean> uniqueNonterminals1 = new LinkedHashMap<>();
            for(Rule rule:grammarCopyRules){
                //uniqueNonterminals.add(rule.getNonterminals());
                uniqueNonterminals1.put(rule.getNonterminals(),false);
            }
            /*for(String unique:uniqueNonterminals){
                boolean contained = false;
                for(Rule rule:grammarCopyRules){
                    if(rule.getTerminals().contains(unique)){
                        contained =true;
                        break;
                    }
                }
                if(!contained){
                    grammarCopyRules.removeIf(rule1 -> rule1.getNonterminals().equals(unique));
                }
            }*/
            for(Map.Entry<String,Boolean> entry:uniqueNonterminals1.entrySet()){
                for(Rule rule:grammarCopyRules){
                    if(rule.getTerminals().contains(entry.getKey())){
                        entry.setValue(true);
                        break;
                    }
                }
                if(entry.getValue().equals(false)){
                    grammarCopyRules.removeIf(rule1 -> rule1.getNonterminals().equals(entry.getKey()));
                }
            }
            ArrayList<String> usefulNonterminalsList = new ArrayList<>(usefulNonterminals);
            iterator1 = grammarCopyRules.iterator();
            while (iterator1.hasNext()) {
                Rule retrieved = iterator1.next();
                String nonterminals = retrieved.getNonterminals();
                if (!stringContains(usefulNonterminalsList, nonterminals)) {
                    iterator1.remove();
                }
            }
            for(Rule rule:grammarCopyRules){
                for(String terminal:rule.getTerminals()){
                    if(terminal.contains(rule.getNonterminals())){
                        ArrayList<String> terminalsCopy = new ArrayList<>();
                        for(String terminal1:rule.getTerminals()){
                            terminalsCopy.add(terminal1);
                        }
                        terminalsCopy.remove(terminal);
                        rule.setTerminals(terminalsCopy);
                    }
                }
            }
            System.out.println("Rules after useless:");
            StringBuilder uselessPrint = new StringBuilder();
            for(Rule rulerule:grammarCopyRules){
                uselessPrint.append(rulerule.getNonterminals());
                uselessPrint.append(" → ");
                ArrayList<String> terminals = rulerule.getTerminals();
                for (String terminal : terminals) {
                    uselessPrint.append(terminal).append(" | ");
                }
                uselessPrint.deleteCharAt(uselessPrint.lastIndexOf("|")+1);
                uselessPrint.deleteCharAt(uselessPrint.lastIndexOf("|")-1);
                uselessPrint.deleteCharAt(uselessPrint.lastIndexOf("|"));
                uselessPrint.append("\n");
            }
            System.out.println(uselessPrint);
        //премахване на правила с несамотни терминали (A → aB)

        for (Rule retrieved : grammarCopyRules) {
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            for (String terminal : ruleTerminals) {
                if ((charsCount(terminal.trim(), /*allTerminals*/grammarUtils.getAllTerminals()) == 1 && charsCount(terminal.trim(), /*allNonterminals*/grammarUtils.getAllNonterminals()) == 1)
                        && terminal.length() == 2) {
                    String nextNonterminal = grammarUtils.getNextNonterminal(grammarCopyRules, /*allNonterminals*/grammarUtils.getAllNonterminals());
                    ArrayList<String> newRuleTerminal = getNewRuleTerminal(terminal, /*allTerminals*/grammarUtils.getAllTerminals(), nextNonterminal);
                    grammarCopyRules.add(new Rule(nextNonterminal.trim(), newRuleTerminal));
                    String updatedTerminal = terminal.trim().replace(newRuleTerminal.getFirst(),nextNonterminal);
                    retrieved.getTerminals().remove(terminal.trim());
                    retrieved.getTerminals().add(updatedTerminal.trim());
                }
            }
        }
            System.out.println("Rules after term:");
            StringBuilder termPrint = new StringBuilder();
            for(Rule rulerule:grammarCopyRules){
                termPrint.append(rulerule.getNonterminals());
                termPrint.append(" → ");
                ArrayList<String> terminals = rulerule.getTerminals();
                for (String terminal : terminals) {
                    termPrint.append(terminal).append(" | ");
                }
                termPrint.deleteCharAt(termPrint.lastIndexOf("|")+1);
                termPrint.deleteCharAt(termPrint.lastIndexOf("|")-1);
                termPrint.deleteCharAt(termPrint.lastIndexOf("|"));
                termPrint.append("\n");
            }
            System.out.println(termPrint);
        //премахване на правила с 2+ нетерминали отдясно (A → BCD)
       Set<Rule> addRules = new LinkedHashSet<>();
        for (Rule retrieved : grammarCopyRules) {
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            for (String terminal : ruleTerminals) {
                if (charsCount(terminal, /*allNonterminals*/grammarUtils.getAllNonterminals()) == terminal.length() && terminal.length() > 2) {
                    String nextNonterminal1 =  grammarUtils.getNextNonterminal(grammarCopyRules, /*allNonterminals*/grammarUtils.getAllNonterminals());
                    char[] oldTerminalArr = terminal.toCharArray();
                    StringBuilder newTerminal = new StringBuilder();
                    //първите два нетерминала - BC
                    for (int i = 0; i < terminal.length() - 1; i++) {
                        newTerminal.append(oldTerminalArr[i]);
                    }
                    ArrayList<String> newRuleTerminal1 = new ArrayList<>();
                    for (int i = 0; i < newTerminal.length(); i++) {
                        newRuleTerminal1.add(String.valueOf(newTerminal.charAt(i)));
                    }
                    //ново правило  - F → BC
                    //grammarCopyRules.add(new Rule(nextNonterminal1, newRuleTerminal1));
                    addRules.add(new Rule(nextNonterminal1, newRuleTerminal1));
                    ArrayList<String> oldRuleNewTerminals = new ArrayList<>();
                    //нов терминал за оригиналното правило - FD
                    String oldRuleNewTerminalBuilder = nextNonterminal1 +
                            oldTerminalArr[2];
                    oldRuleNewTerminals.add(oldRuleNewTerminalBuilder);
                    retrieved.setTerminals(oldRuleNewTerminals);
                }
            }
        }
        grammarCopyRules.addAll(addRules);
            System.out.println("Rules after bin:");
            StringBuilder binPrint = new StringBuilder();
            for(Rule rulerule:grammarCopyRules){
                binPrint.append(rulerule.getNonterminals());
                binPrint.append(" → ");
                ArrayList<String> terminals = rulerule.getTerminals();
                for (String terminal : terminals) {
                    binPrint.append(terminal).append(" | ");
                }
                binPrint.deleteCharAt(binPrint.lastIndexOf("|")+1);
                binPrint.deleteCharAt(binPrint.lastIndexOf("|")-1);
                binPrint.deleteCharAt(binPrint.lastIndexOf("|"));
                binPrint.append("\n");
            }
            System.out.println(binPrint);

        cnfGrammar.setRules(grammarCopyRules);
        System.out.println("Chomskify grammar id: " + cnfGrammar.getId());
        grammarCommands.getGrammarSet().add(cnfGrammar);
        StringBuilder stringBuilder = new StringBuilder();
            for (Rule rule : cnfGrammar.getRules()) {
                String nonterminal = rule.getNonterminals();
                stringBuilder.append(nonterminal);

                stringBuilder.append(" → ");

                ArrayList<String> terminals = rule.getTerminals();
                for (String terminal : terminals) {
                    stringBuilder.append(terminal).append(" | ");
                }
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("|")+1);
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("|")-1);
                stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("|"));
                stringBuilder.append("\n");
            }
            fileHandler.getFileContent().append(stringBuilder);
    }else {System.out.println("Please open a file first");}
    }

    private static ArrayList<String> getNewRuleTerminal(String terminal, String[] allTerminals, String nextNonterminal) {
        char[] terminalCharArr = terminal.toCharArray();

        char terminalchar;
        ArrayList<String> terminalsArrList = new ArrayList<>(Arrays.asList(allTerminals));
        if(terminalsArrList.contains(String.valueOf(terminalCharArr[0]))){
             terminalchar = terminalCharArr[0];
        }
        else{
        terminalchar = terminalCharArr[1];}
        String newTerminal = String.valueOf(terminalchar);
        ArrayList<String> newRuleTerminal = new ArrayList<>();
        newRuleTerminal.add(newTerminal);
        return newRuleTerminal;
    }

    private boolean stringContains(ArrayList<String> arr,String value){
        boolean useful = false;
        for (String nonTerminal : arr) {
            if (arr.contains(value)) {
                useful = true;
                break;
            }
        }
     return useful;
    }

    private ArrayList<String> getNewCombinations(ArrayList<String> ruleTerminals1, String nonterminals, String[] allNonterminals){
        Set<String> newCombinations1 = new HashSet<>();
        ArrayList<String> allNonterminals1 = new ArrayList<>(List.of(allNonterminals));
        for(String terminal:ruleTerminals1){
            if(terminal.contains(nonterminals)){
                int frequency = countFrequency(terminal, grammarUtils.getAllNonterminals());
                String[] arr = terminal.split("");
                int broi = charsCount(terminal,arr);
                String[] newa = new String[broi];
                int ii=0;
                for (String s : arr) {
                    if (allNonterminals1.contains(s)) {
                        newa[ii++] = s;
                    }
                }
                System.out.println(Arrays.toString(newa));
                LinkedHashSet<String> resultLink = powerset(newa);
                Set<String> result = new LinkedHashSet<>(resultLink);
                for(String string:result){
                    String combined = String.join("",string);
                    newCombinations1.add(combined);
                }
            }
        }
        return new ArrayList<>(newCombinations1);
    }


private static int countFrequency(String terminal,String[] allNonterminals){
        int count = 0;
        ArrayList<String> nontermsArr = new ArrayList<>(List.of(allNonterminals));
        String[] terminalArr = terminal.split("");
        for(String term:terminalArr){
            if(nontermsArr.contains(term)){
                count++;
            }
        }
        return count;
}
    private static LinkedHashSet<String> powerset(String[] set) {

        //create the empty power set
        LinkedHashSet<String> power = new LinkedHashSet<>();

        //get the number of elements in the set
        int elements = set.length;

        //the number of members of a power set is 2^n
        int powerElements = (int) Math.pow(2,elements);

        //run a binary counter for the number of power elements
        for (int i = 0; i < powerElements; i++) {

            //convert the binary number to a string containing n digits
            String binary = intToBinary(i, elements);

            //create a new set
            LinkedHashSet<String> innerSet = new LinkedHashSet<>();

            //convert each digit in the current binary number to the corresponding element
            //in the given set
            for (int j = 0; j < binary.length(); j++) {
                if (binary.charAt(j) == '1')
                    innerSet.add(set[j]);
            }

            //add the new set to the power set
            String toAdd = String.join("",innerSet);
            power.add(toAdd);

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
