package edu.ntu.compilers.lab4.scanner.regexp.expr;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;
import edu.ntu.compilers.lab4.scanner.regexp.ExpressionQuantifier;
import edu.ntu.compilers.lab4.util.FormatHelper;

/**
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 7:28:27 PM
 */
public class CharacterExpression extends Expression {
    private final char chr;

    public CharacterExpression(char chr) {
        super();

        this.chr = chr;
    }

    public char getCharacter() {
        return chr;
    }

    @Override
    public String toString() {
        String str = FormatHelper.toDebuggableString(chr);
        return str + (quantifier != ExpressionQuantifier.One ? quantifier.Symbol : "");
    }
}
