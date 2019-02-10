package edu.ntu.compilers.lab4.parser;

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;

import java.util.*;

/**
 *
 * Represents a predict table for an LL(1) parser
 *
 * Author: Domi
 * Date: May 2, 2011
 * Time: 8:14:09 PM
 */
public class LL1PredictTable {
    public static final CMMGrammar.Terminal LambdaSymbol = new CMMGrammar.Terminal(-1, null);

    CMMGrammar Grammar;
    CMMGrammar.Rule[][] table;

    // we can use the highest id + 1 as array size and ids as index because the grammar uses rolling ids
    int highestNonTerminalId, highestTerminalId;

    Queue<CMMGrammar.NonTerminal> lambdaSetFringe;
    Map<CMMGrammar.Rule, Integer> ruleNonLambdaCounts;
    final Set<CMMGrammar.NonTerminal> lambdaNonTerminals = new HashSet<CMMGrammar.NonTerminal>();
    final Set<CMMGrammar.Rule> lambdaRules = new HashSet<CMMGrammar.Rule>();
    final Hashtable<CMMGrammar.NonTerminal, Set<CMMGrammar.Rule>> Occurences = new Hashtable<CMMGrammar.NonTerminal, Set<CMMGrammar.Rule>>();

    final Hashtable<CMMGrammar.NonTerminal, GrammarTerminalSet> nonTerminalFirstSets = new Hashtable<CMMGrammar.NonTerminal, GrammarTerminalSet>();
    final Hashtable<CMMGrammar.Rule, GrammarTerminalSet> ruleFirstSets = new Hashtable<CMMGrammar.Rule, GrammarTerminalSet>();
    final Hashtable<CMMGrammar.NonTerminal, GrammarTerminalSet> followSets = new Hashtable<CMMGrammar.NonTerminal, GrammarTerminalSet>();
                                                       

    public LL1PredictTable(CMMGrammar g) {
        Grammar = g;
        for (CMMGrammar.NonTerminal var : g.NonTerminals) {
            highestNonTerminalId = Math.max(var.Id, highestNonTerminalId);
        }
        for (CMMGrammar.Terminal var : g.Terminals) {
            highestTerminalId = Math.max(var.Id, highestTerminalId);
        }

        generateOccurencesSet();
        generateLambdaSets();
        generateFirstSets();
        generateFollowSets();
        
        table = new CMMGrammar.Rule[highestNonTerminalId+1][];

        for (CMMGrammar.NonTerminal nt : g.NonTerminals) {
            for (CMMGrammar.Rule rule : nt.getRules()) {
                //if (rule.producesLambda()) continue;              

                // add the first set
                for (CMMGrammar.Terminal t : getFirstSet(rule)) {
                    if (!producesLambda(t)) {
                        setRule(nt, t, rule);
                    }
                }
                if (producesLambda(rule)) {
                    // also add the follow set
                    for (CMMGrammar.Terminal t : getFollowSet(nt)) {
                        setRule(nt, t, rule);
                    }
                }
            }
        }
    }

    private void setRule(CMMGrammar.NonTerminal var, CMMGrammar.Terminal terminal, CMMGrammar.Rule rule) {
        CMMGrammar.Rule[] innerTable = table[var.Id];
        if (innerTable == null) {
            table[var.Id] = innerTable = new CMMGrammar.Rule[highestTerminalId + 1];
        }
        else if (innerTable[terminal.Id] != null && innerTable[terminal.Id] != rule) {
            // conflict
            throw new ParserException("The given grammar is not LL(1): Predict conflict for NonTerminal \"%s\" and Look-Ahead token \"%s\" - " +
                    "Matches 2 different rules: \"%d\" and \"%d\"",
                    var.Name, terminal.TokenName, innerTable[terminal.Id].Index, rule.Index);
        }
        innerTable[terminal.Id] = rule;
    }


    // #############################################################################
    // Public getters
    
    public CMMGrammar.Rule lookup(CMMGrammar.NonTerminal var, CMMGrammar.Terminal terminal) {
        CMMGrammar.Rule[] innerTable = table[var.Id];
        if (innerTable != null) {
            return innerTable[terminal.Id];
        }
        return null;
    }

