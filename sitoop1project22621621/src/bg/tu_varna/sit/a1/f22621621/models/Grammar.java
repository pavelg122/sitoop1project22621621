package bg.tu_varna.sit.a1.f22621621.models;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Grammar{
    private final long id;
    private Set<Rule> rules = new LinkedHashSet<>();

    public Grammar() {
        this.id = generateUniqueID();
    }

    public long getId() {
        return id;
    }

    public Set<Rule> getRules() {
        return rules;
    }

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




    public void print() {
        int counter = 0;
        StringBuilder stringBuilder=  new StringBuilder();
        for(Rule rule:rules){
            counter++;
            stringBuilder.append("( ").append(counter).append(" ) ");
            String nonterminal =  rule.getNonterminals();
                stringBuilder.append(nonterminal);

            stringBuilder.append(" → ");
            ArrayList<String> terminals = rule.getTerminals();
            for(String terminal:terminals){
                stringBuilder.append(terminal).append(" | ");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf( "|"));
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }


    public void save(String fileName) {
try(PrintWriter printWriter = new PrintWriter(fileName)) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Rule rule : rules) {
        String nonterminal = rule.getNonterminals();
        stringBuilder.append(nonterminal);

        stringBuilder.append(" → ");
        ArrayList<String> terminals = rule.getTerminals();
        for (String terminal : terminals) {
            stringBuilder.append(terminal).append(" | ");
        }
        stringBuilder.append("\n");
    }
    printWriter.write(stringBuilder.toString());
}catch (FileNotFoundException e) {System.out.println(e.getMessage());}
    }


    public void addRule(String rule) {
        String[] ruleParts = rule.split(" → ",2);
            String nonterminals = String.valueOf(ruleParts[0]);
        String[] terminals = ruleParts[1].split(" \\| ");
        ArrayList<String> terminals1 = new ArrayList<>(Arrays.asList(terminals));
      rules.add(new Rule(nonterminals,terminals1));
    }


    public void removeRule(int number) throws Exception {
        if(number<=rules.size()){
            int counter=0;
            for (Rule rule : rules) {
                counter++;
                if (counter == number) {
                    rules.remove(rule);
                    break;
                }
            }
        }else throw new Exception("Index of rule doesn't exist");
    }
    public static long generateUniqueID(){
        Random random = new Random();
        AtomicLong atomicLong = new AtomicLong();

        long id = atomicLong.incrementAndGet();

        return Math.abs(random.nextLong()) + id;
    }
}
