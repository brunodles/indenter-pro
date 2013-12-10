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
public class Line {

    String prefix;
    StringBuilder value;
    String sufix;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(value);
        builder.append(sufix);
        return builder.toString();
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

    public static String joinLines(List<Line> lines, String blankCharacters) {
        StringBuilder builder = new StringBuilder();
        for (Line line : lines) {
            line.prefix = blankCharacters;
            builder.append(line.toString());
        }
        return builder.toString();
    }
}
