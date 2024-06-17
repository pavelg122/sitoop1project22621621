package bg.tu_varna.sit.a1.f22621621.commands;

import bg.tu_varna.sit.a1.f22621621.exceptions.GrammarCNFMismatchException;
import bg.tu_varna.sit.a1.f22621621.exceptions.GrammarIDNotFoundException;
import bg.tu_varna.sit.a1.f22621621.exceptions.InvalidInputException;
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
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=2){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the cyk command.");
                }
                Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
                if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                        "list to see all grammars.");
                }
                ChomskyCommand chomskyCommand = new ChomskyCommand(grammarCommands, fileHandler, grammarUtils);
                if (!chomskyCommand.isCNF(grammar)) {
                    throw new GrammarCNFMismatchException("Grammar is not in CNF.");
                }
                String word = input[1];
                CYK(grammar, word);
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch(Exception e){System.out.println(e.getMessage());}
    }
    private void CYK(Grammar grammar,String word){
        String[] wordArr = word.split("");
        Map<Integer,String> wordNumbered = new HashMap<>();
        int n = wordArr.length;
        for(int i=0;i<n;i++){
            wordNumbered.put(i+1,wordArr[i]);
        }

        Set<String> uniqueNonterminals = new LinkedHashSet<>();
        for(Rule rule : grammar.getRules()){
            uniqueNonterminals.add(rule.getNonterminals().trim());
        }

        Map<Integer,String> nonterminalsNumbered = new LinkedHashMap<>();
        int countNonterminal =1;
        for(String unique: uniqueNonterminals){
            nonterminalsNumbered.put(countNonterminal++,unique);
        }
        int r = nonterminalsNumbered.size();

        boolean[][][]P = new boolean[n+1][n+1][r+1];
        ArrayList<Triple<Integer,Integer,Integer>>[][][] back = new ArrayList[n+1][n+1][r+1];
        for(int i=0;i<=n;i++){
            for(int j=0;j<=n;j++){
                for(int k=0;k<=r;k++){
                    back[i][j][k] = new ArrayList<>();
                }
            }
        }

        for(Map.Entry<Integer,String> entry : wordNumbered.entrySet()){
            int s = entry.getKey();
            String wordStr = entry.getValue();
            for(Rule rule : grammar.getRules()){
                for(String terminal: rule.getTerminals()){
                    if(terminal.trim().equals(wordStr)){
                        int v = grammarUtils.getNonterminalKeyFromMap(rule.getNonterminals(), nonterminalsNumbered);
                        P[1][s][v]=true;
                    }
                }
            }
        }

        ArrayList<String> allTerminals = new ArrayList<>(Arrays.asList(grammarUtils.getAllTerminals()));
        for(int l=2;l<=n;l++)
        {
            for(int s=1;s<=n-l+1;s++)
            {
                for(int p=1;p<=l-1;p++)
                {
                  for(Rule rule1: grammar.getRules())
                  {
                      for(String terminal1: rule1.getTerminals())
                      {
                          String[] termArr = terminal1.trim().split("");
                           if(termArr.length ==2 && !allTerminals.contains(termArr[0]))
                           {
                               int a= grammarUtils.getNonterminalKeyFromMap(rule1.getNonterminals(),nonterminalsNumbered);
                               int b= grammarUtils.getNonterminalKeyFromMap(termArr[0],nonterminalsNumbered);
                               int c= grammarUtils.getNonterminalKeyFromMap(termArr[1],nonterminalsNumbered);
                               if(P[p][s][b] && P[l-p][s+p][c])
                               {
                                   P[l][s][a]=true;
                                   back[l][s][a].add(new Triple<>(p,b,c));
                               }
                           }
                      }
                  }
                }
            }
        }
        if(P[n][1][1]){System.out.println("The word " + word +
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
