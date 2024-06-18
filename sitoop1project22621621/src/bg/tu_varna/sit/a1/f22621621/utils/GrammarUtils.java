package bg.tu_varna.sit.a1.f22621621.utils;

import bg.tu_varna.sit.a1.f22621621.models.Rule;

import java.util.*;

/**
 * The type GrammarUtils. It containts methods that are used in the implementation of
 * multiple commands and contains the necessary String arrays that define the terminals and nonterminals
 * that are used in the application.
 */
public class GrammarUtils {
    private String[] allTerminals = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","s","t","u","v","w","x","y","z",
            "0","1","2","3","4","5","6","7","8","9"};
    private String[] allNonterminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    /**
     * Instantiates a new GrammarUtils object.
     */
    public GrammarUtils() {
    }

    /**
     * Returns the String array of all terminals. The terminals are all lowercase letters from a-z
     * and the numbers from 0-9.
     *
     * @return String array that contains all terminals.
     */
    public String[] getAllTerminals() {
        return allTerminals;
    }

    /**
     * Returns the String array of all nonterminals. The nonterminals are all uppercase letters from A-Z.
     *
     * @return String array that contains all nonterminals.
     */
    public String[] getAllNonterminals() {
        return allNonterminals;
    }

    /**
     * Returns the next free nonterminal String from all nonterminals.
     * Creates a Map that contains all nonterminals and if they are already used.
     * If a Grammar already has Rules that contain the nonterminals A,B,C and D the next free nonterminal is E.
     * @param rules           all rules of the grammar
     * @param allNonterminals all nonterminals
     * @return String - the next free nonterminal which is an uppercase letter
     */
    public String getNextNonterminal(Set<Rule> rules, String[] allNonterminals){
        String nextNonterminal = null;
        Map<String, Boolean> usedTerminals = new HashMap<>();
        for(String letter:allNonterminals){
            usedTerminals.put(letter,false);
        }
        for(Rule rule:rules){
            usedTerminals.put(rule.getNonterminals(),true);
        }
        for(Map.Entry<String,Boolean> entry:usedTerminals.entrySet()){
            if(entry.getValue().equals(false)){
                nextNonterminal = entry.getKey();
                break;
            }
        }
        return nextNonterminal;
    }

    /**
     * Counts the frequency of a pattern String in another text String .
     *
     * @param pat the pattern
     * @param txt the text
     * @return int - the number of times the pattern is contained in the text
     */
    public static int countFreq(String pat, String txt)
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

    /**
     * Counts the number of times elements of a characters String array
     * are present in a terminal String.
     *
     * @param terminal   the terminal String
     * @param characters the characters String array
     * @return int - the number of occurrences
     */
    public int charsCount(String terminal,String[] characters){
        int count=0;
        char[] terminalCharArr = terminal.toCharArray();
        ArrayList<String> charsArrList = new ArrayList<>(Arrays.asList(characters));
        for(char ch:terminalCharArr){
            if(charsArrList.contains(String.valueOf(ch)))
            {count++;}
        }
        return count;
    }

    /**
     * Returns the power set from the given set by using a binary counter
     * Example: S = {a,b,c}
     * P(S) = {[], [c], [b], [b, c], [a], [a, c], [a, b], [a, b, c]}
     * @param set String[]
     * @return LinkedHashSet
     */
    public static LinkedHashSet<String> powerset(String[] set) {

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
    public static String intToBinary(int binary, int digits) {

        String temp = Integer.toBinaryString(binary);
        int foundDigits = temp.length();
        String returner = temp;
        for (int i = foundDigits; i < digits; i++) {
            returner = "0" + returner;
        }

        return returner;
    }

    /**
     * Retuns a String ArrayList that contains all new combinations that are derived of a
     * terminal String using powerset of all nonterminals in the terminal. Removes the combinations
     * that contain the removedEmpty nonterminal. Then it combines the nonterminals and terminals that are contained
     * in the terminal in their normal order.
     *
     * @param terminal        the terminal String
     * @param removedEmpty    a nonterminal String of a Rule that contains an empty terminal
     * @param allNonterminals the allNonterminals String set
     * @return the String ArrayList - that contains the combinations
     */
    public ArrayList<String> getNewCombinations(String terminal,String removedEmpty, String[] allNonterminals){
        ArrayList<String> allNonterminals1 = new ArrayList<>(List.of(allNonterminals));
        String[] arr = terminal.split("");
        int broi = charsCount(terminal,getAllNonterminals());
        int broiterm = charsCount(terminal,getAllTerminals());
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
        LinkedHashSet<String> resultLink = GrammarUtils.powerset(nonTerms);
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

    /**
     * Checks if a terminal String is in Chomsky Normal Form. A terminal is in normal form
     * when the terminal is either a single lowercase letter or two uppercase letters.
     *
     * @param terminal the terminal String
     * @return boolean - true or false
     */
    public boolean isTerminalInNormalForm(String terminal){
        boolean normalForm = false;
        if(terminal.trim().length() == 1 && charsCount(terminal,getAllTerminals()) == 1){normalForm=true;}
        else if(terminal.trim().length() == 2 && charsCount(terminal,getAllNonterminals())==2){normalForm = true;}
        return normalForm;
    }

    /**
     * Checks if all terminals of a Rule are in Chomsky Normal Form.
     * Returns true if the count of the terminals in normal form are equal
     * to the count of all terminals of the Rule.
     *
     * @param terminals the terminals of the rule
     * @return boolean - true or false
     */
    public boolean isRuleTerminalsInNormalForm(ArrayList<String> terminals){
        int normalFormCount = 0;
        for(String terminal:terminals){
            if(isTerminalInNormalForm(terminal)){normalFormCount++;}
        }
        return terminals.size() == normalFormCount;
    }

    /**
     * Returns the number of the nonterminal from the nonterminalsNumbered Map.
     * The map contains all nonterminals of a Grammar with their index
     * numbers. The nonterminal of the first Rule of the Grammar has the number 1, the nonterminal of the second
     * Rule of the Grammar has the number 2 and so on.
     *
     * @param nonterminal          the nonterminal
     * @param nonterminalsNumbered the nonterminalsNumbered Map
     * @return the number of the nonterminal String
     */
    public int getNonterminalKeyFromMap(String nonterminal, Map<Integer, String> nonterminalsNumbered){
        for(Map.Entry<Integer,String> entry2 : nonterminalsNumbered.entrySet()){
            if(entry2.getValue().equals(nonterminal.trim())){
                return entry2.getKey();
            }
        }
        return -1;
    }

    /**
     * Appends the String representation of all Rules of a Set and prints it out.
     * Used to display each step of the algorithm that transforms a Grammar in Chomsky Normal Form.
     * @param rules the Rules Set
     */
    public void printStepRules(Set<Rule> rules){
        StringBuilder print = new StringBuilder();
        for (Rule rule: rules) {
            print.append(rule.toString());
        }
        System.out.println(print);
    }
}
