package edu.ntu.compilers.lab4.util;

/**
 * Author: Domi
 * Date: May 2, 2011
 * Time: 8:02:56 PM
 */
public class CompilerLog {
    public static boolean Silent = false;

    public static void error(String str, Object... args) {
        if (!Silent) {
            System.out.println("ERROR: " +String.format(str, args));
        }
    }

    public static void println(String str, Object... args) {
        if (!Silent) {
            System.out.println(String.format(str, args));
        }
    }
    
}
