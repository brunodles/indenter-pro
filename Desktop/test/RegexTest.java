/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import indenter.model.Option;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bruno
 */
public class RegexTest {

    public static void main(String[] args) {
        String string = "<!teste><3#olha><simples><=>";
        Pattern compile = Pattern.compile("<(?:(?:(\\d+)\\#)|(\\!))?(.+?)>");
        Matcher matcher = compile.matcher(string);
        while (matcher.find()) {
            System.out.println("-");
            for (int i = 1; i <= matcher.groupCount(); i++) {
                
                System.out.printf("%3s - %3s = \"%s\"\n", i, matcher.start(i), matcher.group(i));
            }
        }
        Option.createOptionList(string);
//        Option option = Option.createOption("<=>");
//        System.out.println(option.startIndex(string));
    }
}
