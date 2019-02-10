package edu.ntu.compilers.lab4.scanner;

/**
 * 
 */
public class ScannerException extends RuntimeException {
    public ScannerException() {
    }

    public ScannerException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ScannerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScannerException(Throwable cause) {
        super(cause);
    }
}
