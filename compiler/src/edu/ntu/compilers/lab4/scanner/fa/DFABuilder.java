package edu.ntu.compilers.lab4.scanner.fa;

import edu.ntu.compilers.lab4.tokens.TokenName;

/**
 * Author: Domi
 * Date: Mar 24, 2011
 * Time: 6:22:46 PM
 * 
 * Builds a DFA from a Regular expression in the following steps:
 *
 * 0.  Convert Regular Expression to an And/Or tree
 * 1.  Use tree to build a tree of NFAExpressions (hybrid between NFA and regex)
 * 2.  Use NFAExpressions to create NFA
 * 2a. Remove epsilon transitions
 * 2b. Convert epsilon-free NFA to DFA
 */
public class DFABuilder {
    public static DFA buildTokenDFA(Iterable<? extends TokenName> tokens) {
        NFA nfa = NFABuilder.buildNFA(tokens);
        return nfa.convertToDFA();
    }
}
