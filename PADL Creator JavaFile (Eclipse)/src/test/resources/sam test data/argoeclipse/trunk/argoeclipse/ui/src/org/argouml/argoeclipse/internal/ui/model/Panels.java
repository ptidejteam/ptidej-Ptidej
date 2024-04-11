// $Id: Panels.java 0.1.1 2006/09/15 00:00:00 b00__1 Exp $
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

package org.argouml.argoeclipse.internal.ui.model;

import javax.swing.JPanel;

import org.argouml.ui.ProjectBrowser;

/**
 * Brings JPanels from ArgoUML.
 * 
 * @author Bogdan Pistol
 */
public class Panels {

    // any panel asked should first initialize, so this doesn't matter if it's
    // static and loaded when we first access the class (vs lazy init)
    static {
        InitUI.initialize();
    }
    
    /**
     * Getter for the diagram editor.
     * 
     * @return the editor panel
     */
    public static JPanel getEditorPanel() {
        if (!InitUI.isInitialized()) {
            return null;
        }
        return ProjectBrowser.getInstance().getEditorPane();
    }

    /**
     * Getter for the todo view.
     * 
     * @return the todo panel
     */
    public static JPanel getToDoPanel() {
        if (!InitUI.isInitialized()) {
            return null;
        }
        return ProjectBrowser.getInstance().getTodoPane();
    }

    /**
     * Getter for the details pane.
     * 
     * @return the details panel
     */
    public static JPanel getDetailsPanel0() {
        if (!InitUI.isInitialized()) {
            return null;
        }
        return (JPanel) ProjectBrowser.getInstance().getDetailsPane();
    }

    /**
     * Getter for the project browser panel.
     * 
     * @return the browser panel
     */
    public static JPanel getExplorerPanel() {
        if (!InitUI.isInitialized()) {
            return null;
        }
        return ProjectBrowser.getInstance().getExplorerPane();
    }

}
