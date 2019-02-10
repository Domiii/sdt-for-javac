package edu.ntu.compilers.lab4.scanner.regexp.expr;

import edu.ntu.compilers.lab4.scanner.regexp.ExpressionQuantifier;

/**
 * Any one child is a final state of this sub expression 
 *
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 7:34:05 PM
 */
public class OrExpression extends NonLeafExpression {
    public OrExpression() {
        
    }
    
    public OrExpression(boolean negated) {
        this.negated = negated;
    }

    public String toString() {
        return "[" + super.toString() + "]" + (quantifier != ExpressionQuantifier.One ? quantifier.Symbol : "");
    }
}

