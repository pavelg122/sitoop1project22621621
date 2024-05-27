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
}
