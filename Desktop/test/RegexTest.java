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
        String string = "123456=89";
        Pattern compile = Pattern.compile("=");
        Matcher matcher = compile.matcher(string);
        if (matcher.find()) {
            System.out.println(matcher.start());
        }
        Option option = Option.createOption("<=>");
        System.out.println(option.startIndex(string));
    }
}
