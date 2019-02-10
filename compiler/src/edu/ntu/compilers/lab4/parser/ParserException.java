package edu.ntu.compilers.lab4.parser;

/**
 * Author: Domi
 * Date: Apr 30, 2011
 * Time: 11:06:36 PM
 */
public class ParserException extends RuntimeException {
    public ParserException() {
    }

    public ParserException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }
}
