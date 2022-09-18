// $Id: NavigateTargetForwardAction.java,v 1.3 2006/09/14 05:24:19 tfmorris Exp $
// Copyright (c) 2002-2006 The Regents of the University of California. All
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

package org.argouml.argoeclipse.internal.ui.actions;

import org.eclipse.jface.resource.ImageDescriptor;

import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.i18n.Translator;
import org.argouml.ui.FindDialog;

/**
 * Action to bring up search/find dialog.
 */
public class ActionFind extends
        org.eclipse.jface.action.Action {

    private final static String ACTION_NAME = "find"; //$NON-NLS-1$

    /**
     * Constructor. The default style of the item will be AS_PUSH_BUTTON.
     */
    public ActionFind() {
        this( org.eclipse.jface.action.Action.AS_PUSH_BUTTON);
    }
    
    /**
     * Constructor.
     * @param style the style as in org.eclipse.jface.action.Action 
     */
    public ActionFind(int style) {
        super(ACTION_NAME, style);
        setImageDescriptor(ImageDescriptor.createFromFile(
                ResourceLoaderWrapper.class, ResourceLoaderWrapper
                        .getImageBinding("action." + ACTION_NAME))); //$NON-NLS-1$
        setToolTipText(Translator.localize("action." + ACTION_NAME)); //$NON-NLS-1$
    }
       
    /**
     * Runs the command.
     */
    public void run() {         
        super.run();
        FindDialog.getInstance().setVisible(true);
    }

}
