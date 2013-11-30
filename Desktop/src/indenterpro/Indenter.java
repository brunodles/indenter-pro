/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenterpro;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bruno
 */
public class Indenter {

    public String indent(String string) {
        return string.replaceAll("[\\p{Blank}]++", " ");
    }
    
    public String removeBlankCharacters(String string) {
        return string.replaceAll("[\\p{Blank}]++", " ");
    }

    public String firstsBlankCharacters(String string) {
        Matcher matcher = Pattern.compile("([\\p{Blank}]++).*+").matcher(string);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }

    public int countFirstsBlankCharacters(String string) {
        return firstsBlankCharacters(string).length();
    }

    public static List<String> splitStringAsList(String string) {
        return Arrays.asList(string.split("[\r\n]"));
    }
}
