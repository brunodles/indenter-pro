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

    public String indent(String string, char[] chars) {
        String result = string;
        for (char c : chars) {
            result = indentBlock(result, c);
        }
        return result;
    }

    public String indentBlock(String block, char aChar) {
        List<String> lines = splitStringAsList(block);
//        String firstsBlankCharacters = firstsBlankCharacters(lines);
        fixLines(lines);
        int maxCharacterPosition = findMaxCharacterPosition(lines, aChar);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            String string = lines.get(i);
            if (string.isEmpty()) {
                continue;
            }

            int characterPosition = string.indexOf(aChar);
            if ((characterPosition > 0) && (characterPosition < maxCharacterPosition)) {
//                builder.append(firstsBlankCharacters);
                builder.append(string.substring(0, characterPosition));
                builder.append(fillString(' ', maxCharacterPosition - characterPosition));
                builder.append(string.substring(characterPosition));
//                if (lines.size() < i) {
//                    builder.append('\n');
//                    builder.appendCodePoint('\n');
//                }
            } else {
                builder.append(string);
            }

        }
        return builder.toString();
    }

    public void fixLines(List<String> lines) {
        final int linesSize = lines.size();
        for (int i = 0; i < linesSize; i++) {
//             remove os caracteres brancos e adiciona '\n' no final da linha, caso não seja a ultima
            lines.set(i, removeBlankCharacters(lines.get(i)) + (i + 1 < linesSize ? '\n' : ""));
            lines.set(i, removeBlankCharacters(lines.get(i)));
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

    public int findMaxCharacterPosition(List<String> lines, char aChar) {
        int maxPosition = 0;
        for (String string : lines) {
            int position = string.indexOf(aChar);
            maxPosition |= position;
        }
        return maxPosition;
    }

    public int countMaxFirstsBlankCharacters(List<String> lines) {
        int count = 0;
        for (String string : lines) {
            count |= countFirstsBlankCharacters(string);
        }
        return count;
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
