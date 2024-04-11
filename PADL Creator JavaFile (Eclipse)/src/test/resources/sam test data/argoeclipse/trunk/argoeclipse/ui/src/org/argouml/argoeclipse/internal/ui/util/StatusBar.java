// $Id: StatusBar.java 0.1.1 2006/09/17 00:00:00 b00__1 Exp $
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

package org.argouml.argoeclipse.internal.ui.util;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import org.argouml.argoeclipse.internal.core.model.Register;

/**
 * Utilities for status bar.
 * <p>
 * These methods are connected with the Diagram Editor, so if it isn't
 * opened the medthods will have no effect.
 * @author Bogdan Pistol
 */
public class StatusBar {
    
    /**
     * How much time to show the message in miliseconds.
     */
    private static final int DELAY = 2000;
    
    /**
     * Displays a message on the status bar for a short time.
     * @param msg the message to show
     */
    public static void show(String msg) {
        setMsg(msg);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                }
                setMsg(""); //$NON-NLS-1$
            }
        }).start();
    }
    
    private static void setMsg(final String msg) {
        Display.getDefault().syncExec(new Runnable() {
            public void run() {
                if (!Register.getInstance().isRegistered(Register.EDITOR)) {
                    return;
                }
                IWorkbenchPage page = PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow().getActivePage();
                IEditorPart ed = (IEditorPart) Register.getInstance()
                    .getRegistered(Register.EDITOR);
                // activate the editor first, because
                // we set the status line from the
                // editor and it is visible only if the
                // editor is active
                if (!msg.equals("")) { //$NON-NLS-1$
                    page.activate(ed);
                }
                ed.getEditorSite().getActionBars().getStatusLineManager()
                    .setMessage(msg);
            }
        });        
    }

}
