package edu.ntu.compilers.lab4.cmmcompiler;

public class Label {
    public final int Id;

    public Label(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "L" + Id;
    }
}
