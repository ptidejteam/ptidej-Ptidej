package org.tigris.giant;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The panel containing the todo list
 * @author Bob Tarling
 * @since 11-Feb-04
 */
public class TodoPanel extends JPanel {
    public TodoPanel() {
        setName("Todo/Critics");
        setLayout(new BorderLayout());
        add(new JButton("<html>This panel will show<br>the todo items and<br>critics</html>"));
    }
}
