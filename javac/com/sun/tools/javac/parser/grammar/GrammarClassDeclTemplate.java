package com.sun.tools.javac.parser.grammar;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

/**
 * All information related a generated class.
 */
public abstract class GrammarClassDeclTemplate extends GrammarASTTemplate {
    /**
     * A set of as-is fields, methods, inner classes etc.,
     * as they are found in standard class declarations.
     */
    public final List<JCTree> AdditionalMembers;

    /**
     * All interfaces, implemented by the emitted class that represents this structure
     */
    public final List<JCTree.JCExpression> Interfaces;

    protected GrammarClassDeclTemplate(int pos, Name name, List<JCTree.JCExpression> interfaces, List<JCTree> additionalMembers) {
        super(pos, name);
        
        AdditionalMembers = additionalMembers;
        Interfaces = interfaces;
    }
}
