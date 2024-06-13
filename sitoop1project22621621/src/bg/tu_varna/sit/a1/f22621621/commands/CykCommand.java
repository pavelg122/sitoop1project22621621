package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.GrammarNotInCNFException;
import bg.tu_varna.sit.a1.f22621621.exceptions.NoFileOpenedException;
import bg.tu_varna.sit.a1.f22621621.interfaces.Command;
import bg.tu_varna.sit.a1.f22621621.interfaces.FileHandler;
import bg.tu_varna.sit.a1.f22621621.interfaces.GrammarCommands;
import bg.tu_varna.sit.a1.f22621621.models.Grammar;
import bg.tu_varna.sit.a1.f22621621.models.Rule;
import bg.tu_varna.sit.a1.f22621621.utils.GrammarUtils;

import java.util.*;

public class CykCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    public CykCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils = grammarUtils;
    }

    @Override
    public void invoke(String[] input) {
        if(fileHandler.isFileOpen()) {
            Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
            ChomskyCommand chomskyCommand = new ChomskyCommand(grammarCommands, fileHandler, grammarUtils);
            if (!chomskyCommand.isCNF(grammar)) {
                throw new GrammarNotInCNFException("Grammar is not in CNF");
            }
            String word = input[1];
            CYK(grammar, word);
        }else throw new NoFileOpenedException("No file opened");
    }
    private void CYK(Grammar grammar,String word){
        String[] wordArr = word.split("");
        Map<Integer,String> wordNumbered = new HashMap<>();
        int countWord = 1;
        for(String str:wordArr){
            wordNumbered.put(countWord,str);
            countWord++;
        }
        int n = wordArr.length;
        Set<String> uniqueNonterminals = new HashSet<>();
        for(Rule rule : grammar.getRules()){
            uniqueNonterminals.add(rule.getNonterminals().trim());
        }
        Map<Integer,String> nonterminalsNumbered = new HashMap<>();
        int countNonterminal =1;
        for(String unique: uniqueNonterminals){
            nonterminalsNumbered.put(countNonterminal,unique);
                    countNonterminal++;
        }
        int r = nonterminalsNumbered.size();
        boolean[][][]P = new boolean[n][n][r];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<r;k++){
                    P[i][j][k] = false;
                }
            }
        }
        ArrayList[][][] back = new ArrayList[n][n][r];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                for(int k=0;k<r;k++){
                    back[i][j][k] = new ArrayList<Triple<Integer,Integer,Integer>>();
                }
            }
        }
        for(Map.Entry<Integer,String> entry : wordNumbered.entrySet()){
            for(Rule rule : grammar.getRules()){
                for(String terminal: rule.getTerminals()){

                }
            }
        }
        if(P[n-1][1][1]){System.out.println("The word " + word +
                " is in the language of grammar: "
                + grammar.getId());}
        else {System.out.println("The word " + word +
                " is NOT in the language of grammar: "
                + grammar.getId());}

    }
    public static class Triple<L,M,R>{
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

}
