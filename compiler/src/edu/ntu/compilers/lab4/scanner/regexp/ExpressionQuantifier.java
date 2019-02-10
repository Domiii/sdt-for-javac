package edu.ntu.compilers.lab4.scanner.regexp;

/**
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 7:23:02 PM
 */
public enum ExpressionQuantifier {
    /**
     * Does not have a symbol
     */
    One((char)0, false, false),
    OneOrMore('+', true, false),
    ZeroOrMore('*', true, true),
    ZeroOrOne('?', false, true);

    public final char Symbol;
    public final boolean HasLoop;
    public final boolean IsOptional;

    private ExpressionQuantifier(char symbol, boolean hasLoop, boolean isOptional) {
        Symbol = symbol;
        HasLoop = hasLoop;
        IsOptional = isOptional;
    }

    public static ExpressionQuantifier get(char symbol) {
        switch (symbol) {
            case '+': return OneOrMore;
            case '*': return ZeroOrMore;
            case '?': return ZeroOrOne;
        }
        return null;
    }
}
