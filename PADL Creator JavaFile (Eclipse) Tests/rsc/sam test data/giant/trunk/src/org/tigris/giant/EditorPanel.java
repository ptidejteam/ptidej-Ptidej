package org.tigris.giant;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The panel containing the tree of open projects and targets.
 * @author Bob Tarling
 * @since 11-Feb-04
 */
public class EditorPanel extends JPanel {
    public EditorPanel() {
        setName("Target Editor");
        setLayout(new BorderLayout());
        add(new JButton("<html>This panel<br>will be an<br>XML editor<br>of the<br>selected<br>target</html>"));
    }
}
