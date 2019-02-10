package edu.ntu.compilers.lab4.scanner.fa;

/**
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 11:04:20 PM
 */
public class DFAStateTransition {
    public final DFAState From, To;
    public final char Character;

    public DFAStateTransition(DFAState from, DFAState to, char chr) {
        if (to == null) {
            throw new NullPointerException("to");
        }
        From = from;
        To = to;
        Character = chr;
    }
}
