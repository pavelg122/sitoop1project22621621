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
        Set<Rule> removedEmptyRules = new LinkedHashSet<>();
        String epsilon = "ε";
        while (iterator2.hasNext()) {
            Rule retrieved = iterator2.next();
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            String ruleNonterminals = retrieved.getNonterminals();
           for(String term:ruleTerminals){
               if (term.trim().equals(epsilon)
                       && !(ruleNonterminals.equals(startNonterminals))) {
                   removedEmptyRules.add(retrieved);
               }
           }
        }
        for(Rule remove:removedEmptyRules){
            for(Rule rule:grammarCopyRules){
                for(String terminal:rule.getTerminals()){
                    if(terminal.trim().equals(remove.getNonterminals())){
                        removedEmptyRules.add(rule);
                    }
                }
            }
        }
        for(Rule rule:removedEmptyRules){
            System.out.println(rule.getNonterminals() + rule.getTerminals().toString());
        }
        for (Rule removed : removedEmptyRules) {
            iterator2 = grammarCopyRules.iterator();
            while (iterator2.hasNext()) {
                Rule retrieved = iterator2.next();
                String ruleNonterminals = retrieved.getNonterminals();
                //ArrayList<String> ruleTerminals1 = retrieved.getTerminals();
                for(String terminal: retrieved.getTerminals()){
                    if (terminal.contains(removed.getNonterminals())) {
                        Set<String> newRuleTerminalsSet = new LinkedHashSet<>(retrieved.getTerminals());
                        ArrayList<String> newCombinations = getNewCombinations(terminal, removed.getNonterminals(),grammarUtils.getAllNonterminals());
                        newRuleTerminalsSet.addAll(newCombinations);
                        newRuleTerminalsSet.removeIf(newTerm -> newTerm.contains(epsilon));
                        ArrayList<String> updatedTerminals = new ArrayList<>(newRuleTerminalsSet);
                        retrieved.setTerminals(updatedTerminals);
                    }
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
        Set<Rule> unitRules = new HashSet<>();
        Set<Rule> noUnitRules = new HashSet<>();

        ArrayList<String> allNonterminals1 = new ArrayList<>(Arrays.asList(grammarUtils.getAllNonterminals()));
        while (iterator3.hasNext()) {
            Rule retrieved = iterator3.next();
            String nonterminal = retrieved.getNonterminals();
            ArrayList<String> unitTerminals = new ArrayList<>();
            ArrayList<String> noUnitTerminals = new ArrayList<>();
            for(String term: retrieved.getTerminals()){
              if(term.trim().length()==1&&allNonterminals1.contains(term)){
                  unitTerminals.add(term);
              }else {noUnitTerminals.add(term);}
            }
            if(!unitTerminals.isEmpty()){unitRules.add(new Rule(nonterminal,unitTerminals));}
            if(!noUnitTerminals.isEmpty()){noUnitRules.add(new Rule(nonterminal,noUnitTerminals));}
        }

            for(Rule unitRule:unitRules){
                for(Rule oldRule:grammarCopyRules){
                    Set<String> oldRuleTerminalsCopy = new LinkedHashSet<>(oldRule.getTerminals());
                    if(oldRule.getNonterminals().trim().equals(unitRule.getNonterminals().trim())){
                        for(String unitRuleTerm:unitRule.getTerminals()){
                            oldRuleTerminalsCopy.remove(unitRuleTerm);
                            for(Rule noUnitRule:noUnitRules){
                                if(noUnitRule.getNonterminals().equals(unitRuleTerm)){
                                    oldRuleTerminalsCopy.addAll(noUnitRule.getTerminals());
                                }
                            }
                        }
                    }
                    ArrayList<String> updatedTerminals = new ArrayList<>(oldRuleTerminalsCopy);
                    oldRule.setTerminals(updatedTerminals);
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

            /*
            ArrayList<String> allNonterminals = new ArrayList<>(List.of(grammarUtils.getAllNonterminals()));
            Set<String> uniqueNonterminals = new LinkedHashSet<>();
            Map<String,Boolean> uniqueNonterminals1 = new LinkedHashMap<>();
            for(Rule rule:grammarCopyRules){
                uniqueNonterminals1.put(rule.getNonterminals(),false);
            }

            for(Rule rule:grammarCopyRules){
            for(Map.Entry<String,Boolean> entry:uniqueNonterminals1.entrySet()){
                    if(rule.getTerminals().contains(entry.getKey())){
                        entry.setValue(true);
                    }
                }
            }
            for(Map.Entry<String,Boolean> entry:uniqueNonterminals1.entrySet()){
                System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
            }
            for(Map.Entry<String,Boolean> entry:uniqueNonterminals1.entrySet()){
                if(entry.getValue().equals(false)){
                grammarCopyRules.removeIf(rule1 -> rule1.getNonterminals().equals(entry.getKey()));
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
            System.out.println(uselessPrint);*/
        //премахване на правила с несамотни терминали (A → aB)
            ArrayList<String> allTerminals = new ArrayList<>(Arrays.asList(grammarUtils.getAllTerminals()));
            Set<String> termSet = new LinkedHashSet<>();
        for (Rule retrieved : grammarCopyRules) {
            ArrayList<String> ruleTerminals = retrieved.getTerminals();
            for (String terminal : ruleTerminals) {
                if (charsCount(terminal.trim(), grammarUtils.getAllTerminals()) >= 1 && terminal.trim().length() >= 2) {
                    String[] termArr = terminal.split("");
                    for(String term:termArr){
                        if(allTerminals.contains(term)){
                            termSet.add(term);
                        }
                    }
                }
            }
        }

        Set<Rule> newTermRules = new LinkedHashSet<>();
        for(String term:termSet){
            String nonterminal = grammarUtils.getNextNonterminal(grammarCopyRules, grammarUtils.getAllNonterminals());
            ArrayList<String> newTermRuleTerminals = new ArrayList<>();
            newTermRuleTerminals.add(term);
            newTermRules.add(new Rule(nonterminal,newTermRuleTerminals));
            grammarCopyRules.add(new Rule(nonterminal,newTermRuleTerminals));
        }
        for(Rule newTermRule:newTermRules){
            String term = newTermRule.getTerminals().getFirst();
            String nonterminal = newTermRule.getNonterminals();
            ArrayList<String> termsToAdd = new ArrayList<>();
            ArrayList<String> termsToRemove = new ArrayList<>();
            for(Rule rule:grammarCopyRules){
                    Set<String> terminalsCopy = new LinkedHashSet<>(rule.getTerminals());
                    for(String terminalCopy:terminalsCopy){
                        if(terminalCopy.contains(term)&&terminalCopy.length()>=2&&charsCount(terminalCopy,grammarUtils.getAllNonterminals())>0){
                            String updatedTerm = terminalCopy.replace(term,nonterminal);
                            termsToRemove.add(terminalCopy);
                            termsToAdd.add(updatedTerm);
                        }
                    }
                    termsToRemove.forEach(terminalsCopy::remove);
                    terminalsCopy.addAll(termsToAdd);
                    ArrayList<String> updatedTerminals1 = new ArrayList<>(terminalsCopy);
                    rule.setTerminals(updatedTerminals1);

            }
        }
        Set<Rule> rulesToAdd = new LinkedHashSet<>();
            //корекция на новите правила
        System.out.println("newtermrules");
        for(Rule newr:newTermRules){
            System.out.println(newr.getNonterminals() + newr.getTerminals());
            for(Rule rule:grammarCopyRules){
                if(rule.getNonterminals().equals(newr.getNonterminals())){
                    rule.setTerminals(newr.getTerminals());
                }
                else {
                    ArrayList<String> termsRemove = new ArrayList<>();
                    ArrayList<String> termsAdd = new ArrayList<>();
                    Set<String> termsCopy = new LinkedHashSet<>(rule.getTerminals());
                    for(String term:rule.getTerminals()){
                        if(term.contains(newr.getTerminals().getFirst())&&
                                countFreq(newr.getTerminals().getFirst(), term.trim())==term.trim().length()&&term.length()>=2){
                            String nonterminal = grammarUtils.getNextNonterminal(grammarCopyRules, grammarUtils.getAllNonterminals());
                           String updated = term.replace(newr.getTerminals().getFirst(),nonterminal);
                           termsRemove.add(term);
                           termsAdd.add(updated);
                           ArrayList<String> newRuleTerms = new ArrayList<>();
                           newRuleTerms.add(newr.getTerminals().getFirst());
                           rulesToAdd.add(new Rule(nonterminal,newRuleTerms));
                           //grammarCopyRules.add(new Rule(nonterminal,newRuleTerms));
                        }
                    }
                    if(!termsRemove.isEmpty()&&!termsAdd.isEmpty()){
                        termsRemove.forEach(termsCopy::remove);
                        termsCopy.addAll(termsAdd);
                        ArrayList<String> updatedTerms = new ArrayList<>(termsCopy);
                        rule.setTerminals(updatedTerms);
                    }

                }
            }
            grammarCopyRules.addAll(rulesToAdd);
        }

        //grammarCopyRules.addAll(newTermRules);
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

    private ArrayList<String> getNewCombinations(String terminal,String removedEmpty, String[] allNonterminals){
        ArrayList<String> allNonterminals1 = new ArrayList<>(List.of(allNonterminals));

                //int frequency = countFrequency(terminal, grammarUtils.getAllNonterminals());
                String[] arr = terminal.split("");
                int broi = charsCount(terminal,grammarUtils.getAllNonterminals());
                int broiterm = charsCount(terminal,grammarUtils.getAllTerminals());
                String[] nonTerms = new String[broi];
                String[] terms = new String[broiterm];
                int ii=0;
                int it=0;
                for (String s : arr) {
                    if (allNonterminals1.contains(s)) {
                        nonTerms[ii++] = s;
                    }
                    else { if(broiterm>0){terms[it++]=s;}}
                }
               // System.out.println(Arrays.toString(nonTerms));
                LinkedHashSet<String> resultLink = powerset(nonTerms);
                Set<String> toRemove = new HashSet<>();
                for(String powerElement:resultLink){
                if(powerElement.contains(removedEmpty)){toRemove.add(powerElement);}
                }
                resultLink.removeAll(toRemove);
                if(terms.length==0){resultLink.removeFirst();}
                Set<String> result = new LinkedHashSet<>(resultLink);
                Set<String> newCombinations1 = new HashSet<>();
                for(String string:result){
                    String combined;
                    String remainingTerminals = String.join("",terms);
                    if(terms.length == 0){combined = String.join("",string);}
                    else {
                        if(arr[0].equals(terms[0])&& !remainingTerminals.isEmpty()){
                            combined = String.join("",remainingTerminals,string);
                        } else combined = String.join("",string,remainingTerminals);
                    }
                    newCombinations1.add(combined);
                }
        if(terminal.trim().length()>2){
            newCombinations1.removeIf(moreRemove -> moreRemove.trim().length() == 1);
        }

        return new ArrayList<>(newCombinations1);
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
    static int countFreq(String pat, String txt)
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
}
