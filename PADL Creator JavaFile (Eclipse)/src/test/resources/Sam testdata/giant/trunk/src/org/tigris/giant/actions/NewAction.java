package org.tigris.giant.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.apache.log4j.Logger;

import org.tigris.giant.Giant;

public class NewAction extends AbstractAction {
    
    private static final Logger LOG = Logger.getLogger(NewAction.class);
    
    public NewAction() {
        super("New");
    }

    public void actionPerformed(ActionEvent ae) {
        Giant.getInstance().newDiagram();
    }
    
} /* end class NewAction */
