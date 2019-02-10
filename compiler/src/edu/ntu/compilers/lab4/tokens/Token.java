package edu.ntu.compilers.lab4.tokens;

import edu.ntu.compilers.lab4.cmmcompiler.CMMTokenName;
import edu.ntu.compilers.lab4.cmmcompiler.Code;

/**
 *
 */
public class Token {
    public static final Token StartToken = new Token(CMMTokenName.Start);
    public static final Token FinalToken = new Token(CMMTokenName.EOF);

    public final TokenName Name;

    public Token(TokenName name) {
        Name = name;
    }

    public String stringValue() {
        return "";
    }

    public int intValue() {
        throw new RuntimeException("Token does not have an int value: " + Name);
    }

    public boolean isVariable() {
        return false;
    }

    /**
     * @Override
     */
    public String toString() {
        return Name.getExpression().toString();
    }

    public boolean isSyntaxElement() {
        return this != StartToken &&
                (this == FinalToken ||
                        Name.isSyntaxElement());
    }

    public boolean isStartToken() {
        return this == StartToken;
    }

    public boolean isFinalToken() {
        return this == FinalToken;
    }
}
