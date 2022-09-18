// $Id: View.java 0.1.1 2006/09/15 00:00:00 b00__1 Exp $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
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

package org.argouml.argoeclipse.internal.ui.views;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import org.argouml.argoeclipse.internal.core.model.Register;
import org.argouml.argoeclipse.internal.ui.util.SwingWrapper;

/**
 * Generic view.
 * @author Bogdan Pistol
 */
class View extends ViewPart {
    
    private Integer type;
    
    /**
     * Constructor
     * @param i the type of registered view
     */
    protected View(Integer i) {
        type = i;
    }

    /*
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        if (Register.getInstance().isRegistered(type)) {
            new Label(parent, SWT.NONE).setText(
                    ViewsMessages.warningMultipleViews);
        } else {
            Register.getInstance().register(type, this);
            JPanel jp = getPanel();
            if (jp != null) {
                SwingWrapper.wrap(jp, parent);
            } else {
                new Label(parent, SWT.NONE).setText(
                        ViewsMessages.warningNullPanel);
            }
        }
    }
    
    /**
     * The specific view panel. 
     * @return a JPanel
     */
    protected JPanel getPanel() {
        return null;
    }

    /*
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    public void setFocus() {
    }

    /**
     * Frees the resources.
     */
    public void dispose() {
        super.dispose();
        if (Register.getInstance().isRegistered(type, this)) {
            Register.getInstance().unregister(this);
        }
    }
    
}
