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
public class Option {

    public static final String OPTION_REGEX = "<(?:(\\d+)\\#)?(.+?)>";
    int ignorableLines = 0;
    String regex;
    Pattern pattern;

    public Pattern getPattern() {
        return pattern;
    }

    private Option() {
    }

    public int getIgnorableLines() {
        return ignorableLines;
    }

    public String getRegex() {
        return regex;
    }

    public Matcher matcher(String string) {
        return pattern.matcher(string);
    }

    public int startIndex(String string) {
        Matcher matcher = matcher(string);
        if (matcher.find()) {
            if (matcher.groupCount() > 0) {
                return matcher.start(1);
            }
            return matcher.start();
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Option{" + "ignorableLines=" + ignorableLines + ", regex=" + regex + '}';
    }

    // ===============================================
    //      Class Methods
    // ===============================================
    private static Matcher getStrOptionMatcher(String optionsStr) {
        Pattern pattern = Pattern.compile(OPTION_REGEX);
        Matcher matcher = pattern.matcher(optionsStr);
        return matcher;
    }

    private static Option findOption(Matcher matcher) {
        Option option = new Option();
        //            System.out.println(" count =" + matcher.groupCount());
        final String regex = matcher.group(2);
        option.ignorableLines = strToIntDef(matcher.group(1), 0);
        option.regex = regex;
        option.pattern = Pattern.compile(regex);
        return option;
    }

    public static Option createOption(String optionStr) {
        Matcher matcher = getStrOptionMatcher(optionStr);
        if (matcher.find()) {
            return findOption(matcher);
        }
        return null;
    }

    public static List<Option> createOptionList(String optionsStr) {
        Matcher matcher = getStrOptionMatcher(optionsStr);
        ArrayList<Option> options = new ArrayList<Option>();
        while (matcher.find()) {
            options.add(findOption(matcher));
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
}
