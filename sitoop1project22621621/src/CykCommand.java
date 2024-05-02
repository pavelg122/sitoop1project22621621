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
        /*ArrayList<Rule> unitProductions = new ArrayList<>();
        ArrayList<Rule> nonUnitProductions = new ArrayList<>();*/
        ArrayList<String> terminalsUnique = new ArrayList<>(uniqueTerminals);
        ArrayList<String> nonterminalsUnique = new ArrayList<>(uniqueNonterminals);
        String[] nonterminalsArr = new String[grammar.getRules().size()];
        int ii=0;
        for(Rule rule: grammar.getRules()){
            nonterminalsArr[ii] = rule.getNonterminals();
            ii++;
        }
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
        /*for(Rule rule: grammar.getRules()){
                for(String terminal: rule.getTerminals()){
                    if(terminal.length() == 1){
                        ArrayList<String> terminalArrList = new ArrayList<>();
                        terminalArrList.add(terminal);
                        unitProductions.add(new Rule(rule.getNonterminals(), terminalArrList));
                    }
                    if(terminal.length() == 2){
                        char[] terminalCharArr = terminal.toCharArray();
                        ArrayList<String> terminalArrList1 = new ArrayList<>();
                        terminalArrList1.add(String.valueOf(terminalCharArr[0]));
                        terminalArrList1.add(String.valueOf(terminalCharArr[1]));
                        nonUnitProductions.add(new Rule(rule.getNonterminals(), terminalArrList1));
                    }
                }
        }*/
        boolean[][][] P = new boolean[n][n][r];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<r;k++){
                    P[i][j][k] = false;
                }
            }
        }
        ArrayList<Triple<Integer,Integer,Integer>>[][][] back = new ArrayList[n][n][r];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<r;k++){
                    back[i][j][k] = new ArrayList<>();
                }
            }
        }
        for(int s=1;s<=n;s++){
            for(int v=1;v<=r;v++)
                if(isUnitProduction(nonterminalsArr[v-1], word.charAt(s-1),grammar.getRules())){
                P[1][s][v]=true;}
            }

        for(int l=2;l<=n;l++){
            for(int s=1;s<=n-l+1;s++){
                for(int p=1;p<=l-1;p++){
                    for(int a=0;a<r;a++){
                        for(int b=0;b<r;b++){
                            for(int c=0;c<r;c++){
                        if(P[p][s][b]&&P[l-p][s+p][c]){
                            P[l][s][a] = true;
                            back[l][s][a].add(new Triple<>(p,b,c));
                        }
                    }
                        }
                    }


                }
            }
        }
        if(P[n-1][1][1]){System.out.println("The word " + word +
                " is in the language of grammar: "
                + grammar.getId());}
        else {System.out.println("The word " + word +
                " is NOT in the language of grammar: "
                + grammar.getId());}
        /*
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
        }*/
        
        /*if(T.get(0)!= null && T.get(0).get(n-1) != null
        && T.get(0).get(n-1).size() != 0)
            System.out.println("The word " + word +
                    " is in the language of grammar: "
                    + grammar.getId());
        else System.out.println("The word " + word +
                " is NOT in the language of grammar: "
                + grammar.getId());*/
    }
    public class Triple<L,M,R>{
        private final L left;
        private final M middle;
        private final R right;

        public Triple(L left, M middle, R right) {
            this.left = left;
            this.middle = middle;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public M getMiddle() {
            return middle;
        }

        public R getRight() {
            return right;
        }
    }
  private boolean isUnitProduction(String nonterminal,char terminal,Set<Rule> rules){
for(Rule rule:rules){
    for(String terminal1: rule.getTerminals()){
        if(rule.getNonterminals().equals(nonterminal)&&terminal1.equals(String.valueOf(terminal))){
            return true;
        }
    }

}
        return false;
  }
}
