package edu.ntu.compilers.lab4.scanner;

import edu.ntu.compilers.lab4.scanner.fa.DFABuilder;
import edu.ntu.compilers.lab4.scanner.fa.DFA;
import edu.ntu.compilers.lab4.scanner.fa.DFAState;
import edu.ntu.compilers.lab4.tokens.TokenName;
import edu.ntu.compilers.lab4.tokens.Token;
import edu.ntu.compilers.lab4.tokens.TokenDescriptor;
import edu.ntu.compilers.lab4.util.FormatHelper;

import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Domi
 */
public class Scanner<TN extends TokenName> /* implements Iterable<Token> */ {
    public final String FileName;
    Reader reader;

    int lastChr;
    int currentLine = 1, currentCol = 1;
    StringBuilder partialTokenContent = new StringBuilder(256);

    DFA automaton;
    DFAState currentState;

    Token lastToken;
    Token currentToken;

    public Scanner(String fileName, TN[] allTokenNames) throws Exception {
        FileName = fileName;
        File file = new File(FileName);

        if (!file.exists()) {
            throw new FileNotFoundException("Invalid FileName: " + FileName);
        }

        lastToken = currentToken = Token.StartToken;
        reader = new FileReader(file);
        automaton = DFABuilder.buildTokenDFA(Arrays.asList(allTokenNames));
        currentState = automaton.StartState;
    }

    public int getLineNo() { return currentLine; }

    public int getColumnNo() { return currentCol; }

    public Token current() {
        return currentToken;
    }

    private int read() {
        try {
            int chr = reader.read();

            // we support all 3 kinds of line endings (CR, LF, CRLF):
            if (lastChr != '\r' || chr != '\n') { // CRLF -> do nothing, since we already accounted for the line ending
                if (chr == '\n' || chr == '\r') {
                    // new line
                    currentLine++;
                    currentCol = 1;
                }
                else {
                    // old line
                    ++currentCol;
                }
            }

            return chr;
        }
        catch (IOException e) {
            throw new ScannerException("Unable to read from file: " + e.getMessage(), lastToken);
        }
    }

    /**
     * @return The next Token that is not a whitespace or comment
     */
    public Token nextSyntaxElement() {
        next();
        skipUntilNextSyntaxElement();
        return current();
    }

    /**
     * @return The next Token, where whitespace and comment tokens are also emitted.
     */
    public Token next() {
        if (currentToken != null && currentToken.isSyntaxElement()) {
            lastToken = currentToken;
        }
        currentToken = null;
        int chr;
        try {
            do {
                chr = read();
                processChr(chr);
            }
            while (currentToken == null && chr != -1);
            if (currentToken == null) {
                // read till EOF
                currentToken = Token.FinalToken;
            }
        }
        catch (Exception e) {
            // TODO: Error recovery
            throw new ScannerException("Error while scanning (at %d,%d): " + e.getMessage(), currentLine, currentCol);
        }
        return currentToken;
    }

    private void processChr(int chr) {
        DFAState newState = currentState.getNeighbor(chr);

        if (newState != null) {
            // transition exists from currentState
            currentState = newState;

            partialTokenContent.append((char)chr);        // append all characters - TokenCategory decides how to evaluate it
        }
        else if (currentState.isFinalState()) {
            // transition does not exist but we are in final state -> emit the read token
            emitToken(chr);
        }
        else if (chr != -1) {
            // no transition and not in final state -> character is invalid in context
            if (currentState.isStartState()) {
                // outside of token
                throw new ScannerException("Invalid character \"%s\", right after \"%s\"",
                        FormatHelper.toDebuggableString((char) chr), lastToken);
            }
            else {
                // inside of token
                throw new ScannerException("Invalid character \"%s\" in token, after \"%s\"",
                        FormatHelper.toDebuggableString((char) chr), lastToken);
            }
        }
        lastChr = chr;
    }

    private void emitToken(int currentChr) {
        TN name = (TN)currentState.getHighestPrecedenceToken();
        String content = partialTokenContent.toString();
        partialTokenContent.delete(0, partialTokenContent.length());                // clear buffer

        TokenDescriptor.SubScanner scanner = name.descriptor().createSubScanner(content);
        if (scanner != null) {
            boolean scanned;
            int chr;
            scanner.scan(currentChr);
            do {
                chr = read();
            }
            while((scanned = scanner.scan(chr)) && chr != -1);

            if (scanned) {
                // file ended before scanning finished
                throw new ScannerException("Unterminated \"%s\"-token at end of file", name.name());
            }

            currentChr = chr;
            content = scanner.getCurrentString();

            // go back to StartState
            currentState = automaton.StartState;        // go back to StartState
        }
        else {
            currentState = automaton.StartState;        // go back to StartState
        }

        // process the first character of the new token and return
        // keep in mind that this will not emit another token,
        // since one needs to look at, at least, two characters to decide whether a token should be emitted or not
        processChr(currentChr);

        currentToken = name.emitter().emitToken(content);                              // create token

//        if (currentToken.Category.Code > -1)
//            System.out.println(FormatHelper.toDebuggableString(currentToken.toString()));
    }

    // Utility methods


    public boolean isName(TN name) {
        return current().Name == name;
    }

    /**
     * Ignores all current and following white space tokens
     */
    public void skipUntilNextSyntaxElement() {
        while (!current().isSyntaxElement()) {
            next();
        }
    }

    public boolean skipAny(TN name) {
        boolean skipped = false;
        while (next().Name == name)
            skipped = true;
        return skipped;
    }

    public void skipCurrent(TN name) {
        if (current().Name == name) {
            nextSyntaxElement();
        }
        else {
            expectationNotMet(name);
        }
    }

    /**
     * Returns whether the token was skipped
     */
    public boolean skipCurrentOptional(TN name) {
        if (current().Name == name) {
            nextSyntaxElement();
            return true;
        }
        return false;
    }

    public Token getCurrentAndMoveOn() {
        Token current = current();
        nextSyntaxElement();
        return current;
    }

    /**
     * Returns the next token if it has the given name or, else, throws an Exception
     */
    public Token getAndMoveOn(TN name) {
        skipUntilNextSyntaxElement();
        if (current().Name == name) {
            return getCurrentAndMoveOn();
        }
        expectationNotMet(name);
        return null;
    }

    protected void expectationNotMet(TN... name) {
        throw new ScannerException("Expected: %s - Found: %s", Arrays.asList(name), current().Name);
    }
}
