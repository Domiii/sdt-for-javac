package com.sun.tools.javac.parser.grammar;

import com.sun.tools.javac.util.Name;

/**
 * Template used to generate a class, method etc.
 */
public abstract class GrammarASTTemplate {
    /**
     * The position of the declaration of the construct that the AST node represents.
     * E.g. the position of a grammar, non-terminal, rule etc.
     */
    public final int Pos;

    /**
     * The name of the generated class.
     */
    public Name Name;

    protected GrammarASTTemplate(int pos, Name name) {
        Pos = pos;
        Name = name;
    }
    
}
