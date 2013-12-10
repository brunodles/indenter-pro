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

    String prefix = "";
    String value = "";
    String sufix = "";

    Line(String lineStr) {
        this.value = lineStr;
        findFirstsBlankCharacters();
    }

    /**
     * split first blank characters from the line
     *
     * @param string
     * @return
     */
    private void findFirstsBlankCharacters() {
        Matcher matcher = Pattern.compile("([\\p{Blank}]++).*+").matcher(value);
        if (matcher.matches()) {
            prefix = value.substring(0, matcher.end(1));
            value = removeBlankCharacters(value.substring(matcher.end(1)));
//            return matcher.group(1);
        }
    }

    /**
     * Splits one String in a list of String.
     *
     * @param string
     * @return
     */
    public static List<Line> splitStringAsLineList(String string) {
        ArrayList<Line> list = new ArrayList<Line>();
        for (String s : string.split("[\r\n]")) {
            final Line line = new Line(s);
            line.sufix = "\n";
            list.add(line);
        }
        return list;
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
        return prefix + value + sufix;
    }
}