    public boolean producesLambda(CMMGrammar.Rule rule) {
        return lambdaRules.contains(rule);
    }

    public boolean producesLambda(CMMGrammar.Symbol sym) {
        if (sym instanceof CMMGrammar.NonTerminal) {
            // Non-Terminal
            return lambdaNonTerminals.contains((CMMGrammar.NonTerminal)sym);
        }

        // Terminal
        return sym == LambdaSymbol;
    }

    public Set<CMMGrammar.Rule> getOccurences(CMMGrammar.NonTerminal var) {
        Set<CMMGrammar.Rule> set = Occurences.get(var);
        if (set == null) {
            Occurences.put(var, set = new HashSet<CMMGrammar.Rule>());
        }
        return set;
    } 

    public GrammarTerminalSet getFirstSet(CMMGrammar.NonTerminal var) {
        return nonTerminalFirstSets.get(var);
    }

    public GrammarTerminalSet getFirstSet(CMMGrammar.Rule rule) {
        return ruleFirstSets.get(rule);
    }

    public GrammarTerminalSet getFollowSet(CMMGrammar.NonTerminal var) {
        return followSets.get(var);
    }

    
    // #############################################################################
    // Utility sets


    private void generateOccurencesSet() {
        for (CMMGrammar.NonTerminal var : Grammar.NonTerminals) {
            for (CMMGrammar.Rule rule : var.getRules()) {
                List<CMMGrammar.Symbol> syms = rule.getSymbols();
                for (CMMGrammar.Symbol sym : syms) {
                    if (sym instanceof CMMGrammar.NonTerminal) {
                        Set<CMMGrammar.Rule> set = getOccurences((CMMGrammar.NonTerminal)sym);
                        set.add(rule);
                    }
                }
            }
        }
    }

    private void generateLambdaSets() {
        lambdaSetFringe = new LinkedList<CMMGrammar.NonTerminal>();
        ruleNonLambdaCounts = new HashMap<CMMGrammar.Rule, Integer>();
        for (CMMGrammar.NonTerminal var : Grammar.NonTerminals) {
            for (CMMGrammar.Rule rule : var.getRules()) {
                ruleNonLambdaCounts.put(rule, rule.getSymbolCount());
                checkLambdaProduction(rule);
            }
        }

        while (!lambdaSetFringe.isEmpty()) {
            CMMGrammar.NonTerminal var = lambdaSetFringe.remove();
            for (CMMGrammar.Rule occurrence : getOccurences(var)) {
                ruleNonLambdaCounts.put(occurrence, ruleNonLambdaCounts.get(occurrence) - 1);
                checkLambdaProduction(occurrence);
            }
        }
        lambdaSetFringe = null;
        ruleNonLambdaCounts = null;
    }

    private void checkLambdaProduction(CMMGrammar.Rule rule) {
        if (ruleNonLambdaCounts.get(rule) == 0) {

            // rule produces lambda, and thus, it's producing non-terminal, also produces lambda
            lambdaRules.add(rule);
            if (!lambdaNonTerminals.contains(rule.NonTerminal)) {
                lambdaNonTerminals.add(rule.NonTerminal);

                // also update all rules that contain this terminal
                lambdaSetFringe.add(rule.NonTerminal);
            }
        }
    }

    // #############################################################################
    // First set

    GrammarTerminalSet getOrCreateFirstSet(CMMGrammar.NonTerminal var) {
        GrammarTerminalSet set = getFirstSet(var);
        if (set == null) {
            nonTerminalFirstSets.put(var, set = new GrammarTerminalSet());
        }
        return set;
    }

    GrammarTerminalSet getOrCreateFirstSet(CMMGrammar.Rule rule) {
        GrammarTerminalSet set = getFirstSet(rule);
        if (set == null) {
            ruleFirstSets.put(rule, set = new GrammarTerminalSet());
        }
        return set;
    }

