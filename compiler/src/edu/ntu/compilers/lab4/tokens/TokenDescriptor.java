package edu.ntu.compilers.lab4.tokens;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;
import edu.ntu.compilers.lab4.scanner.regexp.RegularExpressionCompiler;

/**
 * Describes what a token consists of
 */
public abstract class TokenDescriptor {
    public Expression compile(String expr) {
        if (expr != null)
            return RegularExpressionCompiler.compile(expr);
        else
            return null;
    }

    /**
     * Once this expression is read, the descriptor will emit a token.
     * After which it will create a SubScanner, and (if createSubScanner is not null)
     * use it to scan the remainder of a potentially unfinished token.
     * This is especially used by GraspingTokenDescriptors. 
     */
    public abstract Expression getInitialExpression();

    
    public abstract SubScanner createSubScanner(String initialString);

    /**
     * Used to scan remainders of certain kinds of tokens
     */
    public abstract class SubScanner {
        protected String initialString;
        protected String currentString = "";


        public SubScanner(String initialString) {
            this.initialString = initialString;
        }

        public String getInitialString() {
            return initialString;
        }

        public String getCurrentString() {
            return currentString;
        }

        /**
         * @return Whether the scanner needs more input
         */
        public abstract boolean scan(int chr);
    }
}
