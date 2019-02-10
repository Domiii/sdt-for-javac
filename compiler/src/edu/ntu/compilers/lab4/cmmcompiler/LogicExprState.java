package edu.ntu.compilers.lab4.cmmcompiler;

/**
 * Author: Domi
 * Date: Jun 8, 2011
 * Time: 4:56:27 PM
 */
public class LogicExprState {
    public boolean Negated;

    /**
     * Either of && or ||
     */
    public CMMTokenName Comparer;

    /**
     * If false, we are currently on the right side of a comparer.
     */
    public boolean Left;

    public Label TrueLabel;
    public Label FalseLabel;

    public LogicExprState(Label trueLabel, Label falseLabel) {
        Left = false;
        TrueLabel = trueLabel;
        FalseLabel = falseLabel;
    }

    public LogicExprState(boolean negated, CMMTokenName comparer, boolean left, Label trueLabel, Label falseLabel) {
        Negated = negated;
        Comparer = comparer;
        Left = left;
        TrueLabel = trueLabel;
        FalseLabel = falseLabel;
    }

    public LogicExprState copy() {
        return new LogicExprState(Negated, Comparer, Left, TrueLabel, FalseLabel);
    }

    /**
     * Comparisons to the left of an OR, or any negated expression that is not OR, jump to the TrueLabel, if comparison is true.
     * All other comparisons jump to the FalseLabel, if comparison is false.
     */
    public boolean jumpToTrueLabel() {
        return Left && ((Comparer == CMMTokenName.LogicalOr && !Negated) ||
                (Comparer != CMMTokenName.LogicalOr && Negated));
    }

    public boolean comperatorInversed() {
        return (Comparer != CMMTokenName.LogicalOr && (!Negated || Left)) ||
                (Comparer == CMMTokenName.LogicalOr && !Left && !Negated);
    }
}
