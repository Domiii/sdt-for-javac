package edu.ntu.compilers.lab4.scanner.fa;

/**
 * Author: Domi
 * Date: Mar 24, 2011
 * Time: 6:17:23 PM
 */
public class NFA {
    public final NFAState StartState, FinalState;
    int level;

    public NFA(int level) {
        this();
        this.level = level;
    }

    public NFA() {
        StartState = new NFAState(null);
        FinalState = new NFAState(null);
    }

    /**
     * Create an equivalent DFA from this NFA
     */
    public DFA convertToDFA() {
        return NFAToDFAConverter.convert(this);
    }
}
