package edu.ntu.compilers.lab4.tokens;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;
import edu.ntu.compilers.lab4.scanner.ScannerException;

import java.util.Arrays;

/**
 * Describes a token based on start and end expressions 
 */
public class GraspingTokenDescriptor extends TokenDescriptor {
    public final Expression StartExpr;
    /**
     * I did not plan my time too well, so I had to simplify this thing.
     * A better approach would be to use a regex for the EndExpression,
     * then reverse the DFA (i.e. swap the status of all Start and Final states)
     * and then traverse all transitions backwards, while reading from the end of
     * the current string to the front. That would yield an NFA which, again, could be converted
     * to a DFA for better performance. 
     */
    public final String[] EndExprs;
    public final char EscapeCharacter;
    /**
     * Whether token can be terminated by EOF
     */
    public final boolean CanEofTerminate;

    public GraspingTokenDescriptor(String startExpr, char escapeCharacter, boolean canEofTerminate, String... endExprs) {
        StartExpr = compile(startExpr);
        EndExprs = endExprs;
        EscapeCharacter = escapeCharacter;
        CanEofTerminate = canEofTerminate;

        if (EscapeCharacter != 0)
            for (String s : endExprs) {
                if (s.contains(escapeCharacter + "")) {
                    throw new ScannerException("Illegal TokenDescriptor - EndExpressions contain escape character: " + escapeCharacter);
                }
            }
    }

    @Override
    public Expression getInitialExpression() {
        return StartExpr;
    }

    @Override
    public SubScanner createSubScanner(String initialString) {
        return new GraspingScanner(initialString);
    }

    private class GraspingScanner extends SubScanner {
        int pos;
        int lastEscapePos = -1;
        boolean escaped = false;

        public GraspingScanner(String initialString) {
            super(initialString);
        }

        @Override
        public boolean scan(int chr) {
            String termination = null;
            if (chr == -1 && CanEofTerminate) {
                // terminate anyway
                termination = "";
            }
            else {
                if (!escaped) {
                    if (EscapeCharacter != 0 && chr == EscapeCharacter) {
                        escaped = true;
                        return true;
                    }
                    for (String endExpr : EndExprs) {
                        // check if string ends with unescaped end-expression
                        if (currentString.endsWith(endExpr) &&
                                lastEscapePos != currentString.length() - endExpr.length()) {
                            termination = endExpr;
                        }
                    }
                }
                else {
                    // escaped
                    // check is hardcoded for CMM string literals for now
                    // (can of course be refined by adding further parameters to TokenDescriptor)
                    if (chr != EscapeCharacter && !Arrays.asList(EndExprs).contains("" + (char)chr)) {
                        throw new ScannerException("Invalid escape sequence: " + (char)chr);
                    }
                    lastEscapePos = pos;
                    escaped = false;
                }
            }

            if (termination != null) {
                // Token has been terminated
                currentString = currentString.substring(0, currentString.length() - termination.length());
                return false;
            }
            else {
                // Token has not yet been terminated
                currentString += (char)chr;
                pos++;
                return true;
            }
        }


    }
}
