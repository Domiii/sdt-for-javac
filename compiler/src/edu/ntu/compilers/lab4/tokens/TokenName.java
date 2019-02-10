package edu.ntu.compilers.lab4.tokens;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;

/**
 * Abstract concept of a token.
 * A token can be anything, that we associate with expressions and then
 * can retrieve from the states of NFAs and DFAs.
 */
public interface TokenName {
    /**
     * The precedence of this token to select the right one from a potential set, inside a DFAState 
     */
    int getPrecedence();

    /**
     * The expression that belongs to this token
     */
    Expression getExpression();

    String name();

    boolean isSyntaxElement();

    boolean isEOF();

    TokenDescriptor descriptor();

    TokenEmitter emitter();
}
