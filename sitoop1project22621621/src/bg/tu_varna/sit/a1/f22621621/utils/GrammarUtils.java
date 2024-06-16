package bg.tu_varna.sit.a1.f22621621.utils;

import bg.tu_varna.sit.a1.f22621621.models.Rule;

import java.util.*;

public class GrammarUtils {
    private String[] allTerminals = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","s","t","u","v","w","x","y","z",
            "0","1","2","3","4","5","6","7","8","9"};
    private String[] allNonterminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public GrammarUtils() {
    }

    public String[] getAllTerminals() {
        return allTerminals;
    }

    public String[] getAllNonterminals() {
        return allNonterminals;
    }
    public String getNextNonterminal(Set<Rule> rules, String[] allNonterminals){
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
    public boolean isTerminalInNormalForm(String terminal){
        boolean normalForm = false;
        if(terminal.trim().length() == 1 && charsCount(terminal,getAllTerminals()) == 1){normalForm=true;}
        else if(terminal.trim().length() == 2 && charsCount(terminal,getAllNonterminals())==2){normalForm = true;}
        return normalForm;
    }
    public boolean isRuleTerminalsInNormalForm(ArrayList<String> terminals){
        int normalFormCount = 0;
        for(String terminal:terminals){
            if(isTerminalInNormalForm(terminal)){normalFormCount++;}
        }
        return terminals.size() == normalFormCount;
    }
    public int getNonterminalKeyFromMap(String nonterminal, Map<Integer, String> nonterminalsNumbered){
        for(Map.Entry<Integer,String> entry2 : nonterminalsNumbered.entrySet()){
            if(entry2.getValue().equals(nonterminal.trim())){
                return entry2.getKey();
            }
        }
        return -1;
    }
    public void printStepRules(Set<Rule> rules){
        StringBuilder print = new StringBuilder();
        for (Rule rule: rules) {
            print.append(rule.toString());
        }
        System.out.println(print);
    }
}
