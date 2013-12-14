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
//        String string = "frmVendaFrenteLoja.sbtnF11.Enabled := false;";
//        Pattern compile = Pattern.compile("( ?)(?:[\\w\\.]+?)(?: ?)(\\:=)(?: *)\\w*");
        String string = "java.lang.String nome = \"Rachid\";Integer idade  =24;nome = \"Irineu\";";
        Pattern compile = Pattern.compile("(?:(?<type>[\\w\\.]+)(?:\\s))?(\\w+)(?:\\s*=\\s*)([\\w\"]+);?");
//        Pattern compile = Pattern.compile("(?:(?:[\\w\\._](?: +))?[\\w\\._]( *)(=))");
        Matcher matcher = compile.matcher(string);
        while (matcher.find()) {
            System.out.println("-");
            System.out.println("tipo: " + matcher.group("type"));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                
                System.out.printf("%3s - %3s = \"%s\"\n", i, matcher.start(i), matcher.group(i));
            }
        }
        Option option = Option.createOption("<=>");
//        System.out.println(option.startIndex(string));
    }
}
