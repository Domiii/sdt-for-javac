package edu.ntu.compilers.lab4.parser;

import edu.ntu.compilers.lab4.cmmcompiler.CMMTokenName;
import edu.ntu.compilers.lab4.cmmcompiler.CompilerContext;
import edu.ntu.compilers.lab4.cmmgrammar.CMMGrammar;
import edu.ntu.compilers.lab4.util.CompilerLog;
import edu.ntu.compilers.lab4.scanner.Scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Simple LL(1) parser for the CMM language
 */
public class CMMParser {
    String filename;
    public final CMMGrammar Grammar;
    CMMTokenName[] tokenNames;

    /**
     * Maps each Grammar terminal to it's token code. 
     */
    CMMGrammar.Terminal[] terminalCodeMap;

    /**
     * The LL(1) predict table maps:
     * NonTerminal X LookAheadToken => ProductionRule
     * This way, we can determine at any point what production rule applies to the current context. 
     */
    private LL1PredictTable predictTable;
    Stack<CMMGrammar.Symbol> stack;
    Stack<Integer> indexStack = new Stack<Integer>();
    Scanner scanner;
    CMMGrammar.AST tree;
    CompilerContext Context;

    public CMMParser(String filename, CMMTokenName[] tokenNames) throws Exception {
        this.filename = filename;
        this.tokenNames = tokenNames;

        Context = new CompilerContext();

        // fill terminal - code map
        // We put them into an array, indexed by their code, since we assume that the code is usually a small number.
        // Of course a hash map can be used instead.

        // find max code
        int maxCode = 0;
        for (CMMTokenName name : tokenNames) {
            if (name.isSyntaxElement()) {
                int code = name.getCode();
                maxCode = Math.max(maxCode, code);
                if (code < 0) {
                    throw new ParserException("This parser is not suitable for tokens with a code range < 0 or > 1 million");
                }
            }
        }
        if (maxCode > 1e6) {
            throw new ParserException("This parser is not suitable for tokens with a code range < 0 or > 1 million");
        }

        // create scanner
        try {
            scanner = new Scanner<CMMTokenName>(filename, tokenNames);
        }
        catch (Exception e) {
            logError("Could not open file \"%s\": " + e.getMessage(), filename);
        }

        // create Grammar & PredictTable
        Grammar = new CMMGrammar(Context, scanner);
        predictTable = Grammar.createPredictTable();

        // fill map
        terminalCodeMap = new CMMGrammar.Terminal[maxCode+1];
        //Map<String, CMMGrammar.Terminal> terminals = grammar.Terminals;
        for (CMMGrammar.Terminal terminal : Grammar.Terminals) {
            if (!terminal.TokenName.isSyntaxElement() || terminal.TokenName.isEOF()) continue;

            if (terminal.TokenName.isSyntaxElement()) {
                terminalCodeMap[terminal.TokenName.getCode()] = terminal;
            }
        }
    }

    /**
     * @return The first Terminal that represents the given token.
     */
    public CMMGrammar.Terminal getTerminal(CMMTokenName token) {
        return terminalCodeMap[token.getCode()];
    }

    public boolean isLastChildOfParent(CMMGrammar.Node node) {
        return node.Parent == null ||       // Start symbol only has one rule with a single symbol
                node.index() == node.Parent.Rule.getSymbolCount() - 1;
    }

