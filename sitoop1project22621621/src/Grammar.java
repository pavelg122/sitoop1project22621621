import java.util.Objects;
import java.util.Set;

public class Grammar {
    int id;
    private Set<Rules> rules;

    public Grammar(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Set<Rules> getRules() {
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
}
