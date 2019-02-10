package edu.ntu.compilers.lab4.cmmcompiler;

import edu.ntu.compilers.lab4.scanner.regexp.Expression;
import edu.ntu.compilers.lab4.tokens.*;


/**
 * All tokens that are permitted in the C Minus Minus language
 */
public enum CMMTokenName implements ParsableTokenName {
    // Operators
    Plus("+", "\\+", 43),
    Minus("-", "\\-", 45),
    Star("*", "\\*", 42),
    Slash("/", 47),
    LogicalAnd("&&", 256),
    LogicalOr("||", 257),
    Exclamation("!", 33),

    Less("<", 60),
    Greater(">", 62),
    LessEqual("<=", 258),
    GreaterEqual(">=", 259),
    EqualEqual("==", 260),
    NotEqual("!=", 261),

    OpenParentheses("(", "\\(", 40),
    CloseParentheses(")", "\\)", 41),
    OpenBrackets("[", "\\[", 91),
    CloseBrackets("]", "\\]", 93),
    OpenCurly("{", 123),
    CloseCurly("}", 125),

    Comma(",", 44),
    Semicolon(";", 59),
    Equal("=", 61),


    // Keywords
    Int("int", 262),

    If("if", 263),
    Else("else", 264),
    While("while", 265),

    Break("break", 266),
    Continue("continue", 267),

    Scan("scan", 268),
    Print("print", 269),
    Println("println", 300),


    // Identifiers
    Identifier("id", "\\w[\\w\\d_]*", 270, new VariableTokenEmitter()),


    // Literals
    NumberLiteral("num", "\\d+", 271, new VariableTokenEmitter()),
    /**
     * Escape character: \
     */
    StringLiteral("string", new GraspingTokenDescriptor("\"", '\\', false, "\""), 272),


    // Comments & whitespace are not generated, but we keep them until code generation, for debugging purposes
    CommentSingleLine("", new GraspingTokenDescriptor("//", '\0', true, "\r", "\n"), -1),
    CommentMultiLine("", new GraspingTokenDescriptor("/\\*", '\0', false, "*/"), -1),

    Whitespace("", "\\s+", -1, new VariableTokenEmitter()),

    Start("Start", (String)null, -1),
    EOF("EOF", (String)null, 0);

    public final int Code;
    public final String CanonicalName;
    public final TokenDescriptor Descriptor;
    public final TokenEmitter Emitter;
    public final int Precedence;

    CMMTokenName() {
        this((String)null, 0);
    }

    /**
     * Ctor for a token whose expression is the same as it's canonical name
     */
    CMMTokenName(String expression, int code) {
        this(expression, expression, code);
    }

    CMMTokenName(String canonicalName, String expression, int code) {
        CanonicalName = canonicalName;
        Code = code;
        Descriptor = createDescriptor(expression);
        Precedence = this.ordinal();

        // constant emitter
        final Token constToken = new Token(this);
        Emitter = new TokenEmitter() {
            public Token emitToken(String content) {
                return constToken;
            }
        };
    }

    CMMTokenName(String canonicalName, String expression, int code, TokenEmitter emitter) {
        CanonicalName = canonicalName;
        Code = code;
        Descriptor = createDescriptor(expression);
        Precedence = this.ordinal();

        Emitter = emitter;
        if (emitter instanceof VariableTokenEmitter) {
            ((VariableTokenEmitter)emitter).name = this;
        }
    }

    CMMTokenName(String canonicalName, GraspingTokenDescriptor descriptor, int code) {
        CanonicalName = canonicalName;
        Code = code;
        Descriptor = descriptor;
        Precedence = this.ordinal();

        Emitter = new VariableTokenEmitter();
        ((VariableTokenEmitter)Emitter).name = this;
    }

    private TokenDescriptor createDescriptor(String expr) {
        try {
            return new SimpleTokenDescriptor(expr);
        }
        catch (Throwable e) {
            throw new RuntimeException("Invalid Expression for token: " + name(), e);
        }
    }

    public int getPrecedence() {
        return Precedence;
    }

    public Expression getExpression() {
        return Descriptor.getInitialExpression();
    }

    public TokenDescriptor descriptor() {
        return Descriptor;
    }

    public TokenEmitter emitter() {
        return Emitter;
    }

    public boolean isSyntaxElement() {
        return Code > -1;
    }

    public boolean isEOF() {
        return this == EOF;
    }

    public boolean isVariable() {
        return Emitter instanceof VariableTokenEmitter;
    }

    public int getCode() {
        return Code;
    }

    public String getCanonicalName() {
        return CanonicalName;
    }

    public String toString() {
        return CanonicalName;
    }
}