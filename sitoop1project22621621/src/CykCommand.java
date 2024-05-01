import java.util.*;

public class CykCommand implements Command{
    private GrammarCommands grammarCommands;

    public CykCommand(GrammarCommands grammarCommands) {
        this.grammarCommands = grammarCommands;
    }

    @Override
    public void invoke(String[] input) {
    Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
    String word = input[1];
       CYK(grammar,word);
    }
    private void CYK(Grammar grammar,String word){
        Set<String> uniqueNonterminals = new HashSet<>();
        Set<String> uniqueTerminals = new HashSet<>();
        for(Rule rule: grammar.getRules()){
            uniqueTerminals.addAll(rule.getTerminals());
            uniqueNonterminals.add(rule.getNonterminals());
        }
        ArrayList<String> terminalsUnique = new ArrayList<>(uniqueTerminals);
        ArrayList<String> nonterminalsUnique = new ArrayList<>(uniqueNonterminals);
        Iterator<Rule> iterator1 = grammar.getRules().iterator();
        List<String> wordList = List.of(word.split(""));
        Rule retrieved1 = null;
        if (iterator1.hasNext()) {
            retrieved1 = iterator1.next();
        }
        assert retrieved1 != null;
        String start = retrieved1.getNonterminals();
        int r = uniqueNonterminals.size();
        int n = word.length();
        /*boolean[][][] P = new boolean[n][n][r];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<r;k++){
                    P[i][j][k] = false;
                }
            }
        }

        */
        Map<Integer,Map<Integer,Set<String>>> T = new HashMap<>();
        for(int j=0;j<n;j++){
            for(Rule rule:grammar.getRules()){
                String nonterminal = rule.getNonterminals();
                ArrayList<String> terminals = rule.getTerminals();
                for(String terminal:terminals){
                    if(terminal.length() == 1 && terminal.equals(wordList.get(j))){
                        if(T.get(j) == null)
                            T.put(j,new HashMap<>());
                        T.get(j).computeIfAbsent(j,k -> new HashSet<>()).add(nonterminal);

                    }
                }
            }
            for(int i=j;i>=0;i--){
                for(int k=i;k<=j;k++){
                    for(Rule rule: grammar.getRules()){
                        String nonterminal = rule.getNonterminals();
                        ArrayList<String> terminals = rule.getTerminals();
                        for(String terminal:terminals){
                            if(terminal.length() == 2
                                    && T.get(i) != null
                            &&T.get(i).get(k) != null
                            && T.get(i).get(k).contains(terminal.charAt(0))
                            && T.get(k+1)!=null
                            &&T.get(k+1).get(j)!=null
                            &&T.get(k+1).get(j).contains(terminal.charAt(1))){
                                if(T.get(i) == null)
                                    T.put(i,new HashMap<>());
                                if(T.get(i).get(j) == null)
                                    T.get(i).put(j,new HashSet<>());
                                T.get(i).get(j).add(nonterminal);
                            }
                        }
                    }
                }
            }
        }
        if(T.get(0)!= null && T.get(0).get(n-1) != null
        && T.get(0).get(n-1).size() != 0)
            System.out.println("The word " + word +
                    " is in the language of grammar: "
                    + grammar.getId());
        else System.out.println("The word " + word +
                " is NOT in the language of grammar: "
                + grammar.getId());
    }
}
