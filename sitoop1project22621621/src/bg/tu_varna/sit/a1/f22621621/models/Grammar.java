package bg.tu_varna.sit.a1.f22621621.models;

import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidRuleNumberException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The type Grammar. It's one of the main elements of the application and is defined by an ID and a Set of Rules.
 *
 */
public class Grammar{
    private final long id;
    private Set<Rule> rules = new LinkedHashSet<>();

    /**
     * Instantiates a new Grammar.
     */
    public Grammar() {
        this.id = generateUniqueID();
    }

    /**
     * Gets ID of the Grammar.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets rules of the Grammar.
     *
     * @return the rules
     */
    public Set<Rule> getRules() {
        return rules;
    }

    /**
     * Sets rules of the Grammar.
     *
     * @param rules the rules
     */
    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grammar grammar = (Grammar) o;
        return id == grammar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    /**
     * Prints the Grammar.
     */
    public void print() {
        int counter = 0;
        StringBuilder stringBuilder=  new StringBuilder();
        for(Rule rule:rules){
            counter++;
            stringBuilder.append("( ").append(counter).append(" ) ");
            stringBuilder.append(rule.toString());
        }
        System.out.println(stringBuilder);
    }


    /**
     * Saves a Grammar in a file. Creates a StringBuilder that contains the String representation
     * of the Grammar and writes it in the file using a PrintWriter. A FileNotFoundException is caught
     * in case the method doesn't find the file.
     * @param fileName the file name
     */
    public void save(String fileName) {
try(PrintWriter printWriter = new PrintWriter(fileName)) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Rule rule : rules) {
        stringBuilder.append(rule.toString());
    }
    printWriter.write(stringBuilder.toString());
}catch (FileNotFoundException e) {System.out.println(e.getMessage());}
    }


    /**
     * Adds a Rule to the Grammar. Takes a String that contains a Rule and transforms it
     * into a Rule object and then tries adding it to the Rule Set of the Grammar.
     *
     * @param rule the String that contains the Rule
     */
    public void addRule(String rule) {
       try {
           String[] ruleParts = rule.split(" → ", 2);
           String nonterminals = String.valueOf(ruleParts[0]);
           String[] terminals = ruleParts[1].split(" \\| ");
           ArrayList<String> terminals1 = new ArrayList<>(Arrays.asList(terminals));
           rules.add(new Rule(nonterminals, terminals1));
       }catch(Exception e) {System.out.println(e.getMessage());}
    }


    /**
     * Removes a Rule from the Grammar. It iterates over each rule and increments a counter.
     * After finding the number it removes the current rule that the iterator is on. If the number
     * is negative or larger than the amount of Rules in the Grammar it throws an exception and displays an error
     * message.
     * @param number the number of the Rule to be removed
     * @throws InvalidRuleNumberException if the number cannot be an index of a Rule
     */
    public void removeRule(int number) throws InvalidRuleNumberException {
        if(number<=rules.size() && number>0){
            int counter=0;
            for (Rule rule : rules) {
                counter++;
                if (counter == number) {
                    rules.remove(rule);
                    break;
                }
            }
        }else throw new InvalidRuleNumberException("Index of rule doesn't exist");
    }

    /**
     * Generates a unique ID for the Grammar of type long using Random and AtomicLong.
     *
     * @return long - the unique ID
     */
    public static long generateUniqueID(){
        Random random = new Random();
        AtomicLong atomicLong = new AtomicLong();

        long id = atomicLong.incrementAndGet();

        return Math.abs(random.nextLong()) + id;
    }
    /**
     * Creates a String representation of the Grammar that contains all Rules of the Grammar.
     *
     * @return String - representation of the Grammar
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule rule : rules) {stringBuilder.append(rule.toString());}
        return stringBuilder.toString();
    }
}
