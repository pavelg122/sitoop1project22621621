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

/**
 * The type ChomskifyCommand. Transforms a Grammar in Chomsky Normal Form if it is not already in the normal form.
 * Prints the ID of the new Grammar.
 */
public class ChomskifyCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;

    /**
     * Instantiates a new ChomskifyCommand.
     *
     * @param grammarCommands the grammar commands
     * @param fileHandler     the file handler
     * @param grammarUtils    the grammar utils
     */
    public ChomskifyCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils =grammarUtils;
    }
    /**
     * Checks if a file is open and if it isn't a NoFileOpenException is thrown.
     *Checks if the arguments length is the correct amount and if it isn't an InvalidInputException
     * is thrown. Gets the Grammar from the Grammar Set and if that operation fails a GrammarIDNotFoundException is thrown.
     * First the method checks if the Grammar is already in Chomsky Normal Form and if it is a GrammarCNFMismatchException is thrown.
     * If it isn't the Grammar is transformed into a Grammar that is in Chomsky Normal Form.
     * The conversion of a Grammar to Chomsky Normal Form happens with the following order of transformations:
     * Start, Del, Unit, Term, Bin.
     * <br>After each stage the current state of the Grammar is displayed.
     * When the transformation goes through all the stages the ID of the new Grammar is displayed, the Grammar is added to the Grammar Set and
     * its String representation is appended to the file content.
     * @param input - the user input
     * @throws InvalidInputException - when the number of input arguments doesn't match the command arguments count
     * @throws NoFileOpenedException - when a file isn't open
     * @throws GrammarIDNotFoundException - when a Grammar isn't found
     * @throws GrammarCNFMismatchException - if the Grammar is already in Chomsky Normal Form
     */
    @Override
    public void invoke(String[] input) throws GrammarCNFMismatchException {
        try {
            if (fileHandler.isFileOpen()) {
                if(input.length !=1){
                    throw new InvalidInputException("Invalid number of arguments. Please type help to see the correct syntax for the chomskify command.");
                }
                Grammar grammar = grammarCommands.getGrammar(Long.parseLong(input[0]));
                if(grammar == null) {throw new GrammarIDNotFoundException("Grammar ID: " + input[0] + " not found. Please type " +
                        "list to see all grammars.");
                }
                ChomskyCommand chomskyCommand = new ChomskyCommand(grammarCommands, fileHandler, grammarUtils);
                if (chomskyCommand.isCNF(grammar)) {
                    throw new GrammarCNFMismatchException("Grammar is already in CNF.");
                }
                Grammar cnfGrammar = new Grammar();
                Set<Rule> rules = grammar.getRules();
                Set<Rule> grammarCopyRules = new LinkedHashSet<>();

                /*
                 * Stage 1. Start - adding a new Rule in the beginning if the nonterminal of the first Rule
                 * is contained in any terminal String of a Rule
                 */
                Iterator<Rule> iterator1 = rules.iterator();
                Rule retrieved1 = null;
                if (iterator1.hasNext()) {
                    retrieved1 = iterator1.next();
                }
                String startNonterminals = "S0";
                boolean newRule = false;
                assert retrieved1 != null;
                String firstRuleNonterminal = retrieved1.getNonterminals();
                for (Rule rule : rules) {
                    for (String terminal : rule.getTerminals()) {
                        if (terminal.contains(firstRuleNonterminal)) {
                            newRule = true;
                            break;
                        }
                    }
                }
                ArrayList<String> firstRuleTerminal = new ArrayList<>();
                if (newRule) {
                    firstRuleTerminal.add(firstRuleNonterminal);
                    grammarCopyRules.add(new Rule(startNonterminals, firstRuleTerminal));
                }
                //creating copies of all Rules of the Grammar
                for (Rule rule : rules) {
                    grammarCopyRules.add(rule.copy());
                }
                //printing the Rules after the start stage
                System.out.println("Rules after start:");
                grammarUtils.printStepRules(grammarCopyRules);

                /*
                 * Stage 2. Del - removing empty Rules. An empty Rule has the form: A → ε. This kind of Rule gets
                 * removed by finding all rules that contain the empty symbol and each rule gets replaced by all combinations
                 * where one or more instances of the nonterminal of the empty Rule gets removed.
                 */
                Iterator<Rule> iterator2 = grammarCopyRules.iterator();
                Set<Rule> removedEmptyRules = new LinkedHashSet<>();
                String epsilon = "ε";
                while (iterator2.hasNext()) {
                    Rule retrieved = iterator2.next();
                    ArrayList<String> ruleTerminals = retrieved.getTerminals();
                    String ruleNonterminals = retrieved.getNonterminals();
                    for (String term : ruleTerminals) {
                        if (term.trim().equals(epsilon)
                                && !(ruleNonterminals.equals(startNonterminals))) {
                            removedEmptyRules.add(retrieved);
                        }
                    }
                }
                for (Rule remove : removedEmptyRules) {
                    for (Rule rule : grammarCopyRules) {
                        for (String terminal : rule.getTerminals()) {
                            if (terminal.trim().equals(remove.getNonterminals())) {
                                removedEmptyRules.add(rule);
                            }
                        }
                    }
                }
                //updating all terminals
                for (Rule removed : removedEmptyRules) {
                    iterator2 = grammarCopyRules.iterator();
                    while (iterator2.hasNext()) {
                        Rule retrieved = iterator2.next();
                        for (String terminal : retrieved.getTerminals()) {
                            if (terminal.contains(removed.getNonterminals())) {
                                Set<String> newRuleTerminalsSet = new LinkedHashSet<>(retrieved.getTerminals());
                                ArrayList<String> newCombinations = grammarUtils.getNewCombinations(terminal, removed.getNonterminals(), grammarUtils.getAllNonterminals());
                                newRuleTerminalsSet.addAll(newCombinations);
                                newRuleTerminalsSet.removeIf(newTerm -> newTerm.contains(epsilon));
                                ArrayList<String> updatedTerminals = new ArrayList<>(newRuleTerminalsSet);
                                retrieved.setTerminals(updatedTerminals);
                            }
                        }

                    }
                }
                //printing the Rules after the del stage
                System.out.println("Rules after del:");
                grammarUtils.printStepRules(grammarCopyRules);
                /*
                 * Stage 3. Unit - removing unit Rules. A unit Rule has the form: A → B. This kind of Rule gets
                 * removed by separating all unit Rules and all other Rules. After that the symbol that is on the right side
                 * gets replaced by all terminals of the Rule that has the same nonterminal symbol(left side) as the one being replaced.
                 * In the example A → B: the symbol B will be replaced by the terminals of the rule that has B on the left side( B → xx).
                 * The rule A → B will become A → xx .
                 */
                Iterator<Rule> iterator3 = grammarCopyRules.iterator();
                Set<Rule> unitRules = new HashSet<>();
                Set<Rule> noUnitRules = new HashSet<>();

                ArrayList<String> allNonterminals1 = new ArrayList<>(Arrays.asList(grammarUtils.getAllNonterminals()));
                while (iterator3.hasNext()) {
                    Rule retrieved = iterator3.next();
                    String nonterminal = retrieved.getNonterminals();
                    ArrayList<String> unitTerminals = new ArrayList<>();
                    ArrayList<String> noUnitTerminals = new ArrayList<>();
                    for (String term : retrieved.getTerminals()) {
                        if (term.trim().length() == 1 && allNonterminals1.contains(term)) {
                            unitTerminals.add(term);
                        } else {
                            noUnitTerminals.add(term);
                        }
                    }
                    if (!unitTerminals.isEmpty()) {
                        unitRules.add(new Rule(nonterminal, unitTerminals));
                    }
                    if (!noUnitTerminals.isEmpty()) {
                        noUnitRules.add(new Rule(nonterminal, noUnitTerminals));
                    }
                }
                //updating all terminals
                for (Rule unitRule : unitRules) {
                    for (Rule oldRule : grammarCopyRules) {
                        Set<String> oldRuleTerminalsCopy = new LinkedHashSet<>(oldRule.getTerminals());
                        if (oldRule.getNonterminals().trim().equals(unitRule.getNonterminals().trim())) {
                            for (String unitRuleTerm : unitRule.getTerminals()) {
                                oldRuleTerminalsCopy.remove(unitRuleTerm);
                                for (Rule noUnitRule : noUnitRules) {
                                    if (noUnitRule.getNonterminals().equals(unitRuleTerm)) {
                                        oldRuleTerminalsCopy.addAll(noUnitRule.getTerminals());
                                    }
                                }
                            }
                        }
                        ArrayList<String> updatedTerminals = new ArrayList<>(oldRuleTerminalsCopy);
                        oldRule.setTerminals(updatedTerminals);
                    }
                }
                //printing the Rules after the unit stage
                System.out.println("Rules after unit:");
                grammarUtils.printStepRules(grammarCopyRules);

                /*
                 * Stage 4. Term - removing term Rules. A term Rule has the form: A → X1...a...Xn. This kind of Rule gets
                 * removed by first finding all term Rules. After that for each terminal(lowercase letter or number) in the terminal String
                 * of every Rule(right side) a new Rule gets created that has the lowecase letter as the right side of the Rule. After that each terminal
                 * gets replaced by the nonterminal symbol of the new Rule.
                 */
                ArrayList<String> allTerminals = new ArrayList<>(Arrays.asList(grammarUtils.getAllTerminals()));
                Set<String> termSet = new LinkedHashSet<>();
                for (Rule retrieved : grammarCopyRules) {
                    ArrayList<String> ruleTerminals = retrieved.getTerminals();
                    for (String terminal : ruleTerminals) {
                        if (grammarUtils.charsCount(terminal.trim(), grammarUtils.getAllTerminals()) >= 1 && terminal.trim().length() >= 2) {
                            String[] termArr = terminal.split("");
                            for (String term : termArr) {
                                if (allTerminals.contains(term)) {
                                    termSet.add(term);
                                }
                            }
                        }
                    }
                }

                Set<Rule> newTermRules = new LinkedHashSet<>();
                for (String term : termSet) {
                    String nonterminal = grammarUtils.getNextNonterminal(grammarCopyRules, grammarUtils.getAllNonterminals());
                    ArrayList<String> newTermRuleTerminals = new ArrayList<>();
                    newTermRuleTerminals.add(term);
                    newTermRules.add(new Rule(nonterminal, newTermRuleTerminals));
                    grammarCopyRules.add(new Rule(nonterminal, newTermRuleTerminals));
                }
                //updating all terminals
                for (Rule newTermRule : newTermRules) {
                    String term = newTermRule.getTerminals().getFirst();
                    String nonterminal = newTermRule.getNonterminals();
                    ArrayList<String> termsToAdd = new ArrayList<>();
                    ArrayList<String> termsToRemove = new ArrayList<>();
                    for (Rule rule : grammarCopyRules) {
                        if (rule.getTerminals().size() > 1 || !grammarUtils.isTerminalInNormalForm(rule.getTerminals().getFirst())) {
                            Set<String> terminalsCopy = new LinkedHashSet<>(rule.getTerminals());
                            for (String terminalCopy : terminalsCopy) {
                                if (terminalCopy.contains(term) && terminalCopy.length() >= 2 && grammarUtils.charsCount(terminalCopy, grammarUtils.getAllNonterminals()) > 0) {
                                    String updatedTerm = terminalCopy.replace(term, nonterminal);
                                    termsToRemove.add(terminalCopy);
                                    termsToAdd.add(updatedTerm);
                                }
                            }
                            termsToRemove.forEach(terminalsCopy::remove);
                            terminalsCopy.addAll(termsToAdd);
                            ArrayList<String> updatedTerminals1 = new ArrayList<>(terminalsCopy);
                            rule.setTerminals(updatedTerminals1);
                        }

                    }
                }
                Set<Rule> rulesToAdd = new LinkedHashSet<>();
                //correction of the new Rules
                for (Rule newr : newTermRules) {
                    for (Rule rule : grammarCopyRules) {
                        if (rule.getNonterminals().equals(newr.getNonterminals())) {
                            rule.setTerminals(newr.getTerminals());
                        } else {
                            ArrayList<String> termsRemove = new ArrayList<>();
                            ArrayList<String> termsAdd = new ArrayList<>();
                            Set<String> termsCopy = new LinkedHashSet<>(rule.getTerminals());
                            for (String term : rule.getTerminals()) {
                                if (term.contains(newr.getTerminals().getFirst()) &&
                                        GrammarUtils.countFreq(newr.getTerminals().getFirst(), term.trim()) == term.trim().length() && term.length() >= 2) {
                                    String nonterminal = grammarUtils.getNextNonterminal(grammarCopyRules, grammarUtils.getAllNonterminals());
                                    String updated = term.replace(newr.getTerminals().getFirst(), nonterminal);
                                    termsRemove.add(term);
                                    termsAdd.add(updated);
                                    ArrayList<String> newRuleTerms = new ArrayList<>();
                                    newRuleTerms.add(newr.getTerminals().getFirst());
                                    rulesToAdd.add(new Rule(nonterminal, newRuleTerms));
                                }
                            }
                            if (!termsRemove.isEmpty() && !termsAdd.isEmpty()) {
                                termsRemove.forEach(termsCopy::remove);
                                termsCopy.addAll(termsAdd);
                                ArrayList<String> updatedTerms = new ArrayList<>(termsCopy);
                                rule.setTerminals(updatedTerms);
                            }

                        }
                    }
                    grammarCopyRules.addAll(rulesToAdd);
                }
                //printing the Rules after the term stage
                System.out.println("Rules after term:");
                grammarUtils.printStepRules(grammarCopyRules);
                /*
                 * Stage 5. Bin - Bin - removing bin Rules. A bin Rule has the form: A → X1X2...Xn. This kind of Rule gets
                 * removed by finding all Rules that have more than 2 nonterminal symbols, and they have nonterminal symbols only in their
                 * right side. A new Rule gets created with the first 2 nonterminal symbols and after that the first 2 symbols get replaced
                 * by the nonterminal of the new Rule. If a change has occurred the old Rule gets updated.
                 */
                ArrayList<String> newBinTerminals = new ArrayList<>();
                for (Rule retrieved : grammarCopyRules) {
                    ArrayList<String> ruleTerminals = retrieved.getTerminals();
                    for (String terminal : ruleTerminals) {
                        if (grammarUtils.charsCount(terminal.trim(), grammarUtils.getAllNonterminals()) == terminal.trim().length() && terminal.trim().length() > 2) {
                            StringBuilder newTerminal = new StringBuilder();
                            //first two nonterminals - BC
                            String[] terminalParts = terminal.split("");
                            newTerminal.append(terminalParts[0]).append(terminalParts[1]);
                            newBinTerminals.add(String.valueOf(newTerminal));
                        }
                    }
                }

                Set<Rule> newBinRules = new LinkedHashSet<>();
                for (String bin : newBinTerminals) {
                    String nonterminal = grammarUtils.getNextNonterminal(grammarCopyRules, grammarUtils.getAllNonterminals());
                    ArrayList<String> newBinRuleTerminals = new ArrayList<>();
                    newBinRuleTerminals.add(bin);
                    newBinRules.add(new Rule(nonterminal, newBinRuleTerminals));
                    grammarCopyRules.add(new Rule(nonterminal, newBinRuleTerminals));
                }
                //updating all terminals
                for (Rule binRule : newBinRules) {
                    String term = binRule.getTerminals().getFirst();
                    String nonterminal = binRule.getNonterminals();
                    for (Rule rule : grammarCopyRules) {
                        boolean changed = false;
                        ArrayList<String> termsToAddBin = new ArrayList<>();
                        ArrayList<String> termsToRemoveBin = new ArrayList<>();
                        if ((rule.getTerminals().size() > 1 && !grammarUtils.isRuleTerminalsInNormalForm(rule.getTerminals())) || !grammarUtils.isTerminalInNormalForm(rule.getTerminals().getFirst())) {
                            Set<String> terminalsCopy = new LinkedHashSet<>(rule.getTerminals());
                            for (String termCopy : terminalsCopy) {
                                if (termCopy.startsWith(term) && termCopy.trim().length() > 2) {
                                    String replacementTerminal = termCopy.replace(term, nonterminal);
                                    termsToAddBin.add(replacementTerminal);
                                    termsToRemoveBin.add(termCopy);
                                    changed = true;
                                    break;
                                }
                            }
                            if (changed) {
                                termsToRemoveBin.forEach(terminalsCopy::remove);
                                terminalsCopy.addAll(termsToAddBin);
                                ArrayList<String> updatedTerminals = new ArrayList<>(terminalsCopy);
                                rule.setTerminals(updatedTerminals);
                                break;
                            }
                        }
                    }
                }
                //printing the Rules after the bin stage
                System.out.println("Rules after bin:");
                grammarUtils.printStepRules(grammarCopyRules);

                cnfGrammar.setRules(grammarCopyRules);
                System.out.println("Chomskify grammar id: " + cnfGrammar.getId());
                grammarCommands.getGrammarSet().add(cnfGrammar);
                StringBuilder stringBuilder = new StringBuilder();
                for (Rule rule : cnfGrammar.getRules()) {
                    stringBuilder.append(rule.toString());
                }
                fileHandler.getFileContent().append(stringBuilder);
            } else throw new NoFileOpenedException("No file opened. Please type help to see the correct syntax for the open command.");
        }catch(Exception e) {System.out.println(e.getMessage());}
    }
}
