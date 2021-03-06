/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 * @author Bruno
 */
public class LineMatcher {

    private Line line;
//    private Matcher matcher;
    private List<Match> matches;
    private Option option;

    public LineMatcher(Line line, Option option) {
        this.line = line;
        this.option = option;
        buildMatcher();
//        removeSpacesOnSpaceGroup();
    }

    StringBuilder value() {
        return line.value();
    }

    public int spaceIndex(CharSequence string) {
        return startIndex(option.spaceGroupIndex);
    }

    public int startIdentableGroupIndex() {
        int result = startIndex(option.identableGroupIndex);
        if (result < 0) {
            result = startIndex(option.spaceGroupIndex);
        }
        return result;
    }

    public int startSpaceGroupIndex() {
        return startIndex(option.spaceGroupIndex);
    }

    public void insert(int offset, String str) {
        line.insert(offset, str);
    }

    void rebuild() {
        line.rebuild();
        buildMatcher();
    }

    private int startIndex(int group) {
        buildMatcher();
        return matches.get(0).start(group);
    }

    private int matchersCount() {
        return matches.size();
    }

    private boolean buildMatcher() {
        Matcher matcher = option.matcher(line);
        matches = Match.fromMatcher(matcher);
        return !matches.isEmpty();
    }

    String prefix() {
        return line.prefix();
    }

    public int prefixLength() {
        return line.prefixLength();
    }

    @Deprecated
    private void removeSpacesOnSpaceGroup() {
////        buildMatcher();
//        String spaceGroup = matcher.group(option.spaceGroupIndex);
//        if (spaceGroup.replaceAll("[\\p{Blank}]++", "").isEmpty()) {
//            int start = matcher.start(option.spaceGroupIndex);
//            int end = matcher.end(option.spaceGroupIndex);
//            value().replace(start, end, " ");
//        }
////        buildMatcher();
    }

    @Override
    public String toString() {
        return line.toString();
    }
}
