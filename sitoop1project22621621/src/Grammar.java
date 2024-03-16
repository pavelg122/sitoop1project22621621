import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Grammar implements GrammarCommands{
    int id;
    private Set<Rule> rules = new HashSet<>();

    public Grammar(int id) {
        this.id = id;
    }

    public int getId() {
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
    public void print(int id) {

    }

    @Override
    public void save(int id, String fileName) {

    }

    @Override
    public void addRule(int id, String rule) {
        String[] ruleParts = rule.split("→");
        String[] terminals = ruleParts[1].split("|");
      rules.add(new Rule(ruleParts[0],terminals));
      /*System.out.println(ruleParts[0]);
      for(String terminal:terminals){
          System.out.println(terminal);
      }*/
    }

    @Override
    public void removeRule(int id, int number) {

    }
}
