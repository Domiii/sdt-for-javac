package edu.ntu.compilers.lab4.scanner.regexp;

import edu.ntu.compilers.lab4.scanner.regexp.expr.CharacterExpression;
import edu.ntu.compilers.lab4.scanner.regexp.expr.NonLeafExpression;
import edu.ntu.compilers.lab4.scanner.regexp.expr.OrExpression;

/**
 * Represents a node in a RegularExpressionTree
 *
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 7:22:19 PM
 */
public abstract class Expression {
    protected NonLeafExpression parent;
    protected ExpressionQuantifier quantifier;
    protected boolean negated;

    protected Expression() { 
        this(ExpressionQuantifier.One);
    }

    protected Expression(ExpressionQuantifier quantifier) {
        this.quantifier = quantifier;
    }

    public NonLeafExpression getParent() {
        return parent;
    }

    public ExpressionQuantifier getQuantifier() {
        return quantifier;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setParent(NonLeafExpression parent) {
        if (parent == null) {
            throw new NullPointerException("parent");
        }
        parent.children.add(this);
        this.parent = parent;
    }

    /*
     * Some pre-defined character classes
     */

    static OrExpression createAnyExpression() {
        OrExpression expr = new OrExpression();
        for (char c = 1; c < 256; c++) {
            new CharacterExpression(c).setParent(expr);
        }
        return expr;
    }

    /**
     * Any character
     */
    public final static OrExpression Any() { return createAnyExpression(); }
    
    /**
     * Any digit between 0 and 9
     */
    public final static OrExpression AnyDigit() { return (OrExpression) RegularExpressionCompiler.compile("0-9").children.get(0); }

    /**
     * Any roman letter between a and z or A and Z
     */
    public final static OrExpression AnyLetter() { return (OrExpression) RegularExpressionCompiler.compile("[a-zA-Z]").children.get(0); }

    /**
     * Space, line breaks, tabulator
     */
    public final static OrExpression AnyWhitespace() { return RegularExpressionCompiler.compile("\t \r\n", new OrExpression()); }
}
