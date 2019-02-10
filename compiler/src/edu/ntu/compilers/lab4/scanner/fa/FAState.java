package edu.ntu.compilers.lab4.scanner.fa;

/**
 * Author: Domi
 * Date: Mar 25, 2011
 * Time: 8:12:27 PM
 */
public interface FAState {
    boolean hasOutgoingTransition(int chr);
}
