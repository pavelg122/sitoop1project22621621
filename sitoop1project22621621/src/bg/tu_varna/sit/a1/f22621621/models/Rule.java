package bg.tu_varna.sit.a1.f22621621.models;

import java.util.ArrayList;
import java.util.Objects;


/**
 * The type Rule.
 */
public class Rule {
    private String nonterminals;
    private ArrayList<String> terminals;

    /**
     * Instantiates a new Rule.
     *
     * @param nonterminals the nonterminals
     * @param terminals    the terminals
     */
    public Rule(String nonterminals, ArrayList<String> terminals) {
        this.nonterminals = nonterminals;
        this.terminals = terminals;
    }

    /**
     * Gets the nonterminals of the Rule.
     *
     * @return the nonterminals
     */
    public String getNonterminals() {
        return nonterminals;
    }

    /**
     * Gets the terminals of the Rule.
     *
     * @return the terminals
     */
    public ArrayList<String> getTerminals() {
        return terminals;
    }
    /**
     * Compares the Rule to another object.
     * @param o the other object
     * @return true or false - depending on if both objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(nonterminals, rule.nonterminals) && Objects.equals(terminals, rule.terminals);
    }

    /**
     * Creates hash code of the Rule using its nonterminals and terminals.
     *
     * @return hash code of the Rule
     */
    @Override
    public int hashCode() {
        return Objects.hash(nonterminals, terminals);
    }

    /**
     * Sets nonterminals of the Rule.
     *
     * @param nonterminals the nonterminals
     */
    public void setNonterminals(String nonterminals) {
        this.nonterminals = nonterminals;
    }

    /**
     * Sets terminals of the Rule.
     *
     * @param terminals the terminals
     */
    public void setTerminals(ArrayList<String> terminals) {
        this.terminals = terminals;
    }

    /**
     * Creates a deep Copy of the Rule.
     *
     * @return the rule copy
     */
    public Rule copy(){
        return new Rule(this.nonterminals,new ArrayList<>(this.terminals));
    }

    /**
     * Creates a String representation of the Rule.
     *
     * @return String - representation of the Rule
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nonterminals);
        sb.append(" → ");
        for (String terminal : terminals) {
            sb.append(terminal).append(" | ");
        }
        sb.deleteCharAt(sb.lastIndexOf("|")+1);
        sb.deleteCharAt(sb.lastIndexOf("|")-1);
        sb.deleteCharAt(sb.lastIndexOf("|"));
        sb.append("\n");
        return sb.toString();
    }
}
