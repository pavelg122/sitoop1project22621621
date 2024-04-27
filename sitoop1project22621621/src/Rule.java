import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Rule {
    private String[] nonterminals;
    private String[] terminals;

    public Rule(String[] nonterminals, String[] terminals) {
        this.nonterminals = nonterminals;
        this.terminals = terminals;
    }

    public String[] getNonterminals() {
        return nonterminals;
    }

    public String[] getTerminals() {
        return terminals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.deepEquals(nonterminals, rule.nonterminals) && Objects.deepEquals(terminals, rule.terminals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(nonterminals), Arrays.hashCode(terminals));
    }

    public void setNonterminals(String[] nonterminals) {
        this.nonterminals = nonterminals;
    }

    public void setTerminals(String[] terminals) {
        this.terminals = terminals;
    }
}
