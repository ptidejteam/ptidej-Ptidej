package org.tigris.giant;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The panel containing the console output
 * @author Bob Tarling
 * @since 11-Feb-04
 */
public class ConsolePanel extends JPanel {
    public ConsolePanel() {
        setName("Console");
        setLayout(new BorderLayout());
        add(new JButton("<html>This panel will show<br>the console output<br>of running ant</html>"));
    }
}
