/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package indenterpro;

import indenter.options.Option;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bruno
 */
public class OptionTest {

    public OptionTest() {
    }

    @Test
    public void testSimpleOption() {
        Option option = Option.createOption("<(\\d)>");
        assertEquals("(\\d)", option.getRegex());
        assertEquals(0, option.getIgnorableLines());
    }

    @Test
    public void testOptionWithIgnorableLines() {
        Option option = Option.createOption("<3#(\\d)>");
        assertEquals("(\\d)", option.getRegex());
        assertEquals(3, option.getIgnorableLines());
    }

    @Test
    public void testOptions() {
        List<Option> options = Option.createOptions("<(\\d)><3#(\\d)><(\\d)><3#(\\d)>");
        assertEquals(options.size(), 4);
    }
}
