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
public class Match {

    List<MatchGroup> groups = new ArrayList<MatchGroup>();

    public int start(int group) {
        return group(group).startIndex;
    }

    public int end(int group) {
        return group(group).endIndex;
    }

    private MatchGroup group(int group) {
        return groups.get(group - 1);
    }

    public static List<Match> fromMatcher(Matcher matcher) {
        ArrayList<Match> result = new ArrayList<Match>();
        while (matcher.find()) {
            Match match = Match.buildMatchGroups(matcher);
            result.add(match);
        }
        return result;
    }

    private static Match buildMatchGroups(Matcher matcher) {
        Match match = new Match();
        for (int i = 1; i <= matcher.groupCount(); i++) {
            MatchGroup matchGroup = new MatchGroup();
            matchGroup.group = i;
            matchGroup.startIndex = matcher.start(i);
            matchGroup.endIndex = matcher.end(i);
            match.groups.add(matchGroup);
        }
        return match;
    }
}
