// $Id: SwingWrapper.java 2006/06/22 00:00:00 b00__1 $
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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

/**
 * This is a utility class used by views and the editor for common
 * wrapping AWT/Swing in SWT.
 * 
 * @author Bogdan Pistol
 */
public class SwingWrapper {
    
    /**
     * The wrapper.
     * 
     * @param swingPanel Swing component
     * @param parent SWT component
     * @return a JPanel layer placed between the composite parent and awtPanel
     */
    public static JPanel wrap(JPanel swingPanel, Composite parent) {
        Composite composite = new Composite(parent, SWT.EMBEDDED);
        Frame frame = SWT_AWT.new_Frame(composite);
        Panel panel = new Panel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);
        JPanel rootJPanel = new JPanel();
        rootJPanel.setLayout(new BorderLayout());
        panel.add(rootJPanel);

        rootJPanel.add(swingPanel);
        return rootJPanel;
    }

}