    /**
     * Drives the AST creation and then starts the pass propagation
     */
    public CMMGrammar.AST parse() throws ParserException {
        try {
            // create AST
            tree = Grammar.createAST();
            CMMGrammar.Node currentNode = tree.Root;

            // create stack and push the start symbol
            stack = new Stack<CMMGrammar.Symbol>();
            stack.push(Grammar.NonTerminals[1]);        // skip over "Start
            indexStack.push(1);                         // next index of start symbol
            indexStack.push(0);                         // next index of first symbol

            // advance to the first token
            scanner.nextSyntaxElement();
            stackLoop:  while (!stack.isEmpty() && !scanner.current().isFinalToken()) {
                // getTerminal always works because the scanner uses the exact same tokens whose
                // codes have been added to the terminalCodeMap.
                CMMGrammar.Terminal currentTerminal = getTerminal((CMMTokenName)scanner.current().Name);
                CMMGrammar.Symbol stackTop = stack.peek();
                int nextIndex = indexStack.pop();

                if (stackTop == currentTerminal) {
                    // match a terminal
                    stack.pop();

                    // add terminal node to AST
                    // need to use previousNode to identify which terminal we found
                    CMMGrammar.Node node = currentNode.newChild(nextIndex, currentTerminal, scanner.current());

                    // push increased index back on index stack
                    indexStack.push(++nextIndex);

                    scanner.nextSyntaxElement();

                    while (isLastChildOfParent(node)) {
                        // we generated the entire sub tree -> go back up
                        currentNode = currentNode.Parent;
                        node = node.Parent;

                        indexStack.pop();

                        if (currentNode == null) {
                            // generated the Start symbol -> stack should be empty -> done parsing
                            assert(stack.isEmpty());

                            break stackLoop;
                        }
                    }
                }
                else if (stackTop instanceof CMMGrammar.Terminal) {
                    // unexpected terminal
                    logError("Unexpected Token: \"%s\" - Expected: \"%s\"", currentTerminal, stackTop);
                    return null;
                }
                else {
                    // stackTop is non-terminal
                    if (!(stackTop instanceof CMMGrammar.NonTerminal)) {
                        throw new ParserException("Error in grammar - Found symbol that is neither Terminal nor Non-Terminal: " + stackTop);
                    }

                    CMMGrammar.NonTerminal var = (CMMGrammar.NonTerminal)stackTop;
                    CMMGrammar.Rule rule = predictTable.lookup(var, currentTerminal);
                    if (rule == null) {
                        logError("Unexpected Token: \"%s\" in \"%s\"", currentTerminal.TokenName, var);
                        return null;
                    }

                    // pop the top and replace it with the symbols of the predicted production rule
                    stack.pop();
                    // push symbols in reverse order
                    for (int i = rule.getSymbolCount()-1; i >= 0; i--) {
                        CMMGrammar.Symbol sym = rule.getSymbol(i);
                        stack.push(sym);
                    }

                    onPredict(rule, currentNode);

                    // add child to AST
                    CMMGrammar.Node node = currentNode.newChild(nextIndex, rule);

                    // push increased index back on index stack
                    indexStack.push(++nextIndex);

                    if (!node.isLambda()) {
                        // push new index for nodes of new parent
                        indexStack.push(0);
                        
                        // step down the tree
                        currentNode = node;
                    }
                    else {
                        while (isLastChildOfParent(node)) {
                            // we generated the entire sub tree -> go back up
                            currentNode = currentNode.Parent;
                            node = node.Parent;

                            indexStack.pop();

                            if (currentNode == null) {
                                // generated the Start symbol -> stack should be empty -> done parsing
                                assert(stack.isEmpty());

                                // skip until EOF (or unexpected token)
                                scanner.nextSyntaxElement();

                                break stackLoop;
                            }
                        }
                    }
                }
            }

            if (!stack.isEmpty()) {
                logError("Parse stack not empty - Expected: " + Arrays.asList(stack.toArray()));
                return null;
            }
            else if (!scanner.current().isFinalToken()) {
                logError("Symbols found after end of Program.");
                return null;
            }

            CompilerLog.println("Parsed file successfully: " + scanner.FileName);
            return tree;
        }
        catch (Exception otherEx) {
            logError("Error while parsing file: " + otherEx);
            otherEx.printStackTrace();
            return null;
        }
    }

    void logError(String msg, Object... args) {
        if (scanner != null) {
            msg += String.format(" @ %s(%d:%d)", scanner.FileName, scanner.getLineNo(), scanner.getColumnNo());
        }
        CompilerLog.error(msg, args);
    }

    void onPredict(CMMGrammar.Rule rule, CMMGrammar.Node currentNode) {
        if (true) return;

        String indent = "";
        while (currentNode.Parent != null) {
            indent += "  ";
            currentNode = currentNode.Parent;
        }

        List<String> stackStr = new ArrayList<String>();
        for (CMMGrammar.Symbol sym : stack) {
            if (sym instanceof CMMGrammar.NonTerminal) {
                stackStr.add(((CMMGrammar.NonTerminal)sym).Name);
            }
            else {
                stackStr.add(((CMMGrammar.Terminal)sym).TokenName.CanonicalName);
            }
        }
        CompilerLog.println("%sPredict %s: %s %s", indent, scanner.current(), rule.NonTerminal.Name + " #" + rule.Index, stackStr);
    }
}
