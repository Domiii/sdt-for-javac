package edu.ntu.compilers.lab4.cmmcompiler;

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;

class ProcessingCompilerError extends CompilerError {
    public final CMMGrammar.Node Node;

    public ProcessingCompilerError(CMMGrammar.Node node) {
        Node = node;
    }

    public ProcessingCompilerError(CMMGrammar.Node node, String message) {
        super(message);
        Node = node;
    }

    public ProcessingCompilerError(CMMGrammar.Node node,String message, Object... args) {
        super(message, args);
        Node = node;
    }

    public ProcessingCompilerError(CMMGrammar.Node node,String message, Throwable cause) {
        super(message, cause);
        Node = node;
    }

    public ProcessingCompilerError(CMMGrammar.Node node, Throwable cause) {
        super(cause);
        Node = node;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" (%s)", Node);
    }
}

/**
 * Author: Domi
 * Date: May 29, 2011
 * Time: 5:54:14 PM
 */
public class CompilerError extends RuntimeException {
    public CompilerError() {
    }

    public CompilerError(String message) {
        super(message);
    }

    public CompilerError(String message, Object... args) {
        super(String.format(message, args));
    }

    public CompilerError(String message, Throwable cause) {
        super(message, cause);
    }

    public CompilerError(Throwable cause) {
        super(cause);
    }
}
