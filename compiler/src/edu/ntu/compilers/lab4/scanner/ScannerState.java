package edu.ntu.compilers.lab4.scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Domi
 * Date: Mar 1, 2011
 * Time: 8:30:21 PM
 */
public enum ScannerState {
    /**
     * Before a field has been read, or after a field or line has been read
     */
    BeforeField(0),

    /**
     * Inside a quoted field
     */
    InQuotedField(1),

    /**
     * Inside a non-quoted field
     */
    InSimpleField(2),

    /**
     * Right after readng an unmatched quotation mark inside a quoted field
     */
    BehindQuotesInQuotedField(4),

    /**
     * Illegal format
     */
    Invalid(5);

    public final int Id;

    ScannerState(int id)
    {
        Id = id;
    }
}
