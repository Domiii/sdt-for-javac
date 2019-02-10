package edu.ntu.compilers.lab4.util;

import java.util.Iterator;
import java.util.List;

/**
 * Author: Domi
 * Date: Mar 25, 2011
 * Time: 9:35:11 PM
 */
public class FormatHelper {

    public static String toDebuggableString(String str) {
        String out = "";
        for (char c : str.toCharArray()) {
            out += toDebuggableString(c);
        }
        return out;
    }

    public static String toDebuggableString(char chr) {
        String str;
        switch (chr) {
            case '\r': str = "\\r"; break;
            case '\n': str = "\\n"; break;
            case '\t': str = "\\t"; break;
            default: str = chr + ""; break;
        }
        return str;
    }

    public static <T> String toString(List<T> list) {
        return toString(list, ", ");
    }

    public static <T> String toString(List<T> list, String conj) {
        Iterator<T> i = list.iterator();
        if (! i.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        for (;;) {
            T e = i.next();
            sb.append(e);
            if (! i.hasNext())
                return sb.toString();
            sb.append(conj);
        }
    }
}
