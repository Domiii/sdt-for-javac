package edu.ntu.compilers.lab4.scanner.fa;

/**
 * Deterministic Finite Automaton with a single start state
 */
public class DFA {
    public final DFAState StartState;
    public DFA() {
        StartState = new DFAState(this, false);
    }
}
