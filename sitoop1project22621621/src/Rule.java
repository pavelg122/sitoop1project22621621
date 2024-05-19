import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Rule {
    private String nonterminals;
    private ArrayList<String> terminals;

    public Rule(String nonterminals, ArrayList<String> terminals) {
        this.nonterminals = nonterminals;
        this.terminals = terminals;
    }

    public String getNonterminals() {
        return nonterminals;
    }

    public ArrayList<String> getTerminals() {
        return terminals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(nonterminals, rule.nonterminals) && Objects.equals(terminals, rule.terminals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nonterminals, terminals);
    }

    public void setNonterminals(String nonterminals) {
        this.nonterminals = nonterminals;
    }

    public void setTerminals(ArrayList<String> terminals) {
        this.terminals = terminals;
    }
    public Rule copy(){
        return new Rule(this.nonterminals,new ArrayList<>(this.terminals));
    }
}
