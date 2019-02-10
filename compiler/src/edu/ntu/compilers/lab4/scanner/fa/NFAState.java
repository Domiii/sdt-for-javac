package edu.ntu.compilers.lab4.scanner.fa;

import edu.ntu.compilers.lab4.tokens.TokenName;

import java.util.*;

/**
 * Author: Domi
 * Date: Mar 24, 2011
 * Time: 6:07:36 PM
 */
public class NFAState implements Comparable, FAState {
    static int lastDFAStateId;


    /**
     * All transitions, coming into this state
     */
    public final List<NFAStateTransitionSet> IncomingTransitions = new ArrayList<NFAStateTransitionSet>();

    /**
     * All transitions, going out of this state
     */
    public final Map<Character, NFAStateTransitionSet> OutgoingTransitions = new HashMap<Character, NFAStateTransitionSet>();
    public String name;

    TokenName token;
    boolean isFinal;

    NFAState(TokenName token) {
        name = "$" + ++lastDFAStateId;
        this.token = token;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public NFAStateTransitionSet addTransition(NFAState toState) {
        return addTransition('\0', toState);
    }

    public NFAStateTransitionSet addTransition(char character, NFAState toState) {
        NFAStateTransitionSet trans = OutgoingTransitions.get(character);
        if (trans == null) {
            // add new transition set for this character
            OutgoingTransitions.put(character, trans = new NFAStateTransitionSet(this, character));
        }

        // add state to transition set
        trans.addToState(toState);
        return trans;
    }

    public NFAStateTransitionSet getOutgoingTransitions(char chr) {
        return OutgoingTransitions.get(chr);
    }

    public boolean hasOutgoingTransition(int chr) {
        for (NFAState s : outgoingEpsilonClosure()) {
            if (s.OutgoingTransitions.containsKey((char)chr))
                return true;
        }
        return false;
    }

    public SortedSet<NFAState> epsilonClosureSet() {
        SortedSet<NFAState> set = new TreeSet<NFAState>();
        for (NFAState s : outgoingEpsilonClosure()) {
            set.add(s);
        }
        return set;
    }

    /**
     * Yields all States that are reachable from this state through epsilon transitions, including this state itself.
     */
    public Iterable<NFAState> outgoingEpsilonClosure() {
        return new Iterable<NFAState>() {
            public Iterator<NFAState> iterator() {
                return new OutgoingEpsilonIterator();
            }
        };
    }

    /**
     * Yields all States that can reach this state through epsilon transitions, including this state itself.
     */
    public Iterable<NFAState> incomingEpsilonClosure() {
        return new Iterable<NFAState>() {
            public Iterator<NFAState> iterator() {
                return new IncomingEpsilonIterator();
            }
        };
    }

    public int compareTo(Object o) {
        if (o instanceof NFAState) {
            return ((NFAState)o).name.compareTo(name);
        }
        return 1;
    }

    class IncomingEpsilonIterator extends EpsilonIterator {
        @Override
        void search(NFAState next) {
            // add all previous states into the fringe
            for (NFAStateTransitionSet trans : next.IncomingTransitions) {
                if (trans.isEpsilon()) {
                    addToFringe(trans.From);
                }
            }
        }
    }

    class OutgoingEpsilonIterator extends EpsilonIterator {

        @Override
        void search(NFAState next) {
            NFAStateTransitionSet epsilonSet = next.OutgoingTransitions.get('\0');
            if (epsilonSet != null) {
                for (NFAState state : epsilonSet.To) {
                    addToFringe(state);
                }
            }
        }
    }

    abstract class EpsilonIterator implements Iterator<NFAState> {
        Queue<NFAState> fringe = new LinkedList<NFAState>();
        Set<NFAState> visited = new HashSet<NFAState>();

        EpsilonIterator() {
            addToFringe(NFAState.this);
        }

        void addToFringe(NFAState state) {
            if (!visited.contains(state)) {
                fringe.add(state);
                visited.add(state);
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        abstract void search(NFAState next);

        public NFAState next() {
            NFAState next = fringe.remove();

            search(next);

            return next;
        }

        public void remove() {
            fringe.remove();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
