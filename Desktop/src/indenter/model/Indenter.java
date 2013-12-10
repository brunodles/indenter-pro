/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenter.model;

import java.util.List;

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
        List<Line> lines = Line.splitStringAsLineList(string);
        String firstsBlankCharacters = firstsBlankCharacters(lines);
//        System.out.println("firstsBlankCharacters {" + firstsBlankCharacters + "}");
//        fixLines(lines);

        for (Option option : options) {
            indentBlock(lines, option);
        }
        return joinLines(lines, firstsBlankCharacters);
    }

    public String joinLines(List<Line> lines, String blankCharacters) {
        String result = "";
        for (Line line : lines) {
            line.prefix = blankCharacters;
            result += line.toString();
        }
        return result;
    }

    public void indentBlock(List<Line> lines, Option option) {
        int maxCharacterPosition = findMaxCharacterPosition(lines, option);
        for (Line line : lines) {
            String string = line.value;
            String newValue = "";
            int characterPosition = option.startIdentableGroupIndex(string);
            int spacePosition = option.startSpaceGroupIndex(string);
            if ((characterPosition > 0) && (characterPosition < maxCharacterPosition)) {
                newValue += (string.substring(0, spacePosition));
                newValue += (fillString(' ', maxCharacterPosition - characterPosition));
                newValue += (string.substring(spacePosition));
                line.value = newValue;
            }

        }
    }

    public String fillString(char aChar, int size) {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < size) {
            builder.append(aChar);
        }
        return builder.toString();
    }

    public int findMaxCharacterPosition(List<Line> lines, Option option) {
        int maxPosition = 0;
        for (Line line : lines) {
            int position = option.startIdentableGroupIndex(line.value);
            if (position > maxPosition) {
                maxPosition = position;
            }
        }
        return maxPosition;
    }

    public int countMaxFirstsBlankCharacters(List<Line> lines) {
        return firstsBlankCharacters(lines).length();
    }

    public String firstsBlankCharacters(List<Line> lines) {
        int lineIndex = 0;
        int minCount = Integer.MAX_VALUE;
        for (int i = 0; i < lines.size(); i++) {
            int count = lines.get(i).prefix.length();
            if (count < minCount) {
                minCount = count;
                lineIndex = i;
            }
        }
        return lines.get(lineIndex).prefix;
    }
}
