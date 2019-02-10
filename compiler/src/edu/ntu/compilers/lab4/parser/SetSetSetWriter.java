package edu.ntu.compilers.lab4.parser;

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;
import edu.ntu.compilers.lab4.util.FormatHelper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Author: Domi
 * Date: Mar 30, 2011
 * Time: 11:32:33 PM
 */
public class SetSetSetWriter {
    static String FileName;

    final Comparator<CMMGrammar.Terminal> SymbolComparer = new Comparator<CMMGrammar.Terminal>() {
        /**
         * Bigger value indicates that o2 comes first
         * Smaller value indicates that o1 comes first
         */
        public int compare(CMMGrammar.Terminal o1, CMMGrammar.Terminal o2) {
            int c1 = getTerminalClassValue(o1);
            int c2 = getTerminalClassValue(o2);

            if (c1 != c2) {
                // first compare classes
                return c1 - c2;
            }

            // compare among the same class
            return o1.toString().compareTo(o2.toString());
        }

        int getTerminalClassValue(CMMGrammar.Terminal t) {
            if (table.producesLambda(t)) {
                return 0;
            }
            if (t.TokenName.isEOF()) {
                return 1;
            }
            return 3;
        }
    };

    public static void main(String[] args)throws Exception {
        SetSetSetWriter writer = new SetSetSetWriter("CMM.csv");
        writer.writeFile();
    }

    CMMGrammar Grammar;
    String fileName;
    int lineNo = 1;
    BufferedWriter writer;

    LL1PredictTable table;

    public SetSetSetWriter(String outFile) throws Exception {
        this.fileName = outFile;

        try {
            Grammar = new CMMGrammar(null, null);
            table = Grammar.createPredictTable();
        }
        catch (Exception e) {
            throw new RuntimeException("Could not parse CMMGrammar", e);
        }

        try {
            writer = new BufferedWriter(new FileWriter(outFile));
        }
        catch (Exception e) {
            throw new RuntimeException("Could not open file: " + outFile, e);
        }
    }

    public void writeFile() {
        try {
            writeTopicLine();
            for (CMMGrammar.NonTerminal var : Grammar.NonTerminals) {
                writeField(var.Name);
                for (int i = 0, rulesSize = var.getRules().size(); i < rulesSize; i++) {
                    CMMGrammar.Rule rule = var.getRules().get(i);
                    writeComma();
                    writeRule(rule, i == 0);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Could not write file: " + fileName, e);
        }
        finally {
            try {
                writer.close();
            }
            catch (Exception e) {
                throw new RuntimeException("Could not close file: " + fileName, e);
            }
        }
    }

    private void writeRule(CMMGrammar.Rule rule, boolean first) throws IOException {
        final List<String> lambdaList = Arrays.asList("(null)");
        List<String> list;
        if (table.producesLambda(rule)) {
            list =  lambdaList ;
        }
        else {
            list = new ArrayList<String>();
            for (CMMGrammar.Symbol sym : rule.getSymbols()) {
                list.add(sym instanceof CMMGrammar.NonTerminal ?
                        ((CMMGrammar.NonTerminal) sym).Name :
                        ((CMMGrammar.Terminal)sym).TokenName.toString()
                );
            }
        }

        writeField(FormatHelper.toString(list, " "));             // RHS
        writeComma();
        if (first) {
            writeSet(table.getFirstSet(rule.NonTerminal));          // FIRST
        }
        writeComma();
        if (first) {
            writeSet(table.getFollowSet(rule.NonTerminal));         // FOLLOW
        }
        writeComma();
        writeSet(genPredictSet(rule));                             // PREDICT
        writeLine();
    }

    private GrammarTerminalSet genPredictSet(CMMGrammar.Rule rule) {
        GrammarTerminalSet set = new GrammarTerminalSet();
        for (CMMGrammar.Terminal t : table.getFirstSet(rule)) {
            if (!table.producesLambda(t)) {
                set.add(t);
            }
        }
        if (table.producesLambda(rule)) {
            // also add the follow set
            for (CMMGrammar.Terminal t : table.getFollowSet(rule.NonTerminal)) {
                set.add(t);
            }
        }
        return set;
    }

    private void writeSet(GrammarTerminalSet set) throws IOException {
        List<String> list = new ArrayList<String>();
        for (CMMGrammar.Terminal t : set) {
            list.add(table.stringOf(t));
        }
        //Collections.sort(list,SymbolComparer);
        writeField(FormatHelper.toString(list, " "));
    }

    private void writeComma() throws IOException {
        writer.write(",");
    }

    private void writeTopicLine() throws IOException {
        writer.write(" LHS , RHS , FIRST , FOLLOW , PREDICT ");
        writeLine();
    }

    private void writeField(String str) throws IOException {
        writer.write(String.format("\" %s \"", str));
    }

    private void writeLine() {
        try {
            writer.newLine();
            ++lineNo;
        }
        catch (Exception e) {
            throw new RuntimeException("Could not write file: " + fileName, e);
        }
    }
}