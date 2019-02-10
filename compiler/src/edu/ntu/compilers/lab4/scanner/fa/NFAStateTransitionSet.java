package edu.ntu.compilers.lab4.scanner.fa;

import java.util.*;

/**
 * Author: Domi
 * Date: Mar 24, 2011
 * Time: 6:08:05 PM
 */
public class NFAStateTransitionSet {
    public final NFAState From;
    public final Set<NFAState> To = new HashSet<NFAState>();
    public final char Character;

    private SortedSet<NFAState> epsilonReachableStateSet;

    NFAStateTransitionSet(NFAState from, char chr) {
        From = from;
        Character = chr;
    }

    void addToState(NFAState toState) {
        To.add(toState);
        toState.IncomingTransitions.add(this);
    }

    public boolean isEpsilon() {
        return Character == 0;
    }

    public boolean isFinal() {
        for (NFAState s : To) {
            if (s.isFinal()) {
                return true;
            }
        }
        return false;
    }

    /**
     * The sorted set of all target states of all transitions in this set and all states
     * which are reachable from these, through epsilon transitions.
     */
    public SortedSet<NFAState> getSortedEpsilonReachableStateSet() {
        if (epsilonReachableStateSet == null) {
            epsilonReachableStateSet = new TreeSet<NFAState>();
            for (NFAState s : To) {
                for (NFAState epsState : s.outgoingEpsilonClosure()) {
                    epsilonReachableStateSet.add(epsState);
                }
            }
        }
        return epsilonReachableStateSet;
    }

    public String getUniqueNameOfEpsilonReachableStateSet() {
        String combinedName = "";
        for (NFAState s : epsilonReachableStateSet) {
            combinedName += s.name;
        }
        return combinedName;
    }
}
