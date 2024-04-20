import java.util.ArrayList;


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
}
