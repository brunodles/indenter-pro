/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenter.model;

import java.util.ArrayList;
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
        List<Block> blocks = new ArrayList<Block>();
        for (Option option : options) {
            List<Block> optionBlocks = findBlocks(lines, option);
            if (optionBlocks != null && !optionBlocks.isEmpty()) {
                blocks.addAll(optionBlocks);
            }
        }
        for (Block block : blocks) {
            indentBlock(block.lines, block.option);
        }
        return Line.joinLines(lines, firstsBlankCharacters);
    }

    private List<Block> findBlocks(List<Line> lines, Option option) {
        List<Block> result = new ArrayList<Block>();
        Block block = null;
        int errosCount = 0;
        for (Line line : lines) {
            if (option.matcher(line.value).find()) {
                errosCount = 0;
                if (block == null) {
                    block = new Block(option);
                    result.add(block);
                }
                block.addLine(line);
            } else if ((++errosCount > option.ignorableLines) && (block != null)) {
                result.add(block);
                block = null;
            }
        }
        return result;
    }

    public void indentBlock(List<Line> lines, Option option) {
        int maxCharacterPosition = findMaxCharacterPosition(lines, option);
        for (Line line : lines) {
            String string = line.value.toString();
            int characterPosition = option.startIdentableGroupIndex(string);
            int spacePosition = option.startSpaceGroupIndex(string);
            if ((characterPosition > 0) && (characterPosition < maxCharacterPosition)) {
                line.value.insert(spacePosition, fillString(' ', maxCharacterPosition - characterPosition));
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
            int position = option.startIdentableGroupIndex(line.value.toString());
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
