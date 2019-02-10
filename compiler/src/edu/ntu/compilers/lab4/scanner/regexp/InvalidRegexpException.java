package edu.ntu.compilers.lab4.scanner.regexp;

/**
 * Author: Domi
 * Date: Mar 17, 2011
 * Time: 7:54:28 PM
 */
public class InvalidRegexpException extends RuntimeException {
    public InvalidRegexpException() {
    }

    public InvalidRegexpException(String message, Object... args) {
        super(String.format(message, args));
    }

    public InvalidRegexpException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRegexpException(Throwable cause) {
        super(cause);
    }
}
