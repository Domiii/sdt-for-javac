package com.sun.tools.javac.parser.grammar;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;

/**
 * Represents the base ASTNode class of a particular grammar (e.g. CMMNode for the CMM language)
 */
public class GrammarBaseASTNodeDeclTemplate extends GrammarClassDeclTemplate {
    public GrammarBaseASTNodeDeclTemplate(int pos, List<JCTree.JCExpression> interfaces, List<JCTree> additionalMembers) {
        super(pos, null, interfaces, additionalMembers);
    }
}
