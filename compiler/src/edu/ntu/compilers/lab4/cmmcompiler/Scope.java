package edu.ntu.compilers.lab4.cmmcompiler;

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;

import java.util.ArrayList;

/**
 * Author: Domi
 * Date: May 29, 2011
 * Time: 5:10:31 PM
 */
public class Scope {
    static int lastVarId = -1;

    public final int LocalId;
    public final int GlobalId;
    public final Scope Parent;

    public final ArrayList<Scope> Children = new ArrayList<Scope>();
    public final SymbolTable Symbols = new SymbolTable();

    public CMMGrammar.Block Block;

    public Scope(int localId, int globalId, Scope parent, CMMGrammar.Block block) {
        LocalId = localId;
        GlobalId = globalId;
        Parent = parent;
        Block = block;
    }

    /**
     * Throws an exception if the symbol could not be found
     */
    public CMMGrammar.VarDecl getVarDeclOrThrow(String name) {
        CMMGrammar.VarDecl symbol = getVarDecl(name);
        if (symbol == null) {
            throw new CompilerError("Could not compile: Symbol not found " + name);
        }
        return symbol;
    }

    public CMMGrammar.VarDecl getVarDecl(String name) {
        CMMGrammar.VarDecl symbol = Symbols.get(name);
        if (symbol == null && Parent != null) {
            // go up the tree
            return Parent.getVarDecl(name);
        }

        return symbol;
    }

    public int declareVar(CMMGrammar.VarDecl symbol) {
        CMMGrammar.VarDecl existingSymbol = Symbols.get(symbol.Name);
        if (existingSymbol != null) {
            // TODO: Return error code and recover later
            throw new CompilerError("Redefinition of symbol inside same scope: " + symbol.Name);
        }
        Symbols.put(symbol.Name, symbol);
        return ++lastVarId;
    }

    public void lookupVariable(CMMGrammar.VarOccurrence var) {
        CMMGrammar.VarDecl decl = getVarDecl(var.Name);
        if (decl == null) {
            throw new ProcessingCompilerError(var, "Unknown symbol: " + var.Name);
        }
        var.Decl = decl;
        if (decl.Type.ArrayDimensions.size() != var.ArrayDimensions.size()) {
            throw new ProcessingCompilerError(var, "Invalid array access specifies %d dimensions - Expected: %d",
                    var.ArrayDimensions.size(),
                    decl.Type.ArrayDimensions.size());
        }
    }
}
