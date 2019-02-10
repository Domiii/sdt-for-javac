package edu.ntu.compilers.lab4.tokens;

/**
 * Adds code and canonical name to the TokenName interface since they are required by the parser.
 *
 * Author: Domi
 * Date: May 2, 2011
 * Time: 7:33:12 PM
 */
public interface ParsableTokenName extends TokenName {
    int getCode();

    String getCanonicalName();
}
