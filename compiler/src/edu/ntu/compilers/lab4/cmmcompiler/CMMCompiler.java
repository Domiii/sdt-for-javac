package edu.ntu.compilers.lab4.cmmcompiler;
  

import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;
import edu.ntu.compilers.lab4.util.CompilerLog;

/**
 * Author: Domi
 * Date: May 2, 2011
 * Time: 7:29:39 PM
 */
public class CMMCompiler {
    public static String ProgramName;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid arguments - Expected: 2");
        }

        long init = System.currentTimeMillis();

        CompilerLog.Silent = false;

        ProgramName = args[0];

        String programFile = args[1];

        String bassFile = "genAsm/" + ProgramName + ".bass";

        edu.ntu.compilers.lab4.parser.CMMParser parser = new edu.ntu.compilers.lab4.parser.CMMParser(
                programFile, CMMTokenName.values());

        long parseStart = System.currentTimeMillis();

        CMMGrammar.AST tree = parser.parse();
        if (tree == null) {
            // something went wrong
            return;
        }

        parser.Grammar.Code.Gen.start(ProgramName, bassFile);
        
        // First pass: Prepare symbol table etc.
        tree.Root.propagate_prep0();

        long genStart = System.currentTimeMillis();

        // Second pass: Generate code
        tree.Root.propagate_gen();

        parser.Grammar.Code.Gen.close();
        
        long end = System.currentTimeMillis();

        float totalTime = calcTime(init, end);

        System.out.format("Compilation took %.3f seconds: %.3f init (%.2f %%), %.3f parsing (%.2f %%), %.3f code gen (%.2f %%)\n",
                totalTime,

                calcTime(init, parseStart),
                calcPct(init, parseStart, totalTime),

                calcTime(parseStart, genStart),
                calcPct(parseStart, genStart, totalTime),

                calcTime(genStart, end),
                calcPct(genStart, end, totalTime)
        );
    }

    static float calcTime(long start, long end) {
        return (end - start) / 1000f;
    }

    static float calcPct(long start, long end, float total) {
        return 100 * calcTime(start, end) / total;
    }
}