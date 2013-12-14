/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bruno
 */
public class Line implements CharSequence {

    private String prefix;
    private StringBuilder value;
    private String sufix;

    Line(String lineStr) {
        this.prefix = "";
        this.sufix = "";
        findFirstsBlankCharacters(lineStr);
    }

    /**
     * split first blank characters from the line
     *
     * @param string
     * @return
     */
    private void findFirstsBlankCharacters(String value) {
        Matcher matcher = Pattern.compile("([\\p{Blank}]++).*+").matcher(value);
        if (matcher.matches()) {
            prefix = value.substring(0, matcher.end(1));
            value = value.substring(matcher.end(1));
        }
        value = removeBlankCharacters(value);
        this.value = new StringBuilder(value);
    }

    /**
     * Replace extra black characters
     *
     * @param string
     * @return
     */
    private String removeBlankCharacters(String string) {
        return string.trim().replaceAll("[\\p{Blank}]++", " ");
    }

    /**
     * Splits one String in a list of String.
     *
     * @param string
     * @return
     */
    public static List<Line> splitStringAsLineList(String string) {
        ArrayList<Line> list = new ArrayList<Line>();
        Line line = null;
        for (String s : string.split("[\r\n]")) {
            line = new Line(s);
            line.sufix = "\n";
            list.add(line);
        }
        if (line != null) {
            line.sufix = "";
        }
        return list;
    }

    public static String joinLines(List<Line> lines) {
        StringBuilder builder = new StringBuilder();
        for (Line line : lines) {
            builder.append(line.toString());
        }
        return builder.toString();
    }

    String sufix() {
        return sufix;
    }

    String prefix() {
        return prefix;
    }

    public int prefixLength() {
        if (prefix == null) {
            return 0;
        }
        return prefix.length();
    }

    StringBuilder value() {
        return value;
    }

    void insert(int offset, String str) {
        value.insert(offset, str);
    }

    void rebuild() {
        value = new StringBuilder(value.toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(value);
        builder.append(sufix);
        return builder.toString();
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int index) {
        return value.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return subSequence(start, end);
    }
}
