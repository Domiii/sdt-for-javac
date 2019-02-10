package edu.ntu.compilers.lab4.tokens;

/**
 * Used to emit a token, based on it's category and content
 */
public interface TokenEmitter {
    Token emitToken(String content);
}
