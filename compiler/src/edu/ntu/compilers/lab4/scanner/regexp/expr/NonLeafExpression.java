package edu.ntu.compilers.lab4.scanner.regexp.expr;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 8:35:40 PM
 */
public abstract class NonLeafExpression extends Expression {
    public final List<Expression> children;

    public NonLeafExpression() {
        children = new ArrayList<Expression>();
    }

    public List<Expression> getChildren() {
        return children;
    }

    public boolean canHaveChildren() {
        return children != null;
    }

    @Override
    public String toString() {
        String s = "";
        for (Expression e : children) {
            assert e != this;
            s += e;
        }
        return s;
    }
}
