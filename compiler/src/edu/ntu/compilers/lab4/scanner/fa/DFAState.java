package edu.ntu.compilers.lab4.scanner.fa;

import edu.ntu.compilers.lab4.tokens.TokenName;

import java.util.*;

/**
 * 
 */
public class DFAState implements FAState {
    static final Comparator<TokenName> tokenComparator = new Comparator<TokenName>() {
        public int compare(TokenName o1, TokenName o2) {
            return o1.getPrecedence() - o2.getPrecedence();
        }
    };

    /**
     * All transitions going out of this state
     */
    final HashMap<Character, DFAStateTransition> OutgoingTransitions = new HashMap<Character, DFAStateTransition>();
    final DFA dfa;
    final boolean isFinal;
    final SortedSet<TokenName> tokens = new TreeSet<TokenName>(tokenComparator);

    DFAState(DFA dfa, boolean isFinal) {
        this.dfa = dfa;
        this.isFinal = isFinal;
    }

    /**
     * The DFA to which this state belongs
     */
    public DFA getDFA() {
        return dfa;
    }

    public int getTransitionCount() {
        return OutgoingTransitions.size();
    }

    public DFAStateTransition addOutgoingTransition(char c, DFAState to) {
        DFAStateTransition trans = new DFAStateTransition(this, to, c);
        OutgoingTransitions.put(c, trans);
        return trans;
    }

    public boolean hasOutgoingTransition(int chr) {
        return OutgoingTransitions.containsKey((char)chr);
    }

    public boolean hasOutgoingTransition(Character chr) {
        return OutgoingTransitions.containsKey(chr);
    }

    public boolean isStartState() {
        return this == dfa.StartState;
    }

    public boolean isFinalState() {
        return isFinal;
    }

    public SortedSet<TokenName> getTokens() {
        return tokens;
    }

    public TokenName getHighestPrecedenceToken() {
        return tokens.first();
    }

    public DFAState getNeighbor(int chr) {
        DFAStateTransition trans = getTransition((char)chr);
        if (trans != null) {
            return trans.To;
        }
        return null;
    }

    public DFAStateTransition getTransition(Character chr) {
        return OutgoingTransitions.get(chr);
    }

    @Override
    public String toString() {
        return "State (Tokens: " + Arrays.toString(tokens.toArray()) + ")";
    }
}
