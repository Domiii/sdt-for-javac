package edu.ntu.compilers.lab4.scanner.regexp.expr;

import edu.ntu.compilers.lab4.scanner.regexp.ExpressionQuantifier;

/**
 * All children must be visited one after the other to get to the final state of this sub expression
 *
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 7:31:32 PM
 */
public class AndExpression extends NonLeafExpression {
    public AndExpression() {
        
    }

    @Override
     public String toString() {
        return "(" + super.toString() + ")" + (quantifier != ExpressionQuantifier.One ? quantifier.Symbol : "");
    }
}
