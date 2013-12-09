/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenter.model;

import indenter.model.Option;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bruno
 */
public class Indenter {

    List<Option> options;

    public Indenter(String optionsStr) {
        options = Option.createOptionList(optionsStr);
    }

    public String indent(String string) {
        List<String> lines = splitStringAsList(string);
        String firstsBlankCharacters = firstsBlankCharacters(lines);
        System.out.println("firstsBlankCharacters {" + firstsBlankCharacters + "}");
        fixLines(lines);

        for (Option option : options) {
            indentBlock(lines, option);
        }
        return joinLines(lines, firstsBlankCharacters);
    }

    public String joinLines(List<String> lines, String blankCharacters) {
        String result = "";
        for (String string : lines) {
            result += blankCharacters + string + "\n";
        }
        return result;
    }

    public void indentBlock(List<String> lines, Option option) {
        int maxCharacterPosition = findMaxCharacterPosition(lines, option);
        for (int i = 0; i < lines.size(); i++) {
            String string = lines.get(i);
            String line = "";
            int characterPosition = option.startIndex(string);
            if ((characterPosition > 0) && (characterPosition < maxCharacterPosition)) {
                line += (string.substring(0, characterPosition));
                line += (fillString(' ', maxCharacterPosition - characterPosition));
                line += (string.substring(characterPosition));
            } else {
                line += (string);
            }
            lines.set(i, line);

        }
    }

    public void fixLines(List<String> lines) {
        final int linesSize = lines.size();
        for (int i = 0; i < linesSize; i++) {
//             remove os caracteres brancos e adiciona '\n' no final da linha, caso não seja a ultima
//            lines.set(i, removeBlankCharacters(lines.get(i)) + (i + 1 < linesSize ? '\n' : ""));
            lines.set(i, removeBlankCharacters(lines.get(i).trim()));
        }
    }

    public String fillString(char aChar, int size) {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < size) {
            builder.append(aChar);
        }
        return builder.toString();
    }

    private String linesToString(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        for (String string : lines) {
            builder.append(string);
        }
        return builder.toString();
    }

    public int findMaxCharacterPosition(List<String> lines, Option option) {
        int maxPosition = 0;
        for (String string : lines) {
            int position = option.startIndex(string);
            if (position > maxPosition) {
                maxPosition = position;
            }
        }
        return maxPosition;
    }

    public int countMaxFirstsBlankCharacters(List<String> lines) {
        int maxCount = 0;
        for (String string : lines) {
            int count = countFirstsBlankCharacters(string);
            if (count > maxCount) {
                maxCount = count;
            }
        }
        return maxCount;
    }

    /**
     * Replace extra black characters
     *
     * @param string
     * @return
     */
    public String removeBlankCharacters(String string) {
        return string.replaceAll("[\\p{Blank}]++", " ");
    }

    /**
     * Remove firts blank characters
     *
     * @param string
     * @return
     */
    public String firstsBlankCharacters(String string) {
        Matcher matcher = Pattern.compile("([\\p{Blank}]++).*+").matcher(string);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }

    public String firstsBlankCharacters(List<String> lines) {
        int lineIndex = 0;
        int maxCount = 0;
        for (int i = 0; i < lines.size(); i++) {
            int count = countFirstsBlankCharacters(lines.get(i));
            if (count > maxCount) {
                maxCount = count;
                lineIndex = i;
            }
        }
        return firstsBlankCharacters(lines.get(lineIndex));
    }

    /**
     * Count firts blank characters<br/>
     * " String aString;"<br/>
     * This string will return 1;<br/>
     *
     * @param string
     * @return
     */
    public int countFirstsBlankCharacters(String string) {
        return firstsBlankCharacters(string).length();
    }

    /**
     * Splits one String in a list of String.
     *
     * @param string
     * @return
     */
    public List<String> splitStringAsList(String string) {
        return Arrays.asList(string.split("[\r\n]"));
    }

    private String getLineSeparetor() {
        return System.getProperty("line.separator");
    }
}