    private void generateFirstSets() {
        for (CMMGrammar.NonTerminal var : Grammar.NonTerminals) {
            getOrCreateFirstSet(var);

            for (CMMGrammar.Rule rule : var.getRules()) {
                // find the first set of all rules
                generateFirstSet(rule);
                if (producesLambda(rule)) {
                    getOrCreateFirstSet(rule).add(LambdaSymbol);
                }
                getOrCreateFirstSet(var).addAll(getOrCreateFirstSet(rule));
            }
        }
    }

    private void generateFirstSet(CMMGrammar.Rule rule) {
        generateFirstSet(new HashSet<CMMGrammar.NonTerminal>(), getOrCreateFirstSet(rule), rule.getSymbols(), 0);
    }

    void generateFirstSet(GrammarTerminalSet firstSet, List<CMMGrammar.Symbol> symbols, int index) {
        generateFirstSet(new HashSet<CMMGrammar.NonTerminal>(), firstSet, symbols, index);
    }

    private void generateFirstSet(Set<CMMGrammar.NonTerminal> visited, GrammarTerminalSet firstSet, List<CMMGrammar.Symbol> symbols, int index) {
        if (symbols.size() == index) return;

        CMMGrammar.Symbol symbol = symbols.get(index);
        if (symbol instanceof CMMGrammar.Terminal) {
            firstSet.add((CMMGrammar.Terminal)symbol);
            return;
        }

        // symbol is non-terminal
        CMMGrammar.NonTerminal var = (CMMGrammar.NonTerminal)symbol;
        if (!visited.contains(var)) {
            visited.add(var);
            for (CMMGrammar.Rule rule : var.getRules()) {
                generateFirstSet(visited, firstSet, rule.getSymbols(), 0);
            }
        }

        if (producesLambda(var)) {
            // if lambda is in First(X), then
            //  First(Xb) = First(X) U First(b)
            generateFirstSet(visited, firstSet, symbols, index + 1);
        }
    }


    // #############################################################################
    // Follow set

    GrammarTerminalSet getOrCreateFollowSet(CMMGrammar.NonTerminal var) {
        GrammarTerminalSet set = getFollowSet(var);
        if (set == null) {
            followSets.put(var, set = new GrammarTerminalSet());
        }
        return set;
    }

    private void generateFollowSets() {
        for (CMMGrammar.NonTerminal var : Grammar.NonTerminals) {
            generateFollowSet(var);
        }
    }

    private void generateFollowSet(CMMGrammar.NonTerminal var) {
        generateFollowSet(var, getOrCreateFollowSet(var), new HashSet<CMMGrammar.NonTerminal>());
    }

    private void generateFollowSet(CMMGrammar.NonTerminal var, GrammarTerminalSet followSet, Set<CMMGrammar.NonTerminal> visited) {
        if (visited.contains(var)) return;
        visited.add(var);
        
        for (CMMGrammar.Rule rule : getOccurences(var)) {
            for (int i = 0, symCount = rule.getSymbolCount(); i < symCount; i++) {
                CMMGrammar.Symbol symbol = rule.getSymbol(i);
                if (symbol == var) {
                    generateFirstSet(followSet, rule.getSymbols(), i+1);
                    followSet.remove(LambdaSymbol);                             // lambda is omitted
                    if (checkAllProduceLambda(rule.getSymbols(), i+1)) {
                        // also append follow set of this rule's terminal, if the entire sub string can produce lambda
                        generateFollowSet(rule.NonTerminal, followSet, visited);
                    }
                }
            }
        }
    }

    /**
     * Return whether all of the given symbols produce lambda 
     */
    private boolean checkAllProduceLambda(List<CMMGrammar.Symbol> symbols, int i) {
        for (; i < symbols.size(); i++) {
            if (!producesLambda(symbols.get(i))) {
                return false;
            }
        }
        return true;
    }

    public String stringOf(CMMGrammar.Terminal t) {
        if (t.TokenName != null) {
            return t.TokenName.toString();
        }
        if (t == LambdaSymbol) {
            return "(null)";
        }

        return "UNKNOWNASDASDASDJKL:ASD:LASJD:KLASJD:LKSAJD:LKASJD";
    }
}
