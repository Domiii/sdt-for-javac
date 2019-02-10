package edu.ntu.compilers.lab4.tokens;

/**
 * Tokens that do not necessary always have the same content (i.e. numbers, identifiers, literals)
 */
public class VariableTokenEmitter implements TokenEmitter {
    public TokenName name;

    public VariableTokenEmitter() {
    }

    public Token emitToken(String content) {
        return new VariableToken(content, name);
    }
}
