/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenter.options;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bruno
 */
public class Option {

    public static final String OPTION_REGEX = "<(?:(\\d+)\\#)?(.+?)>";

    private static Matcher getMatcher(String optionsStr) {
        Pattern pattern = Pattern.compile(OPTION_REGEX);
        Matcher matcher = pattern.matcher(optionsStr);
        return matcher;
    }

    private static Option getOption(Matcher matcher) {
        Option option = new Option();
        //            System.out.println(" count =" + matcher.groupCount());
        option.ignorableLines = strToIntDef(matcher.group(1), 0);
        option.regex = matcher.group(2);
        return option;
    }
    int ignorableLines = 0;
    String regex;

    private Option() {
    }

    @Override
    public String toString() {
        return "Option{" + "ignorableLines=" + ignorableLines + ", regex=" + regex + '}';
    }

    public static Option createOption(String optionStr) {
        Matcher matcher = getMatcher(optionStr);
        if (matcher.find()) {
            return getOption(matcher);
        }
        return null;
    }

    public static List<Option> createOptions(String optionsStr) {
        Matcher matcher = getMatcher(optionsStr);
        ArrayList<Option> options = new ArrayList<Option>();
        while (matcher.find()) {
            options.add(getOption(matcher));
        }
        return options;
    }

    private static int strToIntDef(String value, int def) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
        }
        return def;
    }

    public int getIgnorableLines() {
        return ignorableLines;
    }

    public String getRegex() {
        return regex;
    }

    public Pattern pattern() {
        return Pattern.compile(regex);
    }

    public Matcher matcher(String string) {
        return pattern().matcher(string);
    }

    public int startIndex(String string) {
        Matcher matcher = matcher(string);
        if (matcher.find()) {
            return matcher.start();
        }
        return -1;
    }
}
