package edu.ntu.compilers.lab4.cmmcompiler;

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;

/**
 * Author: Domi
 * Date: May 29, 2011
 * Time: 4:20:37 PM
 */
public class CompilerContext {
    static int lastScopeId = 0;

    static int newScopeId() {
        return ++lastScopeId;
    }

    Scope rootScope;
    Scope currentScope;

    public CompilerContext() {
    }

    public Scope getCurrentScope() {
        return currentScope;
    }

    public Scope newScope(CMMGrammar.Block block) {
        if (currentScope == null) {
            // first scope
            rootScope = currentScope = new Scope(0, newScopeId(), currentScope, block);
        }
        else {
            Scope newTable = new Scope(currentScope.Children.size(), newScopeId(), currentScope, block);
            currentScope.Children.add(newTable);
            currentScope = newTable;
        }
        return currentScope;
    }

    public void enterScope(Scope scope) {
        if (currentScope == null) {
            assert(rootScope != null);
            currentScope = rootScope;
        }
        else {
            currentScope = scope;
        }
    }

    public void leaveScope() {
        assert(currentScope != null);
        currentScope = currentScope.Parent;
    }
}
