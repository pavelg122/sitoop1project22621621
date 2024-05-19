import java.util.*;

public class UnionCommand implements Command{
    private GrammarCommands grammarCommands;
    private FileHandler fileHandler;
    String[] allNonterminals = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public UnionCommand(GrammarCommands grammarCommands,FileHandler fileHandler) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
    }

    @Override
    public void invoke(String[] input) throws Exception {
        if(fileHandler.isFileOpen()) {
            Grammar grammar1 = grammarCommands.getGrammar(Long.parseLong(input[0]));
            Grammar grammar2 = grammarCommands.getGrammar(Long.parseLong(input[1]));
            Grammar union = new Grammar();
            Set<Rule> unionRules = new LinkedHashSet<>();
            Set<Rule> grammar1Rules = grammar1.getRules();
            Set<Rule> grammar2Rules = grammar2.getRules();

            Set<Rule> grammar1CopyRules = new LinkedHashSet<>();
            Set<Rule> grammar2CopyRules = new LinkedHashSet<>();
            for(Rule rule:grammar1Rules){
                grammar1CopyRules.add(rule.copy());
            }
            for(Rule rule:grammar2Rules){
                grammar2CopyRules.add(rule.copy());
            }
            Set<String> commonNonterminals = getCommonNonterminals(grammar1Rules, grammar2Rules);
            for(Rule rule:grammar1CopyRules){
                for(String common:commonNonterminals){
                    ArrayList<String> updatedTerminals1 = new ArrayList<>();
                    if(rule.getNonterminals().equals(common)){rule.setNonterminals(rule.getNonterminals()+"1");}
                    for(String terminal: rule.getTerminals()){
                        if(terminal.contains(common)){
                            String updated = terminal.replace(common,common+"1");
                            updatedTerminals1.add(updated);
                        }else updatedTerminals1.add(terminal);
                    }
                    rule.setTerminals(updatedTerminals1);
                }
            }

            for(Rule rule:grammar2CopyRules){
                for(String common:commonNonterminals){
                    ArrayList<String> updatedTerminals2 = new ArrayList<>();
                    if(rule.getNonterminals().equals(common)){rule.setNonterminals(rule.getNonterminals()+"2");}
                    for(String terminal: rule.getTerminals()){
                        if(terminal.contains(common)){
                            String updated = terminal.replace(common,common+"2");
                            updatedTerminals2.add(updated);
                        }else updatedTerminals2.add(terminal);
                    }
                    rule.setTerminals(updatedTerminals2);
                }
            }

            Set<Rule> allRules = new LinkedHashSet<>();
            allRules.addAll(grammar1CopyRules);
            allRules.addAll(grammar2CopyRules);

            Iterator<Rule> iterator1 = grammar1CopyRules.iterator();
            Rule retrieved1 = null;
            if (iterator1.hasNext()) {
                retrieved1 = iterator1.next();

            }
            Iterator<Rule> iterator2 = grammar2CopyRules.iterator();
            Rule retrieved2 = null;
            if (iterator2.hasNext()) {
                retrieved2 = iterator2.next();

            }
            String rule1Nonterminals;
            assert retrieved1 != null;
            rule1Nonterminals = retrieved1.getNonterminals();
            assert retrieved2 != null;
            String rule2Nonterminals = retrieved2.getNonterminals();

            ArrayList<String> unionTerminals = new ArrayList<>();
            unionTerminals.add(rule1Nonterminals);
            unionTerminals.add(rule2Nonterminals);

            String unionNonterminals = getNextNonterminal(allRules,allNonterminals);
            unionRules.add(new Rule(unionNonterminals, unionTerminals));
            unionRules.addAll(grammar1CopyRules);
            unionRules.addAll(grammar2CopyRules);
            union.setRules(unionRules);

            System.out.println("union grammar id: " + union.getId());
            StringBuilder stringBuilder = new StringBuilder();
            for (Rule rule : unionRules) {
                String nonterminal = rule.getNonterminals();
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
        }else System.out.println("Please open a file first.");
    }
    private String getNextNonterminal(Set<Rule> rules,String[] allNonterminals){
        String nextNonterminal = null;
        Map<String, Boolean> freeTerminals = new HashMap<>();
        for(String letter:allNonterminals){
            freeTerminals.put(letter,false);
        }
        for(Rule rule:rules){
            freeTerminals.put(rule.getNonterminals(),true);
        }
        for(Map.Entry<String,Boolean> entry:freeTerminals.entrySet()){
            if(entry.getValue().equals(false)){
                nextNonterminal = entry.getKey();
                break;
            }
        }
        return nextNonterminal;
    }
    private static Set<String> getCommonNonterminals(Set<Rule> grammar1Rules, Set<Rule> grammar2Rules) {
        Set<String> grammar1Nonterminals = new LinkedHashSet<>();
        Set<String> grammar2Nonterminals = new LinkedHashSet<>();
        for(Rule rule: grammar1Rules){
            grammar1Nonterminals.add(rule.getNonterminals());
        }

        for(Rule rule: grammar2Rules){
            grammar2Nonterminals.add(rule.getNonterminals());
        }
        Set<String> commonNonterminals = new LinkedHashSet<>(grammar1Nonterminals);
        commonNonterminals.retainAll(grammar2Nonterminals);
        return commonNonterminals;
    }
}
