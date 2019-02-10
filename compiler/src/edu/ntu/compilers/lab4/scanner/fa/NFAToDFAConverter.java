package edu.ntu.compilers.lab4.scanner.fa;

import java.util.*;

/**
 * Author: Domi
 * Date: Mar 24, 2011
 * Time: 9:33:19 PM
 */
public class NFAToDFAConverter {
    final String StartStateName = "Start";

    public static DFA convert(NFA nfa) {
        return new NFAToDFAConverter().doConvert(nfa);
    }

    DFA dfa = new DFA();
    final Map<String, TransitionClosure> ClosuresByName = new HashMap<String, TransitionClosure>();

    final Queue<TransitionClosure> Fringe = new LinkedList<TransitionClosure>();
    /**
     * Set is used for easier book keeping of the fringe
     */
    final Set<TransitionClosure> FringeSet = new HashSet<TransitionClosure>();


    public TransitionClosure getState(TransitionClosure closure) {
        return ClosuresByName.get(closure.getUniqueClosureName());
    }

    public TransitionClosure getClosure(String name) {
        return ClosuresByName.get(name);
    }

    private DFA doConvert(NFA nfa) {
        TransitionClosure startClosure = new TransitionClosure(dfa.StartState, nfa.StartState.epsilonClosureSet());
        startClosure.addClosure();
        doConvert();
        return dfa;
    }

    /**
     * BFS through the NFA to add all possible DFA states
     */
    private void doConvert() {
        while (!Fringe.isEmpty()) {
            TransitionClosure currentClosure = Fringe.remove();
            FringeSet.remove(currentClosure);
            
            // get all NFAStates that are reachable from the current DFAState, in one step
            Map<Character, TransitionClosure> allTransitions = getAllTransitionsOfClosure(currentClosure);

            // iterate over all sets of transitions of the same character
            for (Map.Entry<Character, TransitionClosure> transEntry : allTransitions.entrySet()) {
                char chr = transEntry.getKey();
                TransitionClosure childClosure = transEntry.getValue();
                
                childClosure = childClosure.addClosure();

                // append new DFAState
                currentClosure.state.addOutgoingTransition(chr, childClosure.state);
            }
        }
    }

    /**
     * Collect all states that are reachable from the given closure
     * (so we also have to add all states in the epsilon closure of every transition's target state)
     */
    Map<Character, TransitionClosure> getAllTransitionsOfClosure(SortedSet<NFAState> closure) {
        Map<Character, TransitionClosure> outgoingTransitions = new HashMap<Character, TransitionClosure>();
        for (NFAState state : closure) {
            for (Map.Entry<Character, NFAStateTransitionSet> transEntry : state.OutgoingTransitions.entrySet()) {
                char chr = transEntry.getKey();
                NFAStateTransitionSet transitions = transEntry.getValue();

                if (transitions.isEpsilon()) continue;  // ignore epsilon transitions

                TransitionClosure newList = outgoingTransitions.get(chr);
                if (newList == null) {
                    outgoingTransitions.put(chr, newList = new TransitionClosure(chr));
                }

                for (NFAState s : transitions.To) {
                    // since we work with sets, there will not be any duplicate entries
                    newList.addAll(s.epsilonClosureSet());
                }

            }
        }
        return outgoingTransitions;
    }

    /**
     * A set of States that are reachable from another state through the given Character.
     * Also represents a single state in the DFA.
     */
    class TransitionClosure extends TreeSet<NFAState> {
        public final char Character;
        DFAState state;

        /**
         * Start state ctor
         */
        public TransitionClosure(DFAState state, Set<NFAState> closure) {
            Character = 0;
            this.state = state;
            addAll(closure);
        }

        public TransitionClosure(char chr) {
            Character = chr;
        }

        public String getUniqueClosureName() {
            String name = "";
            for (NFAState s : this) {
                name += s.name;
            }
            return name;
        }

        public boolean isFinal() {
            for (NFAState s : this) {
                if (s.isFinal()) {
                    return true;
                }
            }
            return false;
        }

        TransitionClosure addClosure() {
            String name = getUniqueClosureName();
            TransitionClosure existingClosure = getClosure(name);
            if (existingClosure == null) {
                // does not exist yet
                if (state == null) {
                    state = new DFAState(dfa, isFinal());
                }
                ClosuresByName.put(name, this);
                Fringe.add(this);
                FringeSet.add(this);
                for (NFAState s : this) {
                    if (s.token != null)
                        state.tokens.add(s.token);
                }
                //Fringe.add(this);
                return this;
            }
            return existingClosure;
        }

        @Override
        public String toString() {
            return "" + Character + " (" + size() + ")";
        }
    }
}
