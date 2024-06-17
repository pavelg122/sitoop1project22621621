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

public class ChomskifyCommand implements Command {
    private final GrammarCommands grammarCommands;
    private final FileHandler fileHandler;
    private final GrammarUtils grammarUtils;
    public ChomskifyCommand(GrammarCommands grammarCommands, FileHandler fileHandler, GrammarUtils grammarUtils) {
        this.grammarCommands = grammarCommands;
        this.fileHandler = fileHandler;
        this.grammarUtils =grammarUtils;
    }

    @Override
    public void invoke(String[] input) {
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

                //добавяне на ново правило в началото
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

                for (Rule rule : rules) {
                    grammarCopyRules.add(rule.copy());
                }

                System.out.println("Rules after start:");
                grammarUtils.printStepRules(grammarCopyRules);

                //премахване на правила с празен символ  (A → ε)
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
                System.out.println("Rules after del:");
                grammarUtils.printStepRules(grammarCopyRules);
                //премахване на юнит правила (A → B)
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

                System.out.println("Rules after unit:");
                grammarUtils.printStepRules(grammarCopyRules);

                //премахване на правила с несамотни терминали (A → aB)
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
                //корекция на новите правила
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

                System.out.println("Rules after term:");
                grammarUtils.printStepRules(grammarCopyRules);
                //премахване на правила с 2+ нетерминали отдясно (A → BCD)

                //Set<String> newBinTerminals = new LinkedHashSet<>();
                ArrayList<String> newBinTerminals = new ArrayList<>();
                for (Rule retrieved : grammarCopyRules) {
                    ArrayList<String> ruleTerminals = retrieved.getTerminals();
                    for (String terminal : ruleTerminals) {
                        if (grammarUtils.charsCount(terminal.trim(), grammarUtils.getAllNonterminals()) == terminal.trim().length() && terminal.trim().length() > 2) {
                            StringBuilder newTerminal = new StringBuilder();
                            //първите два нетерминала - BC
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
