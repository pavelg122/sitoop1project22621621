import java.util.*;

public class UnionCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    public UnionCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        Grammar grammar1 = grammarCommands.getGrammar(Long.parseLong(input[0]));
        Grammar grammar2 = grammarCommands.getGrammar(Long.parseLong(input[1]));
        Grammar union = new Grammar();
        Set<Rule> unionRules = new HashSet<>();
        Set<Rule> grammar1Rules = grammar1.getRules();
        Set<Rule> grammar2Rules = grammar2.getRules();
        /*for(Rule rule1:grammar1Rules){
            for(Rule rule2:grammar2Rules){
                if(!rule1.equals(rule2)){
                    if(Arrays.equals(rule1.getNonterminals(), rule2.getNonterminals())){
                        String[] rule1Terminals = rule1.getTerminals();
                        String[] rule2Terminals = rule2.getTerminals();
                        int r1len = rule1Terminals.length;
                        int r2len = rule2Terminals.length;
                       String[] unionTerminals = new String[r1len + r2len];
                        int count=0;
                        System.arraycopy(rule1Terminals,0,unionTerminals,0,r1len);
                        System.arraycopy(rule2Terminals,0,unionTerminals,r1len,r2len);
                        /*for(int i = 0; i < rule1Terminals.length; i++) {
                            unionTerminals[i] = rule1Terminals[i];
                            count++;
                        }
                        for (String rule2Terminal : rule2Terminals) {
                            unionTerminals[count++] = rule2Terminal;
                        }*/
                        /*
                        unionRules.add(new Rule(rule1.getNonterminals(), unionTerminals));
                    }
                    else
                    {unionRules.add(rule1);
                    unionRules.add(rule2);}
                }else {unionRules.add(rule1);}
            }
        }*/

        Iterator<Rule> iterator1 = grammar1Rules.iterator();
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();

        }
        Iterator<Rule> iterator2 = grammar2Rules.iterator();
        Rule retrieved2 = null;
        if (iterator2.hasNext()) {
            retrieved2 = iterator2.next();

        }
        String rule1Nonterminals;
        assert retrieved1 != null;
        rule1Nonterminals = retrieved1.getNonterminals();
        assert retrieved2 != null;
        String rule2Nonterminals = retrieved2.getNonterminals();
        /*int r1len = rule1Nonterminals.length;
        int r2len = rule2Nonterminals.length;*/
        ArrayList<String> unionTerminals = new ArrayList<>();
        unionTerminals.add(rule1Nonterminals);
        unionTerminals.add(rule2Nonterminals);
        /*System.arraycopy(rule1Nonterminals, 0, unionTerminals, 0, r1len);
        System.arraycopy(rule2Nonterminals, 0, unionTerminals, r1len, r2len);*/
        String unionNonterminals = "S";
        unionRules.add(new Rule(unionNonterminals, unionTerminals));
        unionRules.addAll(grammar1Rules);
        unionRules.addAll(grammar2Rules);
        union.setRules(unionRules);
        System.out.println("union grammar id: " + union.getId());
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule rule : unionRules) {
            String nonterminal  = rule.getNonterminals();
                stringBuilder.append(nonterminal);
            stringBuilder.append(" → ");
            ArrayList<String> terminals = rule.getTerminals();
            for (String terminal : terminals) {
                stringBuilder.append(terminal).append(" | ");
            }
            stringBuilder.append("\n");
        }
        fileHandler.getFileContent().append(stringBuilder);
        grammarCommands.getGrammarSet().add(union);
    }
}
