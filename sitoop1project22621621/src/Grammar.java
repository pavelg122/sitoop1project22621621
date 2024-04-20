import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Grammar{
    private long id;
    private Set<Rule> rules = new HashSet<>();

    public Grammar() {
        this.id = generateUniqueID();
    }

    public long getId() {
        return id;
    }

    public Set<Rule> getRules() {
        return rules;
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
        StringBuilder stringBuilder=  new StringBuilder();
        for(Rule rule:rules){
            for(String nonterminal: rule.getNonterminals()){
                stringBuilder.append(nonterminal);
            }
            stringBuilder.append(" → ");
            String[] terminals = rule.getTerminals();
            for(String terminal:terminals){
                stringBuilder.append(terminal).append(" | ");
            }
        }
        System.out.println(stringBuilder.toString());
    }


    public void save(String fileName) {

    }


    public void addRule(String rule) {
        String[] ruleParts = rule.split("→",2);
        String[] nonterminals = new String[100];
        for (int i = 0; i < ruleParts[0].length(); i++) {
            nonterminals[i] = String.valueOf(ruleParts[0].charAt(i));
        }
        String[] terminals = ruleParts[1].split("|");
      rules.add(new Rule(nonterminals,terminals));
        for(String nonterminal:nonterminals){
            System.out.println(nonterminal);
        }
      for(String terminal:terminals){
          System.out.println(terminal);
      }
    }


    public void removeRule(int number) throws Exception {
        if(number<rules.size()){
            int counter=1;
        for(Rule rule:rules){
            if(number==counter){
                rules.remove(rule);
            }
            counter++;
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
