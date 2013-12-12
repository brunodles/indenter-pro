/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenter.model;

import java.util.regex.Matcher;

/**
 *
 * @author Bruno
 */
public class LineMatcher {

    private Line line;
    private Matcher matcher;
    private Option option;

    public LineMatcher(Line line, Option option) {
        this.line = line;
        this.option = option;
        buildMatcher();
//        removeSpacesOnSpaceGroup();
    }

    private StringBuilder value() {
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
        line.value().insert(offset, str);
    }

    private int startIndex(int group) {
//        buildMatcher();
        return matcher.start(group);
    }

    private boolean buildMatcher() {
        matcher = option.matcher(line.value());
        return matcher.find();
    }

    private void removeSpacesOnSpaceGroup() {
//        buildMatcher();
        String spaceGroup = matcher.group(option.spaceGroupIndex);
        if (spaceGroup.replaceAll("[\\p{Blank}]++", "").isEmpty()) {
            int start = matcher.start(option.spaceGroupIndex);
            int end = matcher.end(option.spaceGroupIndex);
            value().replace(start, end, " ");
        }
//        buildMatcher();
    }
}
