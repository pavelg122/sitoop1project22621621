import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Grammar implements GrammarCommands{
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

    @Override
    public void list() {

    }

    @Override
    public void print(long id) {

    }

    @Override
    public void save(long id, String fileName) {

    }

    @Override
    public void addRule(long id, String rule) {
        String[] ruleParts = rule.split("→");
        String[] terminals = ruleParts[1].split("|");
      rules.add(new Rule(ruleParts[0],terminals));
      /*System.out.println(ruleParts[0]);
      for(String terminal:terminals){
          System.out.println(terminal);
      }*/
    }

    @Override
    public void removeRule(long id, int number) throws Exception {
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
