package edu.ntu.compilers.lab4.cmmcompiler;

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Author: Domi
 * Date: May 29, 2011
 * Time: 4:21:07 PM
 */
public class Code {
    static int lastLabelId;
    static int generateLabelId() {
        return ++lastLabelId;
    }
    public static class InstructionList extends ArrayList<Instruction> {}

    public final Gen Gen;
    public final CompilerContext Context;

    public final InstructionList Instructions = new InstructionList();


    // code gen state
    public boolean HasScanStmt;

    /**
     * The stack of all loops that have not completely generated yet
     */
    final Stack<CMMGrammar.WhileStmt> LoopStack = new Stack<CMMGrammar.WhileStmt>();

    /**
     * Decides how ArithExpr nodes are generated
     */
    public boolean IsStore = false;


    // ctor
    public Code(CompilerContext context) {
        Context = context;
        Gen = new Gen(this);
    }

    // #################################################################################
    // jumps & branching

    public void pushLoop(CMMGrammar.WhileStmt stmt) {
        LoopStack.push(stmt);
    }

    public CMMGrammar.WhileStmt getCurrentLoop() {
        return LoopStack.peek();
    }

    public void popLoop(CMMGrammar.WhileStmt stmt) {
        CMMGrammar.WhileStmt top = LoopStack.pop();
        assert(top == stmt);                            // should be ensured by the parser
    }

    /**
     * Create a new label if it does not exist yet
     */
    public Label createLabel() {
        return new Label(generateLabelId());
    }
}
