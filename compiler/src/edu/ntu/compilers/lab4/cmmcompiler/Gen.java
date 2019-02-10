package edu.ntu.compilers.lab4.cmmcompiler;

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;
import edu.ntu.compilers.lab4.util.FormatHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Code generator
 */
public class Gen {
    static String scannerFieldName = "stdin";
    PrintStream writer;

    public void start(String programName, String targetFile) throws IOException {
        writer = new PrintStream(new FileOutputStream(targetFile));

        importt("java.io.*");
        importt("java.util.*");

        classs(programName);

        staticField("Scanner", scannerFieldName);
        defaultCtor();
        mainMethod();
        incIndentLevel();
    }

    public void close() {
        writer.close();
    }

    // #################################################################################
    // load, store & stack manipulation
    String indent = "";
    public final Code Code;

    public Gen(Code code) {
        Code = code;
    }

    void incIndentLevel() {
        indent += "\t";
    }

    void decIndentLevel() {
        indent = indent.substring(0, indent.length() - 2);
    }

    void writeln(String format, Object... args) {
        write(format + "\n", args);
    }

    void write(String format, Object... args) {
        writeUnindented(indent + format, args);
    }

    void writelnUnindented(String format, Object... args) {
        writeUnindented(format + "\n", args);
    }

    void writeUnindented(String str, Object... args) {
        try {
            writer.print(String.format(str, args));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // #################################################################################
    // file & class level

    public void classs(String name) {
        writeln("\n.class public " + name + "\n");
    }

    private void defaultCtor() {
        writeln("\n" +
                ".method public void <init>()\n" +
                "\taload #0\n" +
                "\tinvokespecial void <init>() @ Object\n" +
                "\treturn\n");
    }

    public void staticField(String type, String name) {
        writeln(".field static %s %s\n", type, name);
    }

    public void importt(String packagee) {
        writeln(".import %s", packagee);
    }

    public void mainMethod() {
        method("public static", "void", "main", "String[]");
    }

    public void method(String flags, String retType, String name,  Object... parameterTypes) {
        writeln(".method %s %s %s(%s)", flags, retType, name, FormatHelper.toString(Arrays.asList(parameterTypes)));
    }




    // #################################################################################
    // load & store

    /**
     * Emits a LOAD instruction
     */
    public void iload(CMMGrammar.VarOccurrence var) {
        assert(var != null && var.Decl != null && var.Decl.Type != null);
        assert(var.Decl.Type.BaseType == CMMGrammar.BaseType.Integer);
        writeln("iload #%d", var.Decl.Id);
    }

    public void aload(CMMGrammar.VarOccurrence var) {
        assert(var.isArray());
        writeln("aload #%d", var.Decl.Id);
    }

    public void iaload(CMMGrammar.VarOccurrence var) {
        assert(var.isArray());
        writeln("iaload");
    }

    public void aaload() {
        writeln("aaload");
    }

    public void astore(CMMGrammar.VarDecl array) {
        assert(array.Type.isArray());
        writeln("astore #%d", array.Id);
    }

    public void storeValue(CMMGrammar.VarDecl var) {
        if (var.Type.isArray()) {
            // iastore
            // arguments for iastore are on the stack
            writeln("iastore");
        }
        else {
            assert(var.Type.BaseType == CMMGrammar.BaseType.Integer);
            // istore
            writeln("istore #%d", var.Id);
        }
    }

    /**
     * Emits an LDC instruction
     */
    public void ldc(int num) {
        writeln("ldc %d", num);
    }

    /**
     * Emits an LDC instruction
     */
    public void ldc(String string) {
        writeln("ldc \"%s\"", string);
    }

    // #################################################################################
    // arithmetic instructions

    /**
     * Emits an INEG instruction to calculate the negative value of the integer currently on top of the stack
     */
    public void neg() {
        writeln("ineg");
    }

    public void add() {
        writeln("iadd");
    }

    public void sub() {
        writeln("isub");
    }

    public void mul() {
        writeln("imul");
    }

    public void div() {
        writeln("idiv");
    }


    // #################################################################################
    // I/O

    /**
     * Creates a new scanner and assigns it to the stdin field
     */
    public void initScanner() {
        writeln("new Scanner");
        writeln("dup");
        writeln("getstatic InputStream in @ System");
        writeln("invokespecial void <init>(InputStream) @ Scanner");
        writeln("putstatic Scanner %s", scannerFieldName);
    }

    public void print(CMMGrammar.PrintableExprImpl expr) {
        // load System.out
        writeln("getstatic PrintStream out @ System");

        // load argument
        expr.gen();

        String type = expr instanceof CMMGrammar.PrintableString ? "String" : "int";

        // call the print method
        writeln("invokevirtual void print(%s) @ PrintStream", type);
    }

    public void println() {
        // load System.out
        writeln("getstatic PrintStream out @ System");

        // call the println method
        writeln("invokevirtual void println() @ PrintStream");
    }

    /**
     * Call nextInt on the scanner
     */
    public void scan() {
        writeln("getstatic Scanner %s", scannerFieldName);
        writeln("invokevirtual int nextInt() @ Scanner");
    }

    // #################################################################################
    // branching

    public void cmpEQ(LogicExprState state) {
        cmp(state, "if_icmpeq", "if_icmpne");
    }

    public void cmpNE(LogicExprState state) {
        cmp(state, "if_icmpne", "if_icmpeq");
    }

    public void cmpGT(LogicExprState state) {
        cmp(state, "if_icmpgt", "if_icmple");
    }

    public void cmpLT(LogicExprState state) {
        cmp(state, "if_icmplt", "if_icmpge");
    }

    public void cmpGE(LogicExprState state) {
        cmp(state, "if_icmpge", "if_icmplt");
    }

    public void cmpLE(LogicExprState state) {
        cmp(state, "if_icmple", "if_icmpgt");
    }

    public void cmp(LogicExprState state, String instruction, String oppositeInstruction) {
        String inst = state.comperatorInversed() ?
                oppositeInstruction :
                instruction;

        Label label = state.jumpToTrueLabel() ? state.TrueLabel : state.FalseLabel;

        writeln("%s %s", inst, label);
    }


    // #################################################################################
    // other

    public void newArray(CMMGrammar.VarDecl array) {
        if (array.Type.ArrayDimensions.size() == 1) {
            ldc(array.Type.ArrayDimensions.get(0));
            writeln("newarray int");
        }
        else {
            // multi-dimensional array
            String brackets = "";
            for (int dim : array.Type.ArrayDimensions) {
                ldc(dim);
                brackets += "[]";
            }
            writeln("multianewarray int%s %d-d", brackets, array.Type.ArrayDimensions.size());
        }
        astore(array);
    }

    public void jump(Label label) {
        writeln("goto %s", label);
    }

    public void returnn() {
        writeln("return");
        writeln("");
    }

    public void label(Label label) {
        writelnUnindented("%s:", label);
    }
}
