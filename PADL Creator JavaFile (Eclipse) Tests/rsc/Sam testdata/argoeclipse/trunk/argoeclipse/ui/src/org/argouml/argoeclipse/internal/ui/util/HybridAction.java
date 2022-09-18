// $Id: HybridAction.java 2006/06/28 00:00:00 b00__1 $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.argoeclipse.internal.ui.util;

import java.awt.event.ActionEvent;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;

import org.argouml.application.helpers.ResourceLoaderWrapper;

/**
 * This class transforms a Swing Action in a JFace Action.
 * @author Bogdan Pistol
 */
public class HybridAction extends org.eclipse.jface.action.Action {

    private javax.swing.Action swingAction;
    
    private ActionEvent actionEvent;
    
    /**
     * Constructor. The default style of the item will be AS_PUSH_BUTTON. 
     * ActionEvent and ImagePath is null.
     * 
     * @param action swing action
     */
    public HybridAction(javax.swing.Action action) {
        this(action, null);
    }
    
    /**
     * Constructor. The default style of the item will be AS_PUSH_BUTTON.
     * ActionEvent is null.
     * 
     * @param action swing action
     * @param imagePath the path to the image for this item
     */
    public HybridAction(javax.swing.Action action, String imagePath) {
        this(action, null, org.eclipse.jface.action.Action.AS_PUSH_BUTTON,
                imagePath);
    }
    
    /**
     * Constructor.
     * @param action swing action
     * @param event swing event
     * @param style the style as in org.eclipse.jface.action.Action 
     * @param iconName the name of the image for this item
     */
    public HybridAction(final javax.swing.Action action, ActionEvent event,
            int style, String iconName) {
        super((String) action.getValue(javax.swing.Action.NAME), style);
        swingAction = action;
        actionEvent = event;
        setEnabled(action.isEnabled());
        Object o = action.getValue("SELECTED");         //$NON-NLS-1$
        setChecked(o != null ? ((Boolean) o).booleanValue() : true);
        o = action.getValue(javax.swing.Action.SHORT_DESCRIPTION);
        if (o instanceof String) {
            setToolTipText(((String) o).toString()); // + " ");
        }
        if (iconName != null) {
            URL imageUrl = ResourceLoaderWrapper.lookupIconUrl(iconName);
            if (imageUrl != null) {
                setImageDescriptor(ImageDescriptor.createFromURL(imageUrl));
            }
        }
    }
       
    /**
     * Runs the command.
     */
    public void run() {         
        super.run();
        Display.getDefault().syncExec(new Runnable() {
            public void run() {
                swingAction.actionPerformed(actionEvent);
            }
        });
    }
    
    /**
     * Getter for the swing action.
     * @return the swing action
     */
    public javax.swing.Action getSwingAction() {
        return swingAction;
    }
    
}