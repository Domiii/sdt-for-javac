package edu.ntu.compilers.lab4.tokens;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;

/**
 *
 */
public class SimpleTokenDescriptor extends TokenDescriptor {
    public final String ExpressionString;
    public final Expression Expression;

    public SimpleTokenDescriptor(String expr) {
        ExpressionString = expr;
        Expression = compile(expr);
    }

    @Override
    public Expression getInitialExpression() {
        return Expression;
    }

    @Override
    /**
     * Simple tokens do not need a SubScanner, so it returns null.
     */
    public SubScanner createSubScanner(String initialString) {
        return null;
    }
}
