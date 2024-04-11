package org.tigris.giant.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import org.apache.log4j.Logger;

import org.tigris.giant.Giant;

public class OpenAction extends AbstractAction {
    
    private JComponent component;
    
    private static final Logger LOG = Logger.getLogger(OpenAction.class);
    
    public OpenAction(JComponent component) {
        super("Open...");
        this.component = component;
    }

    public void actionPerformed(ActionEvent ae) {
        Giant.getInstance().openDiagram();
    }
    
} /* end class CmdOpen */
