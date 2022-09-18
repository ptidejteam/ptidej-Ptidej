package org.tigris.giant;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The panel containing the tree of open projects and targets.
 * @author Bob Tarling
 * @since 11-Feb-04
 */
public class ExplorerPanel extends JPanel {
    public ExplorerPanel() {
        setName("Project Explorer");
        setLayout(new BorderLayout());
        add(new JButton("<html>This panel<br>will be<br>the ANT<br>target<br>tree</html>"));
        
        setPreferredSize(getMinimumSize());
        setSize(getMinimumSize());
    }
}
