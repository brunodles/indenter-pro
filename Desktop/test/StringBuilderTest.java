/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bruno
 */
public class StringBuilderTest {

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append("Bruno  Lima");
        builder.insert(6, "de");
        System.out.println(builder.toString());
    }
}
