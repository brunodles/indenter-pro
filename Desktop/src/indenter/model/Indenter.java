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
            if (option.matches(line)) {
                errosCount = 0;
                if (block == null) {
                    block = new Block(option);
                    result.add(block);
                }
                block.addLine(line);
            } else if ((++errosCount > option.ignorableLines) && (block != null)) {
                block = null;
            }
        }
        return result;
    }

    public void indentBlock(List<LineMatcher> lines, Option option) {
        int maxCharacterPosition = findMaxCharacterPosition(lines, option);
        System.out.println("maxCharacterPosition = " + maxCharacterPosition);
        for (LineMatcher line : lines) {
            int characterPosition = line.startIdentableGroupIndex();
            String before = String.format("            [%s]\n", line.value());
            System.out.print(" characterPosition = " + characterPosition);
            int spacePosition = line.startSpaceGroupIndex();
            if ((characterPosition > 0) && (characterPosition < maxCharacterPosition)) {
                line.insert(spacePosition, fillString(' ', maxCharacterPosition - characterPosition));
            }
            // depois de adicionar os espaços o indice do grupo editável 
            // deve ser o mesmo do indice máximo
            line.rebuild();
            System.out.println(" > " + line.startIdentableGroupIndex());
            System.out.println(before);
            System.out.printf("            [%s]\n", line.value());
        }
    }

    public String fillString(char aChar, int size) {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < size) {
            builder.append(aChar);
        }
        return builder.toString();
    }

    public int findMaxCharacterPosition(List<LineMatcher> lines, Option option) {
        int maxPosition = 0;
        for (LineMatcher line : lines) {
            int position = line.startIdentableGroupIndex();
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
            int count = lines.get(i).prefixLength();
            if (count < minCount) {
                minCount = count;
                lineIndex = i;
            }
        }
        return lines.get(lineIndex).prefix();
    }
}
