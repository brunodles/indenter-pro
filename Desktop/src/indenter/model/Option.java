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

    // ( ?) space group.
    public static final String OPTION_REGEX = "<(?:(\\d+)\\#)?(.+?)>";
    int ignorableLines = 0;
    String regex;
    Pattern pattern;
    int spaceGroupIndex;
    int identableGroupIndex;

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

    public int spaceIndex(String string) {
        Matcher matcher = matcher(string);
        if (matcher.find()) {
            return matcher.start(spaceGroupIndex);
        }
        return -1;
    }

    public int startIdentableGroupIndex(String string) {
        int result = startIndex(string, identableGroupIndex);
        if (result < 0) {
            result = startIndex(string, spaceGroupIndex);
        }
        return result;
    }

    public int startSpaceGroupIndex(String string) {
        return startIndex(string, spaceGroupIndex);
    }

    private int startIndex(String string, int group) {
        Matcher matcher = matcher(string);
        if (matcher.find()) {
            try {
                return matcher.start(group);
            } catch (Exception e) {
            }
        }
        return -1;
    }

//    public String applyOption(String line){
//        
//    }
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
        int spaceGroupIndex = findSpaceGroupIndex(regex);
        int identableGroupIndex = findIdentableGroupIndex(regex);
        if (identableGroupIndex < 0) {
            identableGroupIndex = spaceGroupIndex;
        }

        if ((identableGroupIndex >= 0) && (spaceGroupIndex >= 0)) {
            if (spaceGroupIndex == identableGroupIndex) {
                spaceGroupIndex = 1;
                identableGroupIndex = 1;
            } else if (spaceGroupIndex > identableGroupIndex) {
                spaceGroupIndex = 2;
                identableGroupIndex = 1;
            } else {
                spaceGroupIndex = 1;
                identableGroupIndex = 2;
            }
        } else {
            if (spaceGroupIndex < 0) {
                spaceGroupIndex = 0;
            }
            if (identableGroupIndex < 0) {
                identableGroupIndex = 0;
            }
        }
        option.spaceGroupIndex = spaceGroupIndex;
        option.identableGroupIndex = identableGroupIndex;
        return option;
    }

    private static int findSpaceGroupIndex(String regex) {
        return regex.indexOf("( ?)");
    }

    private static int findIdentableGroupIndex(String regex) {
        Pattern pattern = Pattern.compile("\\((.+?)\\)");
        Matcher matcher = pattern.matcher(regex);
        while (matcher.find()) {
            final String group = matcher.group(1);
            if ((!" ?".equals(group)) && (!group.startsWith("?:"))) {
                return matcher.start(1);
            }
        }
        return -1;
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
