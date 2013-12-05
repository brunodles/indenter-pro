/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenterpro;

import indenter.options.Option;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bruno
 */
public class IndenterTest {

    Indenter indenter;

    public IndenterTest() {
        indenter = new Indenter("<=>");
    }

    @Test
    public void testRemoveBlankCharacters() {
        final String in = "String    name   = \"temp\";";
        final String outExpected = "String name = \"temp\";";
        String out = indenter.removeBlankCharacters(in);
        assertEquals(outExpected, out);
    }

    @Test
    public void testCountFirstsBlankCharacters() {
        final String in = "    String name   = \"temp\";"; //4
        assertEquals(4, indenter.countFirstsBlankCharacters(in));
    }

    @Test
    public void testFirstsBlankCharacters() {
        final String in = "        String name   = \"temp\";";
        assertEquals("        ", indenter.firstsBlankCharacters(in));
    }

    @Test
    public void testFillString() {
        final String outExpected = "   "; // 3
        assertEquals(outExpected, indenter.fillString(' ', 3));
    }

    @Test
    public void testLineBiggerInit() {
        final String in = "String testing =  \"testing\";\n"
                          + "String hello = \"hello\";";
        final String outExpected = "String testing = \"testing\";\n"
                                   + "String hello   = \"hello\";\n";
        Option option = Option.createOption("<=>");
        final String out = indenter.indentBlock(in, option);
        assertEquals(outExpected, out);
    }

    @Test
    public void testIndent() {
    }
}
