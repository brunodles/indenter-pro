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
public class Block {

    List<Line> lines = new ArrayList<Line>();
    Option option;

    public Block(Option option) {
        this.option = option;
    }

    public void addLine(Line line) {
        lines.add(line);
    }
}
