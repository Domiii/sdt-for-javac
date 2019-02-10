package edu.ntu.compilers.lab4.scanner.fa;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;
import edu.ntu.compilers.lab4.scanner.regexp.ExpressionQuantifier;
import edu.ntu.compilers.lab4.scanner.regexp.expr.AndExpression;
import edu.ntu.compilers.lab4.scanner.regexp.expr.CharacterExpression;
import edu.ntu.compilers.lab4.scanner.regexp.expr.NonLeafExpression;
import edu.ntu.compilers.lab4.tokens.TokenName;

import java.util.List;

/**
 *
 */
public class NFABuilder {
    public static boolean Debug = false;

    public static NFA buildNFA(Iterable<? extends TokenName> tokens) {
        // create NFA from all tokens - Of course, would also work if tokens were not hardcoded
        NFA nfa = new NFA();
        for (TokenName token : tokens) {
            if (token.getExpression() != null) {
                addSubNFA(nfa, token);
            }
        }
        markFinalStates(nfa);
        return nfa;
    }

    /**
     * Creates an NFA from the given expression tree.
     * Returns the final state of the NFA.
     */
    public static NFA buildNFA(TokenName token) {
        NFA nfa = new NFA();   // start- and final- states of this sub-expression
        addSubNFA(token.getExpression(), nfa);
        setToken(nfa, token);
        markFinalStates(nfa);
        return nfa;
    }

    /**
     * Sets the token of all final states in the given NFA
     */
    private static void setToken(NFA nfa, TokenName token) {
        for (NFAState state : nfa.FinalState.incomingEpsilonClosure()) {
            state.token = token;
        }
    }

    /**
     * Mark all final states: All states that can reach the final state through epsilon transitions 
     */
    private static void markFinalStates(NFA nfa) {
        for (NFAState state : nfa.FinalState.incomingEpsilonClosure()) {
            state.isFinal = true;
        }
    }

    public static NFA addSubNFA(NFA parentNFA, TokenName token) {
        NFA subNFA = new NFA();
        addSubNFA(token.getExpression(), subNFA);
        parentNFA.StartState.addTransition(subNFA.StartState);
        subNFA.FinalState.addTransition(parentNFA.FinalState);
        setToken(subNFA, token);
        return subNFA;
    }

    /**
     * Appends the given expression to the given NFA's start state
     */
    private static void addSubNFA(Expression expr, NFA nfa) {
        debugAdd(nfa, expr);
        if (expr instanceof NonLeafExpression) {
            // and/or grouping of expression sub-trees
            boolean and = expr instanceof AndExpression;
            NFAState prevState = nfa.StartState;

            List<Expression> children = ((NonLeafExpression)expr).children;
            for (int i = 0; i < children.size(); i++) {
                Expression childExpr = children.get(i);

                NFA childNFA = new NFA(nfa.level+1);   // start- and final- states of this sub-expression
                addSubNFA(childExpr, childNFA);

                prevState.addTransition(childNFA.StartState);   // append the sub-expression to previous state
                if (and) {
                    // and: One child after another
                    prevState = childNFA.FinalState;
                }
                else {
                    // or: All children have the same previous expression and same final expression
                    childNFA.FinalState.addTransition(nfa.FinalState);
                }
            }

            if (and) {
                // and: The last child has a transition to the final state
                prevState.addTransition(nfa.FinalState);
            }
        }
        else {
            // single character leaf
            assert expr instanceof CharacterExpression;
            char chr = ((CharacterExpression) expr).getCharacter();

            // Start -chr-> This -epsilon> Final
            NFAState thisState = new NFAState(null);
            nfa.StartState.addTransition(chr, thisState);
            thisState.addTransition(nfa.FinalState);
        }

        // Add transitions for quantifiers
        ExpressionQuantifier quantifier = expr.getQuantifier();
        if (quantifier.HasLoop) {
            nfa.FinalState.addTransition(nfa.StartState);
        }
        if (quantifier.IsOptional) {
            nfa.StartState.addTransition(nfa.FinalState);
        }
    }

    private static void debugAdd(NFA nfa, Expression child) {
        if (!Debug) return;

        String indent = "";
        for (int i = 0; i < nfa.level; i++) {
            indent += " ";
        }
        System.out.println(String.format("%sAppending expr \"%s\" to NFA (%s, %s)\n", indent, child, nfa.StartState, nfa.FinalState));
    }
}
