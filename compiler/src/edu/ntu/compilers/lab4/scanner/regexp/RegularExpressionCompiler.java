package edu.ntu.compilers.lab4.scanner.regexp;

import edu.ntu.compilers.lab4.scanner.regexp.expr.*;

import java.util.Stack;

/**
 * Simple regular expression compiler.
 * Supports zero width negative look ahead (negated AndExpression).
 *
 * Special escapeable characters:
 *      \[]()^*+?.
 *      t -> tab
 *      n -> lf
 *      r -> cr
 *
 * Character classes (NYI):
 *      . -> Anything
 *      \d -> [0-9]
 *      \w -> [a-zA-Z]
 *
 *
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 7:38:08 PM
 */
public class RegularExpressionCompiler {
    public static final String ReservedCharacters = "\\[]()^*+?-";

    public static AndExpression compile(String pattern) {
        return compile(pattern, new AndExpression());
    }

    public static <R extends NonLeafExpression> R compile(String pattern, R root) {
        RegularExpressionCompiler comp = new RegularExpressionCompiler(root, pattern);
        comp.compile();
        return root;
    }

    public static boolean isReservedCharacter(char chr) {
        return ReservedCharacters.contains(chr + "");     // can be made faster by using array lookup
    }

    private String pattern;
    private NonLeafExpression root;

    /**
     * To make sure we have matching braces and parentheses
     */
    Stack<Expression> controlStack = new Stack<Expression>();
    NonLeafExpression currentParent;
    Expression unqualifiedExpr;

    private RegularExpressionCompiler(NonLeafExpression root, String pattern) {
        if (pattern == null) {
            throw new NullPointerException("pattern");
        }
        if (!root.canHaveChildren()) {
            throw new InvalidRegexpException("Invalid root cannot have children: " + root);
        }
        this.pattern = pattern;
        this.root = root;
    }

    public String getPattern() {
        return pattern;
    }

    void compile() {
        currentParent = root;
        int i = 0;
        char chr = 0;
        try {
            char lastChr = 0;
            boolean expectingFollowUp = false;

            for (; i < pattern.length(); i++) {
                chr = pattern.charAt(i);

                if (chr > 255) throw new InvalidRegexpException("Currently only ANSI charsets are supported");

                expectingFollowUp = false;
                Expression newExpr = null;
                boolean isComplete = true;      // in case we have a new expression - Is it already complete?

                switch (lastChr) {
                    case '\\':
                        // escaped character - for now, emit immediately and ignore longer escape sequences, e.g. unicode character codes etc
                        if (isReservedCharacter(chr)) {
                            newExpr = new CharacterExpression(chr);
                        }
                        else {
                            switch (chr) {
                                // special characters
                                case 't': newExpr = new CharacterExpression('\t'); break;   // tab
                                case 'n': newExpr = new CharacterExpression('\n'); break;   // lf
                                case 'r': newExpr = new CharacterExpression('\r'); break;   // cr

                                // character classes
                                case '.': newExpr = Expression.Any(); break;
                                case 'w': newExpr = Expression.AnyLetter(); break;
                                case 'd': newExpr = Expression.AnyDigit(); break;
                                case 's': newExpr = Expression.AnyWhitespace(); break;


                                default: throw new InvalidRegexpException("Illegal escape sequence");
                            }
                        }
                        break;
                    case '-':
                        // character range
                        // any character can be used as target character 

                        if (currentParent.children.isEmpty()) {
                            throw new InvalidRegexpException("Invalid character range: Missing first character");
                        }

                        Expression firstExpr = currentParent.children.get(currentParent.children.size()-1);
                        if (!(firstExpr instanceof CharacterExpression)) {
                            throw new InvalidRegexpException("Invalid character range: Missing first character");
                        }

                        char from = ((CharacterExpression)firstExpr).getCharacter();

                        if (from >= chr) {
                            throw new InvalidRegexpException("Invalid character range: First character > final character");
                        }

                        // remove expression from it's parent
                        assert currentParent == firstExpr.getParent();
                        currentParent.children.remove(firstExpr);

                        OrExpression rangeExpr = new OrExpression();

                        // add all characters in range to new expression
                        firstExpr.setParent(rangeExpr);

                        for (char c = (char) (from+1); c <= chr; c++) {
                            new CharacterExpression(c).setParent(rangeExpr);
                        }
                        newExpr = rangeExpr;
                        break;
                    default:
                        // not special last character
                        switch (chr) {
                            case '*':
                            case '+':
                            case '?':
                                // quantifier
                                if (unqualifiedExpr == null) {
                                    throw new InvalidRegexpException("Illegal quantifier at this location");
                                }
                                unqualifiedExpr.quantifier = ExpressionQuantifier.get(chr);
                                unqualifiedExpr = null;
                                break;

                            case '\\':
                            case '-':
                            case '^':
                                // special handling for these characters, in combination with the next character
                                expectingFollowUp = true;
                                break;

                            // Grouped statement opens
                            case '(':
                                enterNonLeafExpr((NonLeafExpression) (newExpr = new AndExpression()));
                                isComplete = false;
                                break;
                            case '[':
                                enterNonLeafExpr((NonLeafExpression) (newExpr = new OrExpression()));
                                isComplete = false;
                                break;

                            // Grouped statement closes -> pop until first incomplete matching expression and move up the tree
                            case ')':
                                popExpr(AndExpression.class);
                                break;
                            case ']':
                                popExpr(OrExpression.class);
                                break;
                            default:
                                newExpr = new CharacterExpression(chr);
                                break;
                        }
                        break;
                }

                if (newExpr != null) {
                    // emitted new expression: Add to parent expression
                    newExpr.setParent(currentParent);

                    if (lastChr == '^') {
                        // negate expression
                        newExpr.negated = true;
                    }

                    if (isComplete) {
                        // expression is complete and can thus expect a quantifier
                        unqualifiedExpr = newExpr;
                    }
                    else {
                        currentParent = (NonLeafExpression)newExpr;
                    }
                }
                else if (lastChr == '^') {
                    // no negateable expression found
                    throw new InvalidRegexpException("No expression for negation found");
                }

                lastChr = chr;
            }

            if (expectingFollowUp) {
                // the last character is supposed to change the meaning of the following one
                throw new InvalidRegexpException("Invalid character at end of expression:" + lastChr);
            }
        }
        catch (Exception e) {
            throw new InvalidRegexpException(
                    String.format("Regular expression \"%s\" has an error at position #%d (%c): " + e.getMessage(),
                            pattern, i, chr)
                    , e);
        }
    }

    private void enterNonLeafExpr(NonLeafExpression expr) {
        controlStack.push(expr);
    }

    private void popExpr(Class clss) {
        popIncompleteNonLeafExpression(clss);
        if (currentParent.children.size() == 0) {
            throw new InvalidRegexpException("Empty brackets");
        }
        unqualifiedExpr = currentParent;
        currentParent = currentParent.getParent();
        assert currentParent != null;
    }

    /**
     * Pop from control stack and make sure its of the given type
     */
    private void popIncompleteNonLeafExpression(Class clss) {
        if (controlStack.isEmpty()) {
            throw new InvalidRegexpException("Trying to close \"%s\" too often", clss.getCanonicalName());
        }

        Expression top = controlStack.pop();

        if (top.getClass() != clss) {
            throw new InvalidRegexpException("Brackets don't match - Expected open \"%s\" - but found open \"%s\"",
                    clss.getCanonicalName(), top.getClass().getCanonicalName());
        }
    }
}
